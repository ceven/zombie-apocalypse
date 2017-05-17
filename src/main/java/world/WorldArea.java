package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static world.WorldArea.CreatureType.LIVING_CREATURE;

public class WorldArea {

    private static final Logger LOG = LoggerFactory.getLogger(WorldArea.class);

    private CreatureType creatureType = CreatureType.NO_LIVING_CREATURE;

    WorldArea() {

    }

    public boolean mutate() {
        if (!LIVING_CREATURE.equals(this.creatureType)) {
            LOG.debug("Not mutating area {} as it is not of type {}", this.toString(), creatureType.name());
            return false;
        }

        final CreatureType newCreatureType = CreatureType.NO_LIVING_CREATURE;
        LOG.debug("Mutating {} to {}", this.toString(), newCreatureType);
        this.setCreatureType(newCreatureType);
        return true;
    }

    public boolean hasLivingCreature() {
        return LIVING_CREATURE == this.creatureType;
    }

    public void setCreatureType(CreatureType creatureType) {
        this.creatureType = creatureType;
    }

    public enum CreatureType {
        // We're only interested in keeping track of areas with living creatures right now.
        // Hence both empty areas and areas with new zombies are marked as NO_LIVING_CREATURE.
        LIVING_CREATURE, NO_LIVING_CREATURE
    }

    @Override
    public String toString() {
        return creatureType.name();
    }
}
