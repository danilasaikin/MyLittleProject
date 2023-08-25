import java.util.Date;
import java.util.UUID;

public class Transaction {
    private String transactionId;
    private Date date;
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private double amount;

    public Transaction(String sourceAccountNumber, String targetAccountNumber, double amount) {
        this.transactionId = generateTransactionId();
        this.date = new Date();
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Date getDate() {
        return date;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public double getAmount() {
        return amount;
    }
}
