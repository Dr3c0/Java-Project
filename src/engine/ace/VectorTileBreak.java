package engine.ace;

public class VectorTileBreak extends Vector2D{
	int health;
	public boolean broken=false;

	public VectorTileBreak(int x, int y,int health) {
		super(x, y);
		this.health=health;
		
	}
	public void breakTile(int x)
	{
		//System.out.println(health);
		health-=x;
		if(health<0)
			broken=true;
	}

}
