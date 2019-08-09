package com.cnasurety.extagencyint.ivans.batches.merge.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnasurety.extagencyint.ivans.batches.merge.exception.IvansBatchItemException;


@Service
public class NotificationItemWriter implements ItemWriter<Map<String,String>> {

	@Autowired
	NotificationPublishWriter notificationPublishWriter;
	
	public void write(List<? extends Map<String,String>> keyMaps) throws IvansBatchItemException {
	      
	  try {
	
	List<String> globalNotificationIds = new ArrayList<String>();
	Map<String,String> keyMap = new HashMap<String,String>();
		for(Map<String,String> map: keyMaps) {
			keyMap.putAll(map);
		}
		
		globalNotificationIds = keyMap.values().stream()
				.collect(Collectors.toList());
		notificationPublishWriter.writeMessage(globalNotificationIds);
	} catch(Exception exception){ 			
		 throw new IvansBatchItemException("Error in NotificationItemWriter",exception);
	}
	}	  	

}
