package com.Calculate.calculate_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {

    // 任务调度专用线程池（用于@Scheduled）
//    @Bean("taskScheduler")
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(5); // 最大线程数
//        scheduler.setThreadNamePrefix("scheduled-task-");
//        scheduler.initialize();
//        return scheduler;
//    }
//
//    // 异步方法专用线程池（用于@Async）
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(5);
//        executor.setQueueCapacity(10);
//        executor.setThreadNamePrefix("Calculation-");
//        executor.initialize();
//        return executor;
//    }

    @Bean(name = "calculationExecutor")
    public Executor calculationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 核心线程数
        executor.setMaxPoolSize(10); // 最大线程数
        executor.setQueueCapacity(20); // 任务队列容量
        executor.setThreadNamePrefix("Calculation-");
        executor.initialize();
        return executor;
    }
}