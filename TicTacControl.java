/**
 * TicTacControl.java
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 *
 * This class acts as a controller for TicTacToe game
 */
public class TicTacControl implements Runnable
{
	static TicTacBoard ttb ;
	TicView tv ;
	char piece ;
	static int pf = 1 ;
	static int win = 0;
	
	/**
	 * Constructor to initialize model object
	 * @param ttb
	 */
	public TicTacControl(TicTacBoard ttb)
	{	
		this.ttb = ttb ;
	}
	
	/**
	 * Constructor to initialize the game piece of the player
	 * @param piece
	 */
	public TicTacControl(char piece)
	{
		this.piece = piece ;
	}
	
	/**
	 * Put the piece in the board suggested by the user
	 * @param bno
	 * @param piece
	 */
	void putpiece(int bno , char piece)
	{
		if(bno == 1)
			ttb.tic[0][0] = piece ;
		else if(bno == 2)
			ttb.tic[0][1] = piece ;
		else if(bno == 3)
			ttb.tic[0][2] = piece ;
		else if(bno == 4)
			ttb.tic[1][0] = piece ;
		else if(bno == 5)
			ttb.tic[1][1] = piece ;
		else if(bno == 6)
			ttb.tic[1][2] = piece ;
		else if(bno == 7)
			ttb.tic[2][0] = piece ;
		else if(bno == 8)
			ttb.tic[2][1] = piece ;
		else if(bno == 9)
			ttb.tic[2][2] = piece ;
	}
	
	/**
	 * Function checks whether user has won the game by his last move
	 * @param piece
	 * @return
	 */
	public boolean isWin(char piece)
	{
		int i , j , x = 0 , y = 0 , z = 0 , w = 0 , m = 0 , n = 0 , o = 0  ;
		for(i = 0 ; i < 3 ; i ++)
		{
			for(j = 0 ; j < 3 ; j ++)
			{
				if(ttb.tic[i][j] == piece && i == j )
					x = x + 1 ;
				if(ttb.tic[i][j] == piece && i == 0)
					y = y + 1 ;
				if(ttb.tic[i][j] == piece && i == 1)
					z = z + 1 ;
				if(ttb.tic[i][j] == piece && i == 2)
					w = w + 1 ;
				if(ttb.tic[i][j] == piece && j == 0)
					m = m + 1 ;
				if(ttb.tic[i][j] == piece && j == 1)
					n = n + 1 ;
				if(ttb.tic[i][j] == piece && j == 2)
					o = o + 1 ;
			}
		}
		if(x == 3 || y == 3 || z == 3 || w == 3 || m == 3 || n == 3 || o == 3)
			return true ;
		if(ttb.tic[0][2] == ttb.tic[1][1] && ttb.tic[0][2] == ttb.tic[2][0] && ttb.tic[0][2] == piece)
			return true ;
		return false ;
	}
	
	/**
	 * Function checks whether all board blocks are filled and game is drawn
	 * @return
	 */
	public boolean draw()
	{
		int x = 0 , i , j ;
		
		for(i = 0 ; i < 3 ; i ++)
			for(j = 0 ; j < 3 ; j ++)
				if(ttb.tic[i][j] != '_')
					x = x + 1 ;
		
		if(x == 9)
			return true ;
		return false ;
	}
	
	/**
	 * The function checks whether a piece can be put into the block
	 * suggested by the user
	 * @param bno
	 * @return
	 */
	public boolean canPlace(int bno)
	{
		if(bno == 1 && ttb.tic[0][0] != '_')
			return false ;
		else if(bno == 2 && ttb.tic[0][1] != '_')
			return false ;
		else if(bno == 3 && ttb.tic[0][2] != '_')
			return false ;
		else if(bno == 4 && ttb.tic[1][0] != '_')
			return false ;
		else if(bno == 5 && ttb.tic[1][1] != '_')
			return false ;
		else if(bno == 6 && ttb.tic[1][2] != '_')
			return false ;
		else if(bno == 7 && ttb.tic[2][0] != '_')
			return false ;
		else if(bno == 8 && ttb.tic[2][1] != '_')
			return false ;
		else if(bno == 9 && ttb.tic[2][2] != '_')
			return false ;
		
		return true ;
	}
	
	/**
	 * Run method of Thread class
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
			 * The below code runs for the first player
			 */
			if(piece == 'X')
			{
				if(pf == 1)
				{
					int bno = 0 ;
					tv = new TicView(ttb) ;
					tv.display() ;
					
					int pass = 0 ;
					while(pass == 0)
					{
						System.out.println("Player 1 , Enter your board number") ;
						try
						{
							bno = Integer.parseInt(br.readLine()) ;
						}catch(Exception e) {}
						
						if(bno > 9 || bno < 1 || canPlace(bno) == false)
							System.out.println("Wrong number entered !!");
						else
							pass = 1;
					}
					putpiece(bno , piece) ;
					if(isWin(piece))
					{
						tv = new TicView(ttb) ;
						tv.display() ;
						tv.winner("Player 1");
						win = 1 ;
					}
					else if(draw())
					{
						tv = new TicView(ttb) ;
						tv.display() ;
						tv.draw();
						win = 1 ;
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
			/**
			 * The below code runs for second player
			 */
			else if(piece == '0')
			{
				if(pf == 2)
				{
					int bno = 0 ;
					tv = new TicView(ttb) ;
					tv.display() ;
					
					int pass = 0 ;
					while(pass == 0)
					{
						System.out.println("Player 2 , Enter your board number") ;
						try
						{
							bno = Integer.parseInt(br.readLine()) ;
						}catch(Exception e) {}
						
						if(bno > 9 || bno < 1 || canPlace(bno) == false)
							System.out.println("Wrong number entered !!");
						else
							pass = 1;
					}
					
					putpiece(bno , piece) ;
					if(isWin(piece))
					{
						tv = new TicView(ttb) ;
						tv.display() ;
						tv.winner("Player 2");
						win = 1 ;
					}
					else if(draw())
					{
						tv = new TicView(ttb) ;
						tv.display() ;
						tv.draw();
						win = 1 ;
					}
					else
						pf = 1 ;
				}
				else
				{
					try{
						wait() ;
					}catch(Exception e) {}
				}
			}
			if(win == 1)
				break ;
		}
	}
	
	/**
	 * Below function starts the game between two players
	 */
	void playgame()
	{
		TicTacControl ttc1  = new TicTacControl('X') ;
		TicTacControl ttc2  = new TicTacControl('0') ;
		Thread t1 = new Thread(ttc1) ;
		Thread t2 = new Thread(ttc2) ;
		t1.start();
		t2.start();
	}
}
