package pl.com.piotrslowinski.ui.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class BookDetailsView extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label("Details"));
    }
}
