import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day25 {
    private static List<SpaceTimePoint> listOfPoints = new ArrayList<>();
    private static List<Constellation> listOfConstellations = new ArrayList<>();
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day25input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;

            while((input = br.readLine()) != null){
                String[] temp = input.split(",");
                int x = Integer.valueOf(temp[0]);
                int y = Integer.valueOf(temp[1]);
                int z = Integer.valueOf(temp[2]);
                int t = Integer.valueOf(temp[3]);
                listOfPoints.add(new SpaceTimePoint(x,y,z,t));
            }

            addPointsToConstellations();
            mergeConstellations();

            System.out.println(listOfConstellations.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeConstellations(){
        Set<Constellation> toRemove = new HashSet<>();

        for (Constellation c1 : listOfConstellations){
            if(toRemove.contains(c1)){
                continue;
            }
            for( Constellation c2 : listOfConstellations){
                if(c1.equals(c2) || toRemove.contains(c2)){
                    continue;
                }
                boolean merge = false;
                for(SpaceTimePoint s1 : c1.points){
                    for(SpaceTimePoint s2 : c2.points){
                        if(getManDistance(s1, s2) < 4){

                            merge = true;
                            break;
                        }
                    }
                }
                if(merge){
                    c1.merge(c2);
                    toRemove.add(c2);
                }
            }
        }
        listOfConstellations.removeAll(toRemove);
    }

    private static int getManDistance(SpaceTimePoint p1, SpaceTimePoint p2){
        int x1 = p1.x;
        int y1 = p1.y;
        int z1 = p1.z;
        int t1 = p1.t;
        int x2 = p2.x;
        int y2 = p2.y;
        int z2 = p2.z;
        int t2 = p2.t;

        // x1-x2 or x2-x1 doesn't matter the order as long as you do the same order as the y's
        int distance =
                Math.abs((x2 -x1))
                        + Math.abs((y2 -y1))
                        + Math.abs((z2 - z1))
                        + Math.abs((t2 - t1));

        return distance;
    }

    private static void addPointsToConstellations() {

        for(SpaceTimePoint s1 : listOfPoints){
            boolean added = false;
            for(Constellation c : listOfConstellations){
                if(c.addToConstellation(s1)){
                    added = true;
                }
            }
            if(!added){
                listOfConstellations.add(new Constellation(s1));
            }
        }
    }

    private static class SpaceTimePoint{
        int x;
        int y;
        int z;
        int t;

        private SpaceTimePoint(int x, int y, int z, int t) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.t = t;
        }

    }

    private static class Constellation{
        Set<SpaceTimePoint> points = new HashSet<>();

        private Constellation(SpaceTimePoint stp){
            points.add(stp);
        }

        private boolean addToConstellation(SpaceTimePoint stp){
            boolean added = false;
            List<SpaceTimePoint> toAdd = new ArrayList<>();
            for(SpaceTimePoint s : points){
                if(getManDistance(s, stp) < 4){
                    toAdd.add(stp);
                    added = true;
                }
            }
            points.addAll(toAdd);
            return added;
        }

        private void merge(Constellation c){
            this.points.addAll(c.points);
        }

    }
}