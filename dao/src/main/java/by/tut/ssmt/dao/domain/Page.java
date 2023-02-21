package by.tut.ssmt.dao.domain;

import java.util.ArrayList;
import java.util.List;

public class Page <T> {
private int pageNumber;
private long totalElements;
private int limit;
private String currentUser;
private String orderBy;
private String filter;
private List<T> elements = new ArrayList<>();

    public Page() {}

    public Page(int pageNumber, long totalElements, int limit, String currentUser, String orderBy, String filter, List<T> elements) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.currentUser = currentUser;
        this.orderBy = orderBy;
        this.filter = filter;
        this.elements = elements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page<?> page = (Page<?>) o;

        if (pageNumber != page.pageNumber) return false;
        if (totalElements != page.totalElements) return false;
        if (limit != page.limit) return false;
        if (currentUser != null ? !currentUser.equals(page.currentUser) : page.currentUser != null) return false;
        if (orderBy != null ? !orderBy.equals(page.orderBy) : page.orderBy != null) return false;
        if (filter != null ? !filter.equals(page.filter) : page.filter != null) return false;
        return elements != null ? elements.equals(page.elements) : page.elements == null;
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (currentUser != null ? currentUser.hashCode() : 0);
        result = 31 * result + (orderBy != null ? orderBy.hashCode() : 0);
        result = 31 * result + (filter != null ? filter.hashCode() : 0);
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", totalElements=" + totalElements +
                ", limit=" + limit +
                ", currentUser='" + currentUser + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", filter='" + filter + '\'' +
                ", elements=" + elements +
                '}';
    }
}




