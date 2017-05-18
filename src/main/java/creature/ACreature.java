package creature;

import util.Position;

public abstract class ACreature {

    private Position position;

    public ACreature(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    void updatePosition(int newX, int newY) {
        this.position.setxCoordinate(newX);
        this.position.setyCoordinate(newY);
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
