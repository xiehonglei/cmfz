package com.baizhi.service;

import com.baizhi.dao.ManagerDao;
import com.baizhi.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    private ManagerDao managerDao;

    @Override
    public Manager queryOne(String name, String password) {
        return managerDao.queryOne(name, password);
    }
}
