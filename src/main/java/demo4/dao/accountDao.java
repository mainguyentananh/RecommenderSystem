package demo4.dao;

import demo4.model.account;

public interface accountDao {
	public account findAccountByUserName(String username);
	public account getAccountById(int id);
	public void updateAccount(account ac);
	public void saveAccount(account ac);
	public boolean checkAccountId(int id);

}
