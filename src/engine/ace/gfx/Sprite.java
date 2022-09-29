package engine.ace.gfx;

public class Sprite {
	
	public final int width,height;
	private int x,y;
	public int[] pixels;
	private SpriteSheet sheet;
	public static Sprite ground =new Sprite(16,0,0,SpriteSheet.ground);
	public static Sprite wood =new Sprite(16,0,0,SpriteSheet.wood);
	public static Sprite solid =new Sprite(16,0,0,SpriteSheet.SolidTile);
	public static Sprite voidsprite=new Sprite(16,0x550000);
	public static Sprite shoot=new Sprite(5,0);
	public static Sprite player=new Sprite(16,0,0,SpriteSheet.players);
	public static Sprite bonus=new Sprite(16,0,0,SpriteSheet.healthbonus);
	public static Sprite healthbar=new Sprite(12,4,0,0,SpriteSheet.healthbar);

	public static Sprite n0=new Sprite(5,0,0,SpriteSheet.fontSheet);
	public static Sprite n1=new Sprite(5,1,0,SpriteSheet.fontSheet);
	public static Sprite n2=new Sprite(5,2,0,SpriteSheet.fontSheet);
	public static Sprite n3=new Sprite(5,3,0,SpriteSheet.fontSheet);
	public static Sprite n4=new Sprite(5,4,0,SpriteSheet.fontSheet);
	public static Sprite n5=new Sprite(5,5,0,SpriteSheet.fontSheet);
	public static Sprite n6=new Sprite(5,6,0,SpriteSheet.fontSheet);
	public static Sprite n7=new Sprite(5,0,1,SpriteSheet.fontSheet);
	public static Sprite n8=new Sprite(5,1,1,SpriteSheet.fontSheet);
	public static Sprite n9=new Sprite(5,2,1,SpriteSheet.fontSheet);
	public static Sprite a=new Sprite(5,3,1,SpriteSheet.fontSheet);
	public static Sprite b=new Sprite(5,4,1,SpriteSheet.fontSheet);
	public static Sprite c=new Sprite(5,5,1,SpriteSheet.fontSheet);
	public static Sprite d=new Sprite(5,6,1,SpriteSheet.fontSheet);
	public static Sprite e=new Sprite(5,0,2,SpriteSheet.fontSheet);
	public static Sprite f=new Sprite(5,1,2,SpriteSheet.fontSheet);
	public static Sprite g=new Sprite(5,2,2,SpriteSheet.fontSheet);
	public static Sprite h=new Sprite(5,3,2,SpriteSheet.fontSheet);
	public static Sprite i=new Sprite(5,4,2,SpriteSheet.fontSheet);
	public static Sprite j=new Sprite(5,5,2,SpriteSheet.fontSheet);
	public static Sprite k=new Sprite(5,6,2,SpriteSheet.fontSheet);
	public static Sprite l=new Sprite(5,0,3,SpriteSheet.fontSheet);
	public static Sprite m=new Sprite(5,1,3,SpriteSheet.fontSheet);
	public static Sprite n=new Sprite(5,2,3,SpriteSheet.fontSheet);
	public static Sprite o=new Sprite(5,3,3,SpriteSheet.fontSheet);
	public static Sprite p=new Sprite(5,4,3,SpriteSheet.fontSheet);
	public static Sprite q=new Sprite(5,5,3,SpriteSheet.fontSheet);
	public static Sprite r=new Sprite(5,6,3,SpriteSheet.fontSheet);
	public static Sprite s=new Sprite(5,0,4,SpriteSheet.fontSheet);
	public static Sprite t=new Sprite(5,1,4,SpriteSheet.fontSheet);
	public static Sprite u=new Sprite(5,2,4,SpriteSheet.fontSheet);
	public static Sprite v=new Sprite(5,3,4,SpriteSheet.fontSheet);
	public static Sprite w=new Sprite(5,4,4,SpriteSheet.fontSheet);
	public static Sprite ax=new Sprite(5,5,4,SpriteSheet.fontSheet);
	public static Sprite ay=new Sprite(5,6,4,SpriteSheet.fontSheet);
	public static Sprite z=new Sprite(5,0,5,SpriteSheet.fontSheet);
	
	public Sprite(int size,int x,int y,SpriteSheet sheet)
	{
		width=height=size;
		pixels=new int[width*height];
		this.x=x*width;
		this.y=y*height;
		this.sheet=sheet;
		load();
	}
	public Sprite(int width,int height,int x,int y,SpriteSheet sheet)
	{
		this.width=width;
		this.height=height;
		pixels=new int[width*height ];
		this.x=x*width;
		this.y=y*height;
		this.sheet=sheet;
		load();
	}
	public Sprite() {
		this.width = 5;
		this.height = 5;}
	public Sprite(int size,int colour) {
		width=height=size;
		pixels=new int[width*height];
		setColour(colour);
	}
	private void setColour(int colour)
	{
		for(int i=0;i<pixels.length;i++)
		pixels[i]=colour;
	}
	private void load()
	{
		for(int y =0;y<height;y++)
		{
			for(int x=0;x<width;x++)
			{
				pixels[x+y*width]=sheet.pixels[(x+this.x)+(y+this.y)*sheet.width];
			}
		}
	}
	

}
