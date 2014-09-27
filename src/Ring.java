import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;

public class Ring extends GameObject
{
    private int numSegments;
    private double centerX;
    private double centerY;
    private double ringWidth;
    private double segmentWidth;
    private double offsetAngle;


    public Ring(int numSegments,
                double centerX, double centerY,
                double ringWidth, double segmentWidth)
    {
        this.numSegments = numSegments;
        this.centerX = centerX;
        this.centerY = centerY;
        this.ringWidth = ringWidth;
        this.segmentWidth = segmentWidth;
        this.offsetAngle=0;
    }



    public void update(float delta_ms)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            numSegments++;

            if (numSegments >= 50)
            {
                numSegments = 50;
            }

        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
        {
            numSegments--;

            if (numSegments <= 2) 
            {
                numSegments = 3;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            offsetAngle += .05;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            offsetAngle -= .05;
        }


        if (Keyboard.isKeyDown(Keyboard.KEY_F))
        {
            segmentWidth += 0.01;

            if (segmentWidth >= 1)
            {
                segmentWidth = 1;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D))
        {
            segmentWidth -= 0.01;

            if (segmentWidth <= .01)
            {
                segmentWidth = 0.01;
            }
        }

    }


    public void draw()
    {

        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );

        double deltaAngle = Math.PI * 2 / numSegments;
        
        // GL11.glColor3f( 0.0f, 0.0f, 0.0f );
        GL11.glBegin(GL11.GL_QUADS);


        for (int p=0; p<numSegments; p++)
        {
            float x1 =  (float)(ringWidth * Math.cos(p*deltaAngle + offsetAngle));
            float y1 =  (float)(ringWidth * Math.sin(p*deltaAngle + offsetAngle));

            float x2 =  (float)((ringWidth + segmentWidth) *
                                Math.cos(p*deltaAngle + offsetAngle));

            float y2 =  (float)((ringWidth + segmentWidth) *
                                Math.sin(p*deltaAngle + offsetAngle));


            float x3 =  (float)((ringWidth + segmentWidth) *
                                Math.cos((p+1)*deltaAngle + offsetAngle));

            float y3 =  (float)((ringWidth + segmentWidth) *
                                Math.sin((p+1)*deltaAngle + offsetAngle));


            float x4 =  (float)(ringWidth *
                                Math.cos((p+1)*deltaAngle + offsetAngle));
            float y4 =  (float)(ringWidth *
                                Math.sin((p+1)*deltaAngle + offsetAngle));


            GL11.glVertex3f(x1, y1, 0);
            GL11.glVertex3f(x2, y2, 0);
            GL11.glVertex3f(x3, y3, 0);
            GL11.glVertex3f(x4, y4, 0);



        }

        GL11.glEnd();




    }
    
    
}
