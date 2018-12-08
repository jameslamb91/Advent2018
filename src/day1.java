import java.io.*;
import java.util.ArrayList;
public class day1 {
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day1input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<Integer> intList = new ArrayList<>();
            int result = 0;
            String line;
            while((line = br.readLine()) != null){
                switch (line.charAt(0)){
                    case '-': result -= Integer.parseInt(line.substring(1));
                        break;
                    case '+': result += Integer.parseInt(line.substring(1));
                        break;
                }
                if(intList.contains(result)){
                    break;
                }
                else{
                    intList.add(result);
                }
            }
            System.out.println(result + " " + intList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}