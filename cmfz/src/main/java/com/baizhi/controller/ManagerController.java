package com.baizhi.controller;

import com.baizhi.entity.Manager;
import com.baizhi.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @RequestMapping("/login")
    public String login(String name, String password, String kaptcha, HttpSession session) {
        String sessionKaptcha = (String) session.getAttribute("kaptcha");
        session.removeAttribute(kaptcha);
        if (sessionKaptcha.equalsIgnoreCase(kaptcha)) {
            Manager manager = managerService.queryOne(name, password);
            if (manager != null) {
                session.setAttribute("manager", manager);
                return "redirect:/main/main.jsp";
            } else {
                return "login";
            }
        } else {
            return "login";
        }
    }

    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("manager");
        return "login";
    }
}
