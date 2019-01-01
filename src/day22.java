import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class day22 {
    static int targetX;
    static int targetY;
    static int depth;
    static HashMap<Point, Integer> cacheOfErosionLevel = new HashMap<>();
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day22input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            while((input = br.readLine()) != null){
                if(input.contains("depth")){
                    depth = Integer.valueOf(input.substring(input.indexOf(' ') + 1));
                }
                else{
                    targetX = Integer.valueOf(input.substring(input.indexOf(' ') + 1, input.indexOf(',')));
                    targetY = Integer.valueOf(input.substring(input.indexOf(',') + 1));
                }
            }
//            cave = new char[targetX][targetY];
            int riskLevel = 0;

            for(int i = 0; i <= targetY; i++){
                for(int j = 0; j <= targetX; j++){
                    int temp = getErosionLevel(j, i) % 3;
                    riskLevel += temp;
//                    System.out.println("(" + j + "," + i + ") has risk level of " + temp);
                }
            }
            System.out.println(riskLevel);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getGeologicIndex(int x, int y){
        if(x == 0 && y == 0){
            return 0;
        }
        if(x == targetX && y == targetY){
            return 0;
        }
        if(y == 0){
            return x * 16807;
        }
        if(x == 0){
            return y * 48271;
        }
        return getErosionLevel(x-1, y) * getErosionLevel(x, y-1);
    }

    private static int getErosionLevel(int x, int y){
        Point p = new Point(x, y);
        if(cacheOfErosionLevel.containsKey(p)){
            return cacheOfErosionLevel.get(p);
        }
        else{
            int erosionLevel = (getGeologicIndex(x, y) + depth) % 20183;
            cacheOfErosionLevel.put(p, erosionLevel);
            return erosionLevel;

        }
    }

    private static class Point{
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {

            return Objects.hash(x, y);
        }
    }
}