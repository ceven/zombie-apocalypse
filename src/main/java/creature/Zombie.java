package creature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;
import world.World;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Zombie extends ACreature {

    private static final Logger LOG = LoggerFactory.getLogger(Zombie.class);

    private int points;

    public Zombie(Position position) {
        super(position);
    }

    /**
     * Move this Zombie around grid and return new zombies count
     *
     * @param grid  the grid that the zombie is exploring
     * @param moves
     * @return the list of newly created zombies
     */
    public List<Zombie> explore(final World grid, final List<Move> moves) {
        ArrayList<Zombie> newZombies = newArrayList();
        moves.forEach(m -> {
            LOG.debug("Moving zombie {}", m.name());
            this.move(m, grid);
            if (grid.visitLivingCreature(this)) {
                this.gainPoint();
                newZombies.add(new Zombie(Position.of(this.getXPosition(), this.getYPosition())));

            }
        });
        LOG.debug("Zombie {} gained {} points", this.toString(), points);
        return newZombies;
    }

    private void gainPoint() {
        this.points++;
    }

    private void move(Move move, World world) {
        this.setPosition(
                Position.of(
                        Math.floorMod(getXPosition() + move.getXSteps(), world.getXBoundary()),
                        Math.floorMod(getYPosition() + move.getYSteps(), world.getYBoundary())
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
