package level.tile;

import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;
import java.awt.Rectangle;
public class Tile {
	public int x,y;
	public boolean isBreakable=false;
	
	public Sprite sprite;
    public static Tile solidTile=new SolidTile(Sprite.solid);
    public static Tile ground=new Ground(Sprite.ground);
    public static VoidTile voidtile=new VoidTile(Sprite.voidsprite);
    public static Tile wood=new Wood(Sprite.wood);
  
	public Tile(Sprite sprite)
	{
		this.sprite=sprite;
	}
	public void render(int x,int y , Screen screen)
	{
		
	}
	public boolean solid()
	{
		return false;
	}

}
