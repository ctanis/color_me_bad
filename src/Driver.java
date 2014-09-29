import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.LWJGLException;
import org.lwjgl.BufferUtils;


import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;


import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;


import java.io.IOException;
import java.nio.FloatBuffer;

import java.util.LinkedList;
import java.util.Iterator;



public class Driver
{

    public static final int TARGET_FPS=100;
    public static final int SCR_WIDTH=1024;
    public static final int SCR_HEIGHT=768;

    public static LinkedList<GameObject> tempItems;
    

    public static void main(String[] args) throws LWJGLException, IOException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        AudioManager.getInstance().loadSample("beep", "res/beep.wav");
        AudioManager.getInstance().loadSample("shot", "res/shot.wav");
        AudioManager.getInstance().loadSample("zap", "res/shot.wav");
        AudioManager.getInstance().loadSample("colflip", "res/ding.wav");
        AudioManager.getInstance().loadSample("ouch", "res/shot.wav");

        tempItems = new LinkedList<GameObject>();
        

        java.util.Random rand = new java.util.Random();


        Camera camera = new Camera(0, 0, 1000, // eye position
                                   0, 0, 0,       // look at
                                   .1f, 1000f,     // near/far
                                   45f, 1f*SCR_WIDTH/SCR_HEIGHT ); // fov, aspect ratio


        
        CellColor bgColor = new CellColor(1f,1f,1f);



        Score scoreState = new Score(bgColor);
        scoreState.setGoal(rand.nextInt(3)+1);

        Dispenser dispenser = new Dispenser(bgColor);

        // RingGrid world = new RingGrid(10, 3 + rand.nextInt(5));
        RingGrid world = new RingGrid(8+rand.nextInt(4), 3 + rand.nextInt(17));

        world.setClearColor(bgColor);
        world.setDispenser(dispenser);
        

        long time = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms

        boolean reset=false;
        

        while (! Display.isCloseRequested())
        {
            Display.sync(TARGET_FPS);

            long time2 = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms
            float delta_ms = time2-time;
            
            

            if (Keyboard.isKeyDown(Keyboard.KEY_R) || reset)
            {
                AudioManager.getInstance().play("colflip",
                                                (float)(rand.nextFloat()/3+.2));

                bgColor = new CellColor(1f,1f,1f);
                dispenser.setBgColor(bgColor);
                

                scoreState = new Score(bgColor);
                scoreState.setGoal(rand.nextInt(3)+1);

                world.deactivate();
                // world = new RingGrid(5 + rand.nextInt(12), 3 + rand.nextInt(13));
                world = new RingGrid(8+rand.nextInt(4), 3 + rand.nextInt(17));

                world.setClearColor(bgColor);
                world.setDispenser(dispenser);
            }
            reset=false;
            


            // // update
            camera.update(delta_ms);
            world.update(delta_ms);
            dispenser.update(delta_ms);

            for (GameObject obj : tempItems)
            {
                if (obj.isActive())
                {
                    obj.update(delta_ms);
                }
                
            }


            
            for (Iterator<GameObject> it = tempItems.iterator();
                 it.hasNext(); )
            {
                GameObject obj = it.next();

                if (! obj.isActive())
                {
                    it.remove();
                }
                
            }



            // clear
            GL11.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            camera.use();
            


            // draw


            world.draw();
            dispenser.draw();
            scoreState.draw();

            for (GameObject obj : tempItems)
            {
                obj.draw();                
            }


            int st = scoreState.state();

            if (st > 0)
            {
                System.out.println("you win!");
                reset=true;
            }
            else if (st < 0)
            {
                System.out.println("you lose!");
            }


            
            // GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
            // GL11.glColor3f(0,0,0);
            // GL11.glBegin(GL11.GL_QUADS);
            // GL11.glVertex3f(0, 0, 0);
            // GL11.glVertex3f(200, 0, 0);
            // GL11.glVertex3f(200, 100, 0);
            // GL11.glVertex3f(0, 100, 0);
            // GL11.glEnd();
            

            // end loop
            time=time2;
            Display.update();
            AudioManager.getInstance().update();
        }
        

        AudioManager.getInstance().destroy();
        Display.destroy();
    }
    

    public static void initGL(int width, int height) throws LWJGLException
    {
        // open window of appropriate size
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        Display.setVSyncEnabled(true);
        
        // enable 2D textures
        GL11.glEnable(GL11.GL_TEXTURE_2D);
     
        // set "clear" color to white
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);         

        // enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         
        // set viewport to entire window
        GL11.glViewport(0,0,width,height);
         
        // set up orthographic projectionr
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, -1, 1);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
}
 
