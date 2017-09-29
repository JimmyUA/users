package com.sergey.prykhodko.front.utils;

import com.sergey.prykhodko.exception.DataNotFoundException;
import com.sergey.prykhodko.front.utils.DetachableUserModel;
import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;

public class UserDataProvider implements IDataProvider<User> {

    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());
    private static final String ERROR_MESSAGE = "Data was not found";

    @Override
    public Iterator<? extends User> iterator(long l, long l1) {
        try {
            return new UserManager().getContactsPortionIterator(l, l1);
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException(ERROR_MESSAGE);
        }
    }

    @Override
    public long size() {
        try {
            return new UserManager().getCount();
        } catch (SQLException e) {
           logger.error(e);
            throw new DataNotFoundException(ERROR_MESSAGE);

        }
    }

    @Override
    public IModel<User> model(User user) {
        return new DetachableUserModel(user);
    }

    @Override
    public void detach() {
        // don't need to be implemented
    }

}
