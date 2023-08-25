import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String pin;
    private Owner owner;
    private double balance;
    private List<Transaction> transactionHistory;

    public Account(String accountNumber, String pin, Owner owner) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.owner = owner;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Owner getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        Transaction transaction = new Transaction(accountNumber, accountNumber, amount);
        transactionHistory.add(transaction);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            Transaction transaction = new Transaction(accountNumber, accountNumber, -amount);
            transactionHistory.add(transaction);
        } else {
            System.out.println("Недостаточно средств на счете.");
        }
    }

    public boolean checkPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
