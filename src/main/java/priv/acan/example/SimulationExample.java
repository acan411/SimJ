package priv.acan.example;

import priv.acan.framework.Environment;
import priv.acan.framework.Timeout;

/**
 * 基本超时
 *
 * @author acan
 * @since 2025/08/12 14:28
 */
public class SimulationExample {

    public static void main(String[] args) {
        Environment env = new Environment();
        Timeout timeout = new Timeout(env);

        env.process(() -> {
            System.out.println("Start at " + env.now());
            timeout.wait(5, () -> {
                System.out.println("Resumed at " + env.now());
            });
        });

        env.run(10);
    }

}
