import java.io.*;

public class day5 {

    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day5input");
        String input = "";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            input = br.readLine();
            System.out.println(input.length());
            int[] lengths = new int[26];

            for (char c : alphabet.toCharArray()) {
                lengths[alphabet.indexOf(c)] = reduceByOneLetter(input, c);
            }
            int min = Integer.MAX_VALUE;

            for(int i : lengths){
                if(i < min) {
                    min = i;
                }
            }
            System.out.println(min);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int reduceByOneLetter(String input, char c) {

        String match = Character.toString(c);
        String match2 = Character.toString(Character.toUpperCase(c));
        String result = input;
        result = result.replaceAll(match2, "");
        result = result.replaceAll(match, "");


        return react(result);
    }

    private static int react(String input) {

        String result = input;
        boolean foundMatch = false;
        do{
            foundMatch = false;
            for (int i = 0; i < result.length() - 1; i++) {
                if(result.charAt(i) == (result.charAt(i+1) - 32) || result.charAt(i) == (result.charAt( i+1) + 32)){
                    if(i >= result.length()-1) {
                        result = result.substring(0, i);
                    }else if(i == 0){
                        result = result.substring(i+2);
                    }else{
                        result = result.substring(0, i).concat(result.substring(i+2));
                    }
                    foundMatch = true;
                }
            }

        }while(foundMatch);

        System.out.println(result.length());
        return result.length();
    }
}
