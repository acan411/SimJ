package priv.acan.desmoj;

import desmoj.core.simulator.*;

/**
 * 定义模型类
 *
 * @author acan
 * @since 2025/08/12 16:13
 */

// 1. 定义模型类
public class MyModel extends Model {

    public MyModel(Model owner, String name, boolean showInReport, boolean showInTrace) {
        super(owner, name, showInReport, showInTrace);
    }

    @Override
    public String description() {
        return "My DESMO-J Model";
    }

    @Override
    public void doInitialSchedules() {
        // 初始化事件调度
        MyEvent event = new MyEvent(this, "First Event", true);
        event.schedule(new MyEntity(this, "Entity", true), new TimeSpan(0.0));
    }

    @Override
    public void init() {

    }

}
