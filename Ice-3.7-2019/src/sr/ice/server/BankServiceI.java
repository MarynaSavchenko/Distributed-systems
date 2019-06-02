package sr.ice.server;

import Accounts.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;

import java.util.HashMap;
import java.util.Map;


public class BankServiceI implements BankService {

    Map<String, AccountI> mapAccount = new HashMap<>();

    @Override
    public String createAccount(String firstName, String lastName, String pesel,
                                long monthIncome, Currency currency, Current current) throws WrongPesel {
        if (mapAccount.get(pesel) != null) throw new WrongPesel("Account with such pesel already exists");

        AccountI account = new AccountI(firstName, lastName, pesel, monthIncome, currency);
        mapAccount.put(pesel, account);
        AccountPrx.uncheckedCast(current.adapter.add(account, new Identity(pesel, account.accountDetails.accountType.toString())));
        return account.password;
    }

    @Override
    public AccountPrx login(String pesel, String password, Current current) throws WrongPassword, WrongPesel {
        AccountI account = mapAccount.get(pesel);
        if (account == null) throw new WrongPesel("There is no such pesel");
        if (!account.password.equals(password)) throw new WrongPassword("You provided wrong password");
        return AccountPrx.uncheckedCast(current.adapter.createProxy(new Identity(pesel, account.accountDetails.accountType.toString())));
    }


}
