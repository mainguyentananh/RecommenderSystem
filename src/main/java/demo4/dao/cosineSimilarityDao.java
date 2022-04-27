package demo4.dao;

import java.util.List;

import demo4.model.cosinesimilarity;

public interface cosineSimilarityDao {
	public List<cosinesimilarity> getAllCosineSimilarity();
	public void saveCosineSimilarity(cosinesimilarity cosine);
	public void updateCosineSimilarity(cosinesimilarity cosine);
	public cosinesimilarity getCosineSimilarityByKey(String key);
}
