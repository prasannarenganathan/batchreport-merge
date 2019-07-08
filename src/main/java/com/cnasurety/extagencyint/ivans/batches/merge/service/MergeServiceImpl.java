package com.cnasurety.extagencyint.ivans.batches.merge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.ivans.batches.merge.model.Notification;
import com.cnasurety.extagencyint.ivans.batches.merge.pubsub.PubSubService;
import com.cnasurety.extagencyint.ivans.batches.merge.repository.NotificationRepository;
import com.cnasurety.extagencyint.ivans.batches.merge.util.MergeConstants;

@Component
public class MergeServiceImpl implements MergeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    NotificationRepository notificationRepository;
    
    @Autowired
    PubSubService pubSubService;

	public void mergeAndSendNotification() {

		List<Notification> notifications = notificationRepository.findByNotificationWorkflowStatusTypeCode(MergeConstants.DEFERRED);
		
		//process notifications for publishing the notifications and update the status to STAGED from DEFERRED
		List<String> globalNotificationIds = processNotifcations(notifications);
		
		// send notification Ids to PubSub Service
		pubSubService.sendNotifications(globalNotificationIds);
		

	}
	
	
	private List<String> processNotifcations(List<Notification> notifications){
		Map<String,String> notificationKey = new HashMap<String,String>();
		StringBuilder key =null;
		for (Notification notification : notifications) {
			key = new StringBuilder(notification.getBondNumber()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getAgencyCode()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getEventTypeCode()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getEventSubTypeCode()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getAgencyStateCode()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getWritingCompanyCode()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getTermEffectiveDate().toString()).append(MergeConstants.VALUE_SEPARATOR)
					.append(notification.getTermExpiryDate());			
			if(notification.getEventTypeCode().equals(MergeConstants.TRANSACT)&& notification.getEventSubTypeCode().equals(MergeConstants.NEWBUSN)) {
				notificationKey.put(key.toString(), notification.getNotificationGlobalId());
			}else {
				notificationKey.put(key.toString(), notification.getNotificationGlobalId());
			}
			notification.setNotificationWorkflowStatusTypeCode(MergeConstants.STAGED);
			notificationRepository.save(notification);
		}
		return new ArrayList<String>(notificationKey.values());
	}
}
