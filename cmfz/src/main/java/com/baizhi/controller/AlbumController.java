package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Controller
public class AlbumController {
    @Autowired
    private AlbumServcie albumServcie;

    @RequestMapping("/getAllAlbum")
    public @ResponseBody
    Map getAllAlbum(int page, int rows) {
        return albumServcie.getAll(page, rows);
    }

    @RequestMapping("/addAlbum")
    public @ResponseBody
    boolean addAlbum(Album album, MultipartFile myjar, HttpServletRequest request) {
        try {
            String fileName = myjar.getOriginalFilename();
            fileName = new Date().getTime() + "_" + fileName;
            String realPath = request.getRealPath("/album/");
            String imagePath = "/album/" + fileName;
            album.setCoverimg(imagePath);
            albumServcie.add(album);
            myjar.transferTo(new File(realPath + "\\" + fileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
