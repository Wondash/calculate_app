package com.Calculate.calculate_app.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteService extends Remote {
    String sayHello() throws RemoteException;

    public double calculate(String method, List<Double> numbers) throws RemoteException ;
    public int fibonacci(int n) throws RemoteException;
    double add(double... numbers)throws RemoteException;
    //减法
    double subtract(double first, List<Double> others) throws RemoteException;
    // 乘法
    double multiply(List<Double> numbers)throws RemoteException;

    // 除法
    double divide(double first, List<Double> others) throws RemoteException;

    // 取模
    double modulo(double dividend, double divisor)throws RemoteException;
    // 幂运算
    double power(double base, double exponent)throws RemoteException;
    // 开方
    double squareRoot(List<Double> number)throws RemoteException;
    // 正弦函数
    double sine(double angleInRadians)throws RemoteException;
    // 余弦函数
    double cosine(double angleInRadians)throws RemoteException;
    // 正切函数
    double tangent(double angleInRadians)throws RemoteException;
    // 自然对数
    double naturalLog(double number)throws RemoteException;
    // 以 10 为底的对数
    double log10(double number)throws RemoteException;
//    public void asyncTask() throws RemoteException;
//    public void scheduledTask() throws RemoteException;
}
