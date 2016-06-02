/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 * This class calls the TicTac Controller
 *
 */
public class TicCalller 
{
	/**
	 * This is the main function
	 * @param args
	 */
	public static void main(String args[])
	{
		TicTacBoard ttb = new TicTacBoard() ;
		TicTacControl ttc = new TicTacControl(ttb) ;
		ttc.playgame();
	}
}
