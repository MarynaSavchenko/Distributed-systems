module Accounts {


	struct Client {
		string firstName;
		string lastName;
		string pesel;
		string password;
	};
    
 	sequence<Client> clients;

	enum Currency{
		EUR,
		USD,
		PLN
	};

	enum AccountType{
		STANDART, 
		PREMIUM
	};

	class Credit{
		double homeCurrency;
		optional(1) double foreignCurrency;
	};

	struct AccountDetails{
		double balance;
		Currency currency;
		double monthIncome;
		AccountType accountType;
	};

	exception BankException{
		string message;
	};

	exception WrongPesel extends BankException{};
	exception WrongPassword extends BankException{};

	interface Account{
		AccountDetails getAccountDetails();
		void pay(Currency currency, double amount);
		void withdraw(Currency currency, double amount);
		Credit takeLoan(Currency currency, double amount, int months); 
	};

	interface BankService{
		string createAccount(string firstName, string lastName, string pesel, long monthIncome, Currency currency) throws WrongPesel;
		idempotent Account* login(string pesel, string password) throws WrongPassword, WrongPesel;
	};

};
