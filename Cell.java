
enum RightClick {
    FLAG,
    QUESTION,
    NONE
}

abstract class Cell {
    RightClick rightClick;
    boolean isLeftClicked;

    abstract boolean isBomb();

    abstract boolean isEmpty();

    public void rightClick() {
        switch (rightClick) {
            case FLAG:
                rightClick = RightClick.QUESTION;
                return;
            case QUESTION:
                rightClick = RightClick.NONE;
                return;
            case NONE:
                rightClick = RightClick.FLAG;
                return;
        }
    }

    public void leftClick() {
        isLeftClicked = true;
    }

    public boolean getIsLeftClicked() {
        return isLeftClicked;
    }
}
