package engine.ace.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import engine.ace.Ace2D;
import engine.ace.entity.mob.Player;
import engine.ace.entity.mob.PlayerMP;


public class GameServer extends Thread {
	private DatagramSocket socket;
	private Ace2D game;
	public List <PlayerMP>playermp= new ArrayList<PlayerMP>();
	public GameServer(Ace2D game)
	{
		this.game=game;
		try {
			this.socket=new DatagramSocket(4402);
		} catch (SocketException e) {
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
			//System.out.println("CLIENT >"+message);
			message=message.trim();
			if('0'==message.charAt(0))
			{
				if(message.substring(1).equalsIgnoreCase(game.getPlayer().getUsername()))
				{
					addPlayer(message.substring(1),packet.getAddress(),packet.getPort());
				}else {
					addPlayer(message.substring(1),packet.getAddress(),packet.getPort());
			        addPlayerToAll(message.substring(1));
			        sendPlayerToAll();
				}
			//sendData(("0"+game.getPlayer().getUsername()).getBytes(),packet.getAddress(),packet.getPort());	
		}
			if('1'==message.charAt(0))
			{
				removePlayer(message.substring(1));
				sendDataToAllRemove(message.getBytes());
			}
			if('2'==message.charAt(0))
			{
				updatePlayerToAll(message.getBytes());	
			}
			if('3'==message.charAt(0))
			{
				bonusUpdate(message.getBytes());	
			}
			if('4'==message.charAt(0))
			{
				sendDataToAll(message.getBytes());	
			}
			if('5'==message.charAt(0))
			{
				sendDataToAll(message.getBytes());	
			}
			
		}
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

	private void removePlayer(String username) {
		// TODO Auto-generated method stub
		for(int i=0;i<playermp.size();i++)
			if(playermp.get(i).getUsername().equalsIgnoreCase(username))
				playermp.remove(i);

		
	}

	public void sendData(byte data[],InetAddress ipAddress,int port)
	{
		DatagramPacket packet=new DatagramPacket(data,data.length,ipAddress,port);
		try {
			socket.send(packet);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public void addPlayer(String username,InetAddress i,int port)
	{
		if(game.level.players.get(0).getUsername().equalsIgnoreCase(username))
		{
			game.level.players.get(0).ip=i;
			game.level.players.get(0).port=port;
			playermp.add(game.level.players.get(0));
			
		}else {
		 
	    PlayerMP p=new PlayerMP(null,4,12,3,15,0xffff3300,username,i,port,game);
		game.level.addPlayer(p);
		playermp.add(p);}
		System.out.println(username +" has joined the game");
		
		
	}
	public void addPlayerToAll(String username)
	{ 
			sendDataToAll(("0"+username).getBytes());
	}
	
	public void sendDataToAll(byte[] data)
	{
		String s=new String(data);
		for(int i=0;i<playermp.size();i++)
		{
			if(!s.substring(1).equalsIgnoreCase(playermp.get(i).getUsername()))
			sendData(data,playermp.get(i).ip,playermp.get(i).port);
		}
		
	}
	public void sendDataToAllRemove(byte[] data)
	{
		//String s=new String(data);
		for(int i=0;i<playermp.size();i++)
		{
			//if(!s.substring(1).equalsIgnoreCase(playermp.get(i).getUsername()))
			sendData(data,playermp.get(i).ip,playermp.get(i).port);
		}
		
	}
	public void sendPlayerToAll()
	{
		for(int j=0;j<playermp.size();j++)
		{
			sendDataToAll(("0"+playermp.get(j).getUsername()).getBytes());
		}
		
		
	}
	public void updatePlayerToAll(byte[] data)
	{
		String substring=new String(data);
		String s="";
		for(int i=0;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				break;
				}
		}
		
		for(int i=0;i<playermp.size();i++)
		{
			if(!s.substring(1).equalsIgnoreCase(playermp.get(i).getUsername()))
			sendData(data,playermp.get(i).ip,playermp.get(i).port);
		}
	}
	public void bonusUpdate(byte[] data)
	{
		String substring=new String(data);
		String s="";
		for(int i=0;i<substring.length();i++)
		{
			if(substring.charAt(i)!=' ')
			s+=substring.charAt(i);
			else {
				break;
				}
		}
		
		for(int i=0;i<playermp.size();i++)
		{
			if(!s.substring(1).equalsIgnoreCase(playermp.get(i).getUsername()))
			sendData(data,playermp.get(i).ip,playermp.get(i).port);
		}
	}

}
