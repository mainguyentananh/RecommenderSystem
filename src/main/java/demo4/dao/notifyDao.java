package demo4.dao;

import demo4.model.notify;

public interface notifyDao {
	public notify getNotifyById(String id);
	public void saveNotify(notify n);
	public void updateNotify(notify n);
}
