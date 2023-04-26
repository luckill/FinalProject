package particles;

import Data.*;
import timer.*;

public class Particle
{
    private int x,y;
    private String particleSpriteTag;
    private int lifeCycle;
    private int age;
    private int xMove,yMove;
    private stopWatchX timer;
    private int rootX, rootY;
    private boolean isReset;

    public Particle(int minX, int maxX, int minY, int maxY, String particleSpriteTag, int minLife, int maxLife, int xMove, int yMove, int minDelay, int maxDelay)
    {
        this.particleSpriteTag = particleSpriteTag;
        this.x = getRandomInt(minX, maxX);
        this.y = getRandomInt(minY,maxY);
        this.lifeCycle = getRandomInt(minLife,maxLife);
        this.xMove = xMove;
        this.yMove = yMove;
        int delay = getRandomInt(minDelay, maxDelay);
        this.timer = new stopWatchX(delay);
        this.rootX = x;
        this.rootY = y;
    }

    public boolean isParticleDead()
    {
        if (age >= lifeCycle)
        {
            return true;
        }
        if(x > 1920 || y > 1080)
        {
            return true;
        }
        return false;
    }

    public void simulateAge()
    {
        if (timer.isTimeUp())
        {
            age++;
            x += xMove;
            y += yMove;
            if(isParticleDead())
            {
                x = rootX;
                y = rootY;
                age = 0;
                isReset = true;
            }
        }
    }

    public Frame getCurrentFrame()
    {
        if(timer.isTimeUp())
        {
            age++;
            x += xMove;
            y += yMove;
            if(isParticleDead())
            {
                x= rootX;
                y = rootY;
                age = 0;
                isReset = true;
            }
            timer.resetWatch();
        }
        return new Frame(x, y, particleSpriteTag);
    }

    public boolean hasBeenReset()
    {
        return isReset;
    }

    public void changeX(int newX)
    {
        x = newX;
    }

    public int getX()
    {
        return x;
    }

    public int getLifeCycle()
    {
        return lifeCycle;
    }

    public int getAge()
    {
        return age;
    }

    public void changeSprite(String newSpriteTag)
    {
        particleSpriteTag = newSpriteTag;
    }

    public static int getRandomInt(int last, int first)
    {
        int diff = last - first;
        double num = Math.random() * diff;
        int intNum = (int)num;
        return first + intNum;
    }

    public static int rollDie(int dieSidea)
    {
        double result = Math.random() * dieSidea;
        int res = (int) result;
        res++;
        return res;
    }
}
