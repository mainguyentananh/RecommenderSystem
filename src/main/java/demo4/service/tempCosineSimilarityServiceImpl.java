package demo4.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo4.dao.tempCosineSimilarityDao;
import demo4.model.tempcosinesimilarity;

@Transactional
@Service
public class tempCosineSimilarityServiceImpl implements tempCosineSimilarityService{

	@Autowired
	private tempCosineSimilarityDao repo;
	@Override
	public List<tempcosinesimilarity> getAllTempCosineSimilarity() {
		return repo.getAllTempCosineSimilarity();
	}

	@Override
	public void saveTempCosineSimilarity(tempcosinesimilarity cosine) {
		repo.saveTempCosineSimilarity(cosine);
		
	}

	@Override
	public void updateTempCosineSimilarity(tempcosinesimilarity cosine) {
		repo.updateTempCosineSimilarity(cosine);
	}

	@Override
	public tempcosinesimilarity getTempCosineSimilarityByKey(String key) {
	return	repo.getTempCosineSimilarityByKey(key);
	}

}
