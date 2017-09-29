package com.sergey.prykhodko.front.pages.user.create;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class NotValidUser extends WebPage{

    public NotValidUser() {
        add(new Label("message", "User is not valid"));
    }
}
