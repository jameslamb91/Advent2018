import java.io.*;
import java.util.ArrayList;

public class day3 {
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day3input");

        ArrayList<String> xList = new ArrayList<>();
        ArrayList<String> yList = new ArrayList<>();
        ArrayList<String> dimensionX = new ArrayList<>();
        ArrayList<String> dimensionY = new ArrayList<>();

        int[][] fabric = new int[1000][1000];
        for (int i = 0; i < 1000; i++){
            for (int j = 0; j < 1000; j++){
                fabric[i][j] = 0;
            }
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String input;
            while ((input = br.readLine()) != null) {
                xList.add(input.substring(input.indexOf('@') + 2, input.indexOf(',')));
                yList.add(input.substring(input.indexOf(',') + 1, input.indexOf(':')));
                dimensionX.add(input.substring(input.indexOf(':') + 2, input.indexOf('x')));
                dimensionY.add(input.substring(input.indexOf('x') + 1));
            }

            int count = 0;
            for (int i = 0; i < 1383; i++) {
                int x = Integer.parseInt(xList.get(i));
                int y = Integer.parseInt(yList.get(i));
                int xSize = Integer.parseInt(dimensionX.get(i));
                int ySize = Integer.parseInt(dimensionY.get(i));

                for (int j = 0; j < xSize; j++) {
                    for (int k = 0; k < ySize; k++) {
                        if(fabric[x + j][y + k] == 1){
                            count++;
                        }
                        fabric[x + j][y + k]++;
                    }


                }
            }
            for (int i = 0; i < 1383; i++) {
                int x = Integer.parseInt(xList.get(i));
                int y = Integer.parseInt(yList.get(i));
                int xSize = Integer.parseInt(dimensionX.get(i));
                int ySize = Integer.parseInt(dimensionY.get(i));

                for (int j = 0; j < xSize; j++) {
                    for (int k = 0; k < ySize; k++) {
                        fabric[x + j][y + k]++;
                    }
                }
            }


            for (int i = 0; i < 1383; i++) {
                boolean oneClaim = true;
                int x = Integer.parseInt(xList.get(i));
                int y = Integer.parseInt(yList.get(i));
                int xSize = Integer.parseInt(dimensionX.get(i));
                int ySize = Integer.parseInt(dimensionY.get(i));

                for (int j = 0; j < xSize; j++) {
                    for (int k = 0; k < ySize; k++) {
                        if(fabric[x + j][y + k] > 2){
                            oneClaim = false;
                        }
//                        System.out.print("[" + fabric[x + j][y + k] + "]");
                    }
//                    System.out.println();
//                    if(!oneClaim){
//                        break;
//                    }
                }

                if(oneClaim){
                    System.out.println("id: " + i);
                }
            }


//            System.out.println(count);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
