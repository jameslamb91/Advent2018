import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class day13 {
    static ArrayList<String> tracks = new ArrayList<>();
    static ArrayList<Cart> carts = new ArrayList<>();
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/day13input");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String input;
            int j = 0;
            while((input = br.readLine()) != null){
                for(int i = 0; i < input.length(); i++){
                    char c = input.charAt(i);
                    if (c == '^'){
                        carts.add(new Cart(i, j, 'u'));
                        input = input.substring(0, i) + "|" + input.substring(i+1, input.length());
                    }
                    else if (c == '>'){
                        carts.add(new Cart(i, j, 'r'));
                        input = input.substring(0, i) + "-" + input.substring(i+1, input.length());
                    }
                    else if (c == 'v'){
                        carts.add(new Cart(i, j, 'd'));
                        input = input.substring(0, i) + "|" + input.substring(i+1, input.length());
                    }
                    else if (c == '<'){
                        carts.add(new Cart(i, j, 'l'));
                        input = input.substring(0, i) + "-" + input.substring(i+1, input.length());
                    }
                }
                tracks.add(input);
                j++;
            }

            boolean collision = false;  // used for part one as the while condition
            boolean oneCartLeft = false; // used for part two as the while condition

            while(!oneCartLeft){

//                used for part two result
                if(checkForOnlyOneLivingCart()){
                    for(Cart lastCart : carts){
                        if (!lastCart.dead){
                            System.out.println("Last cart at: " + lastCart.x + "," + lastCart.y);
                        }
                    }
                    oneCartLeft = true;
                }

                for(Cart c : carts){
                    if (!c.dead) {
                        c.move();
                        turnCartIfNeeded(c);
                        checkForCollision(c);

//                        used for part 1 result
                        if (c.dead) {
                            collision = true;
                            System.out.println("Collision at: " + c.x + "," + c.y);

                        }

                    }
                }

                if(checkForOnlyOneLivingCart()){
                    for(Cart lastCart : carts){
                        if (!lastCart.dead){
                            System.out.println("Last cart at: " + lastCart.x + "," + lastCart.y);
                        }
                    }
                }

            }




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void turnCartIfNeeded(Cart c) {
        if (tracks.get(c.y).charAt(c.x) == '/') {
            if (c.direction == 'u') {
                c.direction = 'r';
            } else if (c.direction == 'd') {
                c.direction = 'l';
            } else if (c.direction == 'l') {
                c.direction = 'd';
            } else {
                c.direction = 'u';
            }
        } else if (tracks.get(c.y).charAt(c.x) == '\\') {
            if (c.direction == 'u') {
                c.direction = 'l';
            } else if (c.direction == 'd') {
                c.direction = 'r';
            } else if (c.direction == 'l') {
                c.direction = 'u';
            } else {
                c.direction = 'd';
            }
        } else if (tracks.get(c.y).charAt(c.x) == '+') {
            if (c.turn % 3 == 0) {
                if (c.direction == 'u') {
                    c.direction = 'l';
                } else if (c.direction == 'r') {
                    c.direction = 'u';
                } else if (c.direction == 'd') {
                    c.direction = 'r';
                } else if (c.direction == 'l') {
                    c.direction = 'd';
                }
            }
            // we don't want to change direction for the straight times

            else if (c.turn % 3 == 2) {
                if (c.direction == 'u') {
                    c.direction = 'r';
                } else if (c.direction == 'r') {
                    c.direction = 'd';
                } else if (c.direction == 'd') {
                    c.direction = 'l';
                } else if (c.direction == 'l') {
                    c.direction = 'u';
                }
            }
            c.turn++;
        }
    }

    private static boolean checkForOnlyOneLivingCart(){
        int numOfLivingCarts = 0;
        for(Cart c : carts){
            if (!c.dead){
                numOfLivingCarts++;
            }
        }
        return numOfLivingCarts == 1;
    }

    private static void checkForCollision(Cart c1){
        for(Cart c2 : carts){
            if(!c2.dead) {
                if (c1 != c2 && c2.x == c1.x && c2.y == c1.y) {
                    c1.dead = true;
                    c2.dead = true;
                }
            }
        }
    }

    private static class Cart{
        int x;
        int y;
        char direction;
        int turn;
        boolean dead;

        private Cart(int x, int y, char direction){
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.turn = 0;
            this.dead = false;
        }

        private void move(){

            if(!this.dead) {
                if (this.direction == 'u') {
                    this.y--;
                } else if (this.direction == 'd') {
                    this.y++;
                } else if (this.direction == 'l') {
                    this.x--;
                } else if (this.direction == 'r') {
                    this.x++;
                }
            }
        }
    }
}

