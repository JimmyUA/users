package com.sergey.prykhodko.front.pages.user.create;

import com.sergey.prykhodko.dao.factory.FactoryType;
import com.sergey.prykhodko.front.pages.system.ErrorPage;
import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.PasswordEncoder;
import com.sergey.prykhodko.util.UserValidator;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import java.sql.SQLException;
import java.util.Spliterator;
import java.util.function.Consumer;

import static com.sergey.prykhodko.util.ClassName.*;
import static org.apache.log4j.Logger.*;

public class UserCreateForm extends Form<Void> {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private static Logger logger = getLogger(getCurrentClassName());

    private User user;

    public UserCreateForm(String id) {
        super(id);

        user = new User();

        CompoundPropertyModel<User> model = new CompoundPropertyModel<>(user);
        setDefaultModel(model);

        add(new TextField<String>(LOGIN, model.bind(LOGIN)));
        add(new PasswordTextField(PASSWORD, model.bind(PASSWORD)));
        add(new EmailTextField(EMAIL, model.bind(EMAIL)));
        add(new TextField<String>(FIRST_NAME, model.bind(FIRST_NAME)));
        add(new TextField<String>(LAST_NAME, model.bind(LAST_NAME)));
    }

    public void onSubmit(){
        if (notValidUser()){
            setResponsePage(getApplication().getPageFactory().newPage(NotValidUser.class));
            return;
        }
        try {
            new UserManager().addUser(user, FactoryType.SPRING);
        } catch (SQLException e){
            logger.error(e);
            setResponsePage(getApplication().getPageFactory().newPage(ErrorPage.class));
        }
        user.setPassword(PasswordEncoder.encodePassword(user.getPassword()));
        logger.info("User " + user.toString() + " created");
        getSession().setAttribute("newUser", user);
        setResponsePage(getApplication().getPageFactory().newPage(NewUser.class));
    }


    private boolean notValidUser() {
        return !UserValidator.validate(user);
    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }
}
