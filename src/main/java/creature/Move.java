package creature;

import java.util.Arrays;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.String.format;

public enum Move {

    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    private static final Map<Character, Move> CHAR_TO_MOVE = newHashMap();
    private final int nXSteps, nYSteps;

    static {
        Arrays.stream(values()).forEach(move -> CHAR_TO_MOVE.put(move.getFirstChar(), move));
    }

    Move(int x, int y) {
        this.nXSteps = x;
        this.nYSteps = y;
    }

    public int getXSteps() {
        return nXSteps;
    }

    public int getYSteps() {
        return nYSteps;
    }

    public char getFirstChar() {
        return Character.toUpperCase(this.name().charAt(0));
    }

    public static Move getMove(final char c) throws IllegalArgumentException {
        char uppercaseChar = Character.toUpperCase(c);
        if (CHAR_TO_MOVE.containsKey(uppercaseChar)) {
            return CHAR_TO_MOVE.get(uppercaseChar);
        }
        throw new IllegalArgumentException(format("Character %s is not a valid move", uppercaseChar));
    }

}
