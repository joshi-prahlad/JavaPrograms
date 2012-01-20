import java.util.Arrays;

//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {
	private boolean grid[][];
	private boolean tetrisGrid[][];
	private boolean fullRow[];
	private boolean emptyRow[];

	/**
	 * Constructs a new instance with the given grid. Does not make a copy.
	 * 
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		/**
		 * asserts that grid's height and widht is at least 1.
		 */
		assert (grid.length >= 1);
		assert (grid[0].length >= 1);

		this.grid = grid;
		gridToTetrisGrid();
		/**
		 * create an array whose elements are all true and whose size is equal
		 * to 1st dimension of grid 2-d array.
		 */
		fullRow = new boolean[grid.length];
		for (int i = 0; i < fullRow.length; ++i)
			fullRow[i] = true;
		/**
		 * same as fullRow accepts all the elements are equal to false
		 */
		emptyRow = new boolean[grid.length];
		for (int i = 0; i < emptyRow.length; ++i)
			emptyRow[i] = false;
	}

	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		/**
		 * stores how many full rows has been encountered so far.
		 */
		int shiftBy = 0;
		for (int currentRow = 0; currentRow < tetrisGrid.length;) {
			if (isFull(tetrisGrid[currentRow])) {
				++shiftBy;
				++currentRow;
			}

			else {
				if (isEmpty(tetrisGrid[currentRow])) {
					if (shiftBy != 0) {
						/**
						 * it means we have to clear the shiftBy rows
						 */
						clearRows(currentRow - shiftBy, currentRow);
						shiftBy = 0;
					}
					break;
				} else {
					/**
					 * Shift the current row by number of full rows encountered
					 * so far
					 */
					if (shiftBy != 0)
						tetrisGrid[currentRow - shiftBy] = Arrays.copyOf(
								tetrisGrid[currentRow],
								tetrisGrid[currentRow].length);
					++currentRow;

				}

			}

		}
		if (shiftBy != 0)
			/**
			 * if we did not encountered any empty rows meaning all the rows
			 * were either full or have something
			 */
			clearRows(tetrisGrid.length - shiftBy, tetrisGrid.length);
		tetrisGridToGrid();

	}

	/**
	 * clears rows in tetris grid starting from row "from" to row "to-1".
	 * 
	 * @param from
	 * @param to
	 *            @
	 */
	private void clearRows(int from, int to) {
		for (int i = from; i < to; ++i)
			tetrisGrid[i] = Arrays.copyOf(emptyRow, emptyRow.length);
	}

	/**
	 * checks whether a row is empty or not.
	 * 
	 * @param row
	 * @return
	 */
	private boolean isEmpty(boolean[] row) {
		return Arrays.equals(emptyRow, row);
	}

	/**
	 * accepts a row of tetris grid(column of the 2-d array and checks whehter
	 * all the elements are true or not.
	 * 
	 * @param row
	 * @return
	 */
	private boolean isFull(boolean[] row) {
		return Arrays.equals(fullRow, row);

	}

	/**
	 * Returns the internal 2d grid array.
	 * 
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid; // YOUR CODE HERE
	}

	/**
	 * Rotates the 2-d array grid by 90 degree and assigns it to tetrisGrid
	 */
	private void gridToTetrisGrid() {
		tetrisGrid = new boolean[grid[0].length][grid.length];
		rotate90(grid, tetrisGrid);

	}

	private void tetrisGridToGrid() {
		rotate90(tetrisGrid, grid);
	}

	/**
	 * accepts two 2-d array a1 ,a2. rotates a1 by 90 and assigns it to a2;
	 * 
	 * @param a1
	 * @param a2
	 */
	private void rotate90(boolean a1[][], boolean a2[][]) {
		for (int i = 0; i < a1.length; ++i)
			for (int j = 0; j < a1[i].length; ++j) {
				a2[j][i] = a1[i][j];
			}

	}
}
