/**
 * ConnectClient1.java
 * 
 * Version 1.0
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.* ;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 *
 *This class acts as player 2 in Connect 4 game
 */
public class TicTacClient
{
	/**
	 * This is main function
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception
	{
		Socket s=new Socket("localhost",1234);  
		DataInputStream din=new DataInputStream(s.getInputStream());  
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
		ObjectInputStream ois = new ObjectInputStream(din);
		
		int stop = 0 ;
		String str1 = "" ;
		String str2 = "" ;
		
		/*
		 * Receive instructions from server and pass board number to the client
		 * The updated board is displayed.
		 */
		while(stop == 0)
		{
			TicView tv = (TicView)ois.readObject();
			tv.display();
			str2 = din.readUTF();
			if(str2.equals("Player 1 Wins") || str2.equals("Player 2 Wins") || str2.equals("Draw") )
			{
				if(str2.equals("Draw"))
					tv.draw();
				else
					System.out.println(str2) ;
				stop = 1 ;
			}
			else
			{
				System.out.println(str2) ;
				str1 = br.readLine();
				dout.writeUTF(str1);
				dout.flush();
			}
		}
		
		/*
		 * Close the connections after game is over
		 */
		dout.close();
		din.close();  
		s.close();  
	}
}
