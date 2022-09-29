package engine.ace.entity.Particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import engine.ace.entity.Entity;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;
import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;
import level.Level;

public class Particle extends Entity {
	Random random=new Random();
	private Sprite sprite;
	private double life;
	protected double xa,ya,xx,yy,zz,za;
	PlayerMP player;
	public Particle(short x,short y,byte amount,double life,PlayerMP player)
	{
		
		this(x,y,life,player);
		sprite=new Sprite(3,0xaaaaaa);
		for(byte i=0;i<amount-1;i++)
			player.addParticles(new Particle(x,y,life,player));
	}
	public Particle(short x,short y,double life,PlayerMP player)
	{
		this.player=player;
		level=player.level;
		this.x=(short) x;
		this.y=(short) y;
		this.life = life*60+((random.nextDouble()*100)/3);
		xx=x;yy=y;zz=2.0;za=-0.1;
		sprite=new Sprite(3,0xaaaaaa);
		this.xa=random.nextGaussian();
		this.ya=random.nextGaussian();
		sendParticle();
	}
	public Particle(short x,short y,double life,PlayerMP player,double xa,double ya)
	{
		this.player=player;
		level=player.level;
		this.x=(short) x;
		this.y=(short) y;
		this.life = life;
		xx=x;yy=y;zz=2.0;za=-0.1;
		sprite=new Sprite(3,0xaaaaaa);
		this.xa=xa;
		this.ya=ya;
	}
	public void sendParticle()
	{
		player.game.socketClient.sendData(("5"+player.getUsername()+" "+x+" "+y+" "+life+" "+ xa+" "+ya).getBytes());
	}
	public void update()
	{
		life--;
		if(particleCollision(xa,0)&&collideWithPlayer(xa,0))
			xa*=-1;
		if(particleCollision(0,ya)&&collideWithPlayer(0,ya)) {
			ya*=-1;
		}
		za-=0.1;
		if(zz<0)
		{
			zz=0;
			za*=-0.75;
			xa*=0.2;
			ya*=0.2;
		}
		zz+=za;
		
		
		xx+=xa;
		yy+=ya;
		if(life<0)
			remove();
		
		
	}
	public boolean particleCollision(double xa,double ya)
	{
		boolean solid=false;
		int xc=(int)(xx+xa);
		int xce=(int) (sprite.width+xx+xa);
		int yc=(int) (yy+ya);
		int yce=(int) (sprite.height+yy+ya);
		for(int i=xc;i<xce;i++)
			if(level.getTile(i>>4, yc>>4).solid()) {
				if(level.getTile(i>>4, yc>>4).isBreakable)
					if(level.getTile(i>>4, yc>>4).isBreakable)
						level.tileBreak(level.searchTile(i>>4,yc>>4),1);
				return solid=true;
				}
		
		for(int i=xc;i<xce;i++)
			if(level.getTile(i>>4, yce>>4).solid()) {
				if(level.getTile(i>>4, yce>>4).isBreakable)
					level.tileBreak(level.searchTile(i>>4,yce>>4),1);
				return solid=true;
			}
		for(int i=yc;i<yce;i++)
			if(level.getTile(xc>>4, i>>4).solid()) {
				if(level.getTile(xc>>4, i>>4).isBreakable)
					level.tileBreak(level.searchTile(xc>>4,i>>4),1);
				return solid=true;
			}
				
		for(int i=yc;i<yce;i++)
			if(level.getTile(xce>>4, i>>4).solid()) {
				if(level.getTile(xce>>4, i>>4).isBreakable)
					level.tileBreak(level.searchTile(xce>>4,i>>4),1);
				return solid=true;}
		return solid;		
	}
	public boolean collideWithPlayer(double xa,double ya)
	{
		boolean hit=false;
		int xc=(int)(xx+xa);
		int xce=(int) (sprite.width+xx+xa);
		int yc=(int) (yy+ya);
		int yce=(int) (sprite.height+yy+ya);
		for(int i=0;i<level.players.size();i++)
			if(!level.players.get(i).getUsername().equalsIgnoreCase(player.getUsername()))
			{if(!level.players.get(i).died) {
			if(level.players.get(i).playerColliding(xc,xce,yc,yce)) {
				hit=true;
			}}}
		return hit;
		
		
	}
	public void render(Screen screen)
	{
		screen.renderSprite((int)xx,(int)(yy-zz), sprite, true);
	}


}
