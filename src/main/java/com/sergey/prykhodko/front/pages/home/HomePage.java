package com.sergey.prykhodko.front.pages.home;

import com.sergey.prykhodko.front.pages.basepage.BasePage;
import com.sergey.prykhodko.front.pages.user.UsersList;
import com.sergey.prykhodko.util.Authentication;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;


/**
 * Homepage
 */
public class HomePage extends BasePage {
    private final static String WELCOME = "Welcome to users!";
    private final static String LABEL_ID = "message";
    private final static String USERS_LIST_LINK_ID = "goToUsersList";
    private final static String LOG_OUT_LINK_ID = "logOut";

    private static final long serialVersionUID = 1L;

    public HomePage() {

        add(new Label(LABEL_ID, WELCOME));

    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        Authentication.auth();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setComponents();
    }

    private void setComponents() {
        addUserListLink();

    }


    private void addUserListLink() {
        add(new Link(USERS_LIST_LINK_ID) {
            @Override
            public void onClick() {
                setResponsePage(getApplication().getPageFactory().newPage(UsersList.class));
            }
        }.setAutoEnable(true));
    }

}
