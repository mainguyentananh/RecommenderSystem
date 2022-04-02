package demo4.service;

import org.springframework.transaction.annotation.Transactional;

import demo4.dao.account_roleDao;
import demo4.model.account_role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class account_roleServiceImpl implements account_roleService{

	@Autowired
	private account_roleDao repo;
	@Override
	public void saveAccount_Role(account_role account_role) {
		repo.saveAccount_Role(account_role);
	}

}
