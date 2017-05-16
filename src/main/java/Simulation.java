import com.google.common.collect.Lists;
import creature.LivingCreature;
import creature.Move;
import creature.Zombie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;
import world.WorldGrid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.util.stream.Collectors.toList;

public class Simulation {

    private static final Logger LOG = LoggerFactory.getLogger(Simulation.class);

    private final WorldGrid worldGrid;
    private final Zombie firstZombie;
    private final List<Move> moves;
    private boolean hasRun;

    public Simulation() {
        worldGrid = createDummyWorldGrid();
        firstZombie = new Zombie(Position.of(2, 1));
        moves = Collections.unmodifiableList(
                Lists.charactersOf("DLUURR").stream().map(Move::getMove).collect(toList())
        );
        hasRun = false;
    }

    public void run() {
        if (hasRun) {
            throw new IllegalStateException("Simulation has already run");
        }
        hasRun = true;
        Queue<Zombie> zombieQueue = new LinkedList<>();
        zombieQueue.add(firstZombie);
        while (!zombieQueue.isEmpty()) {
            Zombie currentZombie = zombieQueue.poll();
            moves.forEach(currentZombie::addMove);
            List<Zombie> newZombies = currentZombie.explore(worldGrid);
            zombieQueue.addAll(newZombies);
        }
    }

    private static WorldGrid createDummyWorldGrid() {
        WorldGrid grid = new WorldGrid(4, 4);
        LivingCreature c1 = new LivingCreature(Position.of(0, 1));
        LivingCreature c2 = new LivingCreature(Position.of(1, 2));
        LivingCreature c3 = new LivingCreature(Position.of(3, 1));

        grid.addToWorld(c1);
        grid.addToWorld(c2);
        grid.addToWorld(c3);

        LivingCreature creatureOut = new LivingCreature(Position.of(-1, -6));
        grid.addToWorld(creatureOut);

        return grid;
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.run();
    }

}
