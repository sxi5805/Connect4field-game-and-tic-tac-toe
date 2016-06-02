/*
 * ConnectBoard.java
 * 
 * Version 1.0
 */

import java.io.*;

/**
 * 
 * @author Siddharth Bidwalkar
 * @author Swetha Kannan Iyer
 * 
 * This class acts as Model for 4 player Connect 4 Game
 *
 */

public class ConnectBoard implements Serializable
{
	String brd[][] = new String[10][25] ;
	
	/**
	 * The function initialises the Connect 4 Board
	 */
	public ConnectBoard()
	{
		int i , j , k = 0 ;
		for(i = 0 ; i < 10 ; i ++)
		{
			for(j = 0 ; j < k ; j ++)
			{
				brd[i][j] = " " ;
			}
			for( j = k ; j < 25-k ; j ++)
			{
				brd[i][j] = "_" ;
			}
			for(j = 25-k ; j < 25 ; j ++)
			{
				brd[i][j] = " " ;
			}
			k = k + 1 ;
		}
	}
}
