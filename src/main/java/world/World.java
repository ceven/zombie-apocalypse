package world;

import creature.LivingCreature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;

import java.util.Optional;

import static java.lang.String.format;
import static world.WorldArea.CreatureType.LIVING_CREATURE;

public class World {

    private static final Logger LOG = LoggerFactory.getLogger(World.class);

    private static final int MIN_COORDINATE = 0;

    private final WorldArea[][] grid;

    World(WorldBuilder worldBuilder) {
        validateGridDimensions(worldBuilder.getWidth(), worldBuilder.getHeight());
        grid = new WorldArea[worldBuilder.getWidth()][worldBuilder.getWidth()];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new WorldArea();
            }
        }
    }

    private void validateGridDimensions(int xDimension, int yDimension) throws IllegalArgumentException {
        if (xDimension <= MIN_COORDINATE || yDimension <= MIN_COORDINATE) {
            throw new IllegalArgumentException(format("Grid dimensions must be strictly superior to %s.", MIN_COORDINATE));
        }
    }

    public boolean addToWorld(final LivingCreature livingCreature) {
        Optional<WorldArea> worldGridCase = getCase(livingCreature.getPosition());
        if (worldGridCase.isPresent()) {
            LOG.debug("Adding living creature to world. Position {}.", livingCreature.getPosition().toString());
            worldGridCase.get().setCreatureType(LIVING_CREATURE);
            return true;
        } else {
            LOG.error("Cannot add creature to world: position {} outside of world boundaries [{}x{}]. Skipping.",
                    livingCreature.getPosition().toString(), getXBoundary(), getYBoundary());
            return false;
        }
    }

    public boolean mutate(final Position position) throws IllegalArgumentException {
        Optional<WorldArea> worldCase = this.getCase(position);
        if (!worldCase.isPresent()) {
            throw new IllegalArgumentException("Out of bounds!");

        }
        return worldCase.get().mutate();
    }

    public boolean hasLivingCreature(final Position position) throws IllegalArgumentException {
        Optional<WorldArea> worldCase = this.getCase(position);
        if (!worldCase.isPresent()) {
            throw new IllegalArgumentException("Out of bounds!");
        }
        return worldCase.get().hasLivingCreature();
    }

    private boolean validateCaseCoordinates(int x, int y) {
        return x >= MIN_COORDINATE && y >= MIN_COORDINATE && x < getXBoundary() && y < getYBoundary();
    }

    private Optional<WorldArea> getCase(final Position position) {
        return getCase(position.getxCoordinate(), position.getyCoordinate());
    }

    private Optional<WorldArea> getCase(int x, int y) {
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

}
