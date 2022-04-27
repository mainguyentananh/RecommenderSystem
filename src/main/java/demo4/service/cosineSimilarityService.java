package demo4.service;

import java.util.List;

import demo4.model.cosinesimilarity;

public interface cosineSimilarityService {
	public List<cosinesimilarity> getAllCosineSimilarity();
	public void saveCosineSimilarity(cosinesimilarity cosine);
	public void updateCosineSimilarity(cosinesimilarity cosine);
	public cosinesimilarity getCosineSimilarityByKey(String key);
}
