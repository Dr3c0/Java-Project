package engine.ace.entity.projectile;

import engine.ace.entity.Entity;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;
import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;
import level.Level;

public abstract class Projectile extends Entity{
	protected final int xOrigin,yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx,ny,x,y;
	protected double speed,range,damage;
	double distance;
	PlayerMP player;
	
	public Projectile(int x,int y,double dir,PlayerMP player)
	{
		this.player =player;
		level=player.level;
		xOrigin=x;
		yOrigin=y;
		angle=dir;
		this.x=x;
		this.y=y;
	}
		public void render(Screen s)
	{
		
	}

}
