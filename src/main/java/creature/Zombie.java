package creature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;
import world.WorldGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Zombie extends ACreature {

    private static final Logger LOG = LoggerFactory.getLogger(Zombie.class);

    private final List<Move> moves;
    private int points;

    public Zombie(Position position) {
        super(position);
        moves = newArrayList();
    }

    public void addMove(final Move move) {
        moves.add(move);
    }

    public List<Move> getMoves() {
        return Collections.unmodifiableList(moves);
    }

    @Override
    public String toString() {
        return "Zombie with current position " + getPosition().toString();
    }

    /**
     * Move this Zombie around grid and return new zombies count
     *
     * @param grid the grid that the zombie is exploring
     * @return the list of newly created zombies
     */
    public List<Zombie> explore(final WorldGrid grid) {
        ArrayList<Zombie> newZombies = newArrayList();
        this.getMoves().forEach(m -> {
            LOG.debug("Moving zombie {}", m.getFirstChar());
            this.move(m, grid);
            if (grid.visitLivingCreature(this)) {
                this.gainPoint();
                newZombies.add(new Zombie(Position.of(this.getXPosition(), this.getYPosition())));

            }
        });
        LOG.debug("{} gained {} points", this.toString(), points);
        return newZombies;
    }

    private void gainPoint() {
        this.points++;
    }

    private void move(Move move, WorldGrid worldGrid) {
        this.setPosition(
                Position.of(
                        Math.floorMod(getXPosition() + move.getXSteps(), worldGrid.getXBoundary()),
                        Math.floorMod(getYPosition() + move.getYSteps(), worldGrid.getYBoundary())
                )
        );
    }

    public int getPoints() {
        return points;
    }

    private int getXPosition() {
        return getPosition().getxCoordinate();
    }

    private int getYPosition() {
        return getPosition().getyCoordinate();
    }
}
