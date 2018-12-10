import sun.jvm.hotspot.types.PointerType;

import java.io.*;
import java.util.ArrayList;

public class day10 {
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day10input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            ArrayList<Point> listOfPoints = new ArrayList<>();
            while((input = br.readLine()) != null){
                String[] splitInput = input.split(" v");
                int xPos = Integer.valueOf(splitInput[0].substring(splitInput[0].indexOf('<')+1, splitInput[0].indexOf(',')).trim());
                int yPos = Integer.valueOf(splitInput[0].substring(splitInput[0].indexOf(',')+1, splitInput[0].indexOf('>')).trim());
                int xVel = Integer.valueOf(splitInput[1].substring(splitInput[1].indexOf('<')+1, splitInput[1].indexOf(',')).trim());
                int yVel = Integer.valueOf(splitInput[1].substring(splitInput[1].indexOf(',')+1, splitInput[1].indexOf('>')).trim());
                listOfPoints.add(new Point(xPos, yPos, xVel, yVel));
            }
            int maxDistance = 300;
            int count = 0;
            while (true){

                if(maxDistance < 100){
                    System.out.println("Below graph took " + count + " seconds");
                    graphPoints(listOfPoints);
                }
                count++;
                for(Point p : listOfPoints){

                    p.move();
                }

                maxDistance = getMaxDistanceBetweenPoints(listOfPoints);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Point {
        int xPos;
        int yPos;
        int xVel;
        int yVel;

        private Point(int xPos, int yPos, int xVel, int yVel){
            this.xPos = xPos;
            this.yPos = yPos;
            this.xVel = xVel;
            this.yVel = yVel;
        }

        private void move(){
            xPos += xVel;
            yPos += yVel;
        }
    }

    private static int getMaxDistanceBetweenPoints(ArrayList<Point> pList){
        int maxDistance = Integer.MIN_VALUE;

        for(Point p1 : pList){
            for (Point p2 : pList){
                if(p1.equals(p2)){
                    continue;
                }
                int temp = Math.abs((p1.xPos - p2.xPos) + (p1.yPos - p2.yPos));
                if (temp > maxDistance){
                    maxDistance = temp;
                }
            }

        }

         return maxDistance;
    }

    private static void graphPoints(ArrayList<Point> pList){
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point p : pList){
            if(p.xPos < minX){
                minX = p.xPos;
            }
            if(p.xPos > maxX){
                maxX = p.xPos;
            }
            if(p.yPos < minY){
                minY = p.yPos;
            }
            if(p.yPos > maxY){
                maxY = p.yPos;
            }

        }
        System.out.println("GRAPH STARTED");
        for(int i = minY; i < maxY +1; i++){
            for (int j = minX; j < maxX + 1; j++){
                boolean match = false;
                for(Point p : pList){
                    if(p.xPos == j && p.yPos == i){
                        match = true;
                    }
                }
                if(match){
                    System.out.print("#");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("GRAPH ENDED");
    }
}