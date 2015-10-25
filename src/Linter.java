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

                    if (line.length() > 80){
                        System.out.println(linecnt + ". Line Should not be longer than 80 characters.");
                    }
                    //System.out.println(linecnt);
                    Pattern curr = Pattern.compile(".*[{$|}$|;$]+");
                    Pattern whit = Pattern.compile(".*\\s+$");
                    Pattern dquores = Pattern.compile(".*[']{2}");
                    Pattern singlestatement = Pattern.compile(".*[;]{1}[{\\p{Alpha}\\p{N}}\\p{Punct}}]*+");

                    Matcher end = curr.matcher(line);
                    Boolean semi = end.find();
                    if (!(semi)) {
                        System.out.println(linecnt + ". Statement should end with a semicolon.");
                    }

                    Matcher trawhite = whit.matcher(line);
                    Boolean white = trawhite.find();
                    if (white){
                        System.out.println(linecnt + ". Statement contains trailing white space");
                    }

                    Matcher dquote = dquores.matcher(line);
                    Boolean ifdquoates = dquote.find();
                    if (ifdquoates){
                        System.out.println(linecnt + ". Should use single quotes");
                    }

                    Matcher singlestatem = singlestatement.matcher(line);
                    Boolean singlestate = singlestatem.find();
                    if (singlestate){
                        System.out.println(linecnt + ". Use only one statement per line");
                    }

                    Pattern bractestO = Pattern.compile("[{]+");
                    Matcher bractestOM = bractestO.matcher(line);
                    if (bractestOM.find()) {
                        //System.out.println(linecnt);
                        Pattern open = Pattern.compile("[[\\p{Alpha}][\\p{Digit}][\\p{Punct}]]+[\\p{Blank}]*[{]");

                        Matcher openm = open.matcher(line);
                        Boolean openb = openm.find();
                        //System.out.println(openb);
                        if (!(openb)) {
                            System.out.println(linecnt + ". Open curley braces should not stand-alone.");
                        }
                    }

                    Pattern strictequ = Pattern.compile(".*[=]{2}+");
                    Matcher strictequs = strictequ.matcher(line);
                    if (strictequs.find()) {
                        //System.out.println(linecnt);
                        Pattern strict = Pattern.compile(".*[=]{3}+");

                        Matcher stricte = strict.matcher(line);
                        Boolean equalsthree = stricte.find();
                        //System.out.println(openb);
                        if (!(equalsthree)) {
                            System.out.println(linecnt + ". Should only use strict equal.");
                        }
                    }

                    Pattern bractestC = Pattern.compile("[}]+");
                    Matcher bractestCM = bractestC.matcher(line);
                    if (bractestCM.find()) {
                        Pattern close = Pattern.compile("[[\\p{Alpha}][\\p{Digit}][\\p{Punct}]]+[\\p{Blank}]*[}]");

                        Matcher closem = close.matcher(line);
                        Boolean closeb = closem.find();
                        if (closeb) {
                            System.out.println(linecnt + ". Closing curly braces should stand-alone.");
                        }
                    }
                }
                linecnt++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
