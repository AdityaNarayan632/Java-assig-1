import java.util.Scanner;

// bank account class
class Account {
    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private String email;
    private String phoneNumber;

    
    public Account(int accountNumber, String accountHolderName, double balance, String email, String phoneNumber) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("₹" + amount + " deposited successfully.");
        } else {
            System.out.println("Deposit amount must be positive!");
        }
    }

    
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive!");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            System.out.println("₹" + amount + " withdrawn successfully.");
        }
    }

    public void displayAccountDetails() {
        System.out.println("\n--- Account Details ---");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + accountHolderName);
        System.out.println("Balance        : ₹" + balance);
        System.out.println("Email          : " + email);
        System.out.println("Phone Number   : " + phoneNumber);
        System.out.println("------------------------\n");
    }

    public void updateContactDetails(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        System.out.println("Contact details updated successfully.");
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}

class UserInterface {
    private Account[] accounts;
    private int accountCount;
    private Scanner sc;

    public UserInterface(int size) {
        accounts = new Account[size];
        accountCount = 0;
        sc = new Scanner(System.in);
    }

    public void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double balance = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter email address: ");
        String email = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        int accNumber = 1000 + accountCount + 1;
        accounts[accountCount] = new Account(accNumber, name, balance, email, phone);
        accountCount++;

        System.out.println("Account created successfully with Account Number: " + accNumber);
    }

      private Account findAccount(int accNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accNumber) {
                return accounts[i];
            }
        }
        return null;
    }

    public void performDeposit() {
        System.out.print("Enter account number: ");
        int accNumber = sc.nextInt();
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        Account acc = findAccount(accNumber);
        if (acc != null) acc.deposit(amount);
        else System.out.println("Account not found!");
    }

    public void performWithdrawal() {
        System.out.print("Enter account number: ");
        int accNumber = sc.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        Account acc = findAccount(accNumber);
        if (acc != null) acc.withdraw(amount);
        else System.out.println("Account not found!");
    }

    public void showAccountDetails() {
        System.out.print("Enter account number: ");
        int accNumber = sc.nextInt();
        sc.nextLine();

        Account acc = findAccount(accNumber);
        if (acc != null) acc.displayAccountDetails();
        else System.out.println("Account not found!");
    }

    public void updateContact() {
        System.out.print("Enter account number: ");
        int accNumber = sc.nextInt();
        sc.nextLine();

        Account acc = findAccount(accNumber);
        if (acc != null) {
            System.out.print("Enter new email: ");
            String email = sc.nextLine();
            System.out.print("Enter new phone number: ");
            String phone = sc.nextLine();
            acc.updateContactDetails(email, phone);
        } else {
            System.out.println("Account not found!");
        }
    }

    public void Menu() {
        int choice;
        do {
            System.out.println("1. Create a new account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. View account details");
            System.out.println("5. Update contact details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: createAccount(); break;
                case 2: performDeposit(); break;
                case 3: performWithdrawal(); break;
                case 4: showAccountDetails(); break;
                case 5: updateContact(); break;
                default: System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 6);
    }
}

public class main {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface(50);
        ui.Menu();
    }
}
