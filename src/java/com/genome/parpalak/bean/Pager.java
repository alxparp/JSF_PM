package com.genome.parpalak.bean;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {

    private int selectedPageNumber = 1;
    private int tasksCountOnPage = 5;
    private int totalTasksCount;
    
    private List<T> list;

    public int getFrom() {
        return selectedPageNumber * tasksCountOnPage - tasksCountOnPage;
    }

    public int getTo() {
        return tasksCountOnPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setTotalTasksCount(int totalTasksCount) {
        this.totalTasksCount = totalTasksCount;
    }

    public int getTotalTasksCount() {
        return totalTasksCount;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }
    private List<Integer> pageNumbers = new ArrayList<>();

    public List<Integer> getPageNumbers() {// кол-во страниц для постраничности

        int pageCount = 0;

        if (totalTasksCount % tasksCountOnPage == 0) {
            pageCount = tasksCountOnPage > 0 ? (int) (totalTasksCount / tasksCountOnPage) : 0;
        } else {
            pageCount = tasksCountOnPage > 0 ? (int) (totalTasksCount / tasksCountOnPage) + 1 : 0;
        }

        pageNumbers.clear();

        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }

        return pageNumbers;
    }

    public int getTasksCountOnPage() {
        return tasksCountOnPage;
    }

    public void setTasksCountOnPage(int booksCountOnPage) {
        this.tasksCountOnPage = booksCountOnPage;
    }

}
