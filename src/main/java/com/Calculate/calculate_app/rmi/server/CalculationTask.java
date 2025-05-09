package com.Calculate.calculate_app.rmi.server;

import com.Calculate.calculate_app.rmi.common.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.Callable;

public class CalculationTask implements Callable<Double> {
    private RemoteService remoteService;
    private String method;
    private List<Double> parameters;

    @Autowired
    public CalculationTask(RemoteService remoteService, String method, List<Double> parameters) {
        this.remoteService = remoteService;
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public Double call() throws RemoteException {
        return remoteService.calculate(method, parameters);
    }
}
