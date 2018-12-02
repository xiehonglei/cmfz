package com.baizhi.controller;

import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import com.baizhi.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.UUID;

@Controller
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/addChapter")
    public @ResponseBody
    boolean addChapter(MultipartFile chapter, Chapter chapterr, HttpServletRequest request) {
        try {
            String realPath = request.getSession().getServletContext().getRealPath("/");  //webapp当前项目的路径
            File file = new File(realPath + "/audio");
            if (!file.exists()) {
                file.mkdir();
            }
            String extension = FilenameUtils.getExtension(chapter.getOriginalFilename());
            UUID uuid = UUID.randomUUID();
            String s = uuid.toString();
            String newName = s + "." + extension;
            chapter.transferTo(new File(file.getAbsolutePath(), newName));
            int length = FileUtil.getDuration(new File(file.getAbsolutePath() + "/" + newName));
            long size = chapter.getSize();
            double l = size / 1024.0 / 1024;
            DecimalFormat df = new DecimalFormat(".00");
            chapterr.setId(s);
            chapterr.setSize(df.format(l) + "M");
            String timelength = length / 60 + "分" + length % 60 + "秒";
            chapterr.setDuration(timelength);
            chapterr.setDownPath("/audio/" + newName);
            chapterService.add(chapterr);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/downloadCapter")
    public void downloadCapter(String url, String title, HttpServletRequest request, HttpServletResponse response) {
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String path = realPath + url;
        File file = new File(path);
        int i = url.indexOf(".");
        url = url.substring(i);
        String s = title + url;

        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(s, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("audio/mpeg");

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
