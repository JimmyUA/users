package com.sergey.prykhodko.front.pages.system;

import com.sergey.prykhodko.front.pages.basepage.BasePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class ErrorPage extends BasePage{
    private static final String LABEL_ID = "message";
    private static final String LABEL_MESSAGE = "En ErrorPage occurred";
    private static final String LINK_ID = "goToHomePage";

    public ErrorPage() {
        addLabel();
        addLink();
    }

    private void addLink() {
        add(new Link<String>(LINK_ID) {
            @Override
            public void onClick() {
                setResponsePage(getApplication().getHomePage());
            }
        }.setAutoEnable(true));
    }

    private void addLabel() {
        add(new Label(LABEL_ID, LABEL_MESSAGE));
    }
}
