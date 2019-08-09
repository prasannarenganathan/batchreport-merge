package com.cnasurety.extagencyint.ivans.batches.merge.processor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnasurety.extagencyint.ivans.batches.merge.exception.IvansBatchItemException;
import com.cnasurety.extagencyint.ivans.batches.merge.model.Notification;
import com.cnasurety.extagencyint.ivans.batches.merge.repository.NotificationRepository;
import com.cnasurety.extagencyint.ivans.batches.merge.util.MergeConstants;



@Service
public class NotificationItemProcessor implements ItemProcessor<Notification, Map<String,String>> {

	@Autowired
    NotificationRepository notificationRepository;


@Override
public Map<String,String> process(Notification notification) throws IvansBatchItemException {

try {
	
	StringBuilder key =null;
	Map<String,String> notificationKey= new HashMap<String,String>();
		key = new StringBuilder(notification.getEventTypeCode()).append(MergeConstants.VALUE_SEPARATOR)
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
		
	return  notificationKey;
       
}catch(Exception exception){ 			
	 throw new IvansBatchItemException("Error in NotificationItemProcessor",exception);
}
}
}