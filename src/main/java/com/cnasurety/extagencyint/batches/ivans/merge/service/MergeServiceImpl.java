package com.cnasurety.extagencyint.batches.ivans.merge.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.batches.ivans.merge.model.Notification;
import com.cnasurety.extagencyint.batches.ivans.merge.repository.NotificationRepository;

@Component
public class MergeServiceImpl implements MergeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    NotificationRepository notificationRepository;

	public void mergeAndSendNotification() {
		List<String> notifications = notificationRepository.findByStatus();
		for (String gloablId : notifications) {
			System.out.println(gloablId);
		}
		
	}
}
