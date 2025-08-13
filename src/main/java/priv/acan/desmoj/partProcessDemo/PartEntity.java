package priv.acan.desmoj.partProcessDemo;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.*;

/**
 * 零件实体
 *
 * @author acan
 * @since 2025/08/12 17:20
 */
public class PartEntity extends SimProcess {

    private final TimeInstant arrivalTime;

    public PartEntity(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.arrivalTime = presentTime();
    }

    public double getWaitingTime() {
        return presentTime().getTimeAsDouble() - arrivalTime.getTimeAsDouble();
    }

    @Override
    public void lifeCycle() throws SuspendExecution {
        PartProcessingModel model = (PartProcessingModel) getModel();

        // 1. 进入队列等待机器
        model.getMachineQueue().insert(this);
        sendTraceNote("Part " + getName() + " enters queue.");

        // 2. 如果队列只有当前零件（即机器空闲），开始加工
        if (model.getMachineQueue().length() == 1) {
            processPart();
        }
    }

    private void processPart() throws SuspendExecution {
        PartProcessingModel model = (PartProcessingModel) getModel();

        // 1. 从队列中取出当前零件
        Queue<PartEntity> machineQueue = model.getMachineQueue();
        PartEntity part = machineQueue.first();
        machineQueue.remove(part);

        System.out.println(part.getName());

        // 2. 模拟加工时间
        double duration = model.getProcessingTime().sample();
        sendTraceNote("Part " + part.getName() + " starts processing for " + duration + " minutes.");
//        hold(new TimeSpan(duration));
        if (!part.isScheduled()) {
            hold(new TimeSpan(duration));
        }

        // 3. 加工完成
        sendTraceNote("Part " + part.getName() + " finished. Waited " + part.getWaitingTime() + " minutes.");

        // 4. 检查队列中是否有下一个零件
        if (!machineQueue.isEmpty()) {
            // 递归处理下一个
            machineQueue.first().processPart();
        }
    }
}
