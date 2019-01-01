import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class day16 {
    static ArrayList<int[]> beforeList = new ArrayList<>();
    static ArrayList<int[]> operationList = new ArrayList<>();
    static ArrayList<int[]> afterList = new ArrayList<>();
    static ArrayList<int[]> part2List = new ArrayList<>();

    static int[] tester = new int[4];
    static HashMap<Integer, Set<String>> mapOfRuleCountPerOpCode = new HashMap<>();

    public static void main(String[] args) {
        File file1 = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day16part1input");
        File file2 = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day16part2input");
        try {
            parseInputPart1(file1);

            System.out.println(checkRules());

            parseInputPart2(file2);

            System.out.println(performRules());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseInputPart2(File file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        while ((input = br.readLine()) != null) {

            int[] addToPart2List = new int[4];
            String[] directionsArray = input.split(" ");
            for (int i = 0; i < 4; i++){
                addToPart2List[i] = Integer.parseInt(directionsArray[i]);
            }
            part2List.add(addToPart2List);
        }
    }
    private static void parseInputPart1(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String input;
        while ((input = br.readLine()) != null) {
            if(input.isEmpty()){
                continue;
            }
            if(input.charAt(0) == 'B'){
                String before = input.substring(input.indexOf('[') + 1);
                int[] toAddToBefore = new int[4];
                before = before.replaceAll(", ", "");
                before = before.replaceAll("]", "");
                for (int i = 0; i < 4; i++){
                    toAddToBefore[i] = Integer.parseInt(String.valueOf(before.charAt(i)));
                }
                beforeList.add(toAddToBefore);
            }
            else if(input.charAt(0) == 'A'){
                String after = input.substring(input.indexOf('[') + 1);
                int[] toAddToAfter = new int[4];
                after = after.replaceAll(", ", "");
                after = after.replaceAll("]", "");
                for (int i = 0; i < 4; i++){
                    toAddToAfter[i] = Integer.parseInt(String.valueOf(after.charAt(i)));
                }
                afterList.add(toAddToAfter);
            }
            else{
                int[] addToOrder = new int[4];
                String[] directionsArray = input.split(" ");
                for (int i = 0; i < 4; i++){
                    addToOrder[i] = Integer.parseInt(directionsArray[i]);
                }
                operationList.add(addToOrder);
            }
        }
    }

    private static int checkRules(){

        int count = 0;
        for(int i = 0; i < beforeList.size(); i++){
            int[] before = beforeList.get(i);
            int[] operation = operationList.get(i);
            int[] after = afterList.get(i);
            int numOfMatchingRules = 0;
            int opCode = operation[0];
            int valueA = operation[1];
            int valueB = operation[2];
            int valueC = operation[3];
            int beforeRegA = before[valueA];
            int beforeRegB = before[valueB];
            int resultInRegC = after[valueC];
            boolean match;
            Set<String> matchedRules = new HashSet<>();

            // add reg
            tester = resetTester(i);
            tester[valueC] = beforeRegA + beforeRegB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches addr");
                    matchedRules.add("addr");
                    numOfMatchingRules++;
                }
            }

            // add value
            tester = resetTester(i);
            tester[valueC] = beforeRegA + valueB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches addi");
                    matchedRules.add("addi");
                    numOfMatchingRules++;
                }
            }

            // multiply register
            tester = resetTester(i);
            tester[valueC] = beforeRegA * beforeRegB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches mulr");
                    matchedRules.add("mulr");
                    numOfMatchingRules++;
                }
            }

            // multiply value
            tester = resetTester(i);
            tester[valueC] = beforeRegA * valueB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches muli");
                    matchedRules.add("muli");
                    numOfMatchingRules++;
                }
            }

            // bitwise and reg
            tester = resetTester(i);
            tester[valueC] = beforeRegA & beforeRegB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches banr");
                    matchedRules.add("banr");
                    numOfMatchingRules++;
                }
            }

            // bitwise and value
            tester = resetTester(i);
            tester[valueC] = beforeRegA & valueB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches bani");
                    matchedRules.add("bani");
                    numOfMatchingRules++;
                }
            }

            // bitwise or reg
            tester = resetTester(i);
            tester[valueC] = beforeRegA | beforeRegB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches borr");
                    matchedRules.add("borr");
                    numOfMatchingRules++;
                }
            }

            // bitwise or value
            tester = resetTester(i);
            tester[valueC] = beforeRegA | valueB;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches bori");
                    matchedRules.add("bori");
                    numOfMatchingRules++;
                }
            }

            // set register
            tester = resetTester(i);
            tester[valueC] = beforeRegA;
            if(resultInRegC == tester[valueC]) {
                match = true;
                for (int j = 0; j < tester.length; j++) {
                    if (tester[j] != after[j]) {
                        match = false;
                    }
                }
                if (match) {
                    System.out.println("opCode " + opCode + " matches setr");
                    matchedRules.add("setr");
                    numOfMatchingRules++;
                }
            }

            // set value
            tester = resetTester(i);
            tester[valueC] = valueA;
            if(resultInRegC == tester[valueC]){
                match = true;
                for(int j = 0; j < tester.length; j++){
                    if(tester[j] != after[j]){
                        match = false;
                    }
                }
                if(match){
                    System.out.println("opCode " + opCode + " matches seti");
                    matchedRules.add("seti");
                    numOfMatchingRules++;
                }
            }

            if(resultInRegC == 1 || resultInRegC == 0){

                // gt v/r
                tester = resetTester(i);
                tester[valueC] = valueA > beforeRegB ? 1 : 0;
                if(resultInRegC == 1 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches gtir");
                        matchedRules.add("gtir");
                        numOfMatchingRules++;
                    }
                }
                else if(resultInRegC == 0 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches gtir");
                        matchedRules.add("gtir");
                        numOfMatchingRules++;
                    }
                }

                // gt r/v
                tester = resetTester(i);
                tester[valueC] = beforeRegA > valueB ? 1 : 0;
                if(resultInRegC == 1 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches gtri");
                        matchedRules.add("gtri");
                        numOfMatchingRules++;
                    }
                }
                else if(resultInRegC == 0 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches gtri");
                        matchedRules.add("gtri");
                        numOfMatchingRules++;
                    }
                }

                // gt r/r
                tester = resetTester(i);
                tester[valueC] = beforeRegA > beforeRegB ? 1 : 0;
                if(resultInRegC == 1 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches gtrr");
                        matchedRules.add("gtrr");
                        numOfMatchingRules++;
                    }
                }
                else if(resultInRegC == 0 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches gtrr");
                        matchedRules.add("gtrr");
                        numOfMatchingRules++;
                    }
                }

                // eq v/r
                tester = resetTester(i);
                tester[valueC] = valueA == beforeRegB ? 1 : 0;
                if(resultInRegC == 1 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches eqir");
                        matchedRules.add("eqir");
                        numOfMatchingRules++;
                    }
                }
                else if(resultInRegC == 0 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches eqir");
                        matchedRules.add("eqir");
                        numOfMatchingRules++;
                    }
                }

                // eq r/v
                tester = resetTester(i);
                tester[valueC] = beforeRegA == valueB ? 1 : 0;
                if(resultInRegC == 1 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches eqri");
                        matchedRules.add("eqri");
                        numOfMatchingRules++;
                    }
                }
                else if(resultInRegC == 0 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches eqri");
                        matchedRules.add("eqri");
                        numOfMatchingRules++;
                    }
                }

                // eq r/r
                tester = resetTester(i);
                tester[valueC] = beforeRegA == beforeRegB ? 1 : 0;
                if(resultInRegC == 1 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches eqrr");
                        matchedRules.add("eqrr");
                        numOfMatchingRules++;
                    }
                }
                else if(resultInRegC == 0 && resultInRegC == tester[valueC]){
                    match = true;
                    for(int j = 0; j < tester.length; j++){
                        if(tester[j] != after[j]){
                            match = false;
                        }
                    }
                    if(match){
                        System.out.println("opCode " + opCode + " matches eqrr");
                        matchedRules.add("eqrr");
                        numOfMatchingRules++;
                    }
                }
            }
            if(!mapOfRuleCountPerOpCode.keySet().contains(opCode)){
                mapOfRuleCountPerOpCode.put(opCode, matchedRules);
            }
            else{
                Set<String> toAdd = new HashSet<>();
                for(String s : matchedRules){
                    Set<String> previous = mapOfRuleCountPerOpCode.get(opCode);
                    if(previous.contains(s)){
                       toAdd.add(s);
                    }
                }
                mapOfRuleCountPerOpCode.put(opCode, toAdd);
            }
            if(numOfMatchingRules >= 3){
                count++;
            }
            System.out.println();
        }
        for(int i : mapOfRuleCountPerOpCode.keySet()){
            System.out.println(i + " matches " + mapOfRuleCountPerOpCode.get(i));
        }
        return count;
    }

    private static int performRules() {
        int[] result = {0, 0, 0, 0};

        for (int i = 0; i < part2List.size(); i++) {
            int[] operation = part2List.get(i);
            int opCode = operation[0];
            int valueA = operation[1];
            int valueB = operation[2];
            int valueC = operation[3];
            int regA = result[valueA];
            int regB = result[valueB];


            if(opCode == 0){
                //addr
                result[valueC] = regA + regB;
            }
            else if(opCode == 1){
                //eqri
                result[valueC] = regA == valueB ? 1 : 0;
            }
            else if(opCode == 2){
                //eqir
                result[valueC] = valueA == regB ? 1 : 0;
            }
            else if(opCode == 3){
                //eqrr
                result[valueC] = regA == regB ? 1 : 0;
            }
            else if(opCode == 4){
                //gtir
                result[valueC] = valueA > regB ? 1 : 0;
            }
            else if(opCode == 5){
                //addi
                result[valueC] = regA + valueB;
            }
            else if(opCode == 6){
                //banr
                result[valueC] = regA & regB;
            }
            else if(opCode == 7){
                //gtri
                result[valueC] = regA > valueB ? 1 : 0;
            }
            else if(opCode == 8){
                //bori
                result[valueC] = regA | valueB;
            }
            else if(opCode == 9){
                //muli
                result[valueC] = regA * valueB;
            }
            else if(opCode == 10){
                //seti
                result[valueC] = valueA;
            }
            else if(opCode == 11){
                //gtrr
                result[valueC] = regA > regB ? 1 : 0;
            }
            else if(opCode == 12){
                //setr
                result[valueC] = regA;
            }
            else if(opCode == 13){
                //borr
                result[valueC] = regA | regB;
            }
            else if(opCode == 14){
                //mulr
                result[valueC] = regA * regB;
            }
            else if(opCode == 15){
                //bani
                result[valueC] = regA & valueB;
            }

        }

        return result[0];
    }
    private static int[] resetTester(int indexOfBeforeList){
        int[] before = beforeList.get(indexOfBeforeList);
        int[] tester = new int[before.length];
        for(int i = 0; i < tester.length; i++){
            tester[i] = Integer.valueOf(before[i]);
        }
        return tester;
    }
}