/*
 * Objective: Demonstrate multithreading by implementing a simple banking application that handles deposits and withdrawals in a thread-safe manner.
    • Details: Create a class BankAccount with methods to deposit and withdraw money. Use multithreading to simulate multiple users accessing the account concurrently.
    • Functions to Implement:
        ◦ deposit(double amount): Deposits a specified amount into the account.
        ◦ withdraw(double amount): Withdraws a specified amount from the account, with checks to prevent overdraft.
 */

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds for withdrawal");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class DepositThread extends Thread {
    private BankAccount account;
    private double amount;

    public DepositThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.deposit(amount);
    }
}

class WithdrawThread extends Thread {
    private BankAccount account;
    private double amount;

    public WithdrawThread(BankAccount account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}

public class task2 {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);

        DepositThread deposit1 = new DepositThread(account, 500.0);
        WithdrawThread withdraw1 = new WithdrawThread(account, 200.0);
        WithdrawThread withdraw2 = new WithdrawThread(account, 1000.0);
        DepositThread deposit2 = new DepositThread(account, 300.0);

        //multiple users concurrent access simulation
        deposit1.start();
        withdraw1.start();
        withdraw2.start();
        deposit2.start();

        try {
            deposit1.join();
            withdraw1.join();
            withdraw2.join();
            deposit2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final balance is " + account.getBalance());
    }
}
