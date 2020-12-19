package CowEatingGrass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author kaminari
 */
public class Cow {
    boolean isFacingNorth;
    boolean hasStopped = false;
    int x;
    int y;
    int consumption;
    private static final int LIMIT = 109;
    static boolean[][] farm = new boolean[LIMIT + 1][LIMIT + 1];
    public void eat(){
        if (!inRange()){
            consumption = -1;
            hasStopped = true;
            return;
        }
        if (!farm[x][y]){
             consumption++;
             return;
        }
        hasStopped = true;
    }
    public void move(){
         if (!hasStopped){
             farm[x][y] = true;
             if (isFacingNorth){
                 y++;
             } else {
                 x++;
             }
         }
    }
    public boolean inRange(){
        return x >= 0 && x <= LIMIT && y >= 0 && y <= LIMIT;
    }
    public Cow(Scanner scanner){
        this.isFacingNorth = "N".equals(scanner.next());
        this.x = scanner.nextInt();
        this.y = scanner.nextInt();
    }
    public void printConsumption(){
        System.out.println(consumption == -1 ? "Infinity" : consumption);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
         File file = new File("src/test.md");
         scanner = new Scanner(file);
        int cowCount = scanner.nextInt();
        Cow[] cows = new Cow[cowCount];
        int i = 0;
        while (scanner.hasNext()){
            cows[i++] = new Cow(scanner);
        }

        List<Cow> walkingCows = Arrays.asList(cows);
        while (! walkingCows.isEmpty()){
            walkingCows.forEach(Cow::eat);
            walkingCows.forEach(Cow::move);
            walkingCows = walkingCows.stream().filter(cow -> !cow.hasStopped).collect(Collectors.toList());                                                                                                                                                   
        }
        Arrays.stream(cows).forEach(Cow::printConsumption);
    }

}
