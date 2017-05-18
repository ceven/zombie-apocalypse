package apocalypse;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import creature.LivingCreature;
import creature.Zombie;
import org.junit.Assert;
import org.junit.Test;
import util.Move;
import util.Position;
import world.World;
import world.WorldBuilder;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

public class ZombieApocalypseTest {

    private static final int WORLD_GRID_SIZE = 4;

    @Test
    public void shouldCreateCorrectApocalypseOutcome() {
        World world = createExampleWorldGrid();
        Zombie firstZombie = new Zombie(Position.of(2, 1));
        ImmutableList<Move> moves = ImmutableList.copyOf(
                Lists.charactersOf("DLUURR").stream().map(Move::getMove).collect(toList())
        );
        List<LivingCreature> livingCreatures = createExampleLivingCreatures();

        ZombieApocalypse zombieApocalypse = new ZombieApocalypse(world, firstZombie, moves, livingCreatures);

        ApocalypseOutcome apocalypseOutcome = zombieApocalypse.spreadVirus();

        int expectedScore = 3;
        List<Position> expectedZombiePositions = ImmutableList.of(
                Position.of(3, 0),
                Position.of(2, 1),
                Position.of(1, 0),
                Position.of(0, 0)
        );

        Assert.assertEquals(expectedScore, apocalypseOutcome.getScore());
        Assert.assertEquals(expectedZombiePositions, apocalypseOutcome.getZombiePositions());
    }

    /**
     * @return the set of living creatures as provided in the problem description
     */
    private List<LivingCreature> createExampleLivingCreatures() {
        return newArrayList(
                new LivingCreature(Position.of(0, 1)),
                new LivingCreature(Position.of(1, 2)),
                new LivingCreature(Position.of(3, 1))
        );
    }

    /**
     * @return the grid as provided in the problem description
     */
    private static World createExampleWorldGrid() {
        return new WorldBuilder().ofWidth(WORLD_GRID_SIZE).ofHeight(WORLD_GRID_SIZE).build();
    }
}
