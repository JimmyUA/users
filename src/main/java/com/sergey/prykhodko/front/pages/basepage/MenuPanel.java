package com.sergey.prykhodko.front.pages.basepage;

import com.sergey.prykhodko.front.pages.user.UsersList;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class MenuPanel extends Panel {
    private static final String HOME_PAGE_LINK_ID = "toHomePage";
    private static final String USERS_LIST_PAGE_LINK_ID = "toUsersList";
    private static final String LOG_OUT_LINK_ID = "logOut";

    public MenuPanel(String id) {
        super(id);
        addComponentsToForm();
    }

    private void addComponentsToForm() {
        addHomePageLink();

        addUsersListLink();

        addLogOutLink();
    }

    private void addLogOutLink() {
            add(new Link(LOG_OUT_LINK_ID) {
            @Override
            public void onClick() {
                AuthenticatedWebSession.get().invalidate();
                setResponsePage(getApplication().getHomePage());
            }

        });
    }

    private void addUsersListLink() {
            add(new Link(USERS_LIST_PAGE_LINK_ID) {
            @Override
            public void onClick() {
                setResponsePage(UsersList.class);
            }
        });
    }

    private void addHomePageLink() {
            add(new Link(HOME_PAGE_LINK_ID) {
            @Override
            public void onClick() {
                setResponsePage(getApplication().getHomePage());
            }
        });
    }
}
