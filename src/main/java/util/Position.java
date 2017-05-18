package util;

import java.util.Objects;

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

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Position)) {
            return false;
        }
        Position other = (Position) o;
        return this.getxCoordinate() == other.getxCoordinate() && this.getyCoordinate() == other.getyCoordinate();
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", xCoordinate, yCoordinate);
    }
}
