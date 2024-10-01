public class BombCell extends Cell {

    public BombCell() {
        isLeftClicked = false;
        rightClick = RightClick.NONE;
    }

    @Override
    public boolean isBomb() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        if (isLeftClicked) {
            return "X";
        }
        switch (rightClick) {
            case FLAG:
                return "F";
            case QUESTION:
                return "?";
            case NONE:
                return " ";
        }
        return "";
    }
}
