package com.sergey.prykhodko.front;

import com.sergey.prykhodko.front.pages.home.HomePage;
import com.sergey.prykhodko.front.pages.login.LogInPage;
import com.sergey.prykhodko.front.pages.user.UsersList;
import com.sergey.prykhodko.front.utils.AdminAuthWebSession;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;



public class WicketApplication extends AuthenticatedWebApplication {
    /**
     * Constructor
     */
	public WicketApplication() {
	}

    @Override
    public void init()
    {
        super.init();
        mountPage("/home", HomePage.class);
        mountPage("/users", UsersList.class);
    }

    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return AdminAuthWebSession.class;
    }

    protected Class<? extends WebPage> getSignInPageClass() {
        return LogInPage.class;
    }



    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

}
