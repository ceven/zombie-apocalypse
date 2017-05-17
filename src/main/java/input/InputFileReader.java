package input;

import apocalypse.ZombieApocalypse;
import com.google.common.collect.Lists;
import creature.LivingCreature;
import creature.Move;
import creature.Zombie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Position;
import world.World;
import world.WorldBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

public class InputFileReader {

    private static final Logger LOG = LoggerFactory.getLogger(InputFileReader.class);

    private static final Pattern PATTERN_SINGLE_POSITION = Pattern.compile("^(\\(\\d+,\\d+\\))$");

    private static final Pattern PATTERN_SINGLE_POSITION_ANYWHERE = Pattern.compile("(\\(\\d+,\\d+\\))");

    private static final Pattern PATTERN_POSITIONS = Pattern.compile("^(\\(\\d+,\\d+\\))+$");

    private static final Pattern PATTERN_MOVES = Pattern.compile("^[UDLRudlr]+$");

    private static final Pattern PATTERN_GRID_SIZE = Pattern.compile("^\\d+$");

    public static ZombieApocalypse createZombieApocalypse(final String filePath) throws Exception {
        if (filePath == null) {
            throw new IllegalArgumentException("File path should not be null");
        }
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        if (lines.size() != 4) {
            throw new IllegalStateException(
                    format("Wrong file format: expected 4 lines for file but got %s lines", lines.size())
            );
        }

        World world = createWorld(lines.get(0));
        getLivingCreatures(lines.get(2)).forEach(world::addToWorld);

        Zombie zombie = getZombie(lines.get(1));
        List<Move> zombieMoves = getMoves(lines.get(3));

        return new ZombieApocalypse(world, zombie, zombieMoves);
    }

    private static World createWorld(final String input) throws Exception {
        int worldSize = Integer.parseInt(
                getMatch(input, PATTERN_GRID_SIZE)
                        .orElseThrow(() -> new Exception("Could not determine world size from input"))
        );
        return new WorldBuilder()
                .ofHeight(worldSize)
                .ofWidth(worldSize)
                .build();
    }

    private static Zombie getZombie(final String input) throws Exception {
        String positionStr = getMatch(input, PATTERN_SINGLE_POSITION).orElseThrow(
                () -> new Exception("Could not determine zombie position from input")
        );
        Position zombiePosition = getPosition(positionStr).orElseThrow(
                () -> new Exception("Could not determine zombie position from input")
        );
        return new Zombie(zombiePosition);
    }

    private static List<LivingCreature> getLivingCreatures(final String input) {
        Optional<String> positions = getMatch(input, PATTERN_POSITIONS);
        return positions.map(pos -> getMatches(pos, PATTERN_SINGLE_POSITION_ANYWHERE)
                .stream()
                .map(InputFileReader::getPosition)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(LivingCreature::new)
                .collect(toList())).orElseGet(Collections::emptyList);
    }

    private static List<Move> getMoves(final String input) {
        Optional<String> moves = getMatch(input, PATTERN_MOVES);
        return moves.map(s -> Lists.charactersOf(s).stream().map(Move::getMove).collect(toList()))
                .orElseGet(Collections::emptyList);
    }

    private static Optional<String> getMatch(final String str, final Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            String match = matcher.group();
            LOG.info("Found a match for string {} and pattern {} : {}", str, pattern.toString(), match);
            return Optional.of(match);
        } else {
            LOG.warn("Did not find a match for string {} and pattern {}", str, pattern.toString());
            return Optional.empty();
        }
    }

    private static List<String> getMatches(final String str, final Pattern pattern) {
        Matcher matcher = pattern.matcher(str);
        List<String> matches = newArrayList();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    private static Optional<Position> getPosition(final String str) {
        //FIXME dodgy - should find way to convert (1,2) to pos
        if (str == null || str.length() != 5) {
            return Optional.empty();
        }
        try {
            return Optional.of(
                    Position.of(
                            Integer.parseInt(str.substring(1, 2)),
                            Integer.parseInt(str.substring(3, 4))
                    )
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
