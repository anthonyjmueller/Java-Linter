import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jdk.nashorn.internal.runtime.regexp.RegExp.*;


public class Linter {

    public static boolean whitespace = false;
    public static void main(String[] args) {

        int linecnt = 1;

        File program = new File(args[0]);
        try {
            Scanner code = new Scanner(program);

            while (code.hasNextLine()) {
                String line = code.nextLine();
                if (line.length() != 0) {
                    System.out.println(linecnt);
                    Pattern curr = Pattern.compile(".*[{$|}$|;$]+");
                    Boolean test = true;
                    Matcher end = curr.matcher(line);
                    test = end.find();
                    if (!(test)) {
                        System.out.println(linecnt + ". Statement should end with a semicolon.");
                    }
                }
                linecnt++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
