package nuvemdesoftware.ceramicpro.utils;

import org.springframework.data.domain.*;

import java.util.List;

public class CustomPageImpl<T> extends PageImpl<T> {
    private static final long serialVersionUID = 1L;
    private PageImpl page;
    private String nextPage;
    private String previousPage;
    private int currentPage;

    public CustomPageImpl(List<T> list, PageRequest pageRequest, long totalElements) {
        super(list, pageRequest, totalElements);
        this.page = new PageImpl(list, pageRequest, totalElements);

        this.currentPage = pageRequest.getPageNumber() + 1;
        int nextPageNumber = pageRequest.getPageNumber() + 1;
        this.nextPage = "/?page=" + nextPageNumber + "&size=" + pageRequest.getPageSize();
        if (pageRequest.getPageNumber() > 0) {
            int previousPageNumber = pageRequest.getPageNumber() - 1;
            this.previousPage = "/?page=" + previousPageNumber + "&size=" + pageRequest.getPageSize();
        }
    }

    public PageImpl getPage() {
        return page;
    }

    public void setPage(PageImpl page) {
        this.page = page;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}