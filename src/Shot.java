import org.lwjgl.opengl.GL11;

public class Shot extends GameObject 
{
    // from dispenser tip
    public static final float from_x = 0;
    public static final float from_y = 300;
    public static final float timethresh = 30;
    

    private float to_x;
    private float to_y;
    private float time_accum;
    

    // shoot to 0,0
    public Shot()
    {
        to_x = 0;
        to_y = 0;
        

    }


    public Shot(Coord c)
    {
        to_x = c.x;
        to_y = c.y;
    }
    

    public void update(float delta_ms)
    {
        if (time_accum > timethresh)
        {
            deactivate();
        }

        time_accum += delta_ms;
    }


    public void draw()
    {
        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
        GL11.glColor3f( 1,0,0 );

        System.out.println(from_x + " " + from_y  + " " + " - " + to_x + " " + to_y);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(from_x-2f, from_y, 0);
        GL11.glVertex3f(from_x+2f, from_y, 0);
        GL11.glVertex3f(to_x+2f, to_y, 0);
        GL11.glVertex3f(to_x-2f, to_y, 0);
        GL11.glEnd();
    }


}

