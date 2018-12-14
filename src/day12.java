import java.io.*;
import java.util.HashMap;

public class day12 {
    private static HashMap<String, String> rules = new HashMap<>();
    private static int indexOfPot0 = 0;
    private static String prevGen = "";
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day12input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            prevGen = br.readLine();
            String input;
            while((input = br.readLine()) != null){
                rules.put(input.substring(0, 5), input.substring(9));
            }

            System.out.println("after 20 generations : " + part1());

            System.out.println("after 5B generations : " + part2());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static long part2(){
        long totalOfPots = 0L;

//        printed out 2k generations, 174957, and found that it only increases by 1740 per 20 generations after that.
//        for(int i = 0; i < 100; i++){
//            totalOfPots = Long.valueOf(part1());
//            System.out.println("after " +((i * 20) + 20) +" generations : " + totalOfPots);
//
//        }

        // sum of 2k generations
        totalOfPots = 174957;

        // 50B gens - minus the 2k already done,
        totalOfPots += (50000000000L - 2000) / 20 * 1740;

        return totalOfPots;
    }
    private static int part1() {
        for(int i = 0; i < 20; i++){
            String nextGen = "";
            prevGen = "....." + prevGen + "...";
            indexOfPot0 += 3;
//            System.out.println(prevGen);

            for(int j = 2; j < prevGen.length() - 2; j++){
                String test;
                if(j > prevGen.length() - 3){
                    test = prevGen.substring(j-2, j);
                }
                else{
                    test = prevGen.substring(j-2, j+3);
                }
                nextGen += getNextGen(test);
            }
            prevGen = nextGen;
        }
//        System.out.println(prevGen);
//        System.out.println(indexOfPot0);

        return getTotalPots();
    }

    private static int getTotalPots() {
        int totalPots = 0;

        for(int i = 0; i< prevGen.length(); i++){
            if(prevGen.charAt(i) == '#'){
                totalPots += i - indexOfPot0;
            }
        }


        return totalPots;
    }

    private static String getNextGen(String test){
        while(test.length() < 5){
            test += ".";
        }
        if(rules.containsKey(test)){
            return rules.get(test);
        }
        return ".";
    }

    private static String reverseString(String toReverse){
        String reversed = "";

        for(int i = 0; i<toReverse.length(); i++){
            reversed = toReverse.charAt(i) + reversed;
        }
        return reversed;
    }
}