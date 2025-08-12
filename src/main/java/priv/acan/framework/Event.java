package priv.acan.framework;

/**
 * 事件基类
 *
 * @author acan
 * @since 2025/08/12 14:26
 */
public abstract class Event implements Comparable<Event> {

    double time;

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }

    public abstract void execute();

}