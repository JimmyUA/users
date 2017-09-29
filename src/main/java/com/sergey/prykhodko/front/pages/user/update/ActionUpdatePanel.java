package com.sergey.prykhodko.front.pages.user.update;

import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.ClassName;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ActionUpdatePanel extends Panel{
    private final static Logger logger = Logger.getLogger(ClassName.getCurrentClassName());

    public ActionUpdatePanel(String id, IModel<User> model) {
        super(id, model);

        Form<Void> form = new Form<>("form");

        SubmitLink updateLink = new SubmitLink("update") {

            @Override
            public void onSubmit() {
                User user = (User) ActionUpdatePanel.this.getDefaultModelObject();
                getSession().setAttribute("userToUpdate", user);
                setResponsePage(getApplication().getPageFactory().newPage(UserUpdatePage.class));
            }
        };
        updateLink.setDefaultFormProcessing(false);
        form.add(updateLink);
        add(form);
    }
}
