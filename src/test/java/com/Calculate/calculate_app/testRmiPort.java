package com.Calculate.calculate_app;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class testRmiPort {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        String[] services = registry.list();
        System.out.println("已注册的服务: " + Arrays.toString(services)); // 应包含 "RemoteService"
    }
}
