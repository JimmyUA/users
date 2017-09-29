package com.sergey.prykhodko.front.pages.user.lookup;


import com.sergey.prykhodko.util.UserSearchCriteria;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;


import java.util.Spliterator;
import java.util.function.Consumer;

import static com.sergey.prykhodko.util.ClassName.getCurrentClassName;
import static org.apache.log4j.Logger.getLogger;

public class LookUpForm extends Form<Void> {
    private static final String LAST_NAME = "lastName";
    private static final String LOOK_UP = "lookUpField";

    private UserSearchCriteria searchCriteria;

    private static Logger logger = getLogger(getCurrentClassName());

    public LookUpForm(String id) {
        super(id);

        searchCriteria = new UserSearchCriteria();

        CompoundPropertyModel<UserSearchCriteria> model = new CompoundPropertyModel<>(searchCriteria);
        setDefaultModel(model);

        add(new TextField<String>(LOOK_UP, model.bind(LAST_NAME)));
    }

    public void onSubmit(){
        getSession().setAttribute("searchCriteria", searchCriteria);
        setResponsePage(getApplication().getPageFactory().newPage(SearchResultsPage.class));

    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }
}
