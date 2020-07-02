package com.max.app.concurrency;

public final class ThreadPoolSizeCalculatorMain {

    public static void main(String[] args) throws Exception {

        int cpus = Runtime.getRuntime().availableProcessors();

        // 70%, can be up to 75%
        double desiredUtilization = 0.70;

        // usually we wait 90% of time and only 10% used as computation time
        double waitToComputeRatio = 90.0 / 10.0;

        int threadsCount = (int) (cpus * desiredUtilization * (1.0 + waitToComputeRatio));

        System.out.printf("CPUs: %d%n", cpus);
        System.out.printf("threads count: %d%n", threadsCount);

        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }


}
