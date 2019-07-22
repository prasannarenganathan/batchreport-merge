package com.cnasurety.extagencyint.ivans.batches.merge.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnasurety.extagencyint.ivans.batches.merge.model.Notification;
import com.cnasurety.extagencyint.ivans.batches.merge.repository.NotificationRepository;
import com.cnasurety.extagencyint.ivans.batches.merge.util.MergeConstants;
@Service
public class NotificationReader {

	@Autowired
    NotificationRepository notificationRepository;
	
	public List<String> readNotifications(){
		List<Notification> notifications = notificationRepository.findByNotificationWorkflowStatusTypeCode(MergeConstants.DEFERRED);
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
