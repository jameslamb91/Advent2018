import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class day15 {
    static char[][] cave = new char[32][32];
    static ArrayList<Fighter> fighters = new ArrayList<>();
    static HashMap<String, Boolean> livingRaces = new HashMap<>();
    static HashSet<Coordinate> checkedNodes = new HashSet<>();
    static ConcurrentLinkedQueue<Coordinate> pathToCheck = new ConcurrentLinkedQueue<>();
    static ArrayList<Coordinate> occupiedSpaces = new ArrayList<>();
    static LinkedHashSet<Coordinate> shortestPath = new LinkedHashSet<>();
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day15input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            int y = 0;
            while ((input = br.readLine()) != null) {
                int x = 0;
                for(char c : input.toCharArray()){

                    if(c == 'E'){
                        fighters.add(new Fighter(x, y, "elf"));
                    }
                    else if(c == 'G'){
                        fighters.add(new Fighter(x, y, "goblin"));
                    }
                    cave[x][y] = c;
                    x++;
                }
                y++;
            }

            boolean bothRacesLive = true;
            int numberOfRounds = 0;
//            while(bothRacesLive){


                // set up for round
                for(String key : livingRaces.keySet()){
                    livingRaces.put(key, false);
                }
                sortFighters();

                for(Fighter currentFighter : fighters){
                    ArrayList<Fighter> livingEnemies = new ArrayList<>();
                    ArrayList<Fighter> reachableEnemies = new ArrayList<>();

                    // get list of living enemies
                    for (Fighter target : fighters){
                        if(target.type.equals(currentFighter.type)){
                            continue;
                        }
                        else if (target.health > 0){
                            System.out.println(
                                    "Fighter " + currentFighter.type + " at " + currentFighter.location.toPrint() +
                                    " found a valid path to " + target.type + " at " + target.location.toPrint() + ": " +
                                    isThereValidPathTo(currentFighter.location, target.location));
                            livingEnemies.add(target);
                        }
                    }
                    if(livingEnemies.isEmpty()){
                        numberOfRounds++;

                        // trigger end of game
                        int totalHealth = 0;

                        for (Fighter f : fighters){
                            if(f.health > 0){
                                totalHealth += f.health;
                            }
                        }

                        System.out.println("Part 1 completed after " + numberOfRounds + " rounds of combat. Total health of living creatures is " + totalHealth);
                        System.out.println("Part 1 answer is " + (numberOfRounds * totalHealth));
                    }

//                    for(Fighter target : livingEnemies){
//                        if(isThereValidPathTo(currentFighter.location, target.location)){
//                            reachableEnemies.add(target);
//                        }
//                    }
//                    int distance = Integer.MAX_VALUE;
//                    Fighter closestEnemy = new Fighter(Integer.MAX_VALUE, Integer.MAX_VALUE, "");
//                    for(Fighter target : reachableEnemies) {
//                        int currentDistance = findShortestDistance(currentFighter.location, target.location);
//                        if((currentDistance < distance) ||
//                            (currentDistance == distance && ((target.location.y < closestEnemy.location.y) ||
//                            (target.location.y == closestEnemy.location.y && (target.location.x < closestEnemy.location.x))))){
//                            closestEnemy = target;
//                            distance = currentDistance;
//                        }
//
//                    }




                }

                numberOfRounds++;
//            }
//            int totalHealth = 0;
//
//            for (Fighter f : fighters){
//                if(f.health > 0){
//                    totalHealth += f.health;
//                }
//            }

//            System.out.println("Part 1 completed after " + numberOfRounds + " rounds of combat. Total health of living creatures is " + totalHealth);
//            System.out.println("Part 1 answer is " + (numberOfRounds * totalHealth));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LinkedHashSet<Coordinate> findShortestDistance(Coordinate fighterLocation, Coordinate target){
        LinkedHashSet<Coordinate> shortPath = new LinkedHashSet<>();
        for(Coordinate c : getAdjacentPoints(target)){
            if(canGoToSpace(c)){
                if(!hasCoordinateBeenChecked(c)){
                    pathToCheck.add(c);
                    checkedNodes.add(c);
                }
            }
        }
        for(Coordinate c : pathToCheck){
            if(c.isTheSameAs(fighterLocation)){
                pathToCheck.clear();
                checkedNodes.clear();
            }
            pathToCheck.remove(c);
            findShortestDistance(fighterLocation, c);
        }
        return null;
    }

    private static boolean isThereValidPathTo(Coordinate start, Coordinate target){
        for(Coordinate c : getAdjacentPoints(target)){
            if(canGoToSpace(c)){
                if(!hasCoordinateBeenChecked(c)){
                    pathToCheck.add(c);
                    checkedNodes.add(c);
                }
            }
        }

        for(Coordinate c : pathToCheck){
            for(Coordinate d : getAdjacentPoints(c)){
                if(d.isTheSameAs(start) || c.isTheSameAs(start)){
                    pathToCheck.clear();
                    checkedNodes.clear();
                    return true;
                }
            }
            pathToCheck.remove(c);
            return isThereValidPathTo(start, c);
        }
        return false;
    }
    private static boolean hasCoordinateBeenChecked(Coordinate c){
        for (Coordinate checked : checkedNodes){
            if(checked.isTheSameAs(c)){
                return true;
            }
        }
        return false;
    }
    private static boolean canGoToSpace(int x, int y){
        if (cave[x][y] != '#'){
            return true;
        }
        return false;
    }
    private static boolean canGoToSpace(Coordinate c){
        for(Coordinate d : occupiedSpaces){
            if(d.isTheSameAs(c)){
                return false;
            }
        }
        return canGoToSpace(c.x, c.y);
    }
    private static ArrayList<Coordinate> getAdjacentPoints(int x, int y){
        ArrayList<Coordinate> listToReturn = new ArrayList<>();

        if(y > 0){
            listToReturn.add(new Coordinate(x,y-1));
        }
        if (x > 0){
            listToReturn.add(new Coordinate(x-1,y));
        }
        if ( x < cave.length - 1){
            listToReturn.add(new Coordinate(x+1,y));
        }
        if(y < cave.length - 1){
            listToReturn.add(new Coordinate(x,y+1));
        }
        return listToReturn;
    }
    private static ArrayList<Coordinate> getAdjacentPoints(Coordinate c){
        int x = c.x;
        int y = c.y;
       return getAdjacentPoints(x, y);
    }
    private static Fighter checkForLivingFighterAtCoordinate(Coordinate c){
        for (Fighter f : fighters){
            if(f.health > 0){
                if(f.location.isTheSameAs(c)){
                    return f;
                }
            }
        }
        return null;
    }
    private static void sortFighters() {

        ArrayList<Fighter> temp = fighters;

        boolean notSorted = true;
        while (notSorted) {
            notSorted = false;
            for (int i = 0; i < temp.size() - 1; i++) {
                if ((temp.get(i).location.y > temp.get(i + 1).location.y)
                        || ((temp.get(i).location.y == temp.get(i + 1).location.y)
                        && (temp.get(i).location.x > temp.get(i + 1).location.x))) {
                    temp.add(i, temp.get(i + 1));
                    temp.remove(i + 2);
                    notSorted = true;
                }
            }
        }
        fighters = temp;
    }
    private static class Fighter{
        int health = 200;
        Coordinate location;
        int attackPower = 3;
        String type;
        Fighter(int x, int y, String type){
            this.location = new Coordinate(x, y);
            this.type = type;
            occupiedSpaces.add(this.location);
        }

        private void move(char direction){
            cave[this.location.x][this.location.y] = '.';
            char typeChar = Character.toUpperCase(type.toCharArray()[0]);

            if(direction == 'n'){
                cave[this.location.x][this.location.y] = typeChar;
                this.location.y--;
            }
            else if(direction == 'e'){
                cave[this.location.x][this.location.y] = typeChar;
                this.location.x++;
            }
            else if(direction == 's'){
                cave[this.location.x][this.location.y] = typeChar;
                this.location.y++;
            }
            else if(direction == 'w'){
                cave[this.location.x][this.location.y] = typeChar;
                this.location.x--;
            }
        }

        private void attack(Fighter enemy){
            if(enemy.type == this.type){
                System.out.println("THERE'S A MISTAKE IN THE FIGHTING. THE TEAMS ARE FIGHTING EACH OTHER!!!");
                System.exit(100);
            }
            enemy.health -= this.attackPower;
        }

    }
    private static class Coordinate{
        int x;
        int y;
        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }
        private boolean isTheSameAs(Coordinate c){
            return (this == c || (this.x == c.x) && (this.y == c.y));
        }
        private String toPrint(){
            return "(" + this.x + "," + this.y + ")";
        }
    }
}
