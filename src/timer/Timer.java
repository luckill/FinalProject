package timer;

public class Timer
{
    private long duration;
    private long start;

    public Timer(long duration)
    {
        this.duration = duration * 60 * 1000;
        resetWatch();
    }

    public Timer()
    {
        this(Long.MAX_VALUE);
    }

    public boolean isTimeUp()
    {
        int current = (int)System.currentTimeMillis();
        int duration = (int)this.duration;
        int start = (int)this.start;
        return (current - start) >= duration;
    }

    public long getRemainingTime()
    {
        return duration - getElapsedTime();
    }

    public long getElapsedTime()
    {
        long current = System.currentTimeMillis();
        return current - start;
    }

    public long getElapsedTimeInMinute()
    {
        int current = (int)System.currentTimeMillis();
        int start = (int)this.start/60000;
        return current - start;
    }

    public void resetWatch()
    {
        start = System.currentTimeMillis();
    }
}
