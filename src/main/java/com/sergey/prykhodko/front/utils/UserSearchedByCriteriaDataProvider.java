package com.sergey.prykhodko.front.utils;

import com.sergey.prykhodko.dao.factory.FactoryType;
import com.sergey.prykhodko.exception.DataNotFoundException;
import com.sergey.prykhodko.front.pages.user.lookup.SearchResultsPage;
import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import com.sergey.prykhodko.util.UserSearchCriteria;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class UserSearchedByCriteriaDataProvider implements IDataProvider<User> {
    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());
    private static final String ERROR_MESSAGE = "Data was not found";


    private UserSearchCriteria criteria;

    public UserSearchedByCriteriaDataProvider(SearchResultsPage searchResultsPagePage) {
        criteria = (UserSearchCriteria) searchResultsPagePage.getSession().getAttribute("searchCriteria");
    }

    @Override
    public Iterator<? extends User> iterator(long l, long l1) {
        List<User> foundByCriteria;
        try {
            foundByCriteria = new UserManager().getUsersByCriteria(criteria, FactoryType.SPRING);
        } catch (SQLException e) {
            logger.error(e);
            throw new DataNotFoundException(ERROR_MESSAGE);

        }
        return foundByCriteria.iterator();
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

    }
}
