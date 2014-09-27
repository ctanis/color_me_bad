public class GameObject
{
    private boolean active;

    public GameObject()
    {
        active=true;
    }
    
    public void update(float delta_ms)
    {
        
    }

    public void draw()
    {
        
    }


    public boolean isActive()
    {
        return active;
    }

    public void activate()
    {
        active=true;
    }

    public void deactivate()
    {
        active=false;
    }


}
