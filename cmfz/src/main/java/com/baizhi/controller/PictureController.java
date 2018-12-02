package com.baizhi.controller;

import com.baizhi.entity.Picture;
import com.baizhi.service.PictureService;
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
public class PictureController {
    @Autowired
    private PictureService pictureService;

    @RequestMapping("/getAllPicture")
    public @ResponseBody
    Map getAllPicture(int page, int rows) {
        return pictureService.getAll(page, rows);
    }

    @RequestMapping("/updatePicture")
    public @ResponseBody
    boolean updatePicture(Picture picture) {
        try {
            System.out.println(picture);
            pictureService.update(picture);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/deletePicture")
    public @ResponseBody
    boolean deletePicture(int id) {
        try {
            pictureService.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/addPicture")
    public @ResponseBody
    boolean addPicture(Picture picture, MultipartFile myjar, HttpServletRequest request) {
        try {
            String fileName = myjar.getOriginalFilename();
            fileName = new Date().getTime() + "_" + fileName;
            String realPath = request.getRealPath("/shouye/");

            String imagePath = "/shouye/" + fileName;
            picture.setImagePath(imagePath);
            System.out.println(picture);
            pictureService.add(picture);
            myjar.transferTo(new File(realPath + "\\" + fileName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
