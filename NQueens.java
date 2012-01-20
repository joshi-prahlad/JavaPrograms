package source;

public class NQueens {
	public static void main(String[] args) {
		int board[] = { -1, -1, -1, -1, -1, -1, -1, -1 };
		solveNQueen(board, 0);
		for (int i : board) {
			System.out.println(i);
		}
	}

	public static boolean solveNQueen(int board[], int currentRow) {
		if (currentRow == board.length) {
			return true;
		}

		for (int column = 0; column < board.length; ++column) {
			boolean isLegalMove = true;
			for (int row = 0; row < currentRow; ++row) {
				if ((board[row] == column)
						|| (board[row] == (column - (currentRow - row)))
						|| (board[row] == (column + (currentRow - row)))) {
					isLegalMove = false;
				}
			}
			if (isLegalMove) {
				board[currentRow] = column;
				if (solveNQueen(board, currentRow + 1)) {
					return true;
				}

			}

		}
		return false;

	}
}
