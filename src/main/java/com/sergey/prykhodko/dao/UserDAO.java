package com.sergey.prykhodko.dao;

import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.UserSearchCriteria;

import java.util.List;

public interface UserDAO {

    void add(User user);

    void update(User user);

    void delete(User user);

    User getByLogin(String login);

    List<User> getAll();

    List<User> findByCriteria(UserSearchCriteria criteria);

    List<? extends User> getPortion(long first, long count);

    long getCount();
}
