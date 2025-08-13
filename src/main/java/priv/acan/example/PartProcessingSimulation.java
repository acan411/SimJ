package priv.acan.example;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * @author acan
 * @since 2025/08/13 09:19
 */
public class PartProcessingSimulation {

    // 仿真时钟
    private double currentTime;

    // 事件队列（按时间排序）
    private final PriorityQueue<Event> eventQueue;

    // 机器状态
    private boolean machineBusy;
    private final Queue<Part> waitingQueue;

    // 统计变量
    private int partsProcessed;
    private double totalWaitingTime;
    private double machineBusyTime;
    // 添加最大队列长度跟踪
    private int maxQueueLength = 0;

    // 随机数生成器
    private final Random random;

    // 参数配置
    private final double SIMULATION_DURATION = 480; // 8小时(分钟)
    private final double MEAN_ARRIVAL_TIME = 5.0;  // 平均到达间隔时间
    private final double MIN_PROCESS_TIME = 2.0;    // 最小加工时间
    private final double MAX_PROCESS_TIME = 8.0;    // 最大加工时间

    public PartProcessingSimulation() {
        this.currentTime = 0.0;
        this.eventQueue = new PriorityQueue<>();
        this.machineBusy = false;
        this.waitingQueue = new LinkedList<>();
        this.partsProcessed = 0;
        this.totalWaitingTime = 0.0;
        this.machineBusyTime = 0.0;
        this.random = new Random();
    }

    // 运行仿真
    public void run() {
        // 安排第一个零件到达事件
        scheduleArrival();

        // 主仿真循环
        while (!eventQueue.isEmpty() && currentTime <= SIMULATION_DURATION) {
            Event event = eventQueue.poll();
            currentTime = event.time;

            if (event instanceof ArrivalEvent) {
                handleArrival();
            } else if (event instanceof FinishProcessingEvent) {
                handleFinishProcessing();
            }
        }

        // 输出统计结果
        printStatistics();
    }

    // 处理零件到达事件
    private void handleArrival() {
        // 创建新零件
        Part part = new Part(currentTime);

        // 如果机器空闲，立即开始加工
        if (!machineBusy) {
            machineBusy = true;
            double processingTime = MIN_PROCESS_TIME +
                    (MAX_PROCESS_TIME - MIN_PROCESS_TIME) * random.nextDouble();
            scheduleFinishProcessing(processingTime);
        } else {
            // 机器忙，加入等待队列
            waitingQueue.add(part);
            // 更新最大队列长度
            if (waitingQueue.size() > maxQueueLength) {
                maxQueueLength = waitingQueue.size();
            }
        }

        // 安排下一个零件到达
        scheduleArrival();
    }

    // 处理加工完成事件
    private void handleFinishProcessing() {
        partsProcessed++;
        machineBusy = false;

        // 如果有等待的零件，开始加工下一个
        if (!waitingQueue.isEmpty()) {
            machineBusy = true;
            Part nextPart = waitingQueue.poll();

            // 计算等待时间
            double waitTime = currentTime - nextPart.arrivalTime;
            totalWaitingTime += waitTime;

            double processingTime = MIN_PROCESS_TIME +
                    (MAX_PROCESS_TIME - MIN_PROCESS_TIME) * random.nextDouble();
            scheduleFinishProcessing(processingTime);
        }
    }

    // 安排零件到达事件
    private void scheduleArrival() {
        // 生成服从指数分布的零件到达间隔时间
        double arrivalTime = currentTime + (-MEAN_ARRIVAL_TIME * Math.log(1 - random.nextDouble()));
        if (arrivalTime <= SIMULATION_DURATION) {
            eventQueue.add(new ArrivalEvent(arrivalTime));
        }
    }

    // 安排加工完成事件
    private void scheduleFinishProcessing(double processingTime) {
        double finishTime = currentTime + processingTime;
        machineBusyTime += processingTime;
        eventQueue.add(new FinishProcessingEvent(finishTime));
    }

    // 输出统计结果
    private void printStatistics() {
        System.out.println("\n=== 仿真结果 ===");
        System.out.printf("仿真时间: %.2f 分钟\n", SIMULATION_DURATION);
        System.out.printf("加工零件总数: %d\n", partsProcessed);
        System.out.printf("平均等待时间: %.2f 分钟\n", totalWaitingTime / partsProcessed);
        System.out.printf("机器利用率: %.2f%%\n", (machineBusyTime / SIMULATION_DURATION) * 100);
        System.out.printf("队列最大长度: %d\n", getMaxQueueLength());
    }

    // 实现getMaxQueueLength()方法
    private int getMaxQueueLength() {
        return maxQueueLength;
    }

    // 辅助类和数据结构
    private static class Event implements Comparable<Event> {
        double time;

        public Event(double time) {
            this.time = time;
        }

        @Override
        public int compareTo(Event other) {
            return Double.compare(this.time, other.time);
        }
    }

    private static class ArrivalEvent extends Event {
        public ArrivalEvent(double time) {
            super(time);
        }
    }

    private static class FinishProcessingEvent extends Event {
        public FinishProcessingEvent(double time) {
            super(time);
        }
    }

    private static class Part {
        double arrivalTime;

        public Part(double arrivalTime) {
            this.arrivalTime = arrivalTime;
        }
    }

    public static void main(String[] args) {
        PartProcessingSimulation sim = new PartProcessingSimulation();
        sim.run();
    }
}
