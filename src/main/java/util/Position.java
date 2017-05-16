package util;

public final class Position {

    private int xCoordinate;
    private int yCoordinate;

    public Position(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public String toString() {
        return String.format("Position [%s, %s]", xCoordinate, yCoordinate);
    }
}
