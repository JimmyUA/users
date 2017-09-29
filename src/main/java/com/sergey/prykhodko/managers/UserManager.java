package com.sergey.prykhodko.managers;

import com.sergey.prykhodko.dao.UserDAO;
import com.sergey.prykhodko.dao.factory.FactoryDAO;
import com.sergey.prykhodko.dao.factory.FactoryType;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import com.sergey.prykhodko.util.UserSearchCriteria;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class UserManager {
    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    public void addUser(User user, FactoryType factoryType) throws SQLException {
        UserDAO userDAO = getUserDAO(factoryType);
        userDAO.add(user);
    }

    public Iterator<? extends User> getContactsPortionIterator(long first, long count) throws SQLException {
        UserDAO userDAO = getUserDAO(FactoryType.SPRING);
        return userDAO.getPortion(first, count).iterator();
    }

    public User getUserByID(String login) throws SQLException {
        UserDAO userDAO = getUserDAO(FactoryType.SPRING);
        return userDAO.getByLogin(login);
    }

    public long getCount() throws SQLException {
        UserDAO userDAO = getUserDAO(FactoryType.SPRING);
        return userDAO.getCount();
    }

    public void deleteUser(User user, FactoryType factoryType) throws SQLException {
        UserDAO userDAO = getUserDAO(factoryType);
        userDAO.delete(user);
    }

    public void updateUser(User user, FactoryType factoryType) throws SQLException {
        UserDAO userDAO = getUserDAO(factoryType);
        userDAO.update(user);
    }

    public List<User> getUsersByCriteria(UserSearchCriteria criteria, FactoryType factoryType) throws SQLException {
        UserDAO userDAO = getUserDAO(factoryType);
        return userDAO.findByCriteria(criteria);
    }

    public boolean isUnique(String userLogin, FactoryType factoryType) {
        UserDAO userDAO;
        try {
            userDAO = getUserDAO(factoryType);
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        List<User> users = userDAO.getAll();
        return checkLoginUniqueness(userLogin, users);
    }

    private boolean checkLoginUniqueness(String userLogin, List<User> users) {
        for (User user : users
                ) {
            if (user.getLogin().equals(userLogin)) {
                return false;
            }
        }
        return true;
    }

    private UserDAO getUserDAO(FactoryType factoryType) throws SQLException {
        FactoryDAO factory = FactoryDAO.getFactory(factoryType);
        return factory.getUserDAO();
    }
}
