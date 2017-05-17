import apocalypse.ZombieApocalypse;
import creature.Move;
import org.junit.Assert;
import org.junit.Test;
import util.InputFileReader;
import util.Position;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

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
//        Assert.assertEquals(expectedLivingCreaturesPositions, zombieApocalypse.);//TODO
        Assert.assertEquals(expectedMoves, zombieApocalypse.getMoves());
    }
}
