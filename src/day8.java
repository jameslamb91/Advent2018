import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day8 {
    static String input;
    static int sum = 0;
    static String[] splitInput;
    static ArrayList<Integer> inputAsInt = new ArrayList<>();
    static int value = 0;
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

            sum += getMetaDataSum(inputAsInt);
            System.out.println(sum);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getMetaDataSum(List<Integer> node){
        int result = 0;
        int numOfChildren = node.get(0);
        int numOfMetadata = node.get(1);


        // part 2
        List<Integer> listOfChildrenToCount = new ArrayList<>();
        HashMap<Integer, Integer> mapOfChildren = new HashMap<>();

        for(int i : getListOfMetadata(node, numOfMetadata)){
            if(i <= numOfChildren && i != 0){
                listOfChildrenToCount.add(i);
            }
        }
        //

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

                // part 1
                //result += getMetaDataSum(node);
                //

                // part 2
                mapOfChildren.put(i, getMetaDataSum(node));
            }

            for (int i : listOfChildrenToCount) {
                if (mapOfChildren.containsKey(i-1)){
                    result += mapOfChildren.get(i-1);
                }
            }
            //

//            part 1
//            for (int i = 0; i < numOfMetadata; i++){
//                result += node.get(0);
//                node.remove(0);
//            }
        }
        return result;
    }

    //
    // all below methods are only necesarry for part 2
    //
    private static List<Integer> getListOfMetadata(List<Integer> list, int numOfMetadata){

        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < numOfMetadata; i++){
            result.add(list.get(list.size()-(1+i)));
        }
        return result;
    }

    private static int countOccurancesAndRemove(List<Integer> list, int toCheck){
        int count = 0;
        for (int i : list){
            if(i == toCheck){
                count++;
            }
        }
        for(int i = 0; i < count; i++){
//            list.remove(list.indexOf(toCheck));
        }
        return count;
    }

}