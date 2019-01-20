
/*
 *	@brief main class
 *	@author StarChen  
 */
public class LinearAlgebra 
{
	/*
	*	@brief empty constructor
	*/
	public LinearAlgebra() 
	{
		// TODO Auto-generated constructor stub
	}


	/*
	*	@brief main function use for debugging.
	*/
	public static void main(String[] args) {
		int counter = 0;
		int [][] a = new int [2][4];
		double [][] b = new double [4][5];
		for ( int i = 0 ; i < a.length ; i++ ) 
			for ( int j = 0 ; j < a[0].length ; j++ ) 
			{
				a[i][j] = counter;
				counter ++;
			}
		counter = 0;
		for ( int i = 0 ; i < b.length ; i++ ) 
			for ( int j = 0 ; j < b[0].length ; j++ ) 
			{
				b[i][j] = counter;
				counter += 0.5;
			}
		int [][] c = {{0, 2, 3, 4},
					  {0, 4, 5, 6}};
		
		printMatrix(c);
		//printMatrix(c);
		//printMatrix(b);
		printMatrix(RREF(c));
		//printMatrix(replacement(c, 1, 2, -1.5));
		
	}

	/*
	*	@brief translate original matrix into RREF 
	*/
    public static double[][] RREF (int[][] original)
    {
		if (original == null)
			return null;
        
		double[][] rref = null;
		double[][] elementary = null;
		toDouble(original);
		int m = original.length,
			n = original[0].length;
		rref = new double[m][n+m];
		elementary = new double[n][n];
		//add an elementary matrix at the end of this matrix
           for (int i = 0; i < m; i++)
               for (int j = 0; j < m + n; j++)
				if (j < n)
					rref[i][j] = original[i][j];
                   else
					if (i == (j - n))
						rref[i][j]  = 1;
					else
						rref[i][j] = 0;
		//forward operation to make it REF
		int numOfPivotColumns = 0;
		//int register = 0;
		for (int i = 0; i < n; i++){
			for (int j = i; j < m; j++){
				if (j == i){ //first row scaling
					if (rref[j][i] == 0){ //deal with first column 0 condition
						int temp = 0;
						for(int k = j; k < m; k++){
							if(rref[k][i] != 0){
								temp = k;
								break;
							}
						}
						if(temp == 0){
							//register--;
							break;
						}
						else{ //move the first element 0 row to the back
							interchange(rref, j, temp);
							scaling(rref, j, 1 / rref[j][i]);
						}
					}
					else
						scaling(rref, j, 1 / rref[j][i]);
				}
				else //other rows replacement
					replacement(rref, i, j, -rref[j][i]);
				//register++;
			}
		}
		//backward operation to make it RREF
           for (int i = m - 1; i > -1; i--)
               for (int j = m - 1 - i; j > -1; j--)
               {
				if (rref[j][i] == 1)
					continue;
				else
					replacement(rref, i, j, -rref[j][i]);
			}
		return rref;
	}
	
	/*
	*	@brief replacement operation
	*	add k*row1 to row2
	*/
	public static double[][] replacement (double[][] input, int row1, int row2, double k){
		if (input == null)
			return null;
		for (int i = 0; i < input[0].length; i++){
			input[row2][i] += input[row1][i] * k;
		}
		return input;
	}
	/*
	*	@brief interchange operation
	*	interchange row1 and row2
	*/
    public static double[][] interchange (double[][] input, int row1, int row2)
    {
    	if (input == null)
			return null;
		double temp = 0;
		for (int i = 0; i < input[0].length; i++){
			temp = input[row1][i];
			input[row1][i] = input[row2][i];
			input[row2][i] = temp;
		}
		return input;
	}
	
	public static double[][] scaling (double[][] input, int row, double k){
		if (input == null)
			return null;
		for (int i = 0; i < input[0].length; i++){
			input[row][i] *= k;
		}
		return input;
	}
	
    public static int[][] dotProduct(int [][] firstMatrix, int [][] secondMatrix){
    	if (firstMatrix == null || secondMatrix == null)
			return null;
		int m = firstMatrix.length, 
			n = firstMatrix[0].length, 
			p = secondMatrix.length, 
			q = secondMatrix[0].length, 
			c, d, k;
		int [][] dotProduct = null;
		int sum = 0;
        if(n != p) 
			return null;
			dotProduct = new int [m][q];
            for (c = 0; c < m; c++)
                for (d = 0; d < q; d++)
                {
                    for(k = 0; k < p; k++)
						sum += firstMatrix[c][k] *	secondMatrix[k][d];
					dotProduct[c][d] = sum;
					sum = 0;
				}
		return dotProduct;
	}
	
    public static double[][] dotProduct (double [][] firstMatrix, double [][] secondMatrix){
    	if (firstMatrix == null || secondMatrix == null)
			return null;
		int m = firstMatrix.length, 
			n = firstMatrix[0].length, 
			p = secondMatrix.length, 
			q = secondMatrix[0].length, 
			c, d, k;
		double [][] dotProduct = null;
		int sum = 0;
        if(n != p) 
			return null;
		dotProduct = new double [m][q];
        for (c = 0; c < m; c++)
            for (d = 0; d < q; d++)
            {
                for(k = 0; k < p; k++)
					sum += firstMatrix[c][k] *	
			secondMatrix[k][d];
				dotProduct[c][d] = sum;
				sum = 0;
			}
		return dotProduct;
	}
	
    public static int[][] transpose (int [][] matrix)
    {
		if(matrix == null)
			return null;
		int [][] temp = new int[matrix[0].length][matrix.length];
        for ( int i = 0 ; i < matrix.length ; i++ ) 
             for ( int j = 0 ; j < matrix[0].length ; j++ ) 
				temp [j][i] = matrix[i][j]; 
		return temp;
	}
	
    public static double[][] transpose (double [][] matrix)
    {
		if(matrix == null)
			return null;
		double [][] temp = new double[matrix[0].length][matrix.length];
        for ( int i = 0 ; i < matrix.length ; i++ ) 
            for ( int j = 0 ; j < matrix[0].length ; j++ ) 
				temp [j][i] = matrix[i][j]; 
			return temp;
	}
	/*
	*
	*/
    public static double[][] toDouble (int[][] input)
    {
		if(input == null)
			return null;
		int m = input.length,
			n = input[0].length;
		double[][] output = new double[m][n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
				output[i][j] = input[i][j];
		return output;
	}
	
    public static void printMatrix (int[][] print)
    {
		if (print == null)
			System.out.println("Invalid Matrix!");
        else
        {
			System.out.println("Entered matrix:");
            for ( int i = 0 ; i < print.length ; i++ ) 
            { 
				for ( int j = 0 ; j < print[0].length ; j++ ) 
					System.out.printf("%d\t", print[i][j]); 
				System.out.print("\n"); 
			}
		}
	}
	
    public static void printMatrix (double[][] print)
    {
		if (print == null)
			System.out.println("Invalid Matrix!");
        else
        {
			System.out.println("Entered matrix:");
            for ( int i = 0 ; i < print.length ; i++ )
            { 
				for ( int j = 0 ; j < print[0].length ; j++ ) 
					System.out.printf("%.2f\t", print[i][j]); 
				System.out.print("\n"); 
			}
		}
	}

}
