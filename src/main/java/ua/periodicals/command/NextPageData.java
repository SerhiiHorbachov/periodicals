package ua.periodicals.command;

import ua.periodicals.command.util.ResponseType;

public class NextPageData {
    private String page;
    private ResponseType type;

    public NextPageData(String page, ResponseType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public ResponseType getType() {
        return type;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
