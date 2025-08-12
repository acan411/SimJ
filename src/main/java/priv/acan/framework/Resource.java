package priv.acan.framework;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 资源管理
 *
 * @author acan
 * @since 2025/08/12 14:27
 */
public class Resource {

    private Environment env;
    private int capacity;
    private int available;
    private Queue<Request> queue = new LinkedList<>();

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

    public interface Request {
        void succeed();
    }

}
