package engine.ace.entity.mob;

import java.net.InetAddress;

import Input.Keyboard;
import engine.ace.Ace2D;
import engine.ace.entity.Particle.Particle;
import engine.ace.entity.projectile.Projectile;
import engine.ace.entity.projectile.ShooterProjectile;

public class PlayerMP extends Player {
	public InetAddress ip;
	public int port;
	public Ace2D game;
	public PlayerMP(Keyboard input, int x0, int x1, int y0, int y1, int color, String name,InetAddress i,int p,Ace2D game) {
		super(input, x0, x1, y0, y1, color, name);
		ip=i;
		port=p;
		this.game=game;
	}
	public PlayerMP(Keyboard input, int x0, int x1, int y0, int y1, int color, String name,Ace2D game)
	{
		super(input,x0,x1,y0,y1,color,name);
		this.game=game;
	}
	public void setX(int x)
	{
			this.x=x;
	}
	public void setY(int x)
	{
			this.y=x;
	}
	
	public void shoot(int x,int y,double dir)
	{
		game.socketClient.sendData(("4"+getUsername()+" "+x+" "+y+" "+dir).getBytes());
		Projectile p=new ShooterProjectile(x,y,dir,this);
		projectile.add(p);
	}
	public void shootserver(int x,int y,double dir)
	{
		Projectile p=new ShooterProjectile(x,y,dir,this);
		projectile.add(p);
	}
	public void addParticles(Particle p)
	{
		super.addParticles(p);
	}


}
