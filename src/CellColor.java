public class CellColor
{
    public float r;
    public float g;
    public float b;

    public CellColor(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
            
    }


    public void moveColor(CellColor other)
    {
        this.r = this.r * .8f + other.r*.2f;
        this.g = this.g * .8f + other.g*.2f;
        this.b = this.b * .8f + other.b*.2f;
    }

}

