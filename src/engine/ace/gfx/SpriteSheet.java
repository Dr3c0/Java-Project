package engine.ace.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int width,height;
	public int[] pixels;
	
	public static SpriteSheet SolidTile=new SpriteSheet("/Textures/SolidTile.png",16);
	public static SpriteSheet wood=new SpriteSheet("/Textures/wood.png",16);
	public static SpriteSheet players=new SpriteSheet("/Textures/player.png",16);
	public static SpriteSheet players_up=new SpriteSheet("/Textures/player up.png",16);
	public static SpriteSheet players_right=new SpriteSheet("/Textures/player right.png",16);
	public static SpriteSheet ground=new SpriteSheet("/Textures/ground.png",16);
	public static SpriteSheet healthbar=new SpriteSheet("/Textures/Healthbar.png",12,4);
	public static SpriteSheet healthbonus=new SpriteSheet("/Textures/healthBonus.png",16);
	public static SpriteSheet fontSheet=new SpriteSheet("/Textures/New folder/textSheet.png",35,30);
	public SpriteSheet(String path, int size)
	{
		this.path=path;
		width=height = size;
		pixels=new int[width*height];
		load();
		
	}
	public SpriteSheet(String path, int w,int h)
	{
		this.path=path;
        width = w;
        height=h;
		pixels=new int[width*height];
		load();
		
	}
	private void load()	
	{
		BufferedImage image=null;
		try {
		
			image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w=image.getWidth();
			int h =image.getHeight();
			image.getRGB(0,0,w,h,pixels,0,w);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error");
		}
	
		
	}
	

}