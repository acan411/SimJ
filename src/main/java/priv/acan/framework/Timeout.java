package priv.acan.framework;

import lombok.RequiredArgsConstructor;

/**
 * 超时事件
 *
 * @author acan
 * @since 2025/08/12 14:27
 */
@RequiredArgsConstructor
public class Timeout {

    private final Environment env;

    public void wait(double delay, Runnable callback) {
        env.schedule(new Event() {
            @Override
            public void execute() {
                callback.run();
            }
        }, delay);
    }

}