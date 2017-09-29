package com.sergey.prykhodko.front.pages.user.create;

import org.apache.wicket.PageReference;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class ModalCreatePage extends WebPage{

    private static final String CREATE_FORM = "createForm";
    private static final String CREATE_LABEL_ID = "createLabel";
    private static final String CREATE_LABEL_MESSAGE = "You can create user here";

    public ModalCreatePage(final PageReference modalWindowPage, final ModalWindow window) {

        add(new Label(CREATE_LABEL_ID, CREATE_LABEL_MESSAGE));
        add(new UserCreateForm(CREATE_FORM));
    }
}
