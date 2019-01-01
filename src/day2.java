import java.io.*;
import java.util.ArrayList;

public class day2 {
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day2input");
        ArrayList<String> twoList = new ArrayList<>();
        ArrayList<String> threeList = new ArrayList<>();
        ArrayList<String> inputList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            while ((input = br.readLine()) != null){
                inputList.add(input);

//                for (char location : input.toCharArray()){
//                    int num = (numOfChar(location, input));
//                    if(num == 2 && !twoList.contains(input)){
//                        twoList.add(input);
//                    }
//                    if(num == 3 && !threeList.contains(input)){
//                        threeList.add(input);
//                    }
//                }

            }

            for (int i = 1; i < inputList.size(); i++){
                for(int j = 0; j < inputList.size(); j++)
                    numInCommon(inputList.get(i-1).toCharArray(), inputList.get(j).toCharArray());
            }

            //System.out.println(threeList.size() * twoList.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int numOfChar(char c, String s) {
        int result = 0;
        for (char d : s.toCharArray()){
            if(d == c){
                result++;
            }
        }
        return result;
    }

    private static void numInCommon(char[] c, char[] d){

        int count = 0;
        for(int i = 0; i < c.length; i++){

            if(d[i] == c[i]){
                count++;
            }
        }
        if (count == c.length -1){
            System.out.println(c);
            System.out.println(d);
        }

    }
}
