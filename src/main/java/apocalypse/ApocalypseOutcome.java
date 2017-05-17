package apocalypse;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ApocalypseOutcome {

    private static final Logger LOG = LoggerFactory.getLogger(ApocalypseOutcome.class);

    private int score;
    private final List<Position> zombiePositions;

    public ApocalypseOutcome() {
        this.score = 0;
        this.zombiePositions = newArrayList();
    }

    public void add(final Position position, final int points) {
        if (points < 0) {
            LOG.warn("Negative points [{}] for zombie with final position [{}] will be added to final outcome",
                    points, position.toString());
        }
        this.zombiePositions.add(position);
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public ImmutableList<Position> getZombiePositions() {
        return ImmutableList.copyOf(zombiePositions);
    }
}
