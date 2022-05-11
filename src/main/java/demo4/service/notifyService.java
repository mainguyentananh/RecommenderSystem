package demo4.service;

import demo4.model.notify;

public interface notifyService {
	public notify getNotifyById(String id);
	public void saveNotify(notify n);
	public void updateNotify(notify n);
}
