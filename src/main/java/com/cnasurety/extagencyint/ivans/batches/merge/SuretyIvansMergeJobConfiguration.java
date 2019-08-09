package com.cnasurety.extagencyint.ivans.batches.merge;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.cnasurety.extagencyint.ivans.batches.merge.model.Notification;
import com.cnasurety.extagencyint.ivans.batches.merge.processor.NotificationItemProcessor;
import com.cnasurety.extagencyint.ivans.batches.merge.reader.NotificationItemReader;
import com.cnasurety.extagencyint.ivans.batches.merge.service.MergeService;
import com.cnasurety.extagencyint.ivans.batches.merge.writer.NotificationItemWriter;

@Configuration
@EnableBatchProcessing
@Component
public class SuretyIvansMergeJobConfiguration extends DefaultBatchConfigurer {

	
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    MergeService purgeService;
    
    @Autowired
    NotificationItemReader notificationItemReader;

    @Autowired
    NotificationItemProcessor notificationItemProcessor;

    @Autowired
    NotificationItemWriter notificationItemWriter;

    
    @Bean
    @StepScope
    public Tasklet mergeTransactionsTablesTasklet(@Value("#{jobParameters['message']}") String message) {
        LOGGER.info(message);

        return (stepContribution, chunkContext) -> {
        	purgeService.mergeAndSendNotification();
            return RepeatStatus.FINISHED;
        };
    }

 
    @Bean
    public Step mergeTransactiosStep() {

        return stepBuilderFactory.get("mergeTransactionsTablesTasklet")
                .tasklet(mergeTransactionsTablesTasklet("Merge Notifications, update status and call PUBSUB service ")).build();

    }
    
    @Bean
    public Job PurgeJob(JobCompletionNotificationListener listener, Step purgeStep) {

       
    	 

        return jobBuilderFactory.get("exportIvansMessageJob").incrementer(new RunIdIncrementer()).listener(listener)
                .flow(purgeStep).end().build();
    }

    @Bean
    public Step purgeStep() {
        return stepBuilderFactory.get("notificationStep").<Notification,Map<String,String>>chunk(10)
                .reader(notificationItemReader).processor(notificationItemProcessor).writer(notificationItemWriter)
                .build();
    }
    
   
}
