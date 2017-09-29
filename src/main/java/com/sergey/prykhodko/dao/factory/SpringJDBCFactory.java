package com.sergey.prykhodko.dao.factory;

import com.sergey.prykhodko.dao.UserDAO;
import com.sergey.prykhodko.dao.implementation.UserDAOSpringJDBC;

import java.sql.SQLException;

public class SpringJDBCFactory implements FactoryDAO {
    @Override
    public UserDAO getUserDAO() throws SQLException {
        return new UserDAOSpringJDBC();
    }
}
