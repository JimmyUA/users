package com.sergey.prykhodko.front.pages.user.lookup;

import com.sergey.prykhodko.exception.DataNotFoundException;
import com.sergey.prykhodko.front.pages.basepage.BasePage;
import com.sergey.prykhodko.front.pages.system.ErrorPage;
import com.sergey.prykhodko.front.pages.user.UsersList;
import com.sergey.prykhodko.front.utils.UserSearchedByCriteriaDataProvider;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.util.Authentication;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

public class SearchResultsPage extends BasePage{
    private static final String FORM_ID = "formResults";
    private static final String Button_ID = "cancelButton";
    private static final String DATA_VIEW_ID = "dataView";
    private static final String USER_ID_LABEL_ID = "userId";
    private static final String USER_LOGIN_LABEL_ID = "userLogin";
    private static final String USER_EMAIL_LABEL_ID = "userEmail";
    private static final String USER_FIRST_NAME_LABEL_ID = "userFirstName";
    private static final String USER_LAST_NAME_LABEL_ID = "userLastName";
    private Form<Void> form;
    private DataView<User> dataView;

    public SearchResultsPage() {
        form = new Form<>(FORM_ID);

        dataView = getDataView();

        try {
            form.add(dataView);
        } catch (DataNotFoundException e){
            setResponsePage(ErrorPage.class);
        }

        setComponentsToForm();
        add(form);
    }

    private void setComponentsToForm() {
        form.add(new Button(Button_ID){
            @Override
            public void onSubmit() {
                super.onSubmit();
                setResponsePage(UsersList.class);
            }
        });
    }

    private DataView<User> getDataView() {
        return new DataView<User>(DATA_VIEW_ID, new UserSearchedByCriteriaDataProvider(this)) {
            @Override
            protected void populateItem(Item<User> item) {
                User user = item.getModelObject();
                item.add(new Label(USER_ID_LABEL_ID, String.valueOf(user.getId())));
                item.add(new Label(USER_LOGIN_LABEL_ID, user.getLogin()));
                item.add(new Label(USER_EMAIL_LABEL_ID, user.getEmail()));
                item.add(new Label(USER_FIRST_NAME_LABEL_ID, user.getFirstName()));
                item.add(new Label(USER_LAST_NAME_LABEL_ID, user.getLastName()));
            }


        };
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        Authentication.auth();
    }
}
