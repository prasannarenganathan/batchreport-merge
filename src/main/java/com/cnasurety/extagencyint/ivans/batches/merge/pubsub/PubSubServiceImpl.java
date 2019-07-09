package com.cnasurety.extagencyint.ivans.batches.merge.pubsub;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.ivans.batches.merge.config.ApplicationConfig;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

@Component
public class PubSubServiceImpl implements PubSubService{

	@Autowired
	ApplicationConfig applicationConfig;
	
	@Override
	public String sendNotifications(List<String> notificationsIds) {
	
		 String projectId = ServiceOptions.getDefaultProjectId();
		 ProjectTopicName topicName = ProjectTopicName.of(projectId,applicationConfig.getTopicname());
		 try {
			 Publisher publisher = Publisher.newBuilder(topicName).build(); 
		     ByteString data = ByteString.copyFromUtf8(notificationsIds.get(0));
		     PubsubMessage pubSubMessage = PubsubMessage.newBuilder().setData(data).build();
		     ApiFuture<String> future = publisher.publish(pubSubMessage);
		     
		     ApiFutures.addCallback(future, new ApiFutureCallback<String>() {
		    	 public void onFailure(final Throwable throwable) {
		    		 if(throwable instanceof ApiException) {
		    			 final ApiException apiException = ((ApiException)throwable);
		    			 System.out.println(apiException.getStatusCode().getCode());
		    		 }
		    		 
		    	 }

				@Override
				public void onSuccess(String messageId) {
					System.out.println(messageId);
					
				};
			} 
		    		 
		    		 );
		     
		 }catch(Exception e) {
			 
		 }
		 
		return null;
	}

}
