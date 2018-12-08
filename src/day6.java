
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class day6 {
    private static ArrayList<String> coordinates = new ArrayList<>();
    private static String[][] grid = new String[400][400];
    private static int countLowerThan10k = 0;
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day6input");
        String input = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((input = br.readLine()) != null) {
                coordinates.add(input);
            }

            for (int i = 0; i < 400; i++){
                for(int j = 0; j < 400; j++){
                    if(getSumOfManDis(j, i) < 10000){
                        countLowerThan10k++;
                    }
                    int index = getMinManDisCoor(j, i);
                    grid[j][i] = (index == -1 ? "." : String.valueOf(index));
                    System.out.print(grid[j][i] + " | ");
                }
                System.out.println();
            }
            System.out.println(countLowerThan10k);
//            System.out.println(getMaxArea());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getManDistance(int x1, int y1, int x2, int y2){
        // x1-x2 or x2-x1 doesn't matter the order as long as you do the same order as the y's
        int distance = Math.abs((x2-x1)) + Math.abs((y2-y1));

        return distance;
    }

    private static int getMinManDisCoor(int x1, int y1){
        int min = Integer.MAX_VALUE;
        int minIndex = 0;
        for(String s : coordinates){
            int x2 = Integer.parseInt(s.substring(0, s.indexOf(',')));
            int y2 = Integer.parseInt(s.substring(s.indexOf(' ') +1));
            int temp = getManDistance(x1, y1, x2, y2);
            if (temp < min) {
                min = temp;
                minIndex = coordinates.indexOf(s);
            }else if (temp == min){
                minIndex = -1;
            }
        }
        return minIndex;
    }

    private static int getMaxArea(){

        int max = Integer.MIN_VALUE;
        HashMap<String, Integer> area = new HashMap<>();
        for (int i = 0; i < 400; i++){
            for(int j = 0; j < 400; j++){
                if(area.containsKey(grid[j][i])){
                    area.put(grid[j][i], area.get(grid[j][i]) + 1);
                }else{
                    area.put(grid[j][i], 1);
                }
            }
        }

        removeInfiniteAreas(area);

        for(String s : area.keySet()){
            if(area.get(s) > max){
                max = area.get(s);
            }
        }
        return max;
    }

    private static void removeInfiniteAreas(HashMap<String, Integer> area) {

        if(area.containsKey(".")){
            area.remove(".");
        }
        for(int i = 0; i < 400; i++){
            if (area.containsKey(grid[0][i])) {
                area.remove(grid[0][i]);
            }
        }
        for(int i = 0; i < 400; i++){
            if (area.containsKey(grid[i][0])) {
                area.remove(grid[i][0]);
            }
        }
        for(int i = 0; i < 400; i++){
            if (area.containsKey(grid[399][i])) {
                area.remove(grid[399][i]);
            }
        }
        for(int i = 0; i < 400; i++){
            if (area.containsKey(grid[i][399])) {
                area.remove(grid[i][399]);
            }
        }
    }
    private static int getSumOfManDis(int x1, int y1){
        int sum = 0;
        for(String s : coordinates){
            int x2 = Integer.parseInt(s.substring(0, s.indexOf(',')));
            int y2 = Integer.parseInt(s.substring(s.indexOf(' ') +1));
            sum += getManDistance(x1, y1, x2, y2);
        }
        return sum;
    }
}
