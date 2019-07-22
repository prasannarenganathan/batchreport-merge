package com.cnasurety.extagencyint.ivans.batches.merge.pusub.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.ivans.batches.merge.config.ApplicationConfig;
import com.cnasurety.extagencyint.ivans.batches.merge.pojo.NotificationRequest;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;

 

@Component
public class MessagePublisher {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PubSubTemplate pubSubTemplate;
    
    @Autowired
	ApplicationConfig applicationConfig;
 
    public boolean publish(String desitnation, NotificationRequest request) {
    	
		 
		 try {	
		     PubsubMessage publisherMessage = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(request.toString())).build();
		     pubSubTemplate.publish(applicationConfig.getTopicname(), publisherMessage);
		     
		 }catch(Exception e) {
			 LOGGER.error("Error in publishing topic {}",e);
			 return false;
		 }
       
        LOGGER.info("Published to " + desitnation);
        return true;

    }

 

}