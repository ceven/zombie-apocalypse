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

    void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
