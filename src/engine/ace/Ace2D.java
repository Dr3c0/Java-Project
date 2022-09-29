package engine.ace;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Input.Keyboard;
import Input.Mouse;
import Input.WindowHandler;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;
import engine.ace.gfx.Screen;
import engine.ace.gfx.Sprite;
import engine.ace.net.GameClient;
import engine.ace.net.GameServer;

import level.Level;
import level.RandomLevel;
import level.SpawnLevel;
public class Ace2D extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	//Resolution
	public static int width=300;
	public static int  height=(width/16*9);
	public static int scale=3;
	private PlayerMP player;

	
	//Create a thread
	private Thread thread;
	private boolean running=false;
	private int  time=0;
	//image
	private BufferedImage image= new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	private int pixels[]= ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	
	
	public Keyboard key;
	
	public Level level;
	public WindowHandler windowHandler;
	
	
	Sprite sprite;
	//JFrame construction
	public JFrame frame;
	
	public  GameClient socketClient;
	public  GameServer socketServer;
	
	public Ace2D()
	{
		Dimension size = new Dimension(width * scale,height * scale);
		setPreferredSize(size);//preferred size
		
		frame = new JFrame();
		 
		screen=new Screen(width,height);
		
		key=new Keyboard();
		this.addKeyListener(key);
		Mouse m= new Mouse();
		this.addMouseListener(m);
		this.addMouseMotionListener(m);
		windowHandler=new WindowHandler(this);
		level=new SpawnLevel("/Textures/level.png");
		setPlayer(new PlayerMP(key,4,12,3,15,0xffff3300,JOptionPane.showInputDialog("Enter User Name"),null,-1,this));
		level.addPlayer(getPlayer());
		sprite = new Sprite(3,0xffffff);
		if(JOptionPane.showConfirmDialog(this,"Do you want to start the server")==0)
		{
			socketServer=new GameServer(this);
		}
		socketClient=new GameClient(this,"localhost");
		socketClient.sendData(("0"+player.getUsername()).getBytes());
		
		
		
		
	}
	public static int getWindowWidth()
	{
		return (scale*width);
	}
	public static int getWindowHeight()
	{
		return (scale*height);
	}
	
	//start game
	public synchronized void start()
	{ 
		
		running=true;
		thread=new Thread(this,"Display");
		thread.start();
		if(socketServer!=null)
		socketServer.start();
		socketClient.start();


		
	}
	//end
	public synchronized void stop()
	{
		running=false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		
		}
	}
	public void run() {
		long lastTime=System.nanoTime();
		double nspt=1000000000.0/60d;
		double delta=0;
		int frames=0,updates=0;
		long timer=System.currentTimeMillis();
		
		while(running)
		{
			long now=System.nanoTime();
			delta+=(now-lastTime)/nspt;
			while(delta>=1) {
				update();
				updates++;
				delta--;
			}
			lastTime=now;
	        render();
	        frames++;
	        
	        if(System.currentTimeMillis()-timer>=1000)
	        {
	        	//System.out.println(updates+":Updates "+frames+":Frames");
	        	this.frame.setTitle("ACE "+frames);
	            timer+=1000;
	        	updates=frames=0;
	        	
	        }
		}
		stop();
	}
	
	public void update()
	{
		if(socketServer!=null)
		time++;
		key.update();
		level.update();
		if(time==1800&&socketServer!=null)
		{
			time=0;
			int a[]=level.spawnBonus();
			socketClient.sendData(("3"+player.getUsername()+" "+a[0]+" "+a[1]).getBytes());
		}
		socketClient.sendPlayerUpdate(player.getUsername(), player.x, player.y);
	}
	
	public void render()
	{
		BufferStrategy bs= getBufferStrategy();
		if(bs==null) {
			createBufferStrategy(3);
		    return;
		}
		screen.clear();
		try {
	
		level.render(level.players.get(0).x-screen.width/2,level.players.get(0).y-screen.height/2, screen);}
		catch(Exception e)
		{
			
		}
		for(int i=0;i<width*height;i++)
		pixels[i]=screen.pixels[i];
		Graphics g=bs.getDrawGraphics();
		g.drawImage(image,0,0,getWidth(),getHeight(),null);
		g.fillRect(Mouse.getX()-9,Mouse.getY()-9,18,18);
		//g.drawString(""+Mouse.getButton(),50,50);
		g.dispose();
	    bs.show();
		
	}
	public static void main(String a[])
	{
		Ace2D game=new Ace2D();
		game.frame.setResizable(false);//Re-sizable not negative
		game.frame.setTitle("Ace");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
		
		
		
	}
	public PlayerMP getPlayer() {
		return player;
	}
	public void setPlayer(PlayerMP player) {
		this.player = player;
	}

}
