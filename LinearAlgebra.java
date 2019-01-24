import java.util.Vector;

/*
 *	@brief main class
 *	@author StarChen  
 *	@author NLC
 */
public class LinearAlgebra {

	int row, clo;
	public static double delta = 0.000001;

	/*
	 * @brief constructor
	 */
	public LinearAlgebra(int row, int clo) {
		this.row = row;
		this.clo = clo;
		// TODO finish constructor
	}

	/*
	 * @brief main function use for debugging.
	 */
	public static void main(String[] args) {
		int counter = 0;
		int[][] a = new int[10][10];
		double[][] b = new double[4][5];
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++) {
				a[i][j] = (int) (Math.random() * 10000);
				// counter++;
			}
		counter = 0;
		for (int i = 0; i < b.length; i++)
			for (int j = 0; j < b[0].length; j++) {
				b[i][j] = counter;
				counter += 0.5;
			}
		int[][] c = { { 0, 2, 3, 4 }, { 0, 4, 5, 6 } };
		printMatrix(a);
		// printMatrix(c);
		// printMatrix(b);
		// System.out.println(a[2-1][3-1]);
		printMatrix(RREF(a));
		// printMatrix(replacement(c, 1, 2, -1.5));

	}

	/*
	 * @brief translate original matrix into RREF
	 */
	public static double[][] REF(int[][] original) {
		if (original == null)
			return null;

		double[][] ref = null;
		double[][] elementary = null;
		toDouble(original);
		int m = original.length, n = original[0].length;
		ref = new double[m][n + m];
		elementary = new double[n][n];
		// add an elementary matrix at the end of this matrix
		for (int i = 0; i < m; i++)
			for (int j = 0; j < m + n; j++)
				if (j < n)
					ref[i][j] = original[i][j];
				else if (i == (j - n))
					ref[i][j] = 1;
				else
					ref[i][j] = 0;
		// forward operation to make it REF

		int register = 0; // used to mark the row of pivot element
		for (int i = 0; i < n; i++) {
			for (int j = register; j < m; j++) {
				if (j == register) { // first row scaling
					if (Math.abs(ref[j][i] - 0) < delta) { // deal with first column 0 condition
						int temp = 0;
						for (int k = j; k < m; k++) {
							if (Math.abs(ref[k][i] - 0) > delta) {
								temp = k;
								break;
							}
						}
						if (temp == 0) {
							register--;
							break;
						} else { // move the first element 0 row to the back
							interchange(ref, j, temp);
							scaling(ref, j, 1 / ref[j][i]);
						}
					} else
						scaling(ref, j, 1 / ref[j][i]);
				} else // other rows replacement
					replacement(ref, register, j, -ref[j][i]);
			}
			register++;
		}

		return ref;
	}

	/*
	 * @brief translate original matrix into RREF
	 */
	public static double[][] RREF(int[][] original) {
		if (original == null)
			return null;

		double[][] rref = REF(original);// .clone();
		double[][] elementary = null;
		int m = original.length, n = original[0].length;
		elementary = new double[n][n];
		// backward operation to make it RREF
		for (int i = m - 1; i > -1; i--)
			for (int j = n - 1; j > -1; j--) {
				if (Math.abs(rref[i][j] - 1) < delta) {
					if (Math.abs(j) < delta)
						continue;
					else {
						if (Math.abs(rref[i][j - 1] - 0) < delta) {
							for (int k = i - 1; k > -1; k--) {
								replacement(rref, i, k, -rref[k][j]);
							}
							break;
						}
					}
				}

			}
		return rref;
	}

	/*
	 * @brief replacement operation add k*row1 to row2
	 */
	public static double[][] replacement(double[][] input, int row1, int row2, double k) {
		for (int i = 0; i < input[0].length; i++)
			input[row2][i] += input[row1][i] * k;
		return input;
	}

	/*
	 * @brief interchange operation interchange row1 and row2
	 */
	public static double[][] interchange(double[][] input, int row1, int row2) {
		double temp = 0;
		for (int i = 0; i < input[0].length; i++) {
			temp = input[row1][i];
			input[row1][i] = input[row2][i];
			input[row2][i] = temp;
		}
		return input;
	}

	public static double[][] scaling(double[][] input, int row, double k) {
		for (int i = 0; i < input[0].length; i++)
			input[row][i] *= k;
		return input;
	}

	public static int[][] dotProduct(int[][] firstMatrix, int[][] secondMatrix) {
		int m = firstMatrix.length, n = firstMatrix[0].length, p = secondMatrix.length, q = secondMatrix[0].length, c,
				d, k;
		int[][] dotProduct = null;
		int sum = 0;
		if (n != p)
			return null;
		dotProduct = new int[m][q];
		for (c = 0; c < m; c++)
			for (d = 0; d < q; d++) {
				for (k = 0; k < p; k++)
					sum += firstMatrix[c][k] * secondMatrix[k][d];
				dotProduct[c][d] = sum;
				sum = 0;
			}
		return dotProduct;
	}

	public static double[][] dotProduct(double[][] firstMatrix, double[][] secondMatrix) {
		int m = firstMatrix.length, n = firstMatrix[0].length, p = secondMatrix.length, q = secondMatrix[0].length, c,
				d, k;
		double[][] dotProduct = null;
		int sum = 0;
		if (n != p)
			return null;
		dotProduct = new double[m][q];
		for (c = 0; c < m; c++)
			for (d = 0; d < q; d++) {
				for (k = 0; k < p; k++)
					sum += firstMatrix[c][k] * secondMatrix[k][d];
				dotProduct[c][d] = sum;
				sum = 0;
			}
		return dotProduct;
	}

	public static int[][] transpose(int[][] matrix) {
		if (matrix == null)
			return null;
		int[][] temp = new int[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				temp[j][i] = matrix[i][j];
		return temp;
	}

	public static double[][] transpose(double[][] matrix) {
		if (matrix == null)
			return null;
		double[][] temp = new double[matrix[0].length][matrix.length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[0].length; j++)
				temp[j][i] = matrix[i][j];
		return temp;
	}

	/*
	*
	*/
	public static double[][] toDouble(int[][] input) {
		if (input == null)
			return null;
		int m = input.length, n = input[0].length;
		double[][] output = new double[m][n];
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				output[i][j] = input[i][j];
		return output;
	}

	public static void printMatrix(int[][] print) {
		if (print == null)
			System.out.println("Invalid Matrix!");
		else {
			System.out.println("Entered matrix:");
			for (int i = 0; i < print.length; i++) {
				for (int j = 0; j < print[0].length; j++)
					System.out.print(print[i][j] + "\t");
				System.out.print("\n");
			}
		}
	}

	public static void printMatrix(double[][] print) {
		if (print == null)
			System.out.println("Invalid Matrix!");
		else {
			System.out.println("Entered matrix:");
			for (int i = 0; i < print.length; i++) {
				for (int j = 0; j < print[0].length; j++)
					if (isDoubleEqual(print[i][j], 0))
						System.out.printf("%.2f\t", 0.00);
					else
						System.out.printf("%.2f\t", print[i][j]);
				System.out.print("\n");
			}
		}
	}

	/*
	 * @brief judge two number, precision : 0.00001
	 */
	public static boolean isDoubleEqual(double number1, double number2) {
		if ((number2 - 0.00001 < number1) && (number1 < number2 + 0.00001))
			return true;
		return false;
	}

}
