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

import java.util.ArrayList;



public class Driver
{

    public static final int TARGET_FPS=100;
    public static final int SCR_WIDTH=1024;
    public static final int SCR_HEIGHT=768;


    public static void main(String[] args) throws LWJGLException, IOException
    {
        initGL(SCR_WIDTH, SCR_HEIGHT);

        AudioManager.getInstance().loadSample("beep", "res/beep.wav");
        AudioManager.getInstance().loadSample("shot", "res/shot.wav");
        AudioManager.getInstance().loadSample("zap", "res/shot.wav");
        AudioManager.getInstance().loadSample("colflip", "res/ding.wav");
        AudioManager.getInstance().loadSample("ouch", "res/shot.wav");

        // AudioManager.getInstance().loadSample("buzz", "res/gotcha.wav");
        // AudioManager.getInstance().loadSample("victory", "res/hurray.wav");


        Camera camera = new Camera(0, 0, 10, // eye position
                                   0, 0, 0,       // look at
                                   .1f, 1000f,     // near/far
                                   45f, 1f*SCR_WIDTH/SCR_HEIGHT ); // fov, aspect ratio


        
        CellColor bgColor = new CellColor(1f,1f,1f);
        

        
        ArrayList<Ring> levels = new ArrayList<>();

        int count=10;

        for (int i = 0; i<count; i++)
        {
            levels.add(new Ring(3, 0, 0, (3f/count)*(i+1), 0.02));
        }


        // Ring outer = new Ring(18, 0, 0, 3, 0.02);
        // Ring inner = new Ring(18, 0, 0, .5, 0.02);


        java.util.Random rand = new java.util.Random();

        Dispenser dispenser = new Dispenser();
        RingGrid world = new RingGrid(2 + rand.nextInt(12), 3 + rand.nextInt(13));
        world.setClearColor(bgColor);
        world.setDispenser(dispenser);
        

        
        long time = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms


        


        while (! Display.isCloseRequested())
        {
            Display.sync(TARGET_FPS);

            long time2 = (Sys.getTime()*1000)/Sys.getTimerResolution(); // ms
            float delta_ms = time2-time;
            

            if (Keyboard.isKeyDown(Keyboard.KEY_R))
            {
                AudioManager.getInstance().play("colflip",
                                                (float)(rand.nextFloat()/3+.2));

                bgColor = new CellColor(1f,1f,1f);

                world.deactivate();
                world = new RingGrid(2 + rand.nextInt(12), 3 + rand.nextInt(13));
                world.setClearColor(bgColor);
                world.setDispenser(dispenser);
            }


            // update
            camera.update(delta_ms);
            world.update(delta_ms);
            dispenser.update(delta_ms);

            // for (Ring r : levels)
            // {
            //     r.update(delta_ms);
            // }
            
            // clear
            GL11.glClearColor(bgColor.r, bgColor.g, bgColor.b, 1f);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            camera.use();
            

            // // draw
            // int c1=0;
            // int c2=1;
            // int c3=2;

            // for (Ring r : levels)
            // {
                
            //     GL11.glColor3f( (c1%5)*.201f, (c2%5)*.202f, (c3%5)*.203f );

            //     c1++;
            //     c2++;
            //     c3++;
                

            //     r.draw();
            // }


            world.draw();
            dispenser.draw();
            

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
        // GL11.glEnable(GL11.GL_TEXTURE_2D);              
     
        // set "clear" color to black
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
 
