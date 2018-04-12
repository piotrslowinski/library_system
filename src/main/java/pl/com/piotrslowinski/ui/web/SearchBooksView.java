package pl.com.piotrslowinski.ui.web;


import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;
import pl.com.piotrslowinski.application.BookDto;
import pl.com.piotrslowinski.application.BookFinder;
import pl.com.piotrslowinski.application.BookSearchCriteria;
import pl.com.piotrslowinski.application.BookSearchResults;
import pl.com.piotrslowinski.ui.web.general.PagingComponent;
import pl.com.piotrslowinski.ui.web.general.UIConstants;

import java.util.List;

@SpringUI
public class SearchBooksView extends UI {

    @Autowired
    SpringViewProvider viewProvider;

    @Autowired
    private BookFinder bookFinder;

    private Layout rootLayout;

    private Grid<BookDto> booksGrid;

    private Layout searchFieldsLayout;

    private TextField titleSearchTextFiels;
    private TextField isbnSearchTextFiels;

    private Label totalResultsFoundLabel;

    private PagingComponent pagingComponent;

    private int previouslySearchedLastPageIndex;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        createRootLayout();
        createAndConfigureChildComponents();
        populateRootLayout();
    }

    private void populateRootLayout() {
        rootLayout.addComponent(searchFieldsLayout);
        rootLayout.addComponent(booksGrid);
        rootLayout.addComponent(totalResultsFoundLabel);
        rootLayout.addComponent(pagingComponent);
    }

    private void createAndConfigureChildComponents() {
        buildAndConfigureBooksGrid();
        buildAndConfigureSearchTextFields();
        buildAndConfigureAdditionalComponents();
    }

    private void buildAndConfigureAdditionalComponents() {
        totalResultsFoundLabel = new Label();
        pagingComponent = new PagingComponent();
        pagingComponent.setSizeFull();
        configurePagingFieldsAndButtons();
    }

    private void configurePagingFieldsAndButtons() {
        pagingComponent.getCurrentPageTextField().addValueChangeListener(valueChangeEvent -> searchBooks());
        pagingComponent.getNextPageButton().addClickListener(clickEvent -> searchNextPageOfBooks());
        pagingComponent.getPreviousPageButton().addClickListener(clickEvent -> searchPreviousPageOfBooks());
        pagingComponent.getFirstPageButton().addClickListener(clickEvent -> searchFirstPageOfBooks());
        pagingComponent.getLastPageButton().addClickListener(clickEvent -> searchLastPageOfBooks());
        pagingComponent.getResultsPerPageComboBox().addValueChangeListener(valueChangeEvent -> searchBooks());
    }

    private void searchLastPageOfBooks() {
        BookSearchCriteria bsc = collectCriteria();
        if(bsc.getPageNumber() >= previouslySearchedLastPageIndex) return;
        BookSearchResults bsr = getSearchResults(bsc);
        bsc.setPageNumber(bsr.getPagesCount());
        bsr = getSearchResults(bsc);
        populateViewWithSearchResults(bsr, bsc.getPageNumber());
    }

    private void searchFirstPageOfBooks() {
        BookSearchCriteria bsc = collectCriteria();
        if(bsc.getPageNumber() <= 1) return;
        bsc.setPageNumber(1);
        BookSearchResults bsr = getSearchResults(bsc);
        populateViewWithSearchResults(bsr, bsc.getPageNumber());
    }

    private void searchPreviousPageOfBooks() {
        BookSearchCriteria bsc = collectCriteria();
        bsc.setPageNumber(bsc.getPageNumber() - 1);
        if(bsc.getPageNumber() < 1) return;
        BookSearchResults bsr = getSearchResults(bsc);
        populateViewWithSearchResults(bsr, bsc.getPageNumber());
    }

    private void searchNextPageOfBooks() {
        BookSearchCriteria bsc = collectCriteria();
        bsc.setPageNumber(bsc.getPageNumber() + 1);
        if(bsc.getPageNumber() >= previouslySearchedLastPageIndex) return;
        BookSearchResults bsr = getSearchResults(bsc);
        populateViewWithSearchResults(bsr, bsc.getPageNumber());
    }

    private void buildAndConfigureSearchTextFields() {
        createSearchTextFields();
        configureFilterFields();
        buildAndConfigureLayoutForSearchTextFields();
    }

    private void buildAndConfigureLayoutForSearchTextFields() {
        searchFieldsLayout = new HorizontalLayout();
        searchFieldsLayout.addComponent(titleSearchTextFiels);
        searchFieldsLayout.addComponent(isbnSearchTextFiels);

    }

    private void configureFilterFields() {
        titleSearchTextFiels.addValueChangeListener(event -> searchBooks());
        isbnSearchTextFiels.addValueChangeListener(event -> searchBooks());
    }

    private void searchBooks() {
        BookSearchCriteria bsc = collectCriteria();
        BookSearchResults bsr = getSearchResults(bsc);
        populateViewWithSearchResults(bsr, bsc.getPageNumber());
    }

    private void populateViewWithSearchResults(BookSearchResults bsr, int currentPageValue) throws NumberFormatException {
        List<BookDto> foundEmployees = bsr.getResults();
        booksGrid.setItems(foundEmployees);
        if (bsr.getPagesCount() <= 0) pagingComponent.setDefaultValueForPagesCount();
        else pagingComponent.setValueForPagesCountLabel(bsr.getPagesCount());
        previouslySearchedLastPageIndex = bsr.getPagesCount();
        pagingComponent.setCurrentPageValue(currentPageValue);
        totalResultsFoundLabel.setValue(String.format(UIConstants.RESULTS_FOUND_CAPTION, bsr.getTotalCount()));
    }

    private BookSearchResults getSearchResults(BookSearchCriteria bsc) {
        return bookFinder.search(bsc);
    }

    private BookSearchCriteria collectCriteria() {
        BookSearchCriteria bsc = new BookSearchCriteria();

        if (!titleSearchTextFiels.isEmpty())
            bsc.setTitleQuery(titleSearchTextFiels.getValue());
        if (!isbnSearchTextFiels.isEmpty())
            bsc.setIsbnQuery(isbnSearchTextFiels.getValue());

        bsc.setPageSize(pagingComponent.getPageSize());
        bsc.setPageNumber(pagingComponent.getPageNumber());
        return bsc;
    }

    private void createSearchTextFields() {
        titleSearchTextFiels = new TextField(UIConstants.TITLE);
        isbnSearchTextFiels = new TextField(UIConstants.ISBN);
    }

    private void buildAndConfigureBooksGrid() {
        booksGrid = new Grid<>();
        booksGrid.addColumn(BookDto::getId).setCaption(UIConstants.BOOK_NUMBER);
        booksGrid.addColumn(BookDto::getTitle).setCaption(UIConstants.TITLE);
        booksGrid.addColumn(BookDto::getIsbn).setCaption(UIConstants.ISBN);
        booksGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        booksGrid.addSelectionListener(event -> {
            if(event.getFirstSelectedItem().isPresent()) {
                BookDto b = event.getFirstSelectedItem().get();
                // Notification.show(b.toString());
            }
        });
        booksGrid.setSizeFull();
    }

    private void createRootLayout() {
        rootLayout = new VerticalLayout();
        setContent(rootLayout);
    }
}
