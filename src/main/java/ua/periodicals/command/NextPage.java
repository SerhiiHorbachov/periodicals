package ua.periodicals.command;

import ua.periodicals.util.DispatchType;

public class NextPage {
    String page;
    DispatchType dispatchType;

    public NextPage() {
    }

    public NextPage(String page, DispatchType dispatchType) {
        this.page = page;
        this.dispatchType = dispatchType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public DispatchType getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(DispatchType dispatchType) {
        this.dispatchType = dispatchType;
    }

    @Override
    public String toString() {
        return "NextPage{" +
            "page='" + page + '\'' +
            ", dispatchType='" + dispatchType + '\'' +
            '}';
    }
}
