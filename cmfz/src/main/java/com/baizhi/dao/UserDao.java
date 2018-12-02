package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.entity.UserDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public int getOneWeekUser();

    public int getTwoWeekUser();

    public int getThreeWeekUser();

    public List<UserDto> getManMap();

    public List<UserDto> getWomenMap();

    public List<User> getAll(@Param("start") int start, @Param("pagesize") int pagesize);

    public int getCount();

    public List<User> getUser();

    public void add(List<User> userList);
}
