package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.notifyDao;
import demo4.model.notify;
@Service
@Transactional
public class notifyServiceImpl implements notifyService{

	@Autowired
	private notifyDao repo;
	
	@Override
	public notify getNotifyById(String id) {
		return repo.getNotifyById(id);
	}

	@Override
	public void saveNotify(notify n) {
		repo.saveNotify(n);
		
	}

	@Override
	public void updateNotify(notify n) {
		repo.updateNotify(n);
		
	}

}
