import org.lwjgl.opengl.GL11;
import java.util.Random;
import org.lwjgl.input.Keyboard;

public class Dispenser extends GameObject 
{
    
    CellColor color;
    Random rand = new Random();
    CellColor bgColor;
    
    

    public Dispenser(CellColor bgColor)
    {
        this.bgColor = bgColor;
        nextColor();
    }

    public void setBgColor(CellColor bgColor)
    {
        this.bgColor = bgColor;
    }


    public CellColor getColor()
    {
        return color;
    }


    public CellColor nextColor()
    {
        CellColor tmp = color;

        // color = new CellColor( .1f+rand.nextInt(6)*.181f,
        //                        .1f+rand.nextInt(6)*.182f,
        //                        .1f+rand.nextInt(6)*.183f );


        do
        {
            color = new CellColor( rand.nextInt(2),
                                   rand.nextInt(2),
                                   rand.nextInt(2) );
        } while (color.r == bgColor.r &&
                 color.g == bgColor.g  &&
                 color.b == bgColor.b);
        
        
        
        return tmp;
    }



    public void update(float delta_ms)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP))
        {
            nextColor();
        }

    }


    public void draw()
    {
        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
        GL11.glColor3f( color.r, color.g, color.b );

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f(0f, 300.0f, 0f);
        GL11.glVertex3f(25f, 400f, 0f);
        GL11.glVertex3f(-25, 400f, 0f);
        GL11.glEnd();
        
        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
        GL11.glColor3f( 0,0,0 );

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f(0f, 300.0f, 0f);
        GL11.glVertex3f(25f, 400f, 0f);
        GL11.glVertex3f(-25, 400f, 0f);
        GL11.glEnd();
        

    }


}
