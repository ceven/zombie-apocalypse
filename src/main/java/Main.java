import apocalypse.ApocalypseOutcome;
import apocalypse.ZombieApocalypse;
import util.InputFileReader;

import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {

        ZombieApocalypse zombieApocalypse =
                InputFileReader.createZombieApocalypse("src/test/file/world-infection-1.txt");

        System.out.println("\n----- ZOMBIE APOCALYPSE STARTED !!! -----\n");
        System.out.println("----- INPUT -----\n");
        printInput(zombieApocalypse);

        ApocalypseOutcome apocalypseOutcome = zombieApocalypse.spreadVirus();

        System.out.println("\n----- OUTPUT -----\n");
        System.out.println(apocalypseOutcome.toString());
    }

    private static void printInput(ZombieApocalypse zombieApocalypse) {
        System.out.println(zombieApocalypse.getWorld().getXBoundary());
        System.out.println(zombieApocalypse.getFirstZombie().getPosition().toString());
        System.out.println(
                zombieApocalypse
                        .getLivingCreatures()
                        .stream()
                        .map(l -> l.getPosition().toString())
                        .collect(Collectors.joining(" "))
        );
        System.out.println(zombieApocalypse.getMoves());
    }
}
