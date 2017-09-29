package com.sergey.prykhodko.front.pages.user.update;

import com.sergey.prykhodko.dao.factory.FactoryType;
import com.sergey.prykhodko.front.pages.system.ErrorPage;
import com.sergey.prykhodko.front.pages.user.UsersList;
import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.CompoundPropertyModel;

import java.sql.SQLException;
import java.util.Spliterator;
import java.util.function.Consumer;

import static com.sergey.prykhodko.util.ClassName.getCurrentClassName;
import static org.apache.log4j.Logger.getLogger;

public class UserUpdateForm extends Form<Void> {


    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private static Logger logger = getLogger(getCurrentClassName());

    private User user;

    public UserUpdateForm(String id) {
        super(id);

        user = (User)getSession().getAttribute("userToUpdate");

        CompoundPropertyModel<User> model = new CompoundPropertyModel<>(user);
        setDefaultModel(model);

        add(new EmailTextField(EMAIL, model.bind(EMAIL)));
        add(new TextField<String>(FIRST_NAME, model.bind(FIRST_NAME)));
        add(new TextField<String>(LAST_NAME, model.bind(LAST_NAME)));
        add(new Button("cancelButton"){
            @Override
            public void onSubmit() {
                super.onSubmit();
                setResponsePage(UsersList.class);
                return;
            }
        });
    }

    public void onSubmit(){
        try {
            new UserManager().updateUser(user, FactoryType.SPRING);
        } catch (SQLException e){
            logger.error(e);
            setResponsePage(getApplication().getPageFactory().newPage(ErrorPage.class));
        }

        setResponsePage(getApplication().getPageFactory().newPage(UsersList.class));

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }

}
