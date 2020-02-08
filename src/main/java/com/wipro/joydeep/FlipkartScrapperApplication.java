package com.wipro.joydeep;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class FlipkartScrapperApplication {
	/*
	 * @Autowired private FlipkartService fs;
	 */
	
	
	public static void main(String[] args) {
		SpringApplication.run(FlipkartScrapperApplication.class, args);
	}
	
	   @Bean
	    public Executor taskExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(10);
	        executor.setMaxPoolSize(10);
	        executor.setQueueCapacity(500);
	        executor.setThreadNamePrefix("FlipkartScrapper");
	        executor.initialize();
	        return executor;
	    }

	
	
	
	/*
	 * @PostConstruct public void performAction() { fs.performAction(); }
	 */

}
