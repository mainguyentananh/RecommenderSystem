package demo4.service;

import java.util.List;

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

	@Override
	public List<feedback> getFeedBackByIdDocument(int id) {
		return repo.getFeedBackByIdDocument(id);
	}

	@Override
	public Object avgStarDocument(int id) {
		return repo.avgStarDocument(id);
	}

	
	
}
