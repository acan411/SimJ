package priv.acan.desmoj.simpleDemo;

import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;

/**
 * 定义事件类
 *
 * @author acan
 * @since 2025/08/12 16:30
 */
public class MyEvent extends Event<MyEntity> {

    public MyEvent(Model model, String s, boolean b) {
        super(model, s, b);
    }

    @Override
    public void eventRoutine(MyEntity entity) {
        // 事件逻辑
        sendTraceNote("Event triggered for " + entity.getName());
    }
}
