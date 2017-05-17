package creature;

import util.Position;

public class LivingCreature extends ACreature {

    public LivingCreature(Position position) {
        super(position);
    }

    @Override
    void setPosition(Position position) {
        throw new UnsupportedOperationException("Cannot change position of a living creature");
    }

}
