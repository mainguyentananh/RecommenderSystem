package demo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.feedbackDao;
import demo4.model.feedback;

@Service
@Transactional
public class feedbackServiceImpl implements feedbackService{

	@Autowired
	private feedbackDao repo;
	
	@Override
	public void saveFeedback(feedback feedback) {
		repo.saveFeedback(feedback);
	}

	
	
}
