package engine.ace.gfx;

import java.util.Random;

import engine.ace.entity.mob.Player;
import level.tile.Tile;

public class Screen {
	public int width;
	public int height;
	public int pixels[];
	public final int mapsize=64;
	public final int mapmask=mapsize-1;
	public int [] tiles=new int[mapsize*mapsize];
	public int xOffset,yOffset;
	public Screen(int w,int h)
	{
		width=w;
		height=h;
		pixels=new int[width*height];
		Random r=new Random();
		for(int i=0;i<mapsize*mapsize;i++)
			tiles[i]=r.nextInt(0xffffff);
	}
	
	
	public void clear()
	{
		for(int i=0;i<width*height;i++)
		{
			pixels[i]=0;
		}
	}
	
	public void render(int xoff,int yoff)
	{
		
		for(int y=0;y<height;y++)
		{
			int yy=y+yoff;
			if (yy<0||yy>=height)continue;
		
			for(int x=0; x<width;x++)
			{
				int xx=x+xoff;
				if (xx<0||xx>=width)continue;
				pixels[xx+yy*width]=Sprite.ground.pixels[(x&15)+(y&15)*Sprite.ground.width];
			}
		}
	}
	
	public void renderTile(int xp, int yp,Tile tile)
	{
		xp=xp-xOffset;yp=yp-yOffset;
		for(int y=0;y<tile.sprite.height;y++)
		{
			int ya=yp+y;
			for(int x=0;x<tile.sprite.width;x++)
			{ 
				int xa= x+xp;
				if (xa<0||xa>=width||ya<0||ya>=height)continue;
				pixels[xa+ya*width]=tile.sprite.pixels[x+y*tile.sprite.width];
				
			}
		}
	}
	public void renderSprite(int xp, int yp,Sprite s,boolean fixed)
	{
		if(fixed) {
		xp=xp-xOffset;yp=yp-yOffset;}
		for(int y=0;y<s.height;y++)
		{
			int ya=yp+y;
			for(int x=0;x<s.width;x++)
			{ 
				int xa= x+xp;
				if (xa<0||xa>=width||ya<0||ya>=height)continue;
				pixels[xa+ya*width]=s.pixels[x+y*s.width];
				
			}
		}
	}
	public void renderSprite2(int xp, int yp,Sprite s,boolean fixed)
	{
		if(fixed) {
		xp=xp-xOffset;yp=yp-yOffset;}
		for(int y=0;y<s.height;y++)
		{
			int ya=yp+y;
			for(int x=0;x<s.width;x++)
			{ 
				int xa= x+xp;
				if (xa<0||xa>=width||ya<0||ya>=height)continue;
				if(s.pixels[x+y*16]!=0xffff00ff)
					pixels[xa+ya*width]=s.pixels[x+y*s.width];
				
			}
		}
	}
	public void setOffset(int x,int y)
	{
		xOffset=x;
		yOffset=y;
	}
	public void renderPlayer(int xp, int yp,Sprite sprite)
	{
		xp=xp-xOffset;yp=yp-yOffset;
		for(int y=0;y<16;y++)
		{
			int ya=yp+y;
			for(int x=0;x<16;x++)
			{ 
				int xa= x+xp;
				if (xa<0||xa>=width||ya<0||ya>=height)continue;
				if(sprite.pixels[x+y*16]!=0xffff00ff)
				pixels[xa+ya*width]=sprite.pixels[x+y*sprite.width];
				
			}
		}
	}
	public void renderPlayer(int xp, int yp,Sprite sprite,int color)
	{
		xp=xp-xOffset;yp=yp-yOffset;
		for(int y=0;y<16;y++)
		{
			int ya=yp+y;
			for(int x=0;x<16;x++)
			{ 
				int xa= x+xp;
				if (xa<0||xa>=width||ya<0||ya>=height)continue;
				if(sprite.pixels[x+y*16]!=0xffff00ff) {
					if(sprite.pixels[x+y*16]!=0xffdeadaf)
					pixels[xa+ya*width]=sprite.pixels[x+y*sprite.width];
					else 
						pixels[xa+ya*width]=color;
				}
				
				
			}
		}
	}
	public void renderBar(int xp, int yp,int health,Sprite sprite)
	{
		xp=xp-xOffset;yp=yp-yOffset;health=health/10;
		if(health>10)health=10;
		for(int y=0;y<sprite.height;y++)
		{
			int health1=health;
			int ya=yp+y;
			for(int x=0;x<sprite.width;x++)
			{ 
				int xa= x+xp;
				if (xa<0||xa>=width||ya<0||ya>=height)continue;
				if(sprite.pixels[x+y*sprite.width]==0xffff00ff) {
					
					if(health1>0)
					{
						health1--;
						pixels[xa+ya*width]=0xff009933;
					}
					else
					{
						{
							pixels[xa+ya*width]=0xffcc0000;
						}
					}
	
				}
				else 
					pixels[xa+ya*width]=sprite.pixels[x+y*sprite.width];
				
				
			}
		}
	}
	public void renderCharacter(int xp,int yp,Sprite s)
	{
					xp=xp-xOffset;yp=yp-yOffset;
			for(int y=0;y<s.height;y++)
			{
				int ya=yp+y;
				for(int x=0;x<s.width;x++)
				{ 
					int xa= x+xp;
					if (xa<0||xa>=width||ya<0||ya>=height)continue;
					if(s.pixels[x+y*s.width]==0xff000000) {
						pixels[xa+ya*width]=s.pixels[x+y*s.width];
						}
	}
	
	
			}
	}
	public void renderString(int x,int y,String s)
	{
		s=s.toUpperCase();
		for(int i=0;i<s.length();i++)
		{
			switch(s.charAt(i)) {
			case 'A':
				renderCharacter(x,y,Sprite.a);
				break;
			case 'B':
				renderCharacter(x,y,Sprite.b);
				break;
			case 'C':
				renderCharacter(x,y,Sprite.c);
				break;
			case 'D':
				renderCharacter(x,y,Sprite.d);
				break;
			case 'E':
				renderCharacter(x,y,Sprite.e);
				break;
			case 'F':
				renderCharacter(x,y,Sprite.f);
				break;
			case 'G':
				renderCharacter(x,y,Sprite.g);
				break;
			case 'H':
				renderCharacter(x,y,Sprite.h);
				break;
			case 'I':
				renderCharacter(x,y,Sprite.i);
				break;
			case 'J':
				renderCharacter(x,y,Sprite.j);
				break;
			case 'K':
				renderCharacter(x,y,Sprite.k);
				break;
			case 'L':
				renderCharacter(x,y,Sprite.l);
				break;
			case 'M':
				renderCharacter(x,y,Sprite.m);
				break;
			case 'N':
				renderCharacter(x,y,Sprite.n);
				break;
			case 'O':
				renderCharacter(x,y,Sprite.o);
				break;
			case 'P':
				renderCharacter(x,y,Sprite.p);
				break;
			case 'Q':
				renderCharacter(x,y,Sprite.q);
				break;
			case 'R':
				renderCharacter(x,y,Sprite.r);
				break;
			case 'S':
				renderCharacter(x,y,Sprite.s);
				break;
			case 'T':
				renderCharacter(x,y,Sprite.t);
				break;
			case 'U':
				renderCharacter(x,y,Sprite.u);
				break;
			case 'V':
				renderCharacter(x,y,Sprite.v);
				break;
			case 'W':
				renderCharacter(x,y,Sprite.w);
				break;
			case 'X':
				renderCharacter(x,y,Sprite.ax);
				break;
			case 'Y':
				renderCharacter(x,y,Sprite.ay);
				break;
			case 'Z':
				renderCharacter(x,y,Sprite.z);
				break;
			case '0':
				renderCharacter(x,y,Sprite.n0);
				break;
			case '1':
				renderCharacter(x,y,Sprite.n1);
				break;
			case '2':
				renderCharacter(x,y,Sprite.n2);
				break;
			case '3':
				renderCharacter(x,y,Sprite.n3);
				break;
			case '4':
				renderCharacter(x,y,Sprite.n4);
				break;
			case '5':
				renderCharacter(x,y,Sprite.n5);
				break;
			case '6':
				renderCharacter(x,y,Sprite.n6);
				break;
			case '7':
				renderCharacter(x,y,Sprite.n7);
				break;
			case '8':
				renderCharacter(x,y,Sprite.n8);
				break;
			case '9':
				renderCharacter(x,y,Sprite.n9);
				break;	
			}
			x+=6;
		}
	}
}
