/**
 * ConnectView.java
 * 
 * Version 1.0
 */
import java.io.* ;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 *
 * This class acts as view for Connect 4 game
 */
public class ConnectView implements Serializable
{
	ConnectBoard cb ;
	
	/**
	 * The constructor accepts the ConnectBoard object
	 * @param cb
	 */
	public ConnectView(ConnectBoard cb)
	{
		this.cb = cb ;
	}
	
	/**
	 * Function displays the contents of the board
	 */
	public void display()
	{
		int i , j ;
		for(i = 0 ; i < 10 ; i ++)
		{
			for(j = 0 ; j < 25 ; j ++)
			{
				System.out.print(cb.brd[i][j]+" ") ;
			}
			System.out.println() ;
		}
	}
}
