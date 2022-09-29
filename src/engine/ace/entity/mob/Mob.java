package engine.ace.entity.mob;

import engine.ace.entity.Entity;
import engine.ace.entity.projectile.Projectile;
import engine.ace.entity.projectile.ShooterProjectile;
import engine.ace.gfx.Sprite;
import engine.ace.entity.Particle.Particle;

public abstract class Mob extends Entity {
	protected Sprite sprite;
	protected int xc,xce,yc,yce;
	protected int dir=0;
	protected boolean moving=false;
	Mob()
	{
		
	}
	Mob(int x0,int x1,int y0,int y1)
	{
		xc=x0;yc=y0;xce=x1;yce=y1;
	}
	public void move(int xa,int ya)
	{		
			
			 
		  
	}
	public void update()
	{
	
	}
	
	
	public void render()
	{
		
	}
}
