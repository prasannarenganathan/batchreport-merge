package com.cnasurety.extagencyint.ivans.batches.merge.pubsub;

import java.util.List;


public interface PubSubService {

	public String sendNotifications(List<String> notificationsIds);
	
}
