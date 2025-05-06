package com.Calculate.calculate_app.rmi.server;

import com.Calculate.calculate_app.rmi.common.RemoteService;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Configuration
public class RmiServerConfig implements DisposableBean {
    private Registry registry;
    @Override
    public void destroy() throws Exception {
        if (registry != null) {
            try {
                registry.unbind("RemoteService");
            } catch (NotBoundException e) {
                // 忽略解绑失败
            }
            // 强制关闭 RMI 注册表
            UnicastRemoteObject.unexportObject(registry, true);
        }
    }
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RmiServerConfig.class);

    @Bean
    public Registry rmiRegistry() throws RemoteException {
        try {
            // 先尝试获取现有注册表
            return LocateRegistry.getRegistry(1099);
        } catch (RemoteException e) {
            // 如果不存在，则创建新注册表
            return LocateRegistry.createRegistry(1099);
        }
    }
    @Bean
    public RemoteService remoteService() throws RemoteException {
        return new RemoteServiceImpl();
    }

    @Bean
    public RmiServiceExporter rmiServiceExporter() throws RemoteException {
//        try {
//            // 手动创建 RMI 注册表（如果不存在）
//            Registry registry = LocateRegistry.createRegistry(1099);
//            System.out.println("RMI 注册表已启动在端口 1099");
//        } catch (RemoteException e) {
//            System.out.println("RMI 注册表已存在，无需重复创建");
//        }
        logger.info("RmiServiceExporter开始");
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("RemoteService"); // 服务名称必须与客户端一致
        exporter.setServiceInterface(RemoteService.class);
        exporter.setService(remoteService());
        exporter.setRegistryPort(1099); // 明确指定注册表端口
        logger.info("RmiServiceExporter结束");
        return exporter;
    }
}
