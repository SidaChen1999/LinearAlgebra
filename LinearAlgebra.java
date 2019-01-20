
public class LinearAlgebra {

	public LinearAlgebra() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int counter = 3;
		int [][] a = new int [2][4];
		double [][] b = new double [4][5];
		for ( int i = 0 ; i < a.length ; i++ ) { 
			for ( int j = 0 ; j < a[0].length ; j++ ) {
				a[i][j] = counter;
				counter ++;
			}
			}
		counter = 0;
		for ( int i = 0 ; i < b.length ; i++ ) { 
			for ( int j = 0 ; j < b[0].length ; j++ ) {
				b[i][j] = counter;
				counter += 0.5;
			}
			}
		double [][] c = {{1, 2, 3},
					  {3, 4, 5}};
		printMatrix(a);
		//printMatrix(c);
		//printMatrix(b);
		//System.out.println(a[2-1][3-1]);
		printMatrix(RREF(a));
		printMatrix(replacement(c, 1, 2, -1.5));
		//printMatrix(transpose(dotProduct(toDouble(a), (b))));
		
		
	}
	
	public static double[][] RREF (int[][] original){
		if (original == null)
			return null;
		else{
			double[][] rref = null;
			double[][] elementary = null;
			toDouble(original);
			int m = original.length,
				n = original[0].length;
			rref = new double[m][n+m];
			elementary = new double[n][n];
			//add an elementary matrix at the end of this matrix
			for (int i = 0; i < m; i++){
				for (int j = 0; j < m + n; j++){
					if (j < n)
						rref[i][j] = original[i][j];
					else{
						if (i == (j - n))
							rref[i][j]  = 1;
						else
							rref[i][j] = 0;
					}
				}
			}
			//forward operation to make it REF
			for (int i = 0; i < n; i++){
				for (int j = i; j < m; j++){
					if (j == i){
						for (int k = m + n - 1; k > i - 1; k--){
							rref[j][k] /= rref[j][i];
						}
					}
					else{
						
						for (int k = m + n - 1; k > i - 1; k--){
							rref[j][k] -= rref[j][i] * rref[i][k];
						}
					}
				}
			}
			//backward operation to make it RREF
			for (int i = m - 1; i > -1; i--){
				for (int j = m - 1 - i; j > -1; j--){
					if (rref[j][i] == 1)
						continue;
					else{
						for (int k = m + n - 1; k > i - 1; k--)
							rref[j][k] -= rref[j][i] * rref[i][k];
					}
				}
			}
			return rref;
		}
	}
	
	public static double[][] replacement (double[][] input, int row1, int row2, double k){
		for (int i = 0; i < input[0].length; i++){
			input[row2 - 1][i] += input[row1 - 1][i] * k;
		}
		return input;
	}
	
	public static double[][] interchange (double[][] input, int row1, int row2){
		double temp = 0;
		for (int i = 0; i < input[0].length; i++){
			temp = input[row1 -1][i];
			input[row1 - 1][i] = input[row2 - 1][i];
			input[row2 - 1][i] = temp;
		}
		return input;
	}
	
	public static double[][] scaling (double[][] input, int row, double k){
		for (int i = 0; i < input[0].length; i++){
			input[row - 1][i] *= k;
		}
		return input;
	}
	
	public static int[][] dotProduct(int [][] firstMatrix, int [][] secondMatrix){
		int m = firstMatrix.length, 
			n = firstMatrix[0].length, 
			p = secondMatrix.length, 
			q = secondMatrix[0].length, 
			c, d, k;
		int [][] dotProduct = null;
		int sum = 0;
		if(n != p) {
			return null;
		}
		else{
			dotProduct = new int [m][q];
			for (c = 0; c < m; c++){
				for (d = 0; d < q; d++){
					for(k = 0; k < p; k++){
						sum += firstMatrix[c][k] * secondMatrix[k][d];
					}
					dotProduct[c][d] = sum;
					sum = 0;
				}
			}
		return dotProduct;
		}
	}
	
	public static double[][] dotProduct (double [][] firstMatrix, double [][] secondMatrix){
		int m = firstMatrix.length, 
			n = firstMatrix[0].length, 
			p = secondMatrix.length, 
			q = secondMatrix[0].length, 
			c, d, k;
		double [][] dotProduct = null;
		int sum = 0;
		if(n != p) {
			return null;
		}
		else{
			dotProduct = new double [m][q];
			for (c = 0; c < m; c++){
				for (d = 0; d < q; d++){
					for(k = 0; k < p; k++){
						sum += firstMatrix[c][k] * secondMatrix[k][d];
					}
					dotProduct[c][d] = sum;
					sum = 0;
				}
			}
		return dotProduct;
		}
	}
	
	public static int[][] transpose (int [][] matrix){
		if(matrix == null)
			return null;
		else{
			int [][] temp = new int[matrix[0].length][matrix.length];
			for ( int i = 0 ; i < matrix.length ; i++ ) { 
				for ( int j = 0 ; j < matrix[0].length ; j++ ) {
					temp [j][i] = matrix[i][j]; 
				}
			}
			return temp;
		}
	}
	
	public static double[][] transpose (double [][] matrix){
		if(matrix == null)
			return null;
		else{
			double [][] temp = new double[matrix[0].length][matrix.length];
			for ( int i = 0 ; i < matrix.length ; i++ ) { 
				for ( int j = 0 ; j < matrix[0].length ; j++ ) {
					temp [j][i] = matrix[i][j]; 
				}
			}
			return temp;
		}
	}
	
	
	
	public static double[][] toDouble (int[][] input){
		if(input == null)
			return null;
		else{
			int m = input.length,
				n = input[0].length;
			double[][] output = new double[m][n];
			for(int i = 0; i < m; i++){
				for(int j = 0; j < n; j++){
					output[i][j] = input[i][j];
				}
			}
			return output;
		}
	}
	
	public static void printMatrix (int[][] print){
		if (print == null)
			System.out.println("Invalid Matrix!");
		else{
			System.out.println("Entered matrix:");
			for ( int i = 0 ; i < print.length ; i++ ) { 
				for ( int j = 0 ; j < print[0].length ; j++ ) 
					System.out.print(print[i][j] + "\t"); 
					System.out.print("\n"); 
				}
		}
	}
	
	public static void printMatrix (double[][] print){
		if (print == null)
			System.out.println("Invalid Matrix!");
		else{
			System.out.println("Entered matrix:");
			for ( int i = 0 ; i < print.length ; i++ ) { 
				for ( int j = 0 ; j < print[0].length ; j++ ) 
					System.out.printf("%.2f\t", print[i][j]); 
					System.out.print("\n"); 
				}
		}
	}

}
