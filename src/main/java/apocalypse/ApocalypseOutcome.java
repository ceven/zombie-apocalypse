package apocalypse;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public final class ApocalypseOutcome {

    private static final Logger LOG = LoggerFactory.getLogger(ApocalypseOutcome.class);

    private int score;
    private final List<Position> zombiePositions;

    public ApocalypseOutcome() {
        this.score = 0;
        this.zombiePositions = newArrayList();
    }

    public void add(final Position zombiePosition, final int points) {
        if (points < 0) {
            LOG.warn("Negative points [{}] for zombie with final position [{}] will be added to final outcome",
                    points, zombiePosition.toString());
        }
        this.zombiePositions.add(zombiePosition);
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public ImmutableList<Position> getZombiePositions() {
        return ImmutableList.copyOf(zombiePositions);
    }

    @Override
    public String toString() {
        String scoreStr = "zombies score: " + getScore();
        String zombiesPositions = "zombies positions: " +
                getZombiePositions()
                        .stream()
                        .map(Position::toString)
                        .collect(Collectors.joining(" "));
        return scoreStr + "\n" + zombiesPositions;
    }
}
