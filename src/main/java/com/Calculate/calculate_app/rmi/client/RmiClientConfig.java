package com.Calculate.calculate_app.rmi.client;

import com.Calculate.calculate_app.rmi.common.RemoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
@DependsOn("rmiServiceExporter") // 强制等待服务端初始化完成
public class RmiClientConfig {

    // 客户端代理 Bean
    @Bean
    public RmiProxyFactoryBean calculatorService() {
        RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
        proxy.setServiceUrl("rmi://localhost:1099/RemoteService");
        proxy.setServiceInterface(RemoteService.class);
        return proxy;
    }
}