package com.cnasurety.extagencyint.batches.ivans.merge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.batches.ivans.merge.model.Notification;
import com.cnasurety.extagencyint.batches.ivans.merge.repository.NotificationRepository;
import com.cnasurety.extagencyint.batches.ivans.merge.util.MergeConstants;

@Component
public class MergeServiceImpl implements MergeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    NotificationRepository notificationRepository;

	public void mergeAndSendNotification() {

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
		}
		List<String> notificationGlobalIdList = new ArrayList<String>(notificationKey.values());
		System.out.println(notificationGlobalIdList);

	}
}
