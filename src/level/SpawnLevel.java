package level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import level.tile.Tile;

public class SpawnLevel extends Level
{
	//private int[] levelPixels;
	public SpawnLevel(String path)
	{
		super(path);
		
	}
	SpawnLevel()
	{
		
	}
	protected void loadLevel(String path)
	{
		
		try {
			BufferedImage image=ImageIO.read(SpawnLevel.class.getResource(path));
			width=image.getWidth();
			
		    height=image.getHeight();
			tiles= new int[width*height];
			image.getRGB(0,0,width,height,tiles,0,width);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	

}
