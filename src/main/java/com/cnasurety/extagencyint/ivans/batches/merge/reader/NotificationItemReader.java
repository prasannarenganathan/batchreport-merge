package com.cnasurety.extagencyint.ivans.batches.merge.reader;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnasurety.extagencyint.ivans.batches.merge.exception.IvansBatchItemException;
import com.cnasurety.extagencyint.ivans.batches.merge.model.Notification;
import com.cnasurety.extagencyint.ivans.batches.merge.repository.NotificationRepository;
import com.cnasurety.extagencyint.ivans.batches.merge.util.MergeConstants;



@Service
public class NotificationItemReader implements ItemReader<Notification>{

	
	@Autowired
    NotificationRepository notificationRepository;
	
	    private ItemReader<Notification> delegate;
	    
		
		@Override
		public Notification read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
			if (delegate == null) {
	            delegate = new IteratorItemReader<>(readTable());
	        }
	        return  delegate.read();
		}
		
	 	public List<Notification> readTable() throws IvansBatchItemException{
	 		List<Notification> notifications = null;

	        try {
	         notifications = notificationRepository.findByNotificationWorkflowStatusTypeCode(MergeConstants.DEFERRED);
	        }catch(Exception e){
				
				 throw new IvansBatchItemException("Error in NotificationItemReader",e);
			 }
	        Notification n =new Notification();
	        n.setNotificationGlobalId("111111");
	        n.setEventTypeCode("TEST1");
	        notifications.add(n);
	        n =new Notification();
	        n.setNotificationGlobalId("111111");
	        n.setEventTypeCode("TEST2");
	        notifications.add(n);
	 		return notifications;
	 	}
	
 
}
