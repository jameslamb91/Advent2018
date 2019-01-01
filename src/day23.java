import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day23 {
    private static List<Nanobot> listOfBots = new ArrayList<>();
    private static int minX = Integer.MAX_VALUE;
    private static int maxX = Integer.MIN_VALUE;
    private static int minY = Integer.MAX_VALUE;
    private static int maxY = Integer.MIN_VALUE;
    private static int minZ = Integer.MAX_VALUE;
    private static int maxZ = Integer.MIN_VALUE;
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day23input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            while((input = br.readLine()) != null){
                String[] values = input.split(",");
                int x = Integer.valueOf(values[0].substring(values[0].indexOf('<') + 1));
                int y = Integer.valueOf(values[1]);
                int z = Integer.valueOf(values[2].substring(0,values[2].indexOf('>')));
                int r = Integer.valueOf(values[3].substring(values[3].indexOf('=') + 1));
                listOfBots.add(new Nanobot(x,y,z,r));

                if(x > maxX){
                    maxX = x;
                }
                if(x < minX){
                    minX = x;
                }
                if(y > maxY){
                    maxY = y;
                }
                if(y < minY){
                    minY = y;
                }
                if(z > maxZ){
                    maxZ = z;
                }
                if(z < minZ){
                    minZ = z;
                }


            }

//            int highestRadius = Integer.MIN_VALUE;
//            Nanobot strongest = new Nanobot();
//            for(Nanobot n : listOfBots){
//                if(n.scanRadius > highestRadius){
//                    strongest = n;
//                    highestRadius = n.scanRadius;
//                }
//                System.out.println("Checking for bots in range of " +
//                        n.x + ","+ n.y +","+ n.z + " with radius " + n.scanRadius);
//                for(Nanobot b : listOfBots){
//                    if(getManDistance(n.x, n.y, n.z, b.x,b.y,b.z) <= n.scanRadius){
//                        System.out.println("Bot in range at " + b.x + "," + b.y + "," + b.z);
//                        n.botsInRange.add(b);
//                    }
//                }
//            }

            int maxBotsInRange = Integer.MIN_VALUE;
            int result = Integer.MAX_VALUE;
            for(int x = minX; x < maxX; x++){
                System.out.println("Calculating x coordinate " + x + " out of " + maxX);
                for(int y = minY; y < maxY; y++){
                    System.out.println("Calculating y coordinate " + y + " out of " + maxY);

                    for(int z = minZ; z < maxZ; z++){
                        System.out.println("Calculating z coordinate " + z + " out of " + maxZ);

                        int botsInRange = 0;

                        for(Nanobot n : listOfBots){
                            if(getManDistance(x,y,z,n.x,n.y,n.z) <= n.scanRadius){
                                botsInRange++;
                            }
                        }

                        if(botsInRange > maxBotsInRange){
                            maxBotsInRange = botsInRange;
                            result = getManDistance(0,0,0,x,y,z);
                        }
                        else if(botsInRange == maxBotsInRange){
                            int temp = getManDistance(0,0,0,x,y,z);
                            if(temp < result){
                                result = temp;
                            }
                        }
                    }
                }
            }
//            System.out.println(strongest.botsInRange.size());
            System.out.println(result);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static int getManDistance(int x1, int y1, int z1, int x2, int y2, int z2){
        // x1-x2 or x2-x1 doesn't matter the order as long as you do the same order as the y's
        int distance = Math.abs((x2-x1)) + Math.abs((y2-y1)) + Math.abs((z2 - z1));

        return distance;
    }

    private static class Nanobot{
        int x;
        int y;
        int z;
        int scanRadius;
        List<Nanobot> botsInRange = new ArrayList<>();

        public Nanobot(){}

        public Nanobot(int x, int y, int z, int scanRadius) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.scanRadius = scanRadius;
        }
    }
}