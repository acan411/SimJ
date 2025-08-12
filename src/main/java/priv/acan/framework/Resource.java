package priv.acan.framework;

import lombok.Getter;
import priv.acan.framework.intf.Request;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 资源管理
 *
 * @author acan
 * @since 2025/08/12 14:27
 */
public class Resource {

    private final Environment env;
    @Getter
    private final int capacity;
    private final Queue<Request> queue = new LinkedList<>();
    private int available;

    public Resource(Environment env, int capacity) {
        this.env = env;
        this.capacity = capacity;
        this.available = capacity;
    }

    public void request(Request request) {
        if (available > 0) {
            available--;
            env.schedule(new Event() {
                @Override
                public void execute() {
                    request.succeed();
                }
            }, 0);
        } else {
            queue.add(request);
        }
    }

    public void release() {
        available++;
        if (!queue.isEmpty()) {
            Request request = queue.poll();
            request.succeed();
            available--;
        }
    }

}
