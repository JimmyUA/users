package com.sergey.prykhodko.front.utils;

import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import org.apache.log4j.Logger;
import org.apache.wicket.model.LoadableDetachableModel;

import java.sql.SQLException;

public class DetachableUserModel extends LoadableDetachableModel<User> {

    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    private String login;

    public DetachableUserModel(User user) {
        this.login = user.getLogin();
    }

    @Override
    protected User load() {
        try {
            return new UserManager().getUserByID(login);
        } catch (SQLException e) {
            logger.error(e);
            return null;
        }
    }
}

