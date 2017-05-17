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
        Position position = Position.of(-1, -6);
        LivingCreature creature = new LivingCreature(position);
        Assert.assertFalse(world.addToWorld(creature));
    }

    @Test
    public void shouldAddLivingCreatureToWorldWhenWithinWorldBoundaries() {
        Position position = Position.of(1, 2);
        LivingCreature creature = new LivingCreature(position);
        Assert.assertTrue(world.addToWorld(creature));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenMutatingAndPositionOutOfWorldBoundaries() {
        Position position = Position.of(-1, 2);
        world.mutate(position);
    }

    @Test
    public void hasLivingCreatureShouldBeFalse() {
        Position position = Position.of(1, 2);
        Assert.assertFalse(world.hasLivingCreature(position));
    }

    @Test
    public void hasLivingCreatureShouldBeTrue() {
        Position position = Position.of(1, 2);
        LivingCreature creature = new LivingCreature(position);
        world.addToWorld(creature);
        Assert.assertTrue(world.hasLivingCreature(position));
    }

    @Test
    public void shouldMutateCaseWithLivingCreature() {
        Position position = Position.of(1, 2);
        LivingCreature creature = new LivingCreature(position);
        world.addToWorld(creature);
        Assert.assertTrue(world.mutate(position));
    }

    @Test
    public void shouldNotMutateCaseWithoutLivingCreature() {
        Position position = Position.of(1, 2);
        Assert.assertFalse(world.mutate(position));
    }

}
