package demo4.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import demo4.model.cosinesimilarity;

@Repository
public class cosineSimilarityDaoImpl implements cosineSimilarityDao{

	@Autowired
	private SessionFactory sessionfactory;
	
	@Override
	public List<cosinesimilarity> getAllCosineSimilarity() {
		Session session = sessionfactory.getCurrentSession();
		String hql = "From cosinesimilarity";
		List<cosinesimilarity> list = session.createQuery(hql,cosinesimilarity.class).list();
		return list;
	}

	@Override
	public void saveCosineSimilarity(cosinesimilarity cosine) {
		Session session = sessionfactory.getCurrentSession();
		session.save(cosine);
	}

	@Override
	public void updateCosineSimilarity(cosinesimilarity cosine) {
		Session session = sessionfactory.getCurrentSession();
		session.update(cosine);
	}

	@Override
	public cosinesimilarity getCosineSimilarityByKey(String key) {
		Session session = sessionfactory.getCurrentSession();
		return session.get(cosinesimilarity.class, key);
	}

}
