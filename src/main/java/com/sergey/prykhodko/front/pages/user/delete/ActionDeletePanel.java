package com.sergey.prykhodko.front.pages.user.delete;

import com.sergey.prykhodko.dao.factory.FactoryType;
import com.sergey.prykhodko.front.utils.ConfirmationLink;
import com.sergey.prykhodko.front.pages.user.UsersList;
import com.sergey.prykhodko.managers.UserManager;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import java.sql.SQLException;

public class ActionDeletePanel extends Panel {
    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    public ActionDeletePanel(String id, IModel<User> model) {
        super(id, model);

        Form<Void> form = new Form<>("form");

        ConfirmationLink<Void> removeLink = new ConfirmationLink<Void>("delete", "Do you really want " +
                "to delete this user?") {

            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                User user = (User) ActionDeletePanel.this.getDefaultModelObject();
                try {
                    new UserManager().deleteUser(user, FactoryType.SPRING);
                } catch (SQLException e) {
                    logger.error(e);
                }
                setResponsePage(UsersList.class);
            }

        };

        form.add(removeLink);
        add(form);
    }
}