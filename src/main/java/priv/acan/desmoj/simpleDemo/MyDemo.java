package priv.acan.desmoj.simpleDemo;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

/**
 * @author acan
 * @since 2025/08/12 16:31
 */
public class MyDemo {

    public static void main(String[] args) {
        // 创建实验
        Experiment experiment = new Experiment("MyExperiment");
        MyModel model = new MyModel(null, "MyModel", false, false);
        model.connectToExperiment(experiment);

        // 设置实验参数
        experiment.setShowProgressBar(false);
        // 仿真结束时间
        experiment.stop(new TimeInstant(100.0));

        // 启动仿真
        experiment.start();
//        experiment.report();
        experiment.finish();
    }

}
