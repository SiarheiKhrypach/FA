package by.tut.ssmt.dao.domain;

import java.util.ArrayList;
import java.util.List;

public class Page <T> {
private int pageNumber;
private long totalElements;
private int limit;
private List<T> elements = new ArrayList<>();

    public Page() {}

    public Page(int pageNumber, long totalElements, int limit, List<T> elements) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
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
        return elements != null ? elements.equals(page.elements) : page.elements == null;
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }
}




