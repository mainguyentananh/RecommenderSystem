package demo4.dao;

import java.util.List;

import demo4.model.feedback;

public interface feedbackDao {
	public void saveFeedback(feedback feedback);
	public List<feedback> getFeedBackByIdDocument(int id);
	public Object avgStarDocument(int id);
	public List<Object[]> feedbackDocumentOfAccount();
	public List<Object> listAccountFeedback();
	
}
