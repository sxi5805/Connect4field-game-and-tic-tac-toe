/**
 * ConnectServer.java
 * 
 * Version 1.0
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 *
 */
public class ConnectServer implements Runnable
{
	static ConnectBoard cb ;
	ConnectView cv ;
	char piece ;
	
	static int pf = 1 ;
	static int win = 0 ;
	static int winner = 0 ;
	
	ServerSocket ss ;
	Socket s ;
	DataInputStream din ;
	DataOutputStream dout ; 
	ObjectOutputStream oos ;
	
	/**
	 * Constructor to initialize the game piece of player
	 * @param piece
	 * @param cb
	 */
	public ConnectServer(char piece , ConnectBoard cb)
	{
		this.piece = piece ;
		this.cb = cb ;
	}
	
	/**
	 * Constructor to initialize game piece and socket components
	 * @param piece
	 * @param ss
	 * @param s
	 * @param din
	 * @param dout
	 * @param oos
	 * @param cb
	 */
	public ConnectServer(char piece, ServerSocket ss, Socket s, DataInputStream din,
			DataOutputStream dout, ObjectOutputStream oos, ConnectBoard cb)
	{
		this.piece = piece ;
		this.ss = ss;
		this.s = s ;
		this.din = din ;
		this.dout = dout ;
		this.oos = oos ;
		this.cb = cb ;
	}
	
	/**
	 * The function checks whether piece can be dropped on that column
	 * @param column
	 * @return
	 */
	public boolean checkIfPiecedCanBeDroppedIn(int column)
	{
		if(column < 0)
			return false;
		else if(column>24)
			return false ;
		else
		{
			int i , x = 0 ; 
			for( i = 9 ; i >= 0 ; i --)
			{
// The code checks whether the column specified is empty or not
				if(cb.brd[i][column].equals("_"))
				{
					//System.out.println(cb.brd[i][column]) ;
					x = x + 1 ;
					break ;
				}
			}
			if( x > 0)
				return true ;
			else
				return false ;
		}
	}
	
	/**
	 * Drop the piece in the column entered by the user
	 * @param column
	 * @param gamePiece
	 */
	public void dropPieces(int column, char gamePiece)
	{
		int i ;
		for(i = 9 ; i >= 0 ; i --)
		{
			if(cb.brd[i][column].equals("_"))
			{
				cb.brd[i][column] = Character.toString(gamePiece) ;
				break ;
			}
		}
	}

/**
 * This function whether the last move made by the user result in any kind of
 * formation of a pattern
 */
	public boolean didLastMoveWin() 
{
	int i , j , x ;

// The code checks whether a horizontal straight line is formed consisting of 4 similar symbols
		for( i = 0 ; i < 10 ; i ++)
		{
			for(j = 0 ; j < 22 ; j ++)
			{
				if(cb.brd[i][j].equals(cb.brd[i][j+1]) &&
						cb.brd[i][j].equals(cb.brd[i][j+2]) &&
						cb.brd[i][j].equals(cb.brd[i][j+3]) &&
						!cb.brd[i][j].equals("_") && !cb.brd[i][j].equals(" "))
				{
					return true ;
				}
			}
		}

// The code checks whether a vertical straight line is formed consisting of 4 similar symbols
		j = 0 ;
		while( j < 25 )
		{
			for(i = 0 ; i < 7 ; i ++)
			{
				if(cb.brd[i][j].equals(cb.brd[i+1][j]) &&
						cb.brd[i][j].equals(cb.brd[i+2][j])  &&
						cb.brd[i][j].equals(cb.brd[i+3][j]) &&
						!cb.brd[i][j].equals("_")  && !cb.brd[i][j].equals(" "))
				{
					return true ;
				}
			}
			j = j + 1 ;
		}

// The code checks whether a slant line is formed consisting of 4 similar symbols
		for(i = 0 ; i < 7 ; i ++)
		{
			for(j = 0 ; j < 22 ; j ++)
			{
				if(cb.brd[i][j].equals(cb.brd[i+1][j+1]) &&
						cb.brd[i][j].equals(cb.brd[i+2][j+2]) &&
						cb.brd[i][j].equals(cb.brd[i+3][j+3]) &&
						!cb.brd[i][j].equals("_")  && !cb.brd[i][j].equals(" "))
				{
					return true ;
				}
					
			}
		}
	
// The code checks whether a reverse slant line is formed consisting of 4 similar symbols
		for(i = 9 ; i >= 3 ; i--)
		{
			for(j = 0 ; j < 22 ; j ++)
			{
				if(cb.brd[i][j].equals(cb.brd[i-1][j+1]) &&
						cb.brd[i][j].equals(cb.brd[i-2][j+2]) &&
						cb.brd[i][j].equals(cb.brd[i-3][j+3]) &&
						!cb.brd[i][j].equals("_")  && !cb.brd[i][j].equals(" "))
				{
					return true ;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * The function checks whether all the places of the board are filled
	 * @return
	 */
	public boolean isItaDraw()
	{
		int i , j , x = 0 ; 
		for(i = 0 ; i < 10 ; i ++)
		{
			for(j = 0 ; j < 25 ; j ++)
			{
				if(cb.brd[i][j].equals("_")) 
				{
					x = x + 1 ;
					break ;
				}
			}
		}
		if(x == 0)
			return true;
		else
			return false ;
	}
	
	/**
	 * The run method of thread class which toggles between different users
	 */
	public void run()
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			try{
				notify() ;
			}catch(Exception e) {}
			/*
			 * The below piece runs for player 1 on the server.
			 */
			if(piece == '+')
			{
				if(pf == 1)
				{
					int cno = 0 ;
					cv = new ConnectView(cb) ;
					cv.display();
					System.out.println("Player 1, Enter column number");
					try{
						cno = Integer.parseInt(br.readLine()) ;
					}catch(Exception e) {}
					if(checkIfPiecedCanBeDroppedIn(cno))
					{
						dropPieces(cno,piece) ;
						if(didLastMoveWin())
						{
							cv = new ConnectView(cb) ;
							cv.display();
							win = 1 ;
							winner = 1 ;
							System.out.println("Player 1 Wins") ;
						}
						else if(isItaDraw())
						{
							cv = new ConnectView(cb) ;
							cv.display();
							win = 1 ; 
							winner = 5 ;
							System.out.println("Draw") ;
						}
						else
							pf = 2 ;
					}
					else
						pf = 2 ;
				}
				else
				{
					try{
						wait() ;
					}catch(Exception e) {}
				}
			}
			/*
			 * The below code runs for player 2. It connects with client program
			 * and sends the game board to the client and waits for the client
			 * to enter board number. Once board number is recieved, the game board
			 * is updated
			 */
			else if(piece == '*')
			{
				if(pf == 2)
				{
					int cno ;
					cv = new ConnectView(cb) ;
					try
					{
						oos.reset();
						oos.writeObject(cv);
						String st = "Player 2, Enter column number" ;
						dout.writeUTF(st);
						dout.flush();
						cno = Integer.parseInt(din.readUTF()) ;
						if(checkIfPiecedCanBeDroppedIn(cno))
						{
							dropPieces(cno,piece) ;
							if(didLastMoveWin())
							{
								cv = new ConnectView(cb) ;
								cv.display();
								win = 1 ;
								winner = 2 ;
								System.out.println("Player 2 Wins") ;
							}
							else if(isItaDraw())
							{
								cv = new ConnectView(cb) ;
								cv.display();
								win = 1 ; 
								winner = 5 ;
								System.out.println("Draw") ;
							}
							else
								pf = 3 ;
						}
						else
							pf = 3 ;
					}catch(Exception e) {}
				}
				else
				{
					try{
						wait() ;
					}catch(Exception e) {}
				}
			}
			/*
			 * The below code runs for player 3. It connects with client program
			 * and sends the game board to the client and waits for the client
			 * to enter board number. Once board number is recieved, the game board
			 * is updated
			 */
			else if(piece == '&')
			{
				if(pf == 3)
				{
					int cno ;
					cv = new ConnectView(cb) ;
					try
					{
						oos.reset();
						oos.writeObject(cv);
						String st = "Player 3, Enter column number" ;
						dout.writeUTF(st);
						dout.flush();
						cno = Integer.parseInt(din.readUTF()) ;
						if(checkIfPiecedCanBeDroppedIn(cno))
						{
							dropPieces(cno,piece) ;
							if(didLastMoveWin())
							{
								cv = new ConnectView(cb) ;
								cv.display();
								win = 1 ;
								winner = 3 ;
								System.out.println("Player 3 Wins") ;
							}
							else if(isItaDraw())
							{
								cv = new ConnectView(cb) ;
								cv.display();
								win = 1 ; 
								winner = 5 ;
								System.out.println("Draw") ;
							}
							else
								pf = 4 ;
						}
						else
							pf = 4 ;
					}catch(Exception e) {}
				}
				else
				{
					try{
						wait() ;
					}catch(Exception e) {}
				}
			}
			/*
			 * The below code runs for player 4. It connects with client program
			 * and sends the game board to the client and waits for the client
			 * to enter board number. Once board number is recieved, the game board
			 * is updated
			 */
			else if(piece == '^')
			{
				if(pf == 4)
				{
					int cno ;
					cv = new ConnectView(cb) ;
					try
					{
						oos.reset();
						oos.writeObject(cv);
						String st = "Player 4, Enter column number" ;
						dout.writeUTF(st);
						dout.flush();
						cno = Integer.parseInt(din.readUTF()) ;
						if(checkIfPiecedCanBeDroppedIn(cno))
						{
							dropPieces(cno,piece) ;
							if(didLastMoveWin())
							{
								cv = new ConnectView(cb) ;
								cv.display();
								win = 1 ;
								winner = 4 ;
								System.out.println("Player 4 Wins") ;
							}
							else if(isItaDraw())
							{
								cv = new ConnectView(cb) ;
								cv.display();
								win = 1 ; 
								winner = 5 ;
								System.out.println("Draw") ;
							}
							else
								pf = 1 ;
						}
						else
							pf = 1 ;
					}catch(Exception e) {}
				}
				else
				{
					try{
						wait() ;
					}catch(Exception e) {}
				}
			}
			
			/*
			 * Once the game is over, all connections are closed and messages
			 */
			if(win == 1)
			{
				cv = new ConnectView(cb) ;
				try
				{
					oos.reset();
					oos.writeObject(cv);
					if(winner == 1)
						dout.writeUTF("Player 1 Wins");
					else if(winner == 2)
						dout.writeUTF("Player 2 Wins");
					else if(winner == 3)
						dout.writeUTF("Player 3 Wins");
					else if(winner == 4)
						dout.writeUTF("Player 4 Wins");
					else if(winner == 5)
						dout.writeUTF("Draw");
					dout.close();
					din.close();
					s.close();
					ss.close();
				}catch(Exception e) {}
				break ;
			}
		}
	}
	
	/**
	 * This is main function
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception
	{
		cb = new ConnectBoard() ;
		
		ConnectServer cs1 = new ConnectServer( '+' , cb) ;
		Thread t1 = new Thread(cs1) ;
		
		ServerSocket ss = new ServerSocket(5678) ;
		
		/*
		 * Accept the connection of player 2
		 */
		Socket s1 = ss.accept() ;
		DataInputStream din1 = new DataInputStream(s1.getInputStream());  
		DataOutputStream dout1 = new DataOutputStream(s1.getOutputStream()); 
		ObjectOutputStream oos1  = new ObjectOutputStream(dout1 );
		System.out.println("Player 2 Connected") ;
		ConnectServer cs2 = new ConnectServer('*', ss, s1, din1, dout1, oos1, cb) ;
		Thread t2 = new Thread(cs2) ;
		
		/*
		 * Accept the connection of player 3
		 */
		Socket s2 = ss.accept() ;
		DataInputStream din2 = new DataInputStream(s2.getInputStream());  
		DataOutputStream dout2 = new DataOutputStream(s2.getOutputStream()); 
		ObjectOutputStream oos2  = new ObjectOutputStream(dout2 );
		System.out.println("Player 3 Connected") ;
		ConnectServer cs3 = new ConnectServer('&', ss, s2, din2, dout2, oos2, cb) ;
		Thread t3 = new Thread(cs3) ;
		
		/*
		 * Accept the connection of player 4
		 */
		Socket s3 = ss.accept() ;
		DataInputStream din3 = new DataInputStream(s3.getInputStream());  
		DataOutputStream dout3 = new DataOutputStream(s3.getOutputStream()); 
		ObjectOutputStream oos3  = new ObjectOutputStream(dout3);
		System.out.println("Player 4 Connected") ;
		ConnectServer cs4 = new ConnectServer('^', ss, s3, din3, dout3, oos3, cb) ;
		Thread t4 = new Thread(cs4) ;
		
		/*
		 * Start all the threads
		 */
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}
}
