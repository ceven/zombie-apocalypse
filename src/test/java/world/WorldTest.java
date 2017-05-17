package world;

import creature.LivingCreature;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Position;

public class WorldTest {

    private World world;

    private static final int WORLD_GRID_SIZE = 4;

    @Before
    public void setUp() {
        world = new WorldBuilder()
                .ofWidth(WORLD_GRID_SIZE)
                .ofHeight(WORLD_GRID_SIZE)
                .build();
    }

    @Test
    public void shouldSkipAddingLivingCreatureOutOfWorldBoundaries() {
        LivingCreature creature = new LivingCreature(Position.of(-1, -6));
        Assert.assertFalse(world.addToWorld(creature));
    }

    @Test
    public void shouldAddLivingCreatureToWorldWhenWithinWorldBoundaries() {
        LivingCreature creature = new LivingCreature(Position.of(1, 2));
        Assert.assertTrue(world.addToWorld(creature));
    }
}
