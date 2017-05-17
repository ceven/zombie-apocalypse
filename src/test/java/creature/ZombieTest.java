package creature;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.Position;
import world.World;
import world.WorldBuilder;

public class ZombieTest {

    private static final int WORLD_GRID_SIZE = 4;

    private Zombie zombie;
    private World world;

    @Before
    public void setUp() {
        Position position = Position.of(WORLD_GRID_SIZE / 2, WORLD_GRID_SIZE - 1);
        zombie = new Zombie(position);
        world = new WorldBuilder()
                .ofWidth(WORLD_GRID_SIZE)
                .ofHeight(WORLD_GRID_SIZE)
                .build();
    }

    @Test
    public void shouldReturnTrueWhenZombieVisitsLivingCreature() {

        world.addToWorld(new LivingCreature(zombie.getPosition()));

        Assert.assertTrue(zombie.visitLivingCreature(world));
    }

    @Test
    public void shouldReturnFalseWhenZombieDoesNotVisitLivingCreature() {

        Position position = Position.of(WORLD_GRID_SIZE / 2, WORLD_GRID_SIZE - 1);

        Zombie zombie = new Zombie(position);

        Assert.assertFalse(zombie.visitLivingCreature(world));
    }
}
