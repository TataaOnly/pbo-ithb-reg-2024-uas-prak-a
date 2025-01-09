package Model;

import java.util.Date;

public class Shipment extends Transactions{
    private int id;
    private int transactionsId;
    private Status status;
    private String evidence; //url gambar
    private Date date;
    private String updatedBy;
    
    public Shipment(int id, int idCustomer, String packageType, double packageWeight, int totalCost, Date createdAt,
            String receiptName, String receiptAddress, String receiptPhone, int id2, int transactionsId, Status status,
            String evidence, Date date, String updatedBy) {
        super(id, idCustomer, packageType, packageWeight, totalCost, createdAt, receiptName, receiptAddress,
                receiptPhone);
        id = id2;
        this.transactionsId = transactionsId;
        this.status = status;
        this.evidence = evidence;
        this.date = date;
        this.updatedBy = updatedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(int transactionsId) {
        this.transactionsId = transactionsId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
}
