package com.gla.catarina.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ThreadUtil implements InitializingBean {

    private static ThreadPoolTaskExecutor taskExecutor;


    @Override
    public void afterPropertiesSet() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(50);
        executor.setCorePoolSize(10);
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(300);
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        ThreadUtil.taskExecutor = executor;
    }

    /**
     * excu task
     *
     * @param runnable task
     */
    public static void run(Runnable runnable) {
        try {
            taskExecutor.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
