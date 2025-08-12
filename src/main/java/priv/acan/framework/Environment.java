package priv.acan.framework;

import priv.acan.framework.intf.Process;

import java.util.PriorityQueue;

/**
 * 仿真环境
 *
 * @author acan
 * @since 2025/08/12 14:25
 */
public class Environment {

    private double now = 0;
    private final PriorityQueue<Event> eventQueue = new PriorityQueue<>();

    public double now() {
        return now;
    }

    public void schedule(Event event, double delay) {
        event.time = now + delay;
        eventQueue.add(event);
    }

    public void run(double until) {
        while (!eventQueue.isEmpty() && now <= until) {
            Event event = eventQueue.poll();
            now = event.time;
            event.execute();
        }
    }

    public void process(Process process) {
        schedule(new Event() {
            @Override
            public void execute() {
                process.run();
            }
        }, 0);
    }

}