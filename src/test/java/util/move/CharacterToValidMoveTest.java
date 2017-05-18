package util.move;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.Move;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CharacterToValidMoveTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {'D', Move.DOWN}, {'d', Move.DOWN},
                {'U', Move.UP}, {'u', Move.UP},
                {'L', Move.LEFT}, {'l', Move.LEFT},
                {'R', Move.RIGHT}, {'r', Move.RIGHT}
        });
    }

    private char charInput;
    private Move expectedMove;

    public CharacterToValidMoveTest(char cInput, Move expectedMove) {
        this.charInput = cInput;
        this.expectedMove = expectedMove;
    }

    @Test
    public void characterShouldRepresentValidMove() {
        Assert.assertEquals(expectedMove, Move.getMove(charInput));
    }
}
