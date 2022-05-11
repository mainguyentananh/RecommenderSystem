package demo4.dao;

import java.util.List;

import demo4.model.tempcosinesimilarity;

public interface tempCosineSimilarityDao {
	public List<tempcosinesimilarity> getAllTempCosineSimilarity();
	public void saveTempCosineSimilarity(tempcosinesimilarity cosine);
	public void updateTempCosineSimilarity(tempcosinesimilarity cosine);
	public tempcosinesimilarity getTempCosineSimilarityByKey(String key);
}
