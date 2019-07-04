package com.cnasurety.extagencyint.ivans.batches.merge.pubsub;

import java.util.List;


public interface PubSubService {

	public void sendNotifications(List<String> notificationsIds);
	
}
