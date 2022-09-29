package engine.ace.entity;

import java.util.Random;

import engine.ace.gfx.Screen;
import level.Level;
import java.awt.Rectangle;
public class Entity {
	public int x,y;
	protected boolean removed =false;
	public Level level;

	public void update()
	{
		
	}
	public void render(Screen screen)
	{
		
	}
	public void remove()
	{
		removed = true;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
	
}
