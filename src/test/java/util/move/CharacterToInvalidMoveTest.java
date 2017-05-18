package util.move;

import org.junit.Test;
import util.Move;

public class CharacterToInvalidMoveTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCharacterIsInvalidMove() {
        Move.getMove('%');
    }
}
