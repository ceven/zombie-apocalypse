package world;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class WorldGridCase {

    private static final Logger LOG = LoggerFactory.getLogger(WorldGridCase.class);

    private CaseType caseType = CaseType.NO_LIVING_CREATURE;

    WorldGridCase() {

    }

    public boolean mutate() throws IllegalStateException {
        if (!CaseType.LIVING_CREATURE.equals(this.caseType)) {
            LOG.debug("Not mutating case {} as it is not of type {}", this.toString(), caseType.name());
            return false;
        }

        final CaseType newCaseType = CaseType.NO_LIVING_CREATURE;
        LOG.debug("Mutating {} to {}", this.toString(), newCaseType);
        this.setCaseType(newCaseType);
        return true;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public enum CaseType {
        LIVING_CREATURE, NO_LIVING_CREATURE
    }

    @Override
    public String toString() {
        return format("Case of type %s", caseType.name());
    }
}
