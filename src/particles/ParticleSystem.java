package particles;

import Data.*;
import logic.*;

import java.awt.image.*;
import java.util.*;

public class ParticleSystem
{
    private Particle[] particles;
    private int x, y;
    private int xRange, yRange;
    private int maxLife;
    private String[] spriteTags;

    public ParticleSystem(int numParticle, int x, int y, int xRange, int yRange, int minLife, int maxLife, int xMove, int yMove, int minDelay, int maxDelay, String[] spriteTags)
    {
        this.xRange = xRange;
        this.yRange = yRange;
        this.x = x;
        this.y = y;
        this.maxLife = maxLife;
        this.particles = new Particle[numParticle];
        this.spriteTags = spriteTags;
        initParticles(xMove, yMove, minDelay, maxDelay, minLife);
    }

    public Particle[] getParticleArray()
    {
        return particles;
    }

    public Iterator<Frame> getParticles()
    {
        List<Frame> parts = new ArrayList<>();
        for (Particle particle : particles)
        {
            Frame temp = particle.getCurrentFrame();
            parts.add(temp);
        }
        return parts.iterator();
    }

    public static void renderParticleSystem(Control ctrl, FireFly fireFly)
    {
        ParticleSystem particleSystem = fireFly.getParticleSystem();
        Iterator<Frame> iterator2 = particleSystem.getParticles();
        while (iterator2.hasNext())
        {
            Frame frame = iterator2.next();
            BufferedImage image = ctrl.getSpriteFromBackBuffer(frame.getSpriteTag()).getSprite();
            BufferedImage fireFlyCopy = image.getSubimage(0, 0, 32, 32);
            Sprite sprite = new Sprite(frame.getX(), frame.getY(), fireFlyCopy, frame.getSpriteTag());
            ctrl.addSpriteToFrontBuffer(sprite);
        }
    }

    private void initParticles(int xMove, int yMove, int minDelay, int maxDelay, int minLife)
    {
        for(int i = 0; i < particles.length; i++)
        {
            int n = spriteTags.length;
            int index = Particle.getRandomInt(0, n-1);
            particles[i] = new Particle(x,(x+xRange), y, (y+yRange), spriteTags[index], minLife, maxLife, xMove, yMove, minDelay, maxDelay);
        }
        boolean isDone = false;
        while(!isDone)
        {
            isDone = true;
            for(int i = 0; i < particles.length; i++)
            {
                particles[i].simulateAge();
                if(!particles[i].hasBeenReset())
                {
                    isDone = false;
                }
            }
        }
    }
}
