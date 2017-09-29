package com.sergey.prykhodko.front.pages.user.update;

import com.sergey.prykhodko.front.pages.basepage.BasePage;
import org.apache.wicket.markup.html.basic.Label;

public class UserUpdatePage extends BasePage{
    private static final String UPDATE_FORM = "updateForm";
    private static final String UPDATE_LABEL_ID = "updateLabel";
    private static final String UPDATE_LABEL_MESSAGE = "You can update user info here";

    public UserUpdatePage(){
        add(new Label(UPDATE_LABEL_ID, UPDATE_LABEL_MESSAGE));
        add(new UserUpdateForm(UPDATE_FORM));
    }
}
