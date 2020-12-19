package CowEatingGrass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
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
    private static final int LIMIT = 100000000;
    private static final Set<Point> farm = new HashSet<>();
    public void eat(){
        if (!inRange()){
            consumption = -1;
            hasStopped = true;
            return;
        }
        if (!farm.contains(new Point(x, y))){
             consumption++;
             return;
        }
        hasStopped = true;
    }
    public void move(){
         if (!hasStopped){
             farm.add(new Point(x, y));
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


    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            }
            if (!(o instanceof Point)) {
                return false;
            }

            Point point = (Point) o;
            return point.x == x && point.y == y;
        }
        @Override
        public int hashCode() {
            int result = 17;
            result += 31 * x + y;

            return result;
        }
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
        while (!walkingCows.isEmpty()){
            walkingCows.forEach(Cow::eat);
            walkingCows.forEach(Cow::move);
            walkingCows = walkingCows.stream().filter(cow -> !cow.hasStopped).collect(Collectors.toList());                                                                                                                                                   
        }
        Arrays.stream(cows).forEach(Cow::printConsumption);
    }

}
