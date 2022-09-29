package level.tile;

import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;

public class VoidTile extends Tile {
	public VoidTile(Sprite sprite)
	{
		super(sprite);
	}
	public void render(int x,int y,Screen S){
		S.renderTile(x,y,this);
	}

}
