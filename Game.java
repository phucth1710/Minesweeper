import java.util.Scanner;

enum ClickType {
    LEFT,
    RIGHT
}

public class Game {
    Board board;
    Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
    }

    private Mode inputMode() throws Exception {
        System.out.println("Please select your difficulty: 1 for easy, 2 for medium, and 3 for hard");
        int intMode = scanner.nextInt();
        Mode mode;
        switch (intMode) {
            case 1:
                mode = Mode.EASY;
                break;
            case 2:
                mode = Mode.MEDIUM;
                break;
            case 3:
                mode = Mode.HARD;
                break;
            default:
                throw new Exception("Invalid mode, please try again");
        }
        return mode;
    }

    private int inputDim() throws Exception {
        System.out.println("Please select your board dimension");
        int dim = scanner.nextInt();
        if (dim <= 3) {
            throw new Exception("board dim should be a positive integer > 3");
        }
        return dim;
    }

    private int[] inputCell(int dim) throws Exception {
        System.out.println("Please select your cell's location.");
        System.out.print("Row (a number between 0 and " + dim + " ): ");
        int i = scanner.nextInt();
        System.out.print("Col (a number between 0 and " + dim + " ): ");
        int j = scanner.nextInt();
        if (i < 0 || j < 0 || i >= dim || j >= dim) {
            throw new Exception("Invalid Cell Location, should be a number between 0 and " + dim + " ).");
        }
        return new int[] { i, j };
    }

    private ClickType inputClickType() throws Exception {
        System.out.println("Please select your click type: 1 for left click and 2 for right click");
        int clickType = scanner.nextInt();
        if (clickType == 1) {
            return ClickType.LEFT;
        } else if (clickType == 2) {
            return ClickType.RIGHT;
        }
        throw new Exception("Invalid click type");
    }

    public void play1Game() throws Exception {
        Mode mode = inputMode();
        int dim = inputDim();
        Board board = new Board(dim, mode);
        int[] firstCellLoc = inputCell(dim);
        board.initializeBoard(firstCellLoc[0], firstCellLoc[1]);
        if(board.isWin()){
            System.out.println("Board dimension is too small that makes you win accidentally, please try again");
        }
        while (true) {
            System.out.println("------------------------------------------------------");
            System.out.println(board);
            ClickType clickType = inputClickType();
            int[] nextCellLoc = inputCell(dim);
            int i = nextCellLoc[0];
            int j = nextCellLoc[1];
            if (clickType == ClickType.LEFT) {
                board.leftClick(i, j);
            } else {
                board.rightClick(i, j);
            }
            if (board.isLoseByClick(i, j)) {
                System.out.println("------------------------------------------------------");
                System.out.println("You lost!");
                break;
            }
            if (board.isWin()) {
                System.out.println("------------------------------------------------------");
                System.out.println("Congratulation you win!");
                break;
            }
        }
        board.revealBoard();
        System.out.println(board);
    }
}
