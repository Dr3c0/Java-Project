package engine.ace.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import engine.ace.Ace2D;
import engine.ace.entity.Particle.Particle;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;


public class GameClient extends Thread {
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Ace2D game;
	
	public GameClient(Ace2D game,String ipAddress)
	{
		this.game=game;
		try {
			this.socket=new DatagramSocket();
			this.ipAddress=InetAddress.getByName(ipAddress);
		} catch (SocketException e) {
						e.printStackTrace();
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void run()
	{
		while(true)
		{
			byte[] data=new byte[1024];
			DatagramPacket packet=new DatagramPacket(data,data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String message=new String(packet.getData());
			//System.out.println("SERVER >"+message);
			
			message=message.trim();
			if('0'==message.charAt(0))
			{
				addPlayer(message.substring(1));
			}
			if('1'==message.charAt(0))
			{
				removePlayer(message.substring(1));
			}
			if('2'==message.charAt(0))
			{
				updateplayer(message.substring(1));
			}
			if('3'==message.charAt(0))
			{
				addbonus(message.substring(1));
			}
			if(message.charAt(0)=='4')
			{
				addProjectileServer(message.substring(1));
			}
			if(message.charAt(0)=='5')
			{
				addParticles(message.substring(1));
			}

			
	}

		
		}	
	

	private void addbonus(String substring) {
		// TODO Auto-generated method stub
		int nextstart=0;
		String s="";
		//String username;
		int x,y;
		for(int i=0;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		//username=s;
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		x=Integer.parseInt(s);
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		y=Integer.parseInt(s);
		game.level.addBonus(x,y);
		
	}

	public void sendData(byte data[])
	{
		DatagramPacket packet=new DatagramPacket(data,data.length,ipAddress,4402);
		try {
			socket.send(packet);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	public void addPlayer(String username)
	{
		boolean isThere=false;
		for(int i=0;i<game.level.players.size();i++)
		 {
			if(game.level.players.get(i).getUsername().equalsIgnoreCase(username))
				isThere=true;
		 }
		if(!isThere) {
		game.level.addPlayer(new PlayerMP(null,4,12,3,15,0xffff3300,username,game));
		System.out.println(username +" has joined the game");
		}
	
	}

	public void quit(String username) {
		sendData(("1"+username).getBytes());
		
	}
	public void sendPlayerUpdate(String username,int x,int y)
	{
		sendData((("2"+username+" "+x)+" "+y).getBytes());
	}
	private void updateplayer(String substring) {
		int nextstart=0;
		String s="";
		String username;
		int x,y;
		for(int i=0;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		username=s;
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		x=Integer.parseInt(s);
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		y=Integer.parseInt(s);
		game.level.setValue(username, x, y);
		
	}
	public void addProjectileServer(String substring)
	{
		int player=-1;
		int nextstart=0;
		String s="";
		String username;
		int x,y;
		double dir;
		//System.out.println(substring);
		for(int i=0;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		username=s;
		for(int i=0;i<game.level.players.size();i++)
			if(username.equalsIgnoreCase(game.level.players.get(i).getUsername())){
				player=i;
			}
		if(player>0)
		{
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		x=Integer.parseInt(s);
		s="";
		//System.out.println(x);
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		y=Integer.parseInt(s);
		//System.out.println(y);
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		dir=Double.parseDouble(s);
		//System.out.println(dir);
		game.level.players.get(player).shootserver(x, y, dir);
		}
	}
	
	public void addParticles(String substring)
	{

		int player=-1;
		int nextstart=0;
		String s="";
		String username;
		short x,y;
		double life,xa,ya;
		//double dir;
		//System.out.println(substring);
		for(int i=0;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		username=s;
		for(int i=0;i<game.level.players.size();i++)
			if(username.equalsIgnoreCase(game.level.players.get(i).getUsername())){
				player=i;
			}
		if(player>0)
		{
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		x=Short.parseShort(s);
		s="";
		//System.out.println(x);
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		y=Short.parseShort(s);
		//System.out.println(y);
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		life=Double.parseDouble(s);
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		xa=Double.parseDouble(s);
		s="";
		for(int i=nextstart+1;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				nextstart=i;
				break;}
		}
		s=s.trim();
		ya=Double.parseDouble(s);
		game.level.players.get(player).addParticles(new Particle(x,y,life,game.level.players.get(player),xa,ya));
		
		}
	}

	private void removePlayer(String username) {
		// TODO Auto-generated method stub
		game.level.removePlayer(username);
		
	}


}
