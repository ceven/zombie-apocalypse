package apocalypse;

import com.google.common.collect.ImmutableList;
import creature.LivingCreature;
import creature.Zombie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Move;
import world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ZombieApocalypse {

    private static final Logger LOG = LoggerFactory.getLogger(ZombieApocalypse.class);

    private final World world;
    private final Zombie firstZombie;
    private final List<Move> moves;
    private final List<LivingCreature> livingCreatures;
    private boolean hasRun;

    public ZombieApocalypse(World world, Zombie firstZombie, List<Move> moves, List<LivingCreature> livingCreatures) {
        this.world = world;
        this.firstZombie = firstZombie;
        this.moves = moves;
        this.hasRun = false;
        this.livingCreatures = livingCreatures;
        livingCreatures.forEach(world::addToWorld);
    }

    /**
     * Run a full simulation of zombie apocalypse, starting with the first zombie spreading the virus
     *
     * @return the outcome of this zombie apocalypse
     * @throws IllegalStateException if this method has already been called
     */
    public ApocalypseOutcome spreadVirus() throws IllegalStateException {
        if (hasRun) {
            throw new IllegalStateException("ZombieApocalypse has already run");
        }
        hasRun = true;
        Queue<Zombie> zombieQueue = new LinkedList<>();
        zombieQueue.add(firstZombie);
        ApocalypseOutcome apocalypseOutcome = new ApocalypseOutcome();
        while (!zombieQueue.isEmpty()) {
            Zombie currentZombie = zombieQueue.poll();
            List<Zombie> newZombies = currentZombie.explore(world, moves);
            zombieQueue.addAll(newZombies);
            apocalypseOutcome.add(currentZombie.getPosition(), currentZombie.getPoints());
        }
        return apocalypseOutcome;
    }

    public World getWorld() {
        return world;
    }

    public Zombie getFirstZombie() {
        return firstZombie;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public ImmutableList<LivingCreature> getLivingCreatures() {
        return ImmutableList.copyOf(livingCreatures);
    }
}
