package engine.ace;

public class Vector2D {
	private int x,y;
	public Vector2D(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
	public int[] getLoc()
	{
		int [] l= {x,y};
		return l;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

}
