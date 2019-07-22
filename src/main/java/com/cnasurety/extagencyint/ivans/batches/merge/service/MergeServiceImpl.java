package com.cnasurety.extagencyint.ivans.batches.merge.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.ivans.batches.merge.reader.NotificationReader;
import com.cnasurety.extagencyint.ivans.batches.merge.writer.NotificationWriter;

@Component
public class MergeServiceImpl implements MergeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    NotificationReader reader;
    
    @Autowired
    NotificationWriter writer;
    
   
  

	public void mergeAndSendNotification() {

		List<String> globalNotificationIds = reader.readNotifications();
		writer.writeMessage(globalNotificationIds);		
	}
	
	
	
}
