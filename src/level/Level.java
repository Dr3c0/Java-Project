package level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import engine.ace.Ace2D;
import engine.ace.Vector2D;
import engine.ace.VectorTileBreak;
import engine.ace.entity.Entity;
import engine.ace.entity.HealthBoost;
import engine.ace.entity.Particle.Particle;
import engine.ace.entity.mob.Mob;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;
import engine.ace.entity.projectile.Projectile;
import engine.ace.gfx.Screen;
import level.tile.Tile;

public class Level {
	
	protected int width,height;
	Random r=new Random();
	protected int [] tiles;
	public List<PlayerMP> players=new ArrayList<PlayerMP>();
	public List<Vector2D> spawnTiles=new ArrayList<Vector2D>();
	public List<HealthBoost> bonus=new ArrayList<HealthBoost>();
	public List<VectorTileBreak>tileCheck=new ArrayList<VectorTileBreak>();
	
	public Level()
	{
		
	}
	public Level(int width,int height)
	{
		this.width=width;
		this.height=height;
		tiles=new int [height*width];
		generateLevel();
		
	}
	public Level(String path)
	{
		loadLevel(path);
		setTiles();
		
				
	}
	public void setTiles()
	{
		for(int y=0;y<height;y++)
		{
			for(int x=0;x<width;x++) {
				if(tiles[x+y*width]==0xffffffff)
					spawnTiles.add(new Vector2D(x,y));
			if(tiles[x+y*width]==0xff777777)
				tileCheck.add(new VectorTileBreak(x,y,1000));}
		}

	}
	protected void generateLevel() {
		
	}
	protected void loadLevel(String path)
	{
		
	}
	public void update()
	{

		for(int i=0;i<players.size();i++)
		{
			
			players.get(i).update();
		}
		for(int i=0;i<bonus.size();i++)
			for(int j=0;j<players.size();j++)
			bonus.get(i).update(players.get(j));
		
		remove();
		
	}
	public int[] spawnBonus()
	{
		int a[]=spawnTiles.get(r.nextInt(spawnTiles.size())).getLoc();
		bonus.add(new HealthBoost(a[0],a[1],this));
		return a;
	}
	public void addBonus(int x,int y)
	{
		bonus.add(new HealthBoost(x,y,this));
	}
	private void time()
	{
		
	}
	public void render(int xScroll,int yScroll,Screen screen)
	{
		screen.setOffset(xScroll,yScroll);
		int x0=xScroll>>4;
		int x1=(xScroll+screen.width+16)>>4;
		int y0=yScroll>>4;
		int y1=(yScroll+screen.height+16)>>4;
		
		for(int y=y0;y<y1;y++)
		{
			for(int x=x0;x<x1;x++)
			{
				getTile(x,y).render(x<<4, y<<4, screen);
			}
		}
		for(int i=0;i<players.size();i++)
			players.get(i).render(screen);
		for(int i=0;i<bonus.size();i++)
			bonus.get(i).render(screen);
		
	}
	public Tile getTile(int x,int y)
	{

		if(x<0||y<0||x>=width||y>=height)return Tile.voidtile;
		if(tiles[x+y*width]==0xffffffff)return Tile.ground;
	
		if(tiles[x+y*width]==0xff000000)return Tile.solidTile;
	
		
		if(tiles[x+y*width]==0xff777777)return Tile.wood;
		return Tile.voidtile;
		
	}
	public void add(Particle e)
	{
		System.out.println(players.size());
		players.get(0).addParticles(e);
	}
	public void remove()
	{
		
		for(int i=0;i<players.size();i++)
			if(players.get(i).isRemoved())
			{
				players.remove(i);
			}
		for(int i=0;i<bonus.size();i++)
			if(bonus.get(i).isRemoved())
			{
				bonus.remove(i);
			}
	}
	public void addPlayer(PlayerMP e)
	{
		
		int a[]=spawnTiles.get(r.nextInt(spawnTiles.size())).getLoc();
		e.x=(a[0]<<4);
		e.y=(a[1]<<4);
		players.add(e);
		e.level=this;	
	}
	public int[] respawn()
	{
		
		int a[]=spawnTiles.get(r.nextInt(spawnTiles.size())).getLoc();
		return a;
	}
	public void setTile(int i, int j, int k) {
		tiles[i+j*width]=k;
		
	}
	public void tileBreak(int i,int x)
	{
	
		if(i!=-1) {
			
		tileCheck.get(i).breakTile(x);
		if(tileCheck.get(i).broken) {
			tiles[tileCheck.get(i).getX()+tileCheck.get(i).getY()*width]=0xffffffff;
			spawnTiles.add(new Vector2D(tileCheck.get(i).getX(),tileCheck.get(i).getY()));
			tileCheck.remove(i);}
		}	
	}
	
	public int searchTile(int x,int y)
	{
		for(int i=0;i<tileCheck.size();i++)
		{
			if(tileCheck.get(i).getY()==y&&tileCheck.get(i).getX()==x)
				return i;
		}
		return -1;
	}
	public void removePlayer(String username) {
		// TODO Auto-generated method stub
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).getUsername().equalsIgnoreCase(username))
				players.remove(i);
		}
	}
	public int sendX(String username) {
		// TODO Auto-generated method stub
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).getUsername().equalsIgnoreCase(username))
				return players.get(i).x;
			
		}
		return -1;
	}
	public int sendY(String username) {
		// TODO Auto-generated method stub
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).getUsername().equalsIgnoreCase(username))
				return players.get(i).y;
			
		}
		return -1;
	}
	public void setX(String username,int xa) {
		// TODO Auto-generated method stub
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).getUsername().equalsIgnoreCase(username))
				players.get(i).x=xa;
			
		}
	}
	public void setY(String username,int ya) {
		// TODO Auto-generated method stub
		for(int i=0;i<players.size();i++)
		{
			if(players.get(i).getUsername().equalsIgnoreCase(username))
				 players.get(i).y=ya;
			
		}
		

	}
	public void setValue(String username,int x,int y)
	{
		setX(username,x);
		setY(username,y);
	}

}
