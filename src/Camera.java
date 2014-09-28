import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;

public class Camera 
{
    // position
    private float x;
    private float y;
    private float z;

    private float vx;
    private float vy;
    private float vz;
    
    private float fov;
    private float aspect_ratio;
    private float near;
    private float far;
    private float alpha;
    private float radius;
    
    
    public Camera(float eyex, float eyey, float eyez,
                  float vx, float vy, float vz,
                  float near, float far,
                  float fov, float aspect_ratio)
    {
        this.x = eyex;
        this.y = eyey;
        this.z = eyez;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.fov = fov;
        this.aspect_ratio = aspect_ratio;
        this.near = near;
        this.far = far;
        this.radius=1;
    }



    public void lookAt(float vx, float vy, float vz)
    {
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        
    }



    public void use()         // call before drawing
    {
        
        // render
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(fov, aspect_ratio, near, far);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        // looking from positive z into negative z
        GLU.gluLookAt( x, y, z,
                       vx, vy, vz,
                       0, 1, 0 );
        GL11.glTranslated(0, 0, radius);
        GL11.glRotated(alpha, 0, 1, 0);
        

    }

    public void update(float delta_ms)
    {
        
    }




    

}

