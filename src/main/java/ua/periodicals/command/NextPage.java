package ua.periodicals.command;

public class NextPage {
    String page;
    String dispatchType;

    public NextPage() {
    }

    public NextPage(String page, String dispatchType) {
        this.page = page;
        this.dispatchType = dispatchType;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public enum DispatchType {
        FORWARD,
        REDIRECT
    }


    @Override
    public String toString() {
        return "NextPage{" +
                "page='" + page + '\'' +
                ", dispatchType='" + dispatchType + '\'' +
                '}';
    }
}
