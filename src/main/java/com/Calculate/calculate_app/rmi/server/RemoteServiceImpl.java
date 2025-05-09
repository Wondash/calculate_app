package com.Calculate.calculate_app.rmi.server;

import com.Calculate.calculate_app.rmi.common.RemoteService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RemoteServiceImpl implements RemoteService {
    protected RemoteServiceImpl() throws RemoteException {
        super();
    }
    @Override

    public double calculate(String method, List<Double> numbers) throws RemoteException {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        return switch (method) {
            case "加法" -> numbers.stream().mapToDouble(Double::doubleValue).sum();
            case "取平均数" -> numbers.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            case "Fibonacci数列的计算" -> Fibonacci(numbers);
            case "减法" -> subtract(numbers.get(0), numbers.subList(1, numbers.size()));
            case "乘法" -> multiply(numbers);
            case "除法" -> divide(numbers.get(0), numbers.subList(1, numbers.size()));
            case "测试" -> test(numbers);
            case "开方" -> squareRoot(numbers);
            default -> throw new RemoteException("不支持的计算方法: " + method);
        };
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Hello from RMI server!";
    }

//    // 使用配置好的taskScheduler线程池执行定时任务
//    @Scheduled(fixedRate = 5000, scheduler = "taskScheduler")
//    public void scheduledTask() throws RemoteException{
////        System.out.println("定时任务执行，当前线程：" + Thread.currentThread().getName());
//        // 可调用其他异步方法或多线程逻辑
//        asyncTask();
//    }
//    @Async
//    public void asyncTask() throws RemoteException{
////        System.out.println("异步任务执行，当前线程：" + Thread.currentThread().getName());
//    }
    //@Async("getAsyncExecutor
    private double test(List<Double> n) throws RemoteException {
        if (n.size() >= 2) {
            throw new RemoteException("请输入单个参数");
        }
        double a = n.get(0);
        int b = (int)a;
        for (int i = 0; i < b; i++) {
            for (int j = i + 1; j < b; j++) {
                for (int k = j + 1; k < b; k++) {
                    a = (a*a*a)%1000000007;
                }
            }
        }
        return a;
    }
    public double add(double... numbers)throws RemoteException {
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }
        return sum;
    }
    public double Fibonacci(List<Double> numbers) throws RemoteException {
        if (numbers.size() != 1) {
            throw new RemoteException("Fibonacci 数列计算需要一个整数参数");
        }
        Double num = numbers.get(0);
        // 检查是否为整数（避免浮点数，如10.0可接受，10.5报错）
        if (!num.equals(Math.rint(num))) { // 判断是否为整数
            throw new RemoteException("Fibonacci 数列的输入必须为整数");
        }
        int n = num.intValue(); // 安全转换
        if (n < 0) {
            throw new RemoteException("Fibonacci 数列的输入不能为负数");
        }
        return fibonacci(n);
    }

    public int fibonacci(int n) throws RemoteException {
        if (n == 0) {return 0;}
        if (n == 1) {return 1;}
        int a = 0, b = 1, result = 0;
        for (int i = 2; i <= n; i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    //减法
    @Override
    public double subtract(double first, List<Double> others) throws RemoteException{
        if (others == null) {
            throw new RemoteException("减数不能为空");
        }
        double result = first;
        for (double num : others) {
            result -= num;
        }
        return result;
    }

    // 乘法
    @Override
    public double multiply(List<Double> numbers)throws RemoteException {
        if (numbers.isEmpty()) {
            return 0;
        }
        double product = 1;
        for (double num : numbers) {
            product *= num;
        }
        return product;
    }

    // 除法
    @Override
    public double divide(double first, List<Double> others) throws RemoteException {
        if (others.size() != 1 || others.get(0) == 0) {
            throw new RemoteException("除数个数须为1且其值不能为0");
        }
        double result = first;
        for (double num : others) {
            result /= num;
        }
        return result;
    }

    // 取模
    @Override
    public double modulo(double dividend, double divisor) throws RemoteException{
        if (divisor == 0) {
            throw new IllegalArgumentException("除数不能为零");
        }
        return dividend % divisor;
    }
    // 幂运算
    @Override
    public double power(double base, double exponent) throws RemoteException{
        return Math.pow(base, exponent);
    }
    // 开方
    @Override
    public double squareRoot(List<Double> number) throws RemoteException{
        if (number.size()!= 1) {
            throw new IllegalArgumentException("参数不能为空且只支持单个参数的开方");
        }
        return Math.sqrt(number.get(0));
    }
    // 正弦函数
    @Override
    public double sine(double angleInRadians) throws RemoteException{
        return Math.sin(angleInRadians);
    }
    // 余弦函数
    @Override
    public double cosine(double angleInRadians) throws RemoteException{
        return Math.cos(angleInRadians);
    }
    // 正切函数
    @Override
    public double tangent(double angleInRadians) throws RemoteException{
        return Math.tan(angleInRadians);
    }
    // 自然对数
    @Override
    public double naturalLog(double number) throws RemoteException{
        if (number <= 0) {
            throw new IllegalArgumentException("对数的真数必须大于零");
        }
        return Math.log(number);
    }
    // 以 10 为底的对数
    @Override
    public double log10(double number) throws RemoteException{
        if (number <= 0) {
            throw new IllegalArgumentException("对数的真数必须大于零");
        }
        return Math.log10(number);
    }
}
