package com.Calculate.calculate_app;

import com.Calculate.calculate_app.rmi.common.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.rmi.RemoteException;

@SpringBootApplication
@EnableScheduling // 启用任务调度
@EnableAsync // 启用异步方法
public class CalculateAppApplication {
	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(CalculateAppApplication.class, args);
	}
}
