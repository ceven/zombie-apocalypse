package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static world.WorldCase.CaseType.LIVING_CREATURE;

public class WorldCase {

    private static final Logger LOG = LoggerFactory.getLogger(WorldCase.class);

    private CaseType caseType = CaseType.NO_LIVING_CREATURE;

    WorldCase() {

    }

    public boolean mutate() {
        if (!LIVING_CREATURE.equals(this.caseType)) {
            LOG.debug("Not mutating case {} as it is not of type {}", this.toString(), caseType.name());
            return false;
        }

        final CaseType newCaseType = CaseType.NO_LIVING_CREATURE;
        LOG.debug("Mutating {} to {}", this.toString(), newCaseType);
        this.setCaseType(newCaseType);
        return true;
    }

    public boolean isLivingCreature() {
        return LIVING_CREATURE == this.caseType;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public enum CaseType {
        LIVING_CREATURE, NO_LIVING_CREATURE
    }

    @Override
    public String toString() {
        return caseType.name();
    }
}
