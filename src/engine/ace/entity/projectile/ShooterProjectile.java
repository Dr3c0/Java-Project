package engine.ace.entity.projectile;

import engine.ace.entity.Particle.Particle;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;
import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;
import level.Level;

public class ShooterProjectile extends Projectile{

	public static double rateOfFire=60/5;
	int x0=0,y0=0,x1=Sprite.shoot.width,y1=Sprite.shoot.height;
	public ShooterProjectile(int x,int y,double dir,PlayerMP player)
	{
		
		super(x,y,dir,player);
		range=200;
		damage=20;
		speed=3;
		//rateOfFire=60/15;
		nx=speed*Math.cos(angle);
		ny=speed*Math.sin(angle);
		
	}
	public void update()
	{
		move();
	}
	protected void move()
	{
		
		x+=nx;
		y+=ny;
		distance=Math.sqrt((x-xOrigin)*(x-xOrigin)+(y-yOrigin)*(y-yOrigin));
		if(distance>range) {
		remove();
		}
		if(collides()||collideWithPlayer())
		{
			if(player.input!=null)
			player.addParticles(new Particle((short)(x-nx),(short)(y-ny),(byte)5,1,player));
			remove();
		}
	}
	public void render(Screen s)
	{
		s.renderSprite((int)(x),(int)( y),Sprite.shoot,true);
	}
	public boolean collides()
	{
		boolean solid=false;
		int xc=(int) (x0+x);
		int xce=(int) (x1+x);
		int yc=(int) (y0+y);
		int yce=(int) (y1+y);
		for(int i=xc;i<xce;i++)
			if(level.getTile(i>>4, yc>>4).solid()) {
				if(level.getTile(i>>4, yc>>4).isBreakable)
					if(level.getTile(i>>4, yc>>4).isBreakable)
						level.tileBreak(level.searchTile(i>>4,yc>>4),15);
				return solid=true;
			}
				
		for(int i=xc;i<xce;i++)
			if(level.getTile(i>>4, yce>>4).solid()) {
				if(level.getTile(i>>4, yce>>4).isBreakable)
					level.tileBreak(level.searchTile(i>>4,yce>>4),15);
				return solid=true;
			}
		for(int i=yc;i<yce;i++)
			if(level.getTile(xc>>4, i>>4).solid()) {
				if(level.getTile(xc>>4, i>>4).isBreakable)
					level.tileBreak(level.searchTile(xc>>4,i>>4),15);
				return solid=true;
			}
				
		for(int i=yc;i<yce;i++)
			if(level.getTile(xce>>4, i>>4).solid()) {
				if(level.getTile(xce>>4, i>>4).isBreakable)
					level.tileBreak(level.searchTile(xce>>4,i>>4),15);
				return solid=true;}
		return solid;		
	}
	public boolean collideWithPlayer()
	{
		boolean hit=false;
		int xc=(int) (x0+x);
		int xce=(int) (x1+x);
		int yc=(int) (y0+y);
		int yce=(int) (y1+y);
		for(int i=0;i<level.players.size();i++)
			if(!level.players.get(i).getUsername().equalsIgnoreCase(player.getUsername()))
			{if(!level.players.get(i).died) {
			if(level.players.get(i).playerColliding(xc,xce,yc,yce)) {
				hit=true;
				level.players.get(i).isHit(15);
			}}}
		return hit;
		
		
	}
}
