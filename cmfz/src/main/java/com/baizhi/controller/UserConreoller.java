package com.baizhi.controller;


import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserConreoller {
    @Autowired
    private UserService userService;

    @RequestMapping("/getWeekCount")
    public @ResponseBody
    Map getWeekCount() {
        return userService.getWeeks();
    }

    @RequestMapping("/getMap")
    public @ResponseBody
    Map getMap() {
        return userService.getMaps();
    }

    @RequestMapping("/getAllUser")
    public @ResponseBody
    Map getAllUser(int page, int rows) {
        return userService.getAll(page, rows);
    }

    @RequestMapping("/importUser")
    public @ResponseBody
    boolean importUser(MultipartFile userfile, HttpServletRequest request) {
        try {
            HSSFWorkbook workbook = null;
            //1.通过流生成动作簿excel
            workbook = new HSSFWorkbook(userfile.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            //获取数据
            //获取行
            //获取最后一行的下标
            int lastRowNum = sheet.getLastRowNum();
            List<User> userlist = new ArrayList<User>();
            for (int i = 0; i < lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i + 1);
                //获取第一个单元格中的数据
                HSSFCell cell = row.getCell(0);
                int id = (int) cell.getNumericCellValue();
                HSSFCell phoneNumcell = row.getCell(1);
                String phoneNum = phoneNumcell.getStringCellValue();

                HSSFCell usernamecell = row.getCell(2);
                String username = usernamecell.getStringCellValue();

                HSSFCell passwordcell = row.getCell(3);
                String password = passwordcell.getStringCellValue();

                HSSFCell saltcell = row.getCell(4);
                String salt = saltcell.getStringCellValue();
                HSSFCell dharmalNamecell = row.getCell(5);
                String dharmalName = dharmalNamecell.getStringCellValue();
                HSSFCell provincecell = row.getCell(6);
                String province = provincecell.getStringCellValue();
                HSSFCell citycell = row.getCell(7);
                String city = citycell.getStringCellValue();
                HSSFCell sexcell = row.getCell(8);
                String sex = sexcell.getStringCellValue();
                HSSFCell signcell = row.getCell(9);
                String sign = signcell.getStringCellValue();
                HSSFCell headPiccell = row.getCell(10);
                String headPic = headPiccell.getStringCellValue();
                HSSFCell statuscell = row.getCell(11);
                String status = statuscell.getStringCellValue();
                HSSFCell datecell = row.getCell(12);
                Date date = datecell.getDateCellValue();
                User user = new User();
                user.setId(id);
                user.setPhoneNum(phoneNum);
                user.setPassword(password);
                user.setSalt(salt);
                user.setDharmalName(dharmalName);
                user.setProvince(province);
                user.setCity(city);
                user.setSex(sex);
                user.setSign(sign);
                user.setHeadPic(headPic);
                user.setStatus(status);
                user.setDate(date);
                user.setUsername(username);
                userlist.add(user);
            }
            System.out.println(userlist);
            userService.add(userlist);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    @RequestMapping("/emportUser")
    public void emportUser(HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = userService.getUser();
        //1.创建工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一张工作表
        HSSFSheet user = workbook.createSheet("user");
        //设置日期格式
        HSSFCellStyle dateStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short formate = dataFormat.getFormat("yyyy-MM-dd");
        dateStyle.setDataFormat(formate);
        //3.填充用户数据
        for (int i = 0; i < userList.size(); i++) {
            User user1 = userList.get(i);
            HSSFRow row = user.createRow(i + 1);
            //1.获取类对象
            Class<? extends User> userClass = user1.getClass();
            //2.获取属性名的数组
            Field[] fields = userClass.getDeclaredFields();
            for (int j = 0; j < fields.length; j++) {
                //3.拼接出来get方法名
                String getName = "get" + fields[j].getName().substring(0, 1).toUpperCase() + fields[j].getName().substring(1);
                //4.调用get方法
                Object invoke = null;
                try {
                    invoke = userClass.getDeclaredMethod(getName, null).invoke(user1, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (invoke instanceof Date) {
                    HSSFCell cell = row.createCell(j);
                    cell.setCellStyle(dateStyle);
                    cell.setCellValue((Date) invoke);
                } else if (invoke instanceof Integer) {
                    row.createCell(j).setCellValue((Integer) invoke);
                } else {
                    row.createCell(j).setCellValue((String) invoke);
                }
            }
        }
        //4.文件输出到本地
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("F://xxxx.xls", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/vnd.ms-excel");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
