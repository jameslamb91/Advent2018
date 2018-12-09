import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day9 {
    static final int NUMBER_OF_PLAYERS = 10;//413;
    static final int FINAL_MARBLE_NUMBER = 1618;//71082;

    public static void main(String[] args) {

        int[] players = new int[NUMBER_OF_PLAYERS];
        int currentPlayer = 0;

        List<Integer> marbles = new ArrayList<>();
        int marbleNumber = 0;
        int currentMarbleIndex = 0;
        HashMap<Integer, Boolean> isPlaced = new HashMap<>();

        while(marbleNumber <= FINAL_MARBLE_NUMBER) {

            int scoreToAdd;

            // This marble will be listed as placed so we don't try to re-place it.
            isPlaced.put(marbleNumber, true);

            if(marbleNumber == 0 || marbleNumber % 23 != 0){
            // normal case

                // add marble to current spot
                marbles.add(currentMarbleIndex, marbleNumber);

                // add 2 to the current spot
                currentMarbleIndex = (currentMarbleIndex - 2 + marbles.size()) % marbles.size();

            }
            else{
            // scoring case

                // score instead of placing
                scoreToAdd = marbleNumber;

                // get the marble 7 marbles to the left
                currentMarbleIndex = (currentMarbleIndex  + 7) % marbles.size();
                int marbleToRemove = marbles.get(currentMarbleIndex);

                // add marble to remove to the score
                scoreToAdd += marbleToRemove;

                // remove marble
                marbles.remove(currentMarbleIndex);

                // allow it to be picked to place
                isPlaced.put(marbleToRemove, false);

                // add score to current player
                players[currentPlayer] += scoreToAdd;
            }

            // get lowest number non placed marble
            marbleNumber = getNextMarbleToPlace(isPlaced);

            // go to the next player
            currentPlayer = (currentPlayer + 1) % players.length;

        }

        // get max score
        int max = Integer.MIN_VALUE;
        for(int i : players){
            if (i > max){
                max = i;
            }
        }
        System.out.println(max);
    }

    private static int getNextMarbleToPlace(HashMap<Integer, Boolean> map){


        // find the lowest number in the map that hasn't been placed
        for(int i = 0; i < map.size(); i++){
            if(!map.containsKey(i) || (map.containsKey(i) && !map.get(i))){
                return i;
            }
        }

        // if everything in the map has been placed, return the map size to place the next marble
        return map.size();
    }

}