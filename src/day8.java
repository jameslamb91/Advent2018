import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day8 {
    static String input;
    static String[] splitInput;
    static ArrayList<Integer> inputAsInt = new ArrayList<>();
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

            System.out.println(part1(inputAsInt));

            // re-initialize list
            for(String s : splitInput){
                inputAsInt.add(Integer.valueOf(s));
            }
            System.out.println(part2(inputAsInt));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int part2(List<Integer> list){
        return getValueOfNode(list);
    }

    private static int part1(List<Integer> list){
        return getMetaData(list);
    }

    private static int getValueOfNode(List<Integer> node){
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
        else{
            int[] childValues = new int[numOfChildren];
            int[] metaDataValues = new int[numOfMetadata];

            for (int i = 0; i < numOfChildren; i++){
                childValues[i] = getValueOfNode(node);
            }

            for (int i = 0; i < numOfMetadata; i++){
                metaDataValues[i] = node.get(0);
                node.remove(0);
            }

            for(int i : metaDataValues){
                if(i == 0){
                    continue;
                }
                if(i-1 < childValues.length){
                    result += childValues[i-1];
                }
            }

        }

        return result;
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