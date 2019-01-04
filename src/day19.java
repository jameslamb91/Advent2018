import java.io.*;
import java.util.ArrayList;

public class day19 {
    private static int pointer;
    private static int pointerRegister = 0;
    private static ArrayList<String[]> instructions = new ArrayList<>();
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day19input");
        try {
            parseInput(file);
            System.out.println(performRules());
            part2();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void part2() {
        //10551330
        int sum = 0;
        int target = 10551330;
        for(int i = 1; i <= 10551330; i++){
            if(target % i == 0){
                sum += i;
            }
        }
        System.out.println(sum);
    }
    private static int performRules() {
        int[] result = {0,0,0,0,0,0};
        pointer = result[pointerRegister];

        while(pointer < instructions.size() && pointer >= 0){
            StringBuilder sb = new StringBuilder();
            for(int i : result){
                sb.append("[" + i + "]");
            }
            sb.append(" : " + (pointer + 2) + " - ");
            String[] instruction = instructions.get(pointer);
            String operation = instruction[0];
            int valueA = Integer.valueOf(instruction[1]);
            int valueB = Integer.valueOf(instruction[2]);
            int valueC = Integer.valueOf(instruction[3]);

            if(operation.equals("addr")){
                //addr
                sb.append("adding register " + valueA + " and register " +valueB);
                result[valueC] = result[valueA] + result[valueB];
            }
            else if(operation.equals("eqri")){
                //eqri
                sb.append("checking if register " + valueA + " is equal to value " + valueB);
                result[valueC] = result[valueA] == valueB ? 1 : 0;
            }
            else if(operation.equals("eqir")){
                //eqir
                sb.append("checking if value " + valueA + " is equal to register " + valueB);
                result[valueC] = valueA == result[valueB] ? 1 : 0;
            }
            else if(operation.equals("eqrr")){
                //eqrr
                sb.append("checking if register " + valueA + " is equal to register " + valueB);
                result[valueC] = result[valueA] == result[valueB] ? 1 : 0;
            }
            else if(operation.equals("gtir")){
                //gtir
                sb.append("checking if value " + valueA + " is > register " + valueB);
                result[valueC] = valueA > result[valueB] ? 1 : 0;
            }
            else if(operation.equals("addi")){
                //addi
                sb.append("adding register " + valueA + " and value " + valueB);
                result[valueC] = result[valueA] + valueB;
            }
            else if(operation.equals("banr")){
                //banr
                sb.append("band on register " + valueA + " and register " + valueB);
                result[valueC] = result[valueA] & result[valueB];
            }
            else if(operation.equals("gtri")){
                //gtri
                sb.append("checking if register " + valueA + "is > value " + valueB);
                result[valueC] = result[valueA] > valueB ? 1 : 0;
            }
            else if(operation.equals("bori")){
                //bori
                sb.append("bor on register " + valueA + " and value " + valueB);
                result[valueC] = result[valueA] | valueB;
            }
            else if(operation.equals("muli")){
                //muli
                sb.append("multiply register " + valueA + " and value " + valueB);
                result[valueC] = result[valueA] * valueB;
            }
            else if(operation.equals("seti")){
                //seti
                sb.append("save value " + valueA);
                result[valueC] = valueA;
            }
            else if(operation.equals("gtrr")){
                //gtrr
                sb.append("checking if register " + valueA + " is > register " + valueB);
                result[valueC] = result[valueA] > result[valueB] ? 1 : 0;
            }
            else if(operation.equals("setr")){
                //setr
                sb.append("save register " + valueA);
                result[valueC] = result[valueA];
            }
            else if(operation.equals("borr")){
                //borr
                sb.append("bor on register " + valueA + " and register " + valueB);
                result[valueC] = result[valueA] | result[valueB];
            }
            else if(operation.equals("mulr")){
                //mulr
                sb.append("multiply register " + valueA + " and register " + valueB);
                result[valueC] = result[valueA] * result[valueB];
            }
            else if(operation.equals("bani")){
                //bani
                sb.append("band on register " + valueA + " and value " + valueB);
                result[valueC] = result[valueA] & valueB;
            }
            sb.append(". Saving to register " + valueC);
            System.out.println(sb.toString());
//            if(valueC == 4){
//                System.out.println(result[4]);
//            }
            pointer = ++result[pointerRegister];
        }

        return result[0];
    }
    private static void parseInput(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        while((input = br.readLine()) != null){

            if(input.startsWith("#")){
                pointerRegister = Integer.valueOf(input.split(" ")[1]);
            }
            else{
                instructions.add(input.split(" "));
            }
        }
    }
}