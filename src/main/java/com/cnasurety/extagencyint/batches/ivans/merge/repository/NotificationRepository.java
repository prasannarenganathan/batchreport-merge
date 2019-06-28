package com.cnasurety.extagencyint.batches.ivans.merge.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cnasurety.extagencyint.batches.ivans.merge.model.Notification;



@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    
	
@Query(nativeQuery=true,value="SELECT \"NOTIFICATION_GLOBAL_ID\" FROM (select \"NOTIFICATION_GLOBAL_ID\", row_number() over(partition by \"BOND_NUMBER\",\"AGENCY_CODE\",(\"EVENT_TYPE_CODE\"='TRANSACT' AND \"EVENT_SUB_TYPE_CODE\"='NEWBUSN'),(\"EVENT_TYPE_CODE\"='TRANSACT' OR \"EVENT_SUB_TYPE_CODE\"='NEWBUSN'),\"AGENCY_STATE_CODE\",\"WRITING_COMPANY_CODE\",\"BOND_NUMBER\",\"TERM_EFFECTIVE_DATE\",\"TERM_EXPIRY_DATE\" order by \"NOTIFICATION_GLOBAL_ID\")  rownumber from workflow.\"NOTIFICATION_TBL\" WHERE \"NOTIFICATION_WORKFLOW_STATUS_TYPE_CODE\" ='DEFERRED' ) AS NGID WHERE rownumber=1")
   List<String> findByStatus();
}
