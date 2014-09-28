import org.lwjgl.opengl.GL11;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;


public class Score extends GameObject 
{
    private CellColor scoreColor;
    private int target;
//    private TrueTypeFont font;
    
    
    public static float WIN_THRESH = .8f;
    public static float LOSE_THRESH = .2f;
    


    public Score(CellColor scoreColor)
    {
        this.scoreColor = scoreColor;

        // font=new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 12), true);

    }

    // 1,2,3 == r,g,b
    public void setGoal(int goal)
    {
        target = goal;
    }


    public void update(float delta_ms)
    {
        

    }

    public int state()
    {
        float win;
        float lose1;
        float lose2;
        
        switch (target)
        {
         case 1:                // red
             win = scoreColor.r;
             lose1 = scoreColor.g;
             lose2 = scoreColor.b;
             break;

         case 2:                // green
             win = scoreColor.g;
             lose1 = scoreColor.r;
             lose2 = scoreColor.b;
             break;

         case 3:                // blue
             win = scoreColor.b;
             lose1 = scoreColor.r;
             lose2 = scoreColor.g;
             break;

         default:
             win=0;
             lose1=0;
             lose2=0;
        }
        
                          


        if (win > WIN_THRESH && lose1 < LOSE_THRESH && lose2 < LOSE_THRESH)
        {
            return 1;
        }
        else if (win < LOSE_THRESH)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }




    public void draw()
    {
        float win_r;
        float win_g;
        float win_b;
        String win_txt;



        switch (target)
        {
         case 1:                // red
             win_r = 1;
             win_g = 0;
             win_b = 0;
             win_txt="RED";

             break;

         case 2:                // green
             win_r = 0;
             win_g = 1;
             win_b = 0;
             win_txt="GREEN";

             break;

         case 3:                // blue
             win_r = 0;
             win_g = 0;
             win_b = 1;
             win_txt="BLUE";

             break;

         default:
             win_r=0f;
             win_g=0f;
             win_b=0f;
             win_txt="error";
             
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
        GL11.glColor3f(win_r/2,win_g/2,win_b/2);
        
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex3f(300f, 280f, 0f);
        GL11.glVertex3f(400f, 280f, 0f);
        GL11.glVertex3f(400f, 380f, 0f);
        GL11.glVertex3f(300f, 380f, 0f);

        GL11.glEnd();
        
        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
        GL11.glColor3f(0f,0f,0f);
        
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex3f(300f, 280f, 0f);
        GL11.glVertex3f(400f, 280f, 0f);
        GL11.glVertex3f(400f, 380f, 0f);
        GL11.glVertex3f(300f, 380f, 0f);


        GL11.glEnd();
            

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );

        GL11.glColor3f(1,0,0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(305, 285, 0);
        GL11.glVertex3f(335, 285, 0);
        GL11.glVertex3f(335, 285+85*scoreColor.r+5, 0);
        GL11.glVertex3f(305, 285+85*scoreColor.r+5, 0);
        GL11.glEnd();

        GL11.glColor3f(0,1,0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(335, 285, 0);
        GL11.glVertex3f(365, 285, 0);
        GL11.glVertex3f(365, 285+85*scoreColor.g+5, 0);
        GL11.glVertex3f(335, 285+85*scoreColor.g+5, 0);
        GL11.glEnd();


        GL11.glColor3f(0,0,1);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(365, 285, 0);
        GL11.glVertex3f(395, 285, 0);
        GL11.glVertex3f(395, 285+85*scoreColor.b+5, 0);
        GL11.glVertex3f(365, 285+85*scoreColor.b+5, 0);
        GL11.glEnd();



        // GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );

        // GL11.glEnable(GL11.GL_TEXTURE_2D);
        // GL11.glColor3f(0f,0f,0f);
        // font.drawString(300, 300, win_txt);
        // GL11.glDisable(GL11.GL_TEXTURE_2D);            

        



    }



}
