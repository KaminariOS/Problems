package CowEatingGrass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kaminari
 */
public class Cow {
    final boolean isFacingNorth;
    boolean hasStopped = false;
    int x;
    int y;
    int consumption;
    private static final int LIMIT = 1000_000_000;
    private static final Set<Point> farm = new HashSet<>();
    public void eat(List<Cow> infinity){
        if (!inRange()){
            consumption = -1;
            hasStopped = true;
            return;
        }
        if (cross(infinity)){
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

    public boolean cross(List<Cow> infinity){
        for (Cow cow : infinity){
            if (cow.isFacingNorth){
                if (y >= cow.y && x == cow.x && !isFacingNorth){
                    return true;
                }
            }
            else{
                if (x >= cow.x && y == cow.y && isFacingNorth){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        //File file = new File("src/test.md");
        // scanner = new Scanner(file);
        int cowCount = scanner.nextInt();
        Cow[] cows = new Cow[cowCount];
        int i = 0;
        while (scanner.hasNext()){
            cows[i++] = new Cow(scanner);
        }

        List<Cow> walkingCows = Arrays.asList(cows);
        List<Cow> infinity = new ArrayList<>();
        while (!walkingCows.isEmpty()){
            infinity.addAll(filterWithInfinity(getMax(walkingCows), infinity));
            walkingCows = walkingCows.stream().filter(cow -> !cow.hasStopped).collect(Collectors.toList());

            walkingCows.forEach(e -> e.eat(infinity));
            walkingCows.forEach(Cow::move);
            walkingCows = walkingCows.stream().filter(cow -> !cow.hasStopped).collect(Collectors.toList());
        }
        Arrays.stream(cows).forEach(Cow::printConsumption);
    }

    public static List<Cow> getMax(List<Cow> walkingCows) {
        Cow maxX = walkingCows.get(0);
        Cow maxY = walkingCows.get(0);
        for (Cow e :walkingCows){
            maxX = (e.x >= maxX.x) ? e : maxX;
            maxY = (e.y >= maxY.y)? e : maxY;
        }
        List<Cow> temp = new ArrayList<>();

        if (!maxX.isFacingNorth){
            temp.add(maxX);
        }
        if (maxY.isFacingNorth){
            temp.add(maxY);
        }
        return temp;
    }

    private static List<Cow> filterWithInfinity(List<Cow> temp, List<Cow> infinity){
        List<Cow> result = new ArrayList<>();
        for (Cow cow : temp){
            if (cow.cross(infinity) || farm.contains(new Point(cow.x, cow.y))){
                continue;
            }
            cow.hasStopped = true;
            cow.consumption = -1;
            result.add(cow);
        }
        return result;
    }

}
