package engine.ace.entity;

import engine.ace.entity.mob.Player;
import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;
import level.Level;

public class HealthBoost extends Entity {
	public HealthBoost(int x,int y,Level level)
	{
		this.x=x;
		this.y=y;
		this.level=level;
	}
	public void update(Player player)
	{
		if(player.playerColliding((x<<4)+1,(x<<4)+14,(y<<4)+1,(y<<4)+14))
			utilized(player);
		
	}
	
	public void render(Screen screen)
	{
		screen.renderSprite2(x<<4,y<<4,Sprite.bonus,true);
	}
	public void utilized(Player player)
	{
		player.addHealth(25);
		//System.out.println(player.getHealth());
		remove();
	}

}
