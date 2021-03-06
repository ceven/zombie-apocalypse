package creature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Move;
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
     * @param world the world that the zombie is exploring
     * @param moves the sequence of moves the zombie will follow
     * @return the list of newly created zombies
     */
    public List<Zombie> explore(final World world, final List<Move> moves) {
        ArrayList<Zombie> newZombies = newArrayList();
        moves.forEach(m -> {
            LOG.debug("Moving zombie {}", m.name());
            this.move(world, m);
            if (this.visitLivingCreature(world)) {
                this.gainPoint();
                newZombies.add(new Zombie(Position.of(this.getXPosition(), this.getYPosition())));

            }
        });
        LOG.debug("Zombie {} gained {} points", this.toString(), points);
        return newZombies;
    }

    /**
     * If the zombie is in an area containing a living creature, the living creature will turn into a zombie.
     *
     * @param world the world the zombie is currently evolving around
     * @return true if the zombie is visiting an area containing a living creature, false otherwise
     */
    public boolean visitLivingCreature(final World world) {
        return world.mutate(this.getPosition());
    }

    private void gainPoint() {
        this.points++;
    }

    /**
     * Move the zombie in the world
     *
     * @param world the world the zombie is currently evolving around
     * @param move  the move to perform
     */
    private void move(final World world, final Move move) {
        this.updatePosition(
                Math.floorMod(getXPosition() + move.getXSteps(), world.getXBoundary()),
                Math.floorMod(getYPosition() + move.getYSteps(), world.getYBoundary())
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
