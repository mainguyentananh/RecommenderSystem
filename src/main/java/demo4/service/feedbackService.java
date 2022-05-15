package demo4.service;

import java.util.List;

import demo4.model.feedback;

public interface feedbackService {
	public void saveFeedback(feedback feedback);
	public List<feedback> getFeedBackByIdDocument(int id);
	public Object avgStarDocument(int id);
	public List<Object[]> feedbackDocumentOfAccount();
	public List<Object> listAccountFeedback();
}
