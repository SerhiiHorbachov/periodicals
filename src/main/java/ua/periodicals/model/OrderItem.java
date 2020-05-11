package ua.periodicals.model;

public class OrderItem extends Entity {

    Long invoiceId;
    Long periodicalId;
    Long costPerMonth;

    public OrderItem() {

    }

    public OrderItem(Long invoiceId, Long periodicalId, Long costPerMonth) {
        this.invoiceId = invoiceId;
        this.periodicalId = periodicalId;
        this.costPerMonth = costPerMonth;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(Long periodicalId) {
        this.periodicalId = periodicalId;
    }

    public Long getCostPerMonth() {
        return costPerMonth;
    }

    public void setCostPerMonth(Long costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "invoiceId=" + invoiceId +
            ", periodicalId=" + periodicalId +
            ", costPerMonth=" + costPerMonth +
            '}';
    }
}
