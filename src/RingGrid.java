import org.lwjgl.opengl.GL11;
import org.lwjgl.input.Keyboard;
import java.util.Random;



public class RingGrid extends GameObject
{
    public static final float MIN_R = 50f;
    public static final float MAX_R = 325f;
    public static final float timethresh=200;
    public static Random rand = new Random();
    

    private static CellColor WHITE =new CellColor(1f,1f,1f);
    

    public static class PolarQuad
    {
        float r1;
        float r2;
        float theta1;
        float theta2;
        boolean solid;
        float lastAngle;
        

        CellColor color;
        
        public PolarQuad(float r1, float r2,
                         float theta1, float theta2)
        {
            this.r1 = r1;
            this.r2 = r2;
            this.theta1 = theta1;
            this.theta2 = theta2;
            solid = false;
        }

        public CellColor giveColor()
        {
            CellColor rval = color;
            color = WHITE;
            solid=false;
            return rval;
        }

        public void setColor(float r, float g, float b)
        {
            color = new CellColor(r,g,b);
            
        }

        public void setColor(CellColor c)
        {
            color = c;
            
        }

        private void drawRaw(float offsetAngle)
        {
            
            GL11.glBegin(GL11.GL_QUADS);

            float x1 =  (float)(r1 * Math.cos(theta1 + offsetAngle));
            float y1 =  (float)(r1 * Math.sin(theta1 + offsetAngle));

            float x2 =  (float)(r2 *
                                Math.cos(theta1 + offsetAngle));

            float y2 =  (float)(r2 *
                                Math.sin(theta1 + offsetAngle));


            float x3 =  (float)(r2 *
                                Math.cos(theta2 + offsetAngle));

            float y3 =  (float)(r2 *
                                Math.sin(theta2 + offsetAngle));


            float x4 =  (float)(r1 *
                                Math.cos(theta2 + offsetAngle));
            float y4 =  (float)(r1 *
                                Math.sin(theta2 + offsetAngle));



            GL11.glVertex3f(x1, y1, 0);
            GL11.glVertex3f(x2, y2, 0);
            GL11.glVertex3f(x3, y3, 0);
            GL11.glVertex3f(x4, y4, 0);

            GL11.glEnd();

        }

        public void draw(float offsetAngle)
        {

            lastAngle = offsetAngle;
            

            if (solid)
            {
                GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_FILL );
                GL11.glColor3f( color.r, color.g, color.b);
                drawRaw(offsetAngle);
            }


            // draw outline
            GL11.glPolygonMode( GL11.GL_FRONT_AND_BACK, GL11.GL_LINE );
            GL11.glColor3f( 0, 0, 0 );
            drawRaw(offsetAngle);

            


        }        

        public void toggle()
        {
            solid = !solid;
        }


        public boolean isSolid()
        {
            return solid;
        }


        public Coord explode()
        {
            Coord newCoord =new Coord();

            float x1 =  (float)(r1 * Math.cos(theta1 + lastAngle));
            float y1 =  (float)(r1 * Math.sin(theta1 + lastAngle));

            float x2 =  (float)(r2 *
                                Math.cos(theta1 + lastAngle));

            float y2 =  (float)(r2 *
                                Math.sin(theta1 + lastAngle));


            float x3 =  (float)(r2 *
                                Math.cos(theta2 + lastAngle));

            float y3 =  (float)(r2 *
                                Math.sin(theta2 + lastAngle));


            float x4 =  (float)(r1 *
                                Math.cos(theta2 + lastAngle));
            float y4 =  (float)(r1 *
                                Math.sin(theta2 + lastAngle));


            newCoord.x = .25f*(x1+x2+x3+x4);
            newCoord.y = .25f*(y1+y2+y3+y4);

            solid = false;

            // Driver.tempItems.enqueue(new ExplodingQuad());

            return newCoord;
        }

    }


    
    private PolarQuad[][] grid;
    private int levels;
    private int width;
    private int lastCol;


    private float offsetAngle=0;
    private float time_accum=0;
    private Dispenser dispenser;
    private CellColor centerColor;
    

    public void setDispenser(Dispenser disp)
    {
        dispenser = disp;
    }

    public void setClearColor(CellColor center)
    {
        centerColor = center;
    }


    public RingGrid(int levels, int width)
    {
        this.levels = levels;
        this.width = width;
        
        float deltaAngle = (float)(2*Math.PI / width);
        float deltaR = (float)(MAX_R - MIN_R) / levels;

        grid = new PolarQuad[levels][width];
        
        float r=MIN_R;
        lastCol = currColumn();
        
        
        int c1=0;
        int c2=1;
        int c3=2;



        for (int lev=0; lev<levels; lev++)
        {
            float angle = 0;


            for (int c=0; c<width; c++)
            {
                grid[lev][c] = new PolarQuad( r, r+deltaR, angle, angle+deltaAngle);
                angle += deltaAngle;
            }


            c1++;
            c2++;
            c3++;



            r += deltaR;
        }
    }

    public void draw()
    {
        for (int lev=0; lev<levels; lev++)
        {
            for (int c=0; c<width; c++)
            {
                grid[lev][c].draw(offsetAngle);
            }
        }

    }
    

    public void reset()
    {
        offsetAngle=0;

        for (int lev=0; lev<levels; lev++)
        {
            for (int c=0; c<width; c++)
            {
                if (grid[lev][c].isSolid())
                {
                    grid[lev][c].toggle();

                }
            }
        }


    }


    public void update(float delta_ms)
    {
        if (!isActive())
        {
            return;
        }
        
        while (Keyboard.next())
        {
            // if ((Keyboard.getEventKey() == Keyboard.KEY_SPACE) &&
            //     Keyboard.getEventKeyState()) // pressed
            // {
            //     // add a propogating shape
            //     int col = currColumn();

            //     if (!grid[levels-1][col].isSolid()) 
            //     {
            //         AudioManager.getInstance().play("shot");
            //         grid[levels-1][col].setColor( dispenser.nextColor() );
            //         grid[levels-1][col].toggle();
            //     }
                
            // }

            if ((Keyboard.getEventKey() == Keyboard.KEY_SPACE) &&
                Keyboard.getEventKeyState()) // pressed
            {
                // zap col
                AudioManager.getInstance().play("zap");

                int col = currColumn();
                boolean shotOne=false;
                

                for (int r=levels-1; r>=0; r--)
                {

                    if (grid[r][col].isSolid())
                    {

                        shotOne = true;
                        
                        Driver.tempItems.add(new Shot(grid[r][col].explode()));

                        break;
                    }
                }

                if (! shotOne)
                {
                    Driver.tempItems.add(new Shot());
                }


            }


        }

        // if (Keyboard.isKeyDown(Keyboard.KEY_R))
        // {
        //     reset();
        //     offsetAngle = 0;
            
        // }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
        {
            offsetAngle += .1;
//            System.out.println(offsetAngle);
            


        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
        {
            offsetAngle -= .1;
//            System.out.println(offsetAngle);
        }

        while (offsetAngle > 2*Math.PI)
        {
            offsetAngle -= (float)(2*Math.PI);
        }

        while (offsetAngle < 0)
        {
            offsetAngle += (float)(2*Math.PI);
        }

        
        int col = currColumn();

        if (lastCol != col)
        {
            //play noise
            AudioManager.getInstance().play("colflip", (float)(rand.nextFloat()/3+.2));

            lastCol  = col;
        }


        time_accum += delta_ms;

        if (time_accum > timethresh)
        {
            boolean playsound=false;


            for (int c=0; c<width; c++)
            {
                if (grid[0][c].isSolid())
                {
                    centerColor.moveColor(grid[0][c].giveColor());
                    playsound=true;
                }
            }

            if (playsound)
            {
                // chirp
                AudioManager.getInstance().play("ouch");
            }


            time_accum = 0;
            playsound = false;
            

            for (int lev=0; lev<levels-1; lev++)
            {
                for (int c=0; c<width; c++)
                {
                    if (! grid[lev][c].isSolid())
                    {
                        if (grid[lev+1][c].isSolid())
                        {
                            playsound=true;

                            grid[lev][c].setColor(grid[lev+1][c].giveColor());
                            grid[lev][c].toggle();
//                            grid[lev+1][c].toggle();
                        }
                    }
                }
            }


            if (playsound)
            {
                // chirp
                AudioManager.getInstance().play("beep");

            }


            for (int c=0; c<width; c++)
            {
                boolean clear=true;

                for (int l=0; l<levels; l++)
                {
                    if (grid[l][c].isSolid())
                    {
//                        clear=false;
                    }
                }

                if (clear)
                {

                    if (rand.nextInt(20) > 17)
                    {
                        grid[levels-1][c].setColor(dispenser.nextColor());
                        grid[levels-1][c].toggle();
                    }
                }
                
            }


        }




    }



    public int currColumn()
    {
        // based on offsetAngle

        double ang = offsetAngle - Math.PI *0.5;

        if (ang > 2*Math.PI)
        {
            ang -= 2*Math.PI;
        }

        if (ang < 0)
        {
            ang += 2*Math.PI;
        }


//        System.out.println(ang + " " + width);
//        System.out.println(width * ang / (2*Math.PI));
        return (int)((2*Math.PI - ang) * width / (2*Math.PI)); 
    }


}
