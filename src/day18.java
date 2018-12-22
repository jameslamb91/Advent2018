import java.io.*;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;

public class day18 {
    static Character[][] field = new Character[50][50];
    static ArrayList<String> previousValues = new ArrayList<>();
    static HashMap<String, Integer> oldFields = new HashMap<>();
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day18input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String input = "";
            int y = 0;
            while((input = br.readLine()) != null){
                int x = 0;
                for(char c : input.toCharArray()){
                    field[x][y] = c;
                    x++;
                }
                y++;
            }
            for(int i = 0; i < 10000; i++){
                int temp = getValue();

                previousValues.add(fieldToText());
                if(oldFields.containsKey(fieldToText())){
                    break;
                }
                oldFields.put(fieldToText(), temp);
                oneMinute();
            }

            int startOfRepeats = 0;
            int lengthOfRepeats = 0;
            for(int i = previousValues.size() - 2; i >= 0; i--){
                if(previousValues.get(previousValues.size() -1).equals(previousValues.get(i))){
                    startOfRepeats = i;
                    lengthOfRepeats = previousValues.size() - 1 - i;
                    break;
                }
            }

            System.out.println("Start of repeats: " + startOfRepeats + ". Number in cycle: " + lengthOfRepeats);

            int indexOfResult = startOfRepeats + ((1000000000 - startOfRepeats) % lengthOfRepeats);

            System.out.println(oldFields.get(previousValues.get(indexOfResult)));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String fieldToText(){
        StringBuilder sb = new StringBuilder();
        for(Character[] c : field){
            for(Character c1: c){
                sb.append(c1);
            }
        }
        return sb.toString();
    }
    private static int getValue() {
        int numOfTrees = 0;
        int numOfLumberyards = 0;
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                if(field[j][i] == '|'){
                    numOfTrees++;
                }
                else if(field[j][i] == '#'){
                    numOfLumberyards++;
                }
            }
        }
        return numOfLumberyards*numOfTrees;
    }
    private static void oneMinute(){
        Character[][] temp = new Character[50][50];
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field.length; j++){
                char c = field[j][i];
                int numOfTrees = 0;
                int numOfLumberyards = 0;
                for(char d : getAdjacentChars(j, i)) {

                    if (d == '|') {
                        numOfTrees++;
                    }
                    else if (d == '#'){
                        numOfLumberyards++;
                    }
                }
                if(c == '.'){
                    if(numOfTrees > 2){
                        temp[j][i] = '|';
                    }
                    else{
                        temp[j][i] = '.';
                    }
                }
                else if(c == '#'){
                    if(numOfTrees > 0 && numOfLumberyards > 0){
                        temp[j][i] = '#';
                    }
                    else{
                        temp[j][i] = '.';
                    }
                }
                else if(c == '|'){
                    if(numOfLumberyards > 2){
                        temp[j][i] = '#';
                    }
                    else{
                        temp[j][i] = '|';
                    }
                }
            }
        }
        field = temp;
    }
    private static char[] getAdjacentChars(int x, int y) {
        final int maxIndex = field.length - 1;
        StringBuilder adjacent = new StringBuilder();
        if (x > 0){
            adjacent.append(field[x-1][y]);
        }
        if(y > 0){
            adjacent.append(field[x][y-1]);
        }
        if(x < maxIndex){
            adjacent.append(field[x+1][y]);
        }
        if(y < maxIndex){
            adjacent.append(field[x][y+1]);
        }
        if(x > 0 && y > 0){
            adjacent.append(field[x-1][y-1]);
        }
        if(x > 0 && y < maxIndex){
            adjacent.append(field[x-1][y+1]);
        }
        if(x < maxIndex && y < maxIndex){
            adjacent.append(field[x+1][y+1]);
        }
        if(y > 0 && x < maxIndex){
            adjacent.append(field[x+1][y-1]);
        }
        char[] toReturn = adjacent.toString().trim().toCharArray();
        return toReturn;
    }
}