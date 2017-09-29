package com.sergey.prykhodko.front.pages.user.create;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class NewUser extends WebPage{

    public NewUser() {
        add(new Label("message", "New user " + getSession().getAttribute("newUser").toString()
        + " is created"));
    }
}
