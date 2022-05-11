package demo4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.tempcosinesimilarity;

@Repository
public class tempCosineSimilarityDaoImpl implements tempCosineSimilarityDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public List<tempcosinesimilarity> getAllTempCosineSimilarity() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From tempcosinesimilarity";
		List<tempcosinesimilarity> l = session.createQuery(hql,tempcosinesimilarity.class).list();
		return l;
	}

	@Override
	public void saveTempCosineSimilarity(tempcosinesimilarity cosine) {
		Session session = sessionfactory.getCurrentSession();
		session.save(cosine);		
	}

	@Override
	public void updateTempCosineSimilarity(tempcosinesimilarity cosine) {
		Session session = sessionfactory.getCurrentSession();
		session.update(cosine);
	}

	@Override
	public tempcosinesimilarity getTempCosineSimilarityByKey(String key) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(tempcosinesimilarity.class, key);
	}

}
