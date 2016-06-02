/*
 * TicTacBoard.java
 * 
 * Version 1.0
 */
import java.io.*;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 *
 * This class acts as a Model for TicTacToe Game
 */
public class TicTacBoard implements Serializable
{
	char tic[][] = new char[3][3] ;
	
	/**
	 * Initialize the board for tictactoe
	 */
	public TicTacBoard()
	{
		int i , j ;
		for(i = 0 ; i < 3 ; i ++)
		{
			for(j = 0 ; j < 3 ; j ++)
			{
				tic[i][j] = '_' ;
			}
		}
	}
}
