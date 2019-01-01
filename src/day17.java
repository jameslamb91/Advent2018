import java.io.*;
import java.util.ArrayList;

public class day17 {
    static int smallestY = Integer.MAX_VALUE;
    static int largestY = Integer.MIN_VALUE;
    static int smallestX = Integer.MAX_VALUE;
    static int largestX = Integer.MIN_VALUE;
    static char[][] scan;
    static ArrayList<String> inputList = new ArrayList<>();
    static int xOfSpring;
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day17input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String input;
            while((input = br.readLine()) != null){
                inputList.add(input);
            }

            getExtremeValues();

            populateScan();


            fillFloorBelowPoint(xOfSpring);
            printScan();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int fillFloorBelowPoint(int x) {
        int y = 0;

        for(int i = 1; i < largestY; i++) {
            if(scan[x][i+1] == '#' || scan[x][i+1] == '~'){
                y = i;
                break;
            }
            else{
                scan[x][i] = '|';
            }
        }


        int xOfOverflow = willOverFlow(x, y);

        for(int i = x; i < largestX - smallestX; i++){
            if( i == xOfOverflow){
                fillFloorBelowPoint(xOfOverflow, y);
            }
            if(scan[i][y] != '#'){
                scan[i][y] = '~';
            }
            else{
                break;
            }
        }

        xOfOverflow = willOverFlow(x, y);
        for(int i = x; i > 0; i--){
            if( i == xOfOverflow){
                return fillFloorBelowPoint(xOfOverflow, y);
            }
            if(scan[i][y] != '#'){
                scan[i][y] = '~';
            }
            else{
                break;
            }
        }
        printScan();
        return fillFloorBelowPoint(x);
    }
    private static int fillFloorBelowPoint(int x, int startY) {
        int y = 0;
        for(int i = startY; i < largestY; i++) {
            if(scan[x][i+1] == '#' || scan[x][i+1] == '~'){
                y = i;
                break;
            }
            else{
                scan[x][i] = '|';
            }
        }


        int xOfOverflow = willOverFlow(x, y);

        for(int i = x; i < largestX - smallestX; i++){
            if( i == xOfOverflow){
                fillFloorBelowPoint(xOfOverflow, y);
            }
            if(scan[i][y] != '#'){
                scan[i][y] = '~';
            }
            else{
                break;
            }
        }

        xOfOverflow = willOverFlow(x, y);
        for(int i = x; i > 0; i--){
            if( i == xOfOverflow){
                fillFloorBelowPoint(xOfOverflow, y);
            }
            if(scan[i][y] != '#'){
                scan[i][y] = '~';
            }
            else{
                break;
            }
        }

        return xOfOverflow == -1 ? x : xOfOverflow;
    }

    private static int willOverFlow(int x, int y){

        for(int i = x; i < largestX - smallestX + 1; i++){
            if(scan[i][y] != '#'){
                if(scan[i][y+1] == '.'){
                    return i;
                }
            }
            else{
                break;
            }
        }
        for(int i = x; i > 0; i--){
            if(scan[i][y] != '#') {
                if (scan[i][y + 1] == '.') {
                    return i;
                }
            }
            else{
                break;
            }
        }

        return -1;
    }
    private static void printScan() {
        for(int i = 0; i <= largestY; i ++){
            for (int j = 0; j <= (largestX - smallestX + 1); j++){
                System.out.print(scan[j][i]);
            }
            System.out.println();
        }
    }

    private static void populateScan() {
        scan = new char[largestX - smallestX + 2][largestY + 1];
        for(int i = 0; i <= largestY; i++){
            for (int j = 0; j <= largestX - smallestX + 1; j++){
                scan[j][i] = '.';
            }
        }
        xOfSpring = 500 - smallestX + 1;
        scan[xOfSpring][0] = '+';

        for(String s : inputList){
            String[] temp = s.split(", ");
            if(s.charAt(0) == 'x'){
                int x = Integer.valueOf(temp[0].substring(temp[0].indexOf('=') + 1)) - smallestX + 1;
                int minY = Integer.valueOf(temp[1].substring(temp[1].indexOf('=') + 1, temp[1].indexOf('.')));
                int maxY = Integer.valueOf(temp[1].substring(temp[1].indexOf('.') + 2));

                for(int i = minY; i <= maxY; i++){
                    scan[x][i] = '#';
                }
            }
            else{
                int y = Integer.valueOf(temp[0].substring(temp[0].indexOf('=') + 1));
                int minX = Integer.valueOf(temp[1].substring(temp[1].indexOf('=') + 1, temp[1].indexOf('.'))) - smallestX + 1;
                int maxX = Integer.valueOf(temp[1].substring(temp[1].indexOf('.') + 2)) - smallestX + 1;

                for(int i = minX; i <= maxX; i++){
                    scan[i][y] = '#';
                }
            }
        }
    }

    private static void getExtremeValues() {
        for(String s : inputList){
            String[] temp = s.split(", ");
            if(s.charAt(0) == 'x'){
                int x = Integer.valueOf(temp[0].substring(temp[0].indexOf('=') + 1));
                int minY = Integer.valueOf(temp[1].substring(temp[1].indexOf('=') + 1, temp[1].indexOf('.')));
                int maxY = Integer.valueOf(temp[1].substring(temp[1].indexOf('.') + 2));

                if(minY < smallestY){
                    smallestY = minY;
                }
                if(maxY > largestY){
                    largestY = maxY;
                }
                if(x > largestX){
                    largestX = x;
                }if(x < smallestX){
                    smallestX = x;
                }
            }
            else{
                int y = Integer.valueOf(temp[0].substring(temp[0].indexOf('=') + 1));
                int minX = Integer.valueOf(temp[1].substring(temp[1].indexOf('=') + 1, temp[1].indexOf('.')));
                int maxX = Integer.valueOf(temp[1].substring(temp[1].indexOf('.') + 2));

                if(minX < smallestX){
                    smallestX = minX;
                }
                if(maxX > largestX){
                    largestX = maxX;
                }
                if(y > largestY){
                    largestY = y;
                }if(y < smallestY){
                    smallestY = y;
                }
            }
        }
    }

}
