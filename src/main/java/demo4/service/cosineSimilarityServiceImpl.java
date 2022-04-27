package demo4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.cosineSimilarityDao;
import demo4.model.cosinesimilarity;

@Service
@Transactional
public class cosineSimilarityServiceImpl implements cosineSimilarityService{

	@Autowired
	private cosineSimilarityDao repo;
	
	@Override
	public List<cosinesimilarity> getAllCosineSimilarity() {		
		return repo.getAllCosineSimilarity();
	}

	@Override
	public void saveCosineSimilarity(cosinesimilarity cosine) {
		repo.saveCosineSimilarity(cosine);
	}

	@Override
	public void updateCosineSimilarity(cosinesimilarity cosine) {
		repo.updateCosineSimilarity(cosine);
	}

	@Override
	public cosinesimilarity getCosineSimilarityByKey(String key) {
		return repo.getCosineSimilarityByKey(key);
	}

}
