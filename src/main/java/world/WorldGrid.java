package world;

import creature.LivingCreature;
import creature.Zombie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;

import java.util.Optional;

import static java.lang.String.format;
import static world.WorldGridCase.CaseType.LIVING_CREATURE;

public class WorldGrid {

    private static final Logger LOG = LoggerFactory.getLogger(WorldGrid.class);

    private static final int MIN_COORDINATE = 0;

    private final WorldGridCase[][] grid;

    public WorldGrid(int xDimension, int yDimension) throws IllegalArgumentException {
        validateGridDimensions(xDimension, yDimension);
        grid = new WorldGridCase[xDimension][yDimension];
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                grid[i][j] = new WorldGridCase();
            }
        }
    }

    private void validateGridDimensions(int xDimension, int yDimension) throws IllegalArgumentException {
        if (xDimension <= MIN_COORDINATE || yDimension <= MIN_COORDINATE) {
            throw new IllegalArgumentException(format("Grid dimensions must be strictly superior to %s.", MIN_COORDINATE));
        }
    }

    public boolean addToWorld(final LivingCreature livingCreature) {
        Optional<WorldGridCase> worldGridCase = getCase(livingCreature.getPosition());
        if (worldGridCase.isPresent()) {
            LOG.debug("Adding {} to world.", livingCreature.toString());
            worldGridCase.get().setCaseType(LIVING_CREATURE);
            return true;
        } else {
            LOG.error("Cannot add {} to world: position outside of world boundaries [{}x{}]. Skipping.",
                    livingCreature.toString(), getXBoundary(), getYBoundary());
            return false;
        }
    }

    private boolean validateCaseCoordinates(int x, int y) {
        return x >= MIN_COORDINATE && y >= MIN_COORDINATE && x < getXBoundary() && y < getYBoundary();
    }

    public Optional<WorldGridCase> getCase(final Position position) {
        return getCase(position.getxCoordinate(), position.getyCoordinate());
    }

    private Optional<WorldGridCase> getCase(int x, int y) {
        if (!validateCaseCoordinates(x, y)) {
            LOG.error("Coordinates [{}, {}] outside of grid boundary (dimensions {}x{})", x, y, getXBoundary(), getYBoundary());
            return Optional.empty();
        }
        return Optional.of(grid[x][y]);
    }

    public int getXBoundary() {
        return grid.length;
    }

    public int getYBoundary() {
        return grid[0].length;
    }

    public boolean visitLivingCreature(final Zombie zombie) {
        Optional<WorldGridCase> zombieCase = getCase(zombie.getPosition());
        if (zombieCase.isPresent()) {
            return zombieCase.get().mutate();
        } else {
            throw new IllegalStateException("Zombie out of bounds!");
        }
    }
}
