package priv.acan.desmoj.partProcessDemo;

import co.paralleluniverse.fibers.SuspendExecution;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

/**
 * @author acan
 * @since 2025/08/12 17:21
 */
public class PartArrivalEvent extends Event<PartEntity> {
    public PartArrivalEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
    }

    @Override
    public void eventRoutine(PartEntity part) throws SuspendExecution {
        PartProcessingModel model = (PartProcessingModel) getModel();

        // 1. 创建新零件并激活其生命周期
        PartEntity newPart = new PartEntity(model, "Part-" + model.presentTime(), true);
        newPart.activate();

        // 2. 安排下一个到达事件
        new PartArrivalEvent(model, "Part Arrival", true)
//                .schedule(new TimeSpan(model.arrivalTime.sample()));
                .schedule(newPart, new TimeSpan(model.getArrivalTime().sample()));
    }
}