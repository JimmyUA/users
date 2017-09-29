package com.sergey.prykhodko.front.pages.user.create;

import com.sergey.prykhodko.util.Authentication;
import org.apache.wicket.markup.html.WebPage;

public class UserCreatePage extends WebPage {
        private static final String CREATE_FORM = "createForm";

    public UserCreatePage(){
        add(new UserCreateForm(CREATE_FORM));
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        Authentication.auth();
    }
}
