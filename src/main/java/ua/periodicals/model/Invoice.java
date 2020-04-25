package ua.periodicals.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Invoice extends Entity {
    private long id;
    private long userId;
    STATUS status;
    Timestamp createdAt;
    Timestamp updatedAt;

    public Invoice() {
    }

    public Invoice(long userId) {
        this.userId = userId;
        this.status = STATUS.IN_PROGRESS;
    }

    public Invoice(long id, long userId, STATUS status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id == invoice.id &&
                userId == invoice.userId &&
                Objects.equals(createdAt, invoice.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, createdAt);
    }

    @Override
    public String toString() {
        return "\nInvoice{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public enum STATUS {
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

}
