import java.util.Arrays;
import java.util.Random;

enum Mode {
    EASY, // 40% bomb
    MEDIUM, // 50% bomb
    HARD // 60% bomb
}

public class Board {
    private int dim;
    private Cell[][] board;
    private int bombProbability;

    public Board(int dim, Mode mode) {
        this.dim = dim;
        board = new Cell[dim][dim];
        switch (mode) {
            case EASY:
                bombProbability = 40;
                break;
            case MEDIUM:
                bombProbability = 50;
                break;
            case HARD:
                bombProbability = 60;
                break;
            default:
                break;
        }
    }

    public Board(Mode mode) {
        this(9, mode);
    }

    public void initializeBoard(int initialRow, int initialCol) {
        Random rand = new Random();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (rand.nextInt(100) < bombProbability && i != initialRow && j != initialCol) {
                    board[i][j] = new BombCell();
                } else {
                    board[i][j] = new EmptyCell();
                }
            }
        }
        updateBombCount();
        clickFirstCell(initialRow, initialCol);
    }

    public boolean isWin() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j].isEmpty() && !board[i][j].isLeftClicked) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLose() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (isLoseByClick(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isLoseByClick(int i, int j) {
        return board[i][j].isBomb() && board[i][j].getIsLeftClicked();
    }

    public void leftClick(int i, int j) {
        board[i][j].leftClick();
    }

    public void rightClick(int i, int j) {
        board[i][j].rightClick();
    }

    @Override
    public String toString() {
        String ans = "";
        int[] colIndex = new int[dim];
        for (int i = 0; i < dim; i++) {
            colIndex[i] = i;
        }
        System.out.println(Arrays.toString(colIndex));
        for (int i = 0; i < dim; i++) {
            Cell[] b = board[i];
            ans += Arrays.toString(b) + " " + i + " " + "\n";
        }
        ans += "X represents bomb, F represents flag, ? represents question, number represents the number of bomb around that cell, and empty means the cell is not yet selected";
        return ans;
    }

    private int bombCount(int i, int j) {
        Cell cell = board[i][j];
        int bomb = 0;
        if (cell.isEmpty()) {
            for (int left = -1; left <= 1; left++) {
                for (int right = -1; right <= 1; right++) {
                    if (left == 0 && right == 0) {
                        continue;
                    }
                    int ni = i + left;
                    int nj = j + right;
                    if (ni >= 0 && ni < dim && nj >= 0 && nj < dim && board[ni][nj].isBomb()) {
                        bomb++;
                    }
                }
            }
        }
        return bomb;
    }

    public void revealBoard() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board[i][j].leftClick();
            }
        }
    }

    private void updateBombCount() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                Cell cell = board[i][j];
                if (cell.isEmpty()) {
                    EmptyCell emptyCell = (EmptyCell) cell;
                    int bomb = bombCount(i, j);
                    emptyCell.setBombCount(bomb);
                }

            }
        }
    }

    private void clickFirstCell(int i, int j) {
        leftClick(i, j);
        // Expand around the cell
        int expandLen = Math.max(dim / 4, 1);
        for (int di = -expandLen; di <= expandLen; di++) {
            for (int dj = -expandLen; dj <= expandLen; dj++) {
                int ni = i + di;
                int nj = j + dj;
                if (ni >= 0 && ni < dim && nj >= 0 && nj < dim && board[ni][nj].isEmpty()) {
                    board[ni][nj].leftClick();
                }
            }
        }
    }

}
