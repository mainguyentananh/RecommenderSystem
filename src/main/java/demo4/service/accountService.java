package demo4.service;

import demo4.model.account;

public interface accountService {
	public account getAccountById(int id);
	public void updateAccount(account ac);
	public void saveAccount(account ac);
	public account findAccountByUserName(String username);
	public Object countAccount();
}
