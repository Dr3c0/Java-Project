package engine.ace.entity.mob;

import java.util.ArrayList;
import java.util.List;

import Input.Keyboard;
import Input.Mouse;
import engine.ace.Ace2D;
import engine.ace.entity.Particle.Particle;
import engine.ace.entity.projectile.Projectile;
import engine.ace.entity.projectile.ShooterProjectile;
import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;

public class Player extends Mob {
	public Keyboard input;
	private double pshootrate=ShooterProjectile.rateOfFire;
	int pshoot=0;	
	int health=100;
	String name;
	int killCount=0;
	int color;
	boolean quit=false,respawn=false;
	public boolean died=false;
	int whenDed=0;
	protected List <Projectile> projectile=new ArrayList<Projectile>();
	private List<Particle> particles=new ArrayList<Particle>(); 
	public Player(Keyboard input)
	{
		this.input=input;
	}
	public Player(int x,int y,Keyboard input)
	{
		this.x=(int) x;
		this.y=(int) y;
		this.input=input;
	}
	public Player(int x,int y,Keyboard input,int x0,int x1,int y0,int y1)
	{
		super(x0,x1,y0,y1);
		this.x= x;
		this.y=y;
		this.input=input;
	}
	public Player(int x,int y,Keyboard input,int x0,int x1,int y0,int y1,int color,String name)
	{
		super(x0,x1,y0,y1);
		this.x= x;
		this.y=y;
		this.input=input;
		this.color=color;
		this.name=name;
	}
	public Player(Keyboard input,int x0,int x1,int y0,int y1,int color,String name)
	{
		super(x0,x1,y0,y1);
		this.color=color;
		this.name=name;
		this.input=input;
	}
	public void update()
	{
		if(!died) {
		int xa=0,ya=0;
		if(input!=null)
		{if(input.up) {ya--;}
		if(input.down){ya++;}
		if(input.left){xa--;}
		if(input.right){xa++;}
		if(xa!=0||ya!=0)move(xa,ya);
		shooting((Ace2D.getWindowWidth()/2),(Ace2D.getWindowHeight()/2));}}
		else {
			whenDed++;
			if(whenDed==300) {
				whenDed=0;
				died=false;
				health=100;
				int a[]=level.respawn();
				x=a[0]<<4;
				y=a[1]<<4;
			}
		}
		for(int i=0;i<particles.size();i++)
			particles.get(i).update();
		for(int i=0;i<projectile.size();i++)
		{
			projectile.get(i).update();
		}
		remove();
		
		
	}
	public void render(Screen screen)
	{
		if(!died) {
		screen.renderPlayer(x, y, Sprite.player,color);
		screen.renderBar(x+2,y-5,health, Sprite.healthbar);
		screen.renderString(x+2, y+16+2, name);
		}
		for(int i=0;i<projectile.size();i++)
		{
			projectile.get(i).render(screen);
		}
		for(int i=0;i<particles.size();i++)
		{
			particles.get(i).render(screen);
		}
		
	}
	public void shooting(int i,int j)
	{
		
		if(pshoot==0)
		{
	
		if(Mouse.getButton()==1)
		{
			double dx=Mouse.getX()-(i);
			double dy=Mouse.getY()-(j);
			double dir=Math.atan2(dy, dx);
			shoot(this.x,this.y,dir);
		}
		
		
		}
		pshoot++;
		if(pshoot==(int)pshootrate)pshoot=0;
	}
	public void isHit(int x)
	{
		//System.out.println(name+":"+health);
		health-=x;
		if(health<0) {
			died=true;
			health=0;}
		System.out.println(name+":"+health);
		
	}
	public void respawn(int x , int y)
	{
		respawn=true;
		this.x=x;
		this.y=y;
		died =false;
	}
	public void quit()
	{
		removePlayer();
	}
	public void removePlayer() {
		this.removed=true;
		
	}
	public void remove()
	{
		for(int i=0;i<projectile.size();i++)
			if(projectile.get(i).isRemoved())
			{
				projectile.remove(i);
			}
		for(int i=0;i<particles.size();i++)
			if(particles.get(i).isRemoved())
			{
				particles.remove(i);
			}
	
			
	}
	public boolean collision(int xa,int ya)
	{
		boolean solid=false;
	    int x0=(x+xa+xc)>>4;
	    int x1=(x+xa+xce)>>4;
	    int y0=(y+ya+yc)>>4;
	    int y1=(y+ya+yce)>>4;
	    if(level.getTile(x0, y0).solid()||level.getTile(x1, y0).solid()||level.getTile(x0, y1).solid()||level.getTile(x1, y1).solid())
	    	solid=true;
	    return solid;
	}
	public void shoot(int x,int y,double dir)
	{
		
		
	}
	public void move(int xa,int ya)
	{
		
		if(xa>0)dir=1;
		if(xa<0)dir=3;
		if(ya>0)dir=2;
		if(ya<0)dir=0;
				
		if(!collision(xa,0))
		{
		x+=xa;
		}
		if(!collision(0,ya))
		{
		y+=ya;
		}
	  
	}
	public void addParticles(Particle p)
	{
		particles.add(p);
	}
	public boolean playerColliding(int xs,int xe,int ys,int ye)
	{
		boolean collide=false;
		int x0=(x+xc);
	    int x1=(x+xce);
	    int y0=(y+yc);
	    int y1=(y+yce);
	    int ax,bx,ay,by;
		for(ax=xs;ax<xe;ax++)
		{
			for(bx=x0;bx<x1;bx++)
			{
					for(ay=ys;ay<ye;ay++)
					{
						for(by=y0;by<y1;by++)
							if(by==ay&&ax==bx)
								return collide =true;
					}
			}
		}
		return collide;
	}
	public void addHealth(int x)
	{
		health+=x;
		if(health>150)
			health=150;
	}
	public int getHealth()
	{
		return health;
	}
	public String getUsername()
	{
		return  name;
	}

}
