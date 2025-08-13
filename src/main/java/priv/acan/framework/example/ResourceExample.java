package priv.acan.framework.example;

import priv.acan.framework.Environment;
import priv.acan.framework.Resource;
import priv.acan.framework.Timeout;

/**
 * 资源竞争
 *
 * @author acan
 * @since 2025/08/12 14:28
 */
public class ResourceExample {

    public static void main(String[] args) {
        Environment env = new Environment();
        Resource resource = new Resource(env, 2); // 容量为2的资源
        Timeout timeout = new Timeout(env);

        // 创建3个进程竞争资源
        for (int i = 0; i < 3; i++) {
            int id = i;
            env.process(() -> {
                System.out.println("Process " + id + " requests at " + env.now());

                resource.request(() -> {
                    System.out.println("Process " + id + " got resource at " + env.now());

                    timeout.wait(3, () -> {
                        System.out.println("Process " + id + " releases at " + env.now());
                        resource.release();
                    });
                });
            });
        }

        env.run(10);
    }

}
