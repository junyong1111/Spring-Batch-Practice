package com.batch_practice;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@SpringBootApplication
public class BatchPracticeApplication {
	private Logger logger = LoggerFactory.getLogger(BatchPracticeApplication.class);

	@Bean
	public Step simpleStep1(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
		return new StepBuilder("passStep", jobRepository)
				.tasklet(testTasklet, platformTransactionManager).build();

	}
	@Bean
	public Tasklet testTasklet(){
		return ((contribution, chunkContext) -> {
			logger.info(">>>>> PassStep 실행");
			return RepeatStatus.FINISHED;
		});
	}


	@Bean
	public Job passJob(JobRepository jobRepository, Step step){
		return new JobBuilder("passJob", jobRepository)
                .start(step)
				.build();
	}
	public static void main(String[] args) {
		SpringApplication.run(BatchPracticeApplication.class, args);
	}

}
