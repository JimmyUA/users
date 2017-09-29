package com.sergey.prykhodko.dao.implementation;

import com.sergey.prykhodko.dao.UserDAO;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import com.sergey.prykhodko.util.DataSources;
import com.sergey.prykhodko.util.PasswordEncoder;
import com.sergey.prykhodko.util.UserSearchCriteria;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static com.sergey.prykhodko.util.SQLCommands.*;

public class UserDAOSpringJDBC implements UserDAO {
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";

    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedTemplate;

    public UserDAOSpringJDBC() throws SQLException {
        DataSource dataSource = DataSources.MY_SQL.getDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> rowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getInt(ID));
        user.setLogin(rs.getString(LOGIN));
        user.setPassword(rs.getString(PASSWORD));
        user.setEmail(rs.getString(EMAIL));
        user.setFirstName(rs.getString(FIRST_NAME));
        user.setLastName(rs.getString(LAST_NAME));
        return user;
    };

    @Override
    public void add(User user) {
        jdbcTemplate.update(INSERT, getAddPreparedStatementSetter(user));
        logger.info("User with login '" + user.getLogin() + "' is stored in DB");

    }

    private PreparedStatementSetter getAddPreparedStatementSetter(final User user) {
        return ps -> {
            int i = 1;
            ps.setString(i++, user.getLogin());
            ps.setString(i++, PasswordEncoder.encodePassword(user.getPassword()));
            ps.setString(i++, user.getEmail());
            ps.setString(i++, user.getFirstName());
            ps.setString(i, user.getLastName());
        };
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE, getUpdatePreparedStatementSetter(user));
        logger.info("User with login '" + user.getLogin() + "is updated in DB");
    }

    private PreparedStatementSetter getUpdatePreparedStatementSetter(final User user) {
        return ps -> {
            int i = 1;
            ps.setString(i++, PasswordEncoder.encodePassword(user.getPassword()));
            ps.setString(i++, user.getEmail());
            ps.setString(i++, user.getFirstName());
            ps.setString(i++, user.getLastName());
            ps.setInt(i, user.getId());
        };
    }

    @Override
    public void delete(User user) {
        jdbcTemplate.update(DELETE, user.getId());
        logger.info("User with login '" + user.getLogin() + "' is deleted from DB");
    }

    @Override
    public User getByLogin(String login) {
        return jdbcTemplate.queryForObject(GET_BY_LOGIN, rowMapper, login);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL, rowMapper);
    }

    @Override
    public List<User> findByCriteria(UserSearchCriteria criteria) {
        String request = GET_BY_CRITERIA;
        if (criteria.isEmpty()) {
            return getAll();
        }
        if (criteria.getId() != null) {
            request += " AND id=:id";
        }
        if (criteria.getLogin() != null) {
            request += " AND login=:login";
        }
        if (criteria.getEmail() != null) {
            request += " AND email=:email";
        }
        if (criteria.getFirstName() != null) {
            request += " AND first_name=:firstName";
        }
        if (criteria.getLastName() != null) {
            request += " AND last_name=:lastName";
        }
        BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(criteria);

        return namedTemplate.query(request, namedParameters, rowMapper);
    }

    @Override
    public List<? extends User> getPortion(long first, long count) {
        return jdbcTemplate.query(GET_PORTION, rowMapper, first, count);
    }

    @Override
    public long getCount() {
        return jdbcTemplate.queryForObject(GET_COUNT, Long.class);
    }
}
