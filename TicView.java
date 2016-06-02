import java.io.* ;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 *
 * This class serves as a view to Tic Tac Toe game
 */
public class TicView implements Serializable
{
	TicTacBoard ttb ;
	
	public TicView(TicTacBoard ttb)
	{
		this.ttb = ttb ;
	}
	
	/**
	 * Display all the contents of the board
	 */
	public void display()
	{
		int i , j ;
		for(i = 0 ; i < 3 ; i ++)
		{
			for(j = 0 ; j < 3 ; j ++)
			{
				System.out.print(ttb.tic[i][j]+" ") ;
			}
			System.out.println() ;
		}
	}
	
	public void winner(String s)
	{
		System.out.println(s+" wins !!!") ;
	}
	
	public void draw()
	{
		System.out.println("Game is drawn") ;
	}
}
