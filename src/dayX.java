import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class dayX {
    public static void main(String[] args) {
        File file = new File("/Users/corey.lamb/Documents/workspace-sts-3.9.2.RELEASE/Advent2018/src/dayXinput");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}