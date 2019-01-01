import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class day7 {
    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static void main(String[] args) {
        HashMap<String, String> mapOfDependencies = new HashMap<>();
        HashMap<String, Integer> mapOfTime = new HashMap<>();
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day7input");
        String input = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((input = br.readLine()) != null) {
                String first = String.valueOf(input.charAt(5));
                String last = String.valueOf(input.charAt(36));
                if(mapOfDependencies.containsKey(first)){
                    mapOfDependencies.put(first, sort(mapOfDependencies.get(first).concat(last)));
                }
                else {
                    mapOfDependencies.put(first, last);
                }

                if(!mapOfDependencies.containsKey(last)){
                    mapOfDependencies.put(last, "");
                }
            }
            String allSteps = "";
            for (String s : mapOfDependencies.keySet()){
                allSteps += (s);
            }
            String work = "";
            String workQueue = "";
            int time = 0;

            for (char c : allSteps.toCharArray()) {
                mapOfTime.put(String.valueOf(c), 61 + alphabet.indexOf(c));
            }


//            mapOfDependencies.remove(String.valueOf(location));
//            allSteps = removeCharFromString(allSteps, location);

            while(!allSteps.isEmpty()){

                // put all available jobs in workQueue
                for (char c : allSteps.toCharArray()) {
                    String key = String.valueOf(c);
                    if (!isKeyInValues(mapOfDependencies, key) && !workQueue.contains(key)) {
                        workQueue += c;
                    }
                }

                // get first 5 from workQueue
                String jobsInProgress = workQueue.length() > 5 ? workQueue.substring(0, 4) : workQueue;
                String jobsToEnd = "";

                // take one unit of time away from each job in progress
                for(char c : jobsInProgress.toCharArray()){
                    String key = String.valueOf(c);
                    if (mapOfTime.containsKey(key)){
                        mapOfTime.put(key, mapOfTime.get(key) - 1);
                    }
                    if(mapOfTime.get(key) == 0){
                        jobsToEnd += c;
                    }
                }
                // increment time by one
                time++;

                // check if any jobs finished, and end them.
                if(jobsToEnd.length() > 0){
                    for(char c : jobsToEnd.toCharArray()){
                        mapOfDependencies.remove(String.valueOf(c));
                        allSteps = removeCharFromString(allSteps, c);
                        mapOfTime.remove(String.valueOf(c));
                        jobsInProgress = removeCharFromString(jobsInProgress, c);
                        workQueue = removeCharFromString(workQueue, c);
                        work += c;
                    }
                }
            }

            System.out.println(work);
            System.out.println(time);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String sort(String input){
        char[] temp = input.toCharArray();
        Arrays.sort(temp);
        return String.valueOf(temp);
    }

    private static boolean isKeyInValues(HashMap<String, String> map, String key){

        for(String temp : map.keySet()){
           if (map.get(temp).indexOf(key) != -1){
               return true;
           }
        }
        return false;
    }

    private static String removeCharFromString(String s, char c){
        String toReturn = "";
        for (char d : s.toCharArray()){
            if(d != c){
                toReturn += String.valueOf(d);
            }
        }
        return toReturn;
    }
}
