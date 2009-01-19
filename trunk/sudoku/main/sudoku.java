import java.util.*;
import java.io.*;


class Sudoku
{
	/* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
	 * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
	int SIZE, N;

	/* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
	 * not yet been revealed are stored as 0. */
	int Grid[][];
	
	class cellPosition{
		public int line, column;		
	}

public boolean Try(int myGrid[][][], int line, int column){
		int i = 0;	//1 because at position myGrid[line][column][0] is the unknown number
	
		
	while( i<N )	//Values left to try
	{
		do{
			i++;
		} while(i < N-1 && myGrid[line][column][i] == -1);

	
		cellPosition lineAndColumn = null;
		
		if (isValidChoice(myGrid, line, column, myGrid[line][column][i]) ){
			
			myGrid[line][column][0] = myGrid[line][column][i];
			
			lineAndColumn = nextUnknownCell(myGrid, line, column);
			
			if (lineAndColumn != null)	{ //If there is a next unknown cell			
				if (Try(myGrid, lineAndColumn.line, lineAndColumn.column))
					return true;				
			}
			else return true;		
		}		
	}
	myGrid[line][column][0] = 0; //Clear any value we wrote
	return false;
}

	
	public cellPosition nextUnknownCell(int[][][] myGrid, int curLine, int curColumn)
	{
		for(int i = curLine; i < N; i++){
			for (int j = curColumn+1; j < N; j++){
				if (j < N){
					if (myGrid[i][j][0] == 0){
						cellPosition returnValue = new cellPosition();
						returnValue.column = j;
						returnValue.line = i;					
						return returnValue;
					}					
				}				
			}
			curColumn = -1;
		}		
		return null;
	}
	
	
	
	public boolean isValidChoice(int myGrid[][][], int line, int column, int value){
		 
		//Validity check on value
		if (value < 1 || value > N)
			return false;
//		Iterate over the line elements
		for (int k=0; k < N; k++){
			if (myGrid[line][k][0] == value && (k!= column) )
					return false;									
		}		
		
		//Iterate over the column elements 
		for (int k=0; k < N; k++){
			if (myGrid[k][column][0] == value && (k != line))
					return false;									
		}
		
		//Iterate over cell elements
		int startCol = 0, startRow = 0;
		if ((column+1) % SIZE == 0)
			startCol = column - (SIZE-1);
		else startCol = (column/SIZE) * SIZE;
		
		if ((line+1) % SIZE == 0)
			startRow = line - (SIZE-1);
		else startRow = (line/SIZE) * SIZE;
		
		for (int m = startRow; m < startRow + SIZE; m++)
			for (int n = startCol; n < startCol + SIZE; n++)
				if (myGrid[m][n][0] == value && (m!= line && n!= column)){
					return false;
				}
		
		return true;
				
	}
	
	public void applyConstraints(int myGrid[][][], int line, int column){
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				
				if (myGrid[i][j][0] == 0){	//We found an unknown element
					//Iterate over the line elements - except 0's
					for (int k=0; k < N; k++){
						if (myGrid[i][k][0] != 0)
							myGrid[i][j][ myGrid[i][k][0] ] = -1;					
					}
					
					//Iterate over the column elements
					for (int k=0; k < N; k++){
						if (myGrid[k][j][0] != 0)
							myGrid[i][j][ myGrid[k][j][0] ] = -1;					
					}
					
					//Iterate over cell elements
					int startCol = 0, startRow = 0;
					if ((j+1) % SIZE == 0)
						startCol = j - (SIZE-1);
					else startCol = (j/SIZE) * SIZE;
					
					if ((i+1) % SIZE == 0)
						startRow = i - (SIZE-1);
					else startRow = (i/SIZE) * SIZE;
					
					for (int m = startRow; m < startRow + SIZE; m++)
						for (int n = startCol; n < startCol + SIZE; n++)
							if (myGrid[m][n][0] != 0){
								int valueToBeCrossedOut = myGrid[m][n][0]; 							
								myGrid[i][j][ valueToBeCrossedOut ] = -1;
							}
				
				}
	}
	
	/* The solve() method should remove all the unknown characters ('x') in the Grid
	 * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
	public void solve()
	{		
		int [][][] myGrid = new int[N][N][N+1];
		for( int i = 0; i < N; i++ ){ 
			for( int j = 0; j < N; j++ ){				
				myGrid[i][j][0] = Grid[i][j];
				for (int k = 1; k < N+1; k++){
					myGrid[i][j][k] = k;
				}
			}
		}

		applyConstraints(myGrid, N, N);
		//There is a minute chance that at this point we might be able to solve the puzzle
		
		for( int i = 0; i < N; i++ ){ 
			for( int j = 0; j < N; j++ ){				
				if (myGrid[i][j][0] == 0){
					Try(myGrid, i, j);
					

					for( int r = 0; r < N; r++ ){ 
						for( int s = 0; s < N; s++ ){				
							Grid[r][s] = myGrid[r][s][0];	
						}
					}
					
					return;
				}				
			}
		}
		

/*		
		for (int k = 0; k < 9; k++){
			
			int i = 0, foundx = 0;

			// searching for 0, which is the unknown value:
			for (i = 0; i < 9; i++)
			{
				if (Grid[k][i] == 0)
				{
					foundx = 1;
					break;
				}
			}

			// if 0 is found in a row
			if (foundx == 1)
			{
				int posx = i, j = 0;
				int results [] = {1,2,3,4,5,6,7,8,9};
				
				// setting the eliminated numbers to -1:
				for (j = 0; j <9; j++)
				{
					if (Grid[k][j] != 0)
					{
						results[Grid[k][j] - 1] = -1;
					}
				}

				for (j = 0; j < 9; j++)
				{
					if (results[j] != -1)
					{
						Grid[k][posx] = results[j];
					}

				}

			}
		}
*/

}


	/*****************************************************************************/
	/* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
	/*****************************************************************************/

	/* Default constructor.  This will initialize all positions to the default 0
	 * value.  Use the read() function to load the Sudoku puzzle from a file or
	 * the standard input. */
	public Sudoku( int size )
	{
		SIZE = size;
		N = size*size;

		Grid = new int[N][N];
		for( int i = 0; i < N; i++ ) 
			for( int j = 0; j < N; j++ ) 
				Grid[i][j] = 0;
	}


	/* readInteger is a helper function for the reading of the input file.  It reads
	 * words until it finds one that represents an integer. For convenience, it will also
	 * recognize the string "x" as equivalent to "0". */
	static int readInteger( InputStream in ) throws Exception
	{
		int result = 0;
		boolean success = false;

		while( !success ) 
		{
			String word = readWord( in );

			try 
			{
				result = Integer.parseInt( word );
				success = true;
			} catch( Exception e ) 
			{
				// Convert 'x' words into 0's
				if( word.compareTo("x") == 0 ) 
				{
					result = 0;
					success = true;
				}
				// Ignore all other words that are not integers
			}
		}

		return result;
	}


	/* readWord is a helper function that reads a word separated by white space. */
	static String readWord( InputStream in ) throws Exception
	{
		StringBuffer result = new StringBuffer();
		int currentChar = in.read();
		String whiteSpace = " \r\t\n";

		// Ignore any leading white space
		while( whiteSpace.indexOf(currentChar) > -1 ) 
		{
			currentChar = in.read();
		}

		// Read all characters until you reach white space
		while( whiteSpace.indexOf(currentChar) == -1 ) 
		{
			result.append( (char) currentChar );
			currentChar = in.read();
		}
		return result.toString();
	}


	/* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
	 * grid is filled in one row at at time, from left to right.  All non-valid
	 * characters are ignored by this function and may be used in the Sudoku file
	 * to increase its legibility. */
	public void read( InputStream in ) throws Exception
	{
		for( int i = 0; i < N; i++ ) 
		{
			for( int j = 0; j < N; j++ ) 
			{
				Grid[i][j] = readInteger( in );
			}
		}
	}


	/* Helper function for the printing of Sudoku puzzle.  This function will print
	 * out text, preceded by enough ' ' characters to make sure that the printint out
	 * takes at least width characters.  */
	void printFixedWidth( String text, int width )
	{
		for( int i = 0; i < width - text.length(); i++ )
			System.out.print( " " );
		System.out.print( text );
	}


	/* The print() function outputs the Sudoku grid to the standard output, using
	 * a bit of extra formatting to make the result clearly readable. */
	public void print()
	{
		// Compute the number of digits necessary to print out each number in the Sudoku puzzle
		int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

		// Create a dashed line to separate the boxes 
		int lineLength = (digits + 1) * N + 2 * SIZE - 3;
		StringBuffer line = new StringBuffer();
		for( int lineInit = 0; lineInit < lineLength; lineInit++ )
			line.append('-');

		// Go through the Grid, printing out its values separated by spaces
		for( int i = 0; i < N; i++ ) 
		{
			for( int j = 0; j < N; j++ ) 
			{
				printFixedWidth( String.valueOf( Grid[i][j] ), digits );
				// Print the vertical lines between boxes 
				if( (j < N-1) && ((j+1) % SIZE == 0) )
					System.out.print( " |" );
				System.out.print( " " );
			}
			System.out.println();

			// Print the horizontal line between boxes
			if( (i < N-1) && ((i+1) % SIZE == 0) )
				System.out.println( line.toString() );
		}
	}


	/* The main function reads in a Sudoku puzzle from the standard input, 
	 * unless a file name is provided as a run-time argument, in which case the
	 * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
	 * outputs the completed puzzle to the standard output. */
	public static void main( String args[] ) throws Exception
	{
		InputStream in;
		if( args.length > 0 ) 
			in = new FileInputStream( args[0] );
		else
			in = System.in;

		// The first number in all Sudoku files must represent the size of the puzzle.  See
		// the example files for the file format.
		int puzzleSize = readInteger( in );
		if( puzzleSize > 100 || puzzleSize < 1 ) 
		{
			System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
			System.exit(-1);
		}

		Sudoku s = new Sudoku( puzzleSize );

		// read the rest of the Sudoku puzzle
		s.read( in );

		// Solve the puzzle.  We don't currently check to verify that the puzzle can be
		// successfully completed.  You may add that check if you want to, but it is not
		// necessary.
		s.solve();

		// Print out the (hopefully completed!) puzzle
		s.print();
	}
}
