import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class day4 {

    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day4inputSorted");

        HashMap<String, Integer> idToNumOfMinutesAsleep = new HashMap<>();
//        ArrayList<Integer> fellTimeFor2663 = new ArrayList<>();
//        ArrayList<Integer> wokeTimeFor2663 = new ArrayList<>();


        HashMap<String, int[]> frequency = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            String id = "";
            int fellAsleep = 0;
            int wokeUp = 0;
            while ((input = br.readLine()) != null) {
                if (input.indexOf('#') != -1) {
                    id = input.substring(input.indexOf('#') + 1, input.indexOf('#') + 5).trim();
                }
                if (input.contains("falls")) {

                    fellAsleep = Integer.parseInt(input.substring(input.indexOf(':') + 1, input.indexOf(']')));

//                    if(id.equals("2663")) {
//                        fellTimeFor2663.add(fellAsleep);
//                    }
                }
                if (input.contains("wakes")) {

                    wokeUp = Integer.parseInt(input.substring(input.indexOf(':') + 1, input.indexOf(']')));

//                    if(id.equals("2663")) {
//                        wokeTimeFor2663.add(wokeUp);
//                    }
                    if (idToNumOfMinutesAsleep.containsKey(id)) {
                        int sleptTotal = idToNumOfMinutesAsleep.get(id);


                        idToNumOfMinutesAsleep.put(id, sleptTotal + wokeUp - fellAsleep);
                        int[] minutes = frequency.get(id);

                        for(int i = fellAsleep; i < wokeUp; i++){

                            minutes[i] += 1;
                        }
                        frequency.put(id, minutes);

                    } else {
                        idToNumOfMinutesAsleep.put(id, wokeUp - fellAsleep);
                        int[] minutes = new int[60];

                        for(int i = fellAsleep; i < wokeUp; i++){

                            minutes[i] += 1;
                        }
                        frequency.put(id, minutes);
                        }
                    }


                }
//
//            String maxID = getMax(idToNumOfMinutesAsleep);
//
//            System.out.println(idToNumOfMinutesAsleep.get(maxID));
//
//            for(int i : fellTimeFor2663){
//                System.out.print(i + " | ");
//            }
//            System.out.println();
//            for(int i : wokeTimeFor2663){
//                System.out.print(i + " | ");
//            }
//            System.out.println();
//
//            System.out.println(idToNumOfMinutesAsleep.keySet().size());
            int max = 0;
            String name = " ";
            int maxIndex = 0;
            for(String s : frequency.keySet()){
                System.out.print("ID :" + s  + "; minutes: ");
                for(int i = 0; i < frequency.get(s).length; i++){

                    System.out.print(i +"|"+frequency.get(s)[i] + "; ");
                    if(frequency.get(s)[i] > max){
                        max = frequency.get(s)[i];
                        name = s;
                        maxIndex = i;
                    }
                }
                System.out.println();
            }
            System.out.println(name + " " +(maxIndex + 1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getMax(Map<String, Integer> map){
        int maxValue = 0;
        String maxId = "";
        for(String s : map.keySet()){
            if(map.get(s) > maxValue){
                maxId = s;
                maxValue = map.get(s);
            }
        }
        return maxId;
    }
}
