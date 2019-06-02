package sr.ice.server;

import Accounts.*;
import com.zeroc.Ice.Current;

public class AccountI implements Account {
    long premiumLimit = 5000;
    int passwordLength = 10;

    String firstName, lastName, pesel, password;
    AccountDetails accountDetails;


    public AccountI(String firstName, String lastName, String pesel, long monthIncome, Currency currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        AccountType accountType = monthIncome > premiumLimit ? AccountType.PREMIUM : AccountType.STANDART;
        this.accountDetails = new AccountDetails(0, currency, monthIncome, accountType);
        this.password = createPassword(passwordLength);
    }

    @Override
    public AccountDetails getAccountDetails(Current current) {
        return this.accountDetails;
    }

    @Override
    public void pay(Currency currency, double amount, Current current) {
        this.accountDetails.balance += amount;
    }

    @Override
    public void withdraw(Currency currency, double amount, Current current) {
        if (this.accountDetails.balance >= amount)
            this.accountDetails.balance -= amount;
        else System.out.println("You don't have enough money:( Try to take a Loan");
    }

    @Override
    public Credit takeLoan(Currency currency, double amount, int months, Current current) {
        if (this.accountDetails.accountType == AccountType.PREMIUM)
            return new Credit(amount);
        else {
            System.out.println("You are not allowed to take a Loan:(");
            return null;
        }
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static String createPassword(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
