package level.tile;

import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;

public class Ground extends Tile {

	public Ground(Sprite sprite) {
		super(sprite);
	}
	public void render(int x,int y,Screen screen)
	{
		screen.renderTile(x,y,this);
	}
	public boolean solid()
	{
		return false;
	}

}
