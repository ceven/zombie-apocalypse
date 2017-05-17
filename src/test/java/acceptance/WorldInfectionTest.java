package acceptance;

import apocalypse.ApocalypseOutcome;
import apocalypse.ZombieApocalypse;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import creature.LivingCreature;
import creature.Move;
import creature.Zombie;
import org.junit.Assert;
import org.junit.Test;
import util.Position;
import world.World;
import world.WorldBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class WorldInfectionTest {

    private static final int WORLD_GRID_SIZE = 4;

    @Test
    public void shouldCreateCorrectApocalypseOutcome() {
        World world = createExampleWorldGrid();
        Zombie firstZombie = new Zombie(Position.of(2, 1));
        ImmutableList<Move> moves = ImmutableList.copyOf(
                Lists.charactersOf("DLUURR").stream().map(Move::getMove).collect(toList())
        );

        ZombieApocalypse zombieApocalypse = new ZombieApocalypse(world, firstZombie, moves);

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
     * @return the grid as provided in the problem description
     */
    private static World createExampleWorldGrid() {
        World world = new WorldBuilder().ofWidth(WORLD_GRID_SIZE).ofHeight(WORLD_GRID_SIZE).build();
        LivingCreature c1 = new LivingCreature(Position.of(0, 1));
        LivingCreature c2 = new LivingCreature(Position.of(1, 2));
        LivingCreature c3 = new LivingCreature(Position.of(3, 1));

        world.addToWorld(c1);
        world.addToWorld(c2);
        world.addToWorld(c3);

        return world;
    }
}
