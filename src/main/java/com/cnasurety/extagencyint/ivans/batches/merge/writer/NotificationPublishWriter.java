package com.cnasurety.extagencyint.ivans.batches.merge.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cnasurety.extagencyint.ivans.batches.merge.pojo.NotificationRequest;
import com.cnasurety.extagencyint.ivans.batches.merge.pusub.messaging.MessagePublisher;

@Service
public class NotificationPublishWriter {
	 @Autowired
	 MessagePublisher publisher;

	    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	    @Value( "${gcp.cna.sur.bond.notify.topic.name}" )
	    private String ivansDeliveryTopic;

	    public void writeMessage(List<String> globalNotificationIds) {
	    	
	    	for(String notificationId: globalNotificationIds) {
	    		 LOGGER.info("Notification Global ID recieved as: " + notificationId);
	 	        NotificationRequest notificationRequest = new NotificationRequest();
	 	        notificationRequest.setNotificationGlobalId(notificationId);
	 	        publisher.publish(this.ivansDeliveryTopic, notificationRequest);
	 	        
	 	        LOGGER.info("Published to " + this.ivansDeliveryTopic);
			}
	       
	        
	        
	        }
}
