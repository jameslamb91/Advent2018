import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Time;

public class day11 {
    private final static int SERIAL_NUMBER = 1955;
    private static int[][] grid = new int[300][300];
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        for(int i = 0; i < grid.length-1; i++){
            for(int j = 0; j < grid.length-1; j++){
                grid[i][j] = getPowerLevel(i+1, j+1);
            }
        }

        int maxX = 0;
        int maxY = 0;
        int maxSize = 0;
        int maxCombinedPower = Integer.MIN_VALUE;

        for(int n = 1; n < grid.length; n++) {
//            System.out.print(n + " | ");
            for (int i = 0; i < grid.length - 1; i++) {
                for (int j = 0; j < grid.length - 1; j++) {
                    int temp = getPowerLevelNxN(i, j, n);
//                    System.out.print(grid[i][j] + " |");
                    if (temp > maxCombinedPower) {
                        maxX = i + 1;
                        maxY = j + 1;
                        maxSize = n;
                        maxCombinedPower = temp;
                    }
                }
//                System.out.println();
            }
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("("+maxX+","+maxY+","+maxSize+") = " + maxCombinedPower + ". Time: " + (endTime - startTime));


    }

    private static int getPowerLevel(int x, int y){
        int rackId = x + 10;
        int power = rackId * y;
        int increasePower = power + SERIAL_NUMBER;
        int finalPowerLevel = increasePower * rackId;
        int removedThousands = finalPowerLevel % 1000;
        int hundredsPlace = removedThousands / 100;

        int minus5 = hundredsPlace - 5;

        return minus5;
    }

    private static int getPowerLevelNxN(int x, int y, int n){
        int result = 0;
        if(x > (grid.length - n) || y > (grid.length - n)){
            return Integer.MIN_VALUE;
        }
        for(int i = x; i < x+n; i++){
            for(int j = y; j < y+n; j++){
                if(grid[i][j]  == Integer.MIN_VALUE){
                    grid[i][j] = getPowerLevel(i+1, j+1);
                    result += grid[i][j];
                }
                else{
                    result += grid[i][j];
                }
            }
        }
        return result;
    }
}