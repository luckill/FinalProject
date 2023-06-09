package particles;

public class ButterFly
{
    private ParticleSystem particleSystem;
    private String[] spriteTags;

    public ButterFly(int xPosition, int yPosition, int xRange, int yRange, int minLife,int maxLife, int numberOfParticles)
    {
        spriteTags = new String[5];
        for (int i = 1; i <= 5; i++)
        {
            String string = "butterfly" + i;
            spriteTags[i-1] = string;
        }
        int xSpeed = 8;
        int ySpeed = 2;
        particleSystem = new ParticleSystem(numberOfParticles, xPosition,yPosition,xRange,yRange,minLife,maxLife,xSpeed,ySpeed,16,18,spriteTags);
    }

    public ParticleSystem getParticleSystem()
    {
        return particleSystem;
    }
}
