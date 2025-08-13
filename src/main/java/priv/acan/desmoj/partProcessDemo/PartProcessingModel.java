package priv.acan.desmoj.partProcessDemo;

import desmoj.core.simulator.*;
import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.TimeSpan;
import lombok.Getter;

/**
 * @author acan
 * @since 2025/08/12 17:19
 */
@Getter
public class PartProcessingModel extends Model {

    // 随机分布：零件到达间隔时间 & 加工时间
    private ContDistExponential arrivalTime;
    private ContDistUniform processingTime;

    // 用 Queue 模拟机器队列（FIFO）
    private Queue<PartEntity> machineQueue;

    public PartProcessingModel(Model owner, String name, boolean showInReport, boolean showInTrace) {
        super(owner, name, showInReport, showInTrace);
    }

    @Override
    public String description() {
        return "Part Processing Demo for DESMO-J 2.5.1e";
    }

    @Override
    public void init() {
        // 初始化随机分布（到达间隔时间均值=5分钟，加工时间范围=2~8分钟）
        arrivalTime = new ContDistExponential(this, "Arrival Time", 5.0, true, false);
        processingTime = new ContDistUniform(this, "Processing Time", 2.0, 8.0, true, false);

        // 初始化队列（模拟机器）
        machineQueue = new Queue<>(this, "Machine Queue", true, true);
    }

    @Override
    public void doInitialSchedules() {
        // 安排第一个零件到达事件
//        new PartArrivalEvent(this, "Part Arrival", true).schedule(new TimeSpan(0));

        PartProcessingModel model = (PartProcessingModel) getModel();
        // 创建新零件并激活其生命周期
        PartEntity newPart = new PartEntity(model, "Part-" + model.presentTime(), true);
        newPart.activate();
        new PartArrivalEvent(this, "Part Arrival", true).schedule(newPart, new TimeSpan(1));
    }


}
