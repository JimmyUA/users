package com.sergey.prykhodko.front.pages.user;

import com.sergey.prykhodko.exception.DataNotFoundException;
import com.sergey.prykhodko.front.pages.basepage.BasePage;
import com.sergey.prykhodko.front.pages.system.ErrorPage;
import com.sergey.prykhodko.front.pages.user.create.ModalCreatePage;
import com.sergey.prykhodko.front.pages.user.lookup.LookUpForm;
import com.sergey.prykhodko.front.pages.user.delete.ActionDeletePanel;
import com.sergey.prykhodko.front.pages.user.update.ActionUpdatePanel;
import com.sergey.prykhodko.model.User;
import com.sergey.prykhodko.front.utils.UserDataProvider;
import com.sergey.prykhodko.util.Authentication;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

public class UsersList extends BasePage {

    private static final String FORM_ID = "mainForm";
    private static final String DATA_VIEW_ID = "paddingDataView";
    private static final String MODAL_WINDOW_ID = "create";
    private static final String LOOK_UP_FORM_ID = "lookUpForm";

    private Form<Void> mainForm;
    private Form<Void> creationForm;
    private DataView<User> dataView;
    private ModalWindow modalWindow;

    public UsersList(){
            }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        Authentication.auth();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        mainForm = new Form<>(FORM_ID);


        modalWindow = new ModalWindow(MODAL_WINDOW_ID);
        configureModalWindow();

        creationForm = getCreationForm();


        dataView = getDataView();
        setComponentsToMainForm();
        dataView.setItemsPerPage(8L);

        addComponentsOnPage();
    }

    private void setComponentsToMainForm() {
        try {
            mainForm.add(dataView);
        } catch (DataNotFoundException e){
            setResponsePage(ErrorPage.class);
        }
        mainForm.add(modalWindow);
        mainForm.add(new PagingNavigator("navigator", dataView));
        mainForm.add(creationForm);
    }

    private void configureModalWindow() {
        setPageCreator();
        setClosedCallBack();
    }

    private void setClosedCallBack() {
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClose(AjaxRequestTarget target) {
                setResponsePage(UsersList.class);
            }
        });
    }

    private void setPageCreator() {
        modalWindow.setPageCreator(new ModalWindow.PageCreator() {
            public Page createPage() {
                // Use this constructor to pass a reference of this page.
                return new ModalCreatePage(UsersList.this.getPageReference(),
                        modalWindow);
            }
        });
    }

    private Form<Void> getCreationForm() {
        return new Form<Void>("userCreation") {

            @Override
            protected void onInitialize() {
                super.onInitialize();

                add(new AjaxLink<Void>("showModalLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        modalWindow.show(target);
                    }
                });
            }

        };
    }

    private DataView<User> getDataView() {
        return new DataView<User>(DATA_VIEW_ID, new UserDataProvider()) {
            @Override
            protected void populateItem(Item<User> item) {
                User user = item.getModelObject();
                item.add(new Label("userId", String.valueOf(user.getId())));
                item.add(new Label("userLogin", user.getLogin()));
                item.add(new Label("userEmail", user.getEmail()));
                item.add(new Label("userFirstName", user.getFirstName()));
                item.add(new Label("userLastName", user.getLastName()));
                item.add(new ActionDeletePanel("remove", item.getModel()));
                item.add(new ActionUpdatePanel("update", item.getModel()));
            }
        };
    }

    private void addComponentsOnPage() {
        add(new LookUpForm(LOOK_UP_FORM_ID));
        add(mainForm);
    }
}
