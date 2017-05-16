import creature.Move;
import org.junit.Test;

public class CharacterToInvalidMoveTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenCharacterIsInvalidMove() {
        Move.getMove('%');
    }
}
