package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.entity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Map getWeeks() {
        Map map = new HashMap();
        int one = userDao.getOneWeekUser();
        int two = userDao.getTwoWeekUser();
        int three = userDao.getThreeWeekUser();
        int[] count = {one, two, three};
        String[] weeks = {"前一周", "前两周", "前三周"};
        map.put("count", count);
        map.put("weeks", weeks);
        return map;
    }

    @Override
    public Map getMaps() {
        Map map = new HashMap();
        List<UserDto> manList = userDao.getManMap();
        List<UserDto> womenList = userDao.getWomenMap();
        map.put("man", manList);
        map.put("women", womenList);
        return map;
    }

    @Override
    public Map getAll(int page, int rows) {
        int start = (page - 1) * rows;
        int total = userDao.getCount();
        int pagesize = rows;
        Map map = new HashMap();
        map.put("total", total);
        map.put("rows", userDao.getAll(start, pagesize));
        return map;
    }

    @Override
    public List<User> getUser() {
        return userDao.getUser();
    }

    @Override
    public void add(List<User> userList) {
        userDao.add(userList);
    }

}
