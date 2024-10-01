public class EmptyCell extends Cell {
    private int bombCount;

    public EmptyCell() {
        isLeftClicked = false;
        rightClick = RightClick.NONE;
        bombCount = 0;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    @Override
    public boolean isBomb() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        if (isLeftClicked) {
            return String.valueOf(bombCount);
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
