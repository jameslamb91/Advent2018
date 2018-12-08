import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class day8 {
    static String input;
    static int sum = 0;
    static Stack<Integer> stack = new Stack<>();
    static String[] splitInput;
    static ArrayList<Integer> inputAsInt = new ArrayList<>();
    static String[] listOfMetadata;
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day8input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            input = br.readLine();

            // split values on the space char
            splitInput = input.split(" ");

            // set up to store all values as integers
            for(String s : splitInput){
                inputAsInt.add(Integer.valueOf(s));
            }

            sum += getMetaData(inputAsInt);

            System.out.println(sum);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getMetaData(List<Integer> node){
        int result = 0;
        int numOfChildren = node.get(0);
        int numOfMetadata = node.get(1);

        node.remove(1);
        node.remove(0);
        // termination case
        if (numOfChildren == 0){
            for (int i = 0; i < numOfMetadata; i++){
                result += node.get(0);
                node.remove(0);
            }
        }

        else {

            for (int i = 0; i < numOfChildren; i++){
                result += getMetaData(node);
            }

            for (int i = 0; i < numOfMetadata; i++){
                result += node.get(0);
                node.remove(0);
            }
        }
        return result;
    }

}