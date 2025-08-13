package priv.acan.desmoj.partProcessDemo;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

/**
 * @author acan
 * @since 2025/08/12 17:22
 */
public class PartProcessDemo {

    public static void main(String[] args) {
        // 创建实验
        Experiment experiment = new Experiment("PartProcessingExperiment");
        PartProcessingModel model = new PartProcessingModel(null, "PartProcessingModel", true, true);
        model.connectToExperiment(experiment);

        // 配置实验（仿真时间, 单位秒）
        experiment.setShowProgressBar(false);
        experiment.stop(new TimeInstant(1000L));
        // 禁用文件输出
        experiment.setSilent(false);

        // 启动仿真
        experiment.start();
        experiment.report();
        experiment.finish();
    }
}
