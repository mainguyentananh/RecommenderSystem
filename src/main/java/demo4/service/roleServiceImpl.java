package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.roleDao;
import demo4.model.role;

@Service
@Transactional
public class roleServiceImpl implements roleService{

	@Autowired
	private roleDao repo;
	
	@Override
	public role findRoleByRoleName(String roleName) {
		return repo.findRoleByRoleName(roleName);
	}

}
