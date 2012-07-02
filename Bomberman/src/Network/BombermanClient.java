package Network;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import Engine.DebugConsole;


public class BombermanClient extends Thread
{
	public static void main(String args[])
	{
		DebugConsole.setVerboseLevel(2);
		BombermanClient client1 = new BombermanClient("localhost", 5730);
		client1.setClientInputState((byte) 18);
		client1.start();
		
		
		
		while (client1.clientConnectedToServer() == false)
		{}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(client1.getServerInputState());
		
		client1.shutdown();

	}
	
	
	private Socket Socket;
	private String address;
	private int port;
	
	private Thread Client_Thread;
	private boolean game_running = true;
	private AtomicBoolean connected = new AtomicBoolean(false);
	
	private byte input_client;
	private byte input_server;
	private Object Lock = new Object();
	
	
	public BombermanClient(String address, int port)
	{
		DebugConsole.Print("Client connecting to address " + address + " port " + port);
		
		this.port = port;
		this.address = address;	
	}
	
	
	
	public void init() throws UnknownHostException, IOException
	{
		DebugConsole.Print("Connecting to server...");
		
		
		this.Socket = new Socket(this.address, this.port);

		
		this.connected.set(true);
		
		DebugConsole.Print("Connection successful");
		DebugConsole.Print("Starting client thread");
		this.Client_Thread = new Thread(this);
		this.Client_Thread.start();
	}
	
	public void run()
	{		
		while (this.game_running == true)
		{
			byte[] buffer_read = null;
			byte[] buffer_send = new byte[1];
			
			try 
			{
				buffer_read = this.getPackage();
			
				synchronized (this.Lock)
				{
					this.input_server = buffer_read[0];
					buffer_send[0] = this.input_client;
				}

				this.sendPackage(buffer_send);
			}
			catch (IOException e) 
			{
				DebugConsole.PrintError("Connection to server lost");
				this.connected.set(false);
				this.game_running = false;
				break;
			}
		}
	}
	
	public void shutdown()
	{
		DebugConsole.Print("Closing connection to server");
		
		this.game_running = false;
		
		try 
		{
			this.Client_Thread.join();
			this.Socket.close();
			this.connected.set(false);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		DebugConsole.Print("Closing connection done");
	}
	
	
	
	private void sendPackage(byte[] pack) throws IOException
	{
		DataOutputStream Output = new DataOutputStream(this.Socket.getOutputStream());
		Output.write(pack);
	}
	
	private byte[] getPackage() throws IOException
	{
		byte[] buffer = new byte[1024];
		int len = 0;
		
		DataInputStream Input = new DataInputStream(this.Socket.getInputStream());
		len = Input.read(buffer);

		byte[] buffer_ret = new byte[len];
		for (int i = 0; i < len; i++)
		{
			buffer_ret[i] = buffer[i];
		}
		
		return buffer_ret;	
	}

	
	
	public boolean clientConnectedToServer()
	{
		return this.connected.get();
	}
	
	public void setClientInputState(byte input_vector)
	{
		synchronized (this.Lock)
		{
			this.input_client = input_vector;
		}
	}
	
	public byte getServerInputState()
	{
		byte tmp;
		synchronized (this.Lock)
		{
			tmp = this.input_server;
		}
		
		return tmp;
	}	
	
	
	
	public static byte convertToPackage(boolean up, boolean down, boolean left, boolean right, boolean bomb)
	{
		byte vector = 0x00;
		if (up == true)
			vector |= (1 << 0);
		if (down == true)
			vector |= (1 << 1);
		if (left == true)
			vector |= (1 << 2);
		if (right == true)
			vector |= (1 << 3);
		if (bomb == true)
			vector |= (1 << 4);
		
		return vector;
	}
	
	public static boolean[] convertToBoolean(byte pack)
	{
		boolean[] input_states = new boolean[5];
		
		if ((pack & (1 << 0)) == 1)
			input_states[0] = true;
		if ((pack & (1 << 1)) == 1)
			input_states[1] = true;
		if ((pack & (1 << 2)) == 1)
			input_states[2] = true;
		if ((pack & (1 << 3)) == 1)
			input_states[3] = true;
		if ((pack & (1 << 4)) == 1)
			input_states[4] = true;
	
		return input_states;
	}

}
