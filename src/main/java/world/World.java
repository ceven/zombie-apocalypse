package world;

import creature.LivingCreature;
import creature.Zombie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;

import java.util.Optional;

import static java.lang.String.format;
import static world.WorldCase.CaseType.LIVING_CREATURE;

public class World {

    private static final Logger LOG = LoggerFactory.getLogger(World.class);

    private static final int MIN_COORDINATE = 0;

    private final WorldCase[][] grid;

    World(WorldBuilder worldBuilder) {
        validateGridDimensions(worldBuilder.getWidth(), worldBuilder.getHeight());
        grid = new WorldCase[worldBuilder.getWidth()][worldBuilder.getWidth()];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new WorldCase();
            }
        }
    }

    private void validateGridDimensions(int xDimension, int yDimension) throws IllegalArgumentException {
        if (xDimension <= MIN_COORDINATE || yDimension <= MIN_COORDINATE) {
            throw new IllegalArgumentException(format("Grid dimensions must be strictly superior to %s.", MIN_COORDINATE));
        }
    }

    public boolean addToWorld(final LivingCreature livingCreature) {
        Optional<WorldCase> worldGridCase = getCase(livingCreature.getPosition());
        if (worldGridCase.isPresent()) {
            LOG.debug("Adding living creature to world. Position {}.", livingCreature.getPosition().toString());
            worldGridCase.get().setCaseType(LIVING_CREATURE);
            return true;
        } else {
            LOG.error("Cannot add creature to world: position {} outside of world boundaries [{}x{}]. Skipping.",
                    livingCreature.getPosition().toString(), getXBoundary(), getYBoundary());
            return false;
        }
    }

    private boolean validateCaseCoordinates(int x, int y) {
        return x >= MIN_COORDINATE && y >= MIN_COORDINATE && x < getXBoundary() && y < getYBoundary();
    }

    public Optional<WorldCase> getCase(final Position position) {
        return getCase(position.getxCoordinate(), position.getyCoordinate());
    }

    private Optional<WorldCase> getCase(int x, int y) {
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
        Optional<WorldCase> zombieCase = getCase(zombie.getPosition());
        if (zombieCase.isPresent()) {
            return zombieCase.get().mutate();
        } else {
            throw new IllegalStateException("Zombie out of bounds!");
        }
    }
}
