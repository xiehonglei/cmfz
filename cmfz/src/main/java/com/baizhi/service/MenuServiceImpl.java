package com.baizhi.service;

import com.baizhi.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override

    public Map getAll() {
        Map map = new HashMap();
        map.put("menulist", menuDao.getAll());

        return map;
    }
}
