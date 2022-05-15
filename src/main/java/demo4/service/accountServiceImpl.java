package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.accountDao;
import demo4.model.account;

@Service
@Transactional
public class accountServiceImpl implements accountService{

	@Autowired
	private accountDao repo;
	
	@Override
	public account getAccountById(int id) {
		return repo.getAccountById(id);
	}

	@Override
	public void updateAccount(account ac) {
		repo.updateAccount(ac);
		
	}

	@Override
	public void saveAccount(account ac) {
		repo.saveAccount(ac);
		
	}

	@Override
	public account findAccountByUserName(String username) {
		return repo.findAccountByUserName(username);
	}

	@Override
	public Object countAccount() {
		return repo.countAccount();
	}

	
	
}
