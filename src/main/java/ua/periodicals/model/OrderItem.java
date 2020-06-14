package ua.periodicals.model;

import java.util.Objects;

public class OrderItem extends Entity {

    Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id) &&
            Objects.equals(invoiceId, orderItem.invoiceId) &&
            Objects.equals(periodicalId, orderItem.periodicalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, periodicalId);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + id +
            ", invoiceId=" + invoiceId +
            ", periodicalId=" + periodicalId +
            ", costPerMonth=" + costPerMonth +
            '}';
    }
}
