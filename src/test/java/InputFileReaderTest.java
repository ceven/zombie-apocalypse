import apocalypse.ZombieApocalypse;
import creature.Move;
import input.InputFileReader;
import org.junit.Assert;
import org.junit.Test;
import util.Position;
import world.WorldCase;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.junit.Assert.fail;

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

        Assert.assertEquals(expectedWorldSize, zombieApocalypse.getWorld().getXBoundary());

        Assert.assertTrue(zombieApocalypse.getWorld().getXBoundary() == zombieApocalypse.getWorld().getYBoundary());

        Assert.assertEquals(expectedZombiePosition, zombieApocalypse.getFirstZombie().getPosition());
        Assert.assertEquals(expectedMoves, zombieApocalypse.getMoves());

        expectedLivingCreaturesPositions.forEach(position -> {
            Optional<WorldCase> worldCase = zombieApocalypse.getWorld().getCase(position);
            if (!worldCase.isPresent()) {
                fail(format("World should have contained a case at position %s", position.toString()));
            } else {
                Assert.assertTrue(worldCase.get().isLivingCreature());
            }
        });
    }
}
