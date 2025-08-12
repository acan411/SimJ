package priv.acan.framework;

/**
 * 超时事件
 *
 * @author acan
 * @since 2025/08/12 14:27
 */
public class Timeout {

    private Environment env;

    public Timeout(Environment env) {
        this.env = env;
    }

    public void wait(double delay, Runnable callback) {
        env.schedule(new Event() {
            @Override
            public void execute() {
                callback.run();
            }
        }, delay);
    }

}