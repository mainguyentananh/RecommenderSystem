package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.model.account;

@Service
@Transactional
public class userDetailService implements UserDetailsService{

	@Autowired
	private accountService repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		account account = repo.findAccountByUserName(username);
		if(account == null)
			throw new UsernameNotFoundException("Not found account with username: " + username);
		return userPrincipal.create(account);
	}

}
