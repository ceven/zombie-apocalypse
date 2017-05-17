package apocalypse;

import creature.Move;
import input.InputFileReader;
import org.junit.Test;
import util.Position;
import world.World;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InputFileReaderTest {

    @Test
    public void shouldReadWellFormattedInputFileCorrectly() throws Exception {
        ZombieApocalypse zombieApocalypse =
                InputFileReader.createZombieApocalypse("src/test/file/world-infection-1.txt");

        int expectedWorldSize = 4;
        Position expectedZombiePosition = Position.of(2, 1);
        List<Position> expectedLivingCreaturesPositions = newArrayList(
                Position.of(0, 1),
                Position.of(1, 2),
                Position.of(3, 1)
        );
        List<Move> expectedMoves = newArrayList(Move.DOWN, Move.LEFT, Move.UP, Move.UP, Move.RIGHT, Move.RIGHT);

        verifyWorldSize(expectedWorldSize, zombieApocalypse.getWorld());

        verifyLivingCreaturesPositions(expectedLivingCreaturesPositions, zombieApocalypse.getWorld());

        assertEquals(expectedZombiePosition, zombieApocalypse.getFirstZombie().getPosition());

        assertEquals(expectedMoves, zombieApocalypse.getMoves());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfFilePathIsNull() throws Exception {
        InputFileReader.createZombieApocalypse(null);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfFileDoesNotContainInput() throws Exception {
        InputFileReader.createZombieApocalypse("src/test/file/bad/emptyfile.txt");
    }

    private void verifyWorldSize(int expectedWorldSize, World world) {
        assertEquals(expectedWorldSize, world.getXBoundary());
        assertTrue(world.getXBoundary() == world.getYBoundary());
    }

    private void verifyLivingCreaturesPositions(List<Position> expectedLivingCreaturesPositions, World world) {
        expectedLivingCreaturesPositions.forEach(position -> assertTrue(world.hasLivingCreature(position)));
    }
}
