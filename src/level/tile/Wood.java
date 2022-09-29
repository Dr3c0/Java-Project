package level.tile;

import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;

public class Wood extends Tile {
	Wood(Sprite sprite)
	{
		super(sprite);
		isBreakable=true;
	}
	public void render(int x,int y,Screen S){
		S.renderTile(x,y,this);
	}
	public boolean solid()
	{
		return true;
	}
	

}
