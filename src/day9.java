import java.util.HashMap;

public class day9 {
    static final int NUMBER_OF_PLAYERS = 413;
    static final int FINAL_MARBLE_NUMBER = 71082 * 100; // times 100 for part 2
    static long[] players = new long[NUMBER_OF_PLAYERS];

    public static void main(String[] args) {


        Marble currentMarble = new Marble(0);

        int currentPlayer = 0;

        currentMarble.add(currentMarble, currentMarble);

        for(int i = 1; i <= FINAL_MARBLE_NUMBER; i++){


            if((i > 0) && (i % 23 == 0)){
                // do scoring stuff
                players[currentPlayer] += i;
                currentMarble = currentMarble.prev.prev.prev.prev.prev.prev.prev;
                players[currentPlayer] += currentMarble.getNumber();
                currentMarble.remove(currentMarble.prev, currentMarble.next);
                currentMarble = currentMarble.next;
            }
            else{
                // add like normal
                Marble m = new Marble(i);
                m.add(currentMarble.next, currentMarble.next.next);
                currentMarble = m;
            }
            currentPlayer = (currentPlayer + 1) % NUMBER_OF_PLAYERS;
        }

        long maxScore = Integer.MIN_VALUE;
        for(long i : players){
            if (i > maxScore){
                maxScore = i;
            }
        }

        System.out.println(maxScore);
    }

    private static class Marble {

        Marble next;
        Marble prev;
        int number;

        Marble(int number){
            this.number = number;
        }

        private void add(Marble prev, Marble next){
            next.prev = this;
            prev.next = this;
            this.next = next;
            this.prev = prev;
        }

        private void remove(Marble prev, Marble next){
            next.prev = this.prev;
            prev.next = this.next;
        }

        private int getNumber(){
            return this.number;
        }
    }
}