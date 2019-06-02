package sr.ice.client;
// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

import Accounts.AccountDetails;
import Accounts.AccountPrx;
import Accounts.BankServicePrx;
import Accounts.Currency;

import java.util.concurrent.CompletableFuture;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.LocalException;

public class Client {
    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;

        try {
            // 1. Inicjalizacja ICE
            communicator = Util.initialize(args);

            // 2. Uzyskanie referencji obiektu na podstawie linii w pliku konfiguracyjnym
            //Ice.ObjectPrx base = communicator.propertyToProxy("Calc1.Proxy");
            // 2. To samo co powy�ej, ale mniej �adnie
            ObjectPrx base = communicator.stringToProxy("bank/bankI1:tcp -h localhost -p 10000:udp -h localhost -p 10000");

            // 3. Rzutowanie, zaw�anie
            BankServicePrx obj = BankServicePrx.checkedCast(base);
            if (obj == null) throw new Error("Invalid proxy");

            BankServicePrx objOneway = (BankServicePrx) obj.ice_oneway();
            BankServicePrx objBatchOneway = (BankServicePrx) obj.ice_batchOneway();
            BankServicePrx objDatagram = (BankServicePrx) obj.ice_datagram();
            BankServicePrx objBatchDatagram = (BankServicePrx) obj.ice_batchDatagram();

            // 4. Wywolanie zdalnych operacji

            String line = null;
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            CompletableFuture<Long> cfl = null;
            AccountPrx accountPrx = null;

            while (true) {
                System.out.println("Write 'login' or 'register' , please: ");
                line = in.readLine();
                if (line.equals("register")) {
                    System.out.println("Give your first name, please:");
                    String firstName = in.readLine();
                    System.out.println("Give your last name, please:");
                    String lastName = in.readLine();
                    System.out.println("Give your pesel, please:");
                    String pesel = in.readLine();
                    System.out.println("Give your income in the month, please:");
                    long income = Long.parseLong(in.readLine());
                    System.out.println("Choose currecy, please:");
                    Currency currency = parseCurrency(in.readLine());
                    System.out.flush();
                    try {
                        String password = obj.createAccount(firstName, lastName, pesel, income, currency);
                        System.out.println("Remember your password, please: " + password);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else if (line.equals("login")) {
                    System.out.println("To login give your pesel, please:");
                    String pesel = in.readLine();
                    System.out.println("Give your password, please:");
                    String password = in.readLine();
                    try {
                        accountPrx = obj.login(pesel, password);
                        break;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            do {
                try {
                    System.out.println("==>");
                    line = in.readLine();
                    if (line.equals("get details")) {
                        AccountDetails accountDetails = accountPrx.getAccountDetails();
                        System.out.printf("Balance: %s %s, monthly income: %s, account type: %s\n",
                                accountDetails.balance, accountDetails.currency, accountDetails.monthIncome, accountDetails.accountType);
                    }

                } catch (java.io.IOException ex) {
                    System.err.println(ex);
                }
            }
            while (!line.equals("q"));


        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (communicator != null) {
            // Clean up
            //
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }

    private static Currency parseCurrency(String currency) {
        switch (currency) {
            case "EUR":
                return Currency.EUR;
            case "USD":
                return Currency.USD;
            case "PLN":
                return Currency.PLN;
            default:
                System.out.println("We don't work with such currency");
                return null;
        }
    }
}