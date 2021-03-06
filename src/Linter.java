import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static jdk.nashorn.internal.runtime.regexp.RegExp.*;
// add ingore for comments and add catch for new line character

public class Linter {

    public static boolean whitespace = false;
    public static void main(String[] args) {

        int linecnt = 1;
        boolean java = false;

        File program = new File(args[0]);
        try {
            Scanner code = new Scanner(program);

            while (code.hasNextLine()) {
                String line;
                if (code.hasNextLine()) {
                    line = code.nextLine();
                }
                else {
                    line = code.next();
                }


                if (line.length() != 0) {
                    if (!(code.hasNextLine())) {
                        Pattern newlinelast = Pattern.compile(".{0}\\n");
                        Matcher newlinelas = newlinelast.matcher(line);
                        Boolean linelast = newlinelas.find();
                        System.out.print(line);
                        if (!(linelast)) {
                            System.out.println("Program should end with an blank line.");
                        }
                    }

                    Pattern comment = Pattern.compile("/{2}.*");
                    Matcher comments = comment.matcher(line);
                    Boolean comm = comments.find();
                    if (!(java)) {
                        Pattern javadoc = Pattern.compile("/{1}\\*{1}");
                        Matcher javado = javadoc.matcher(line);
                        java = javado.find();
                    }
                    if ((!(comm)) && (!(java))) {

                        if (line.length() > 80) {
                            System.out.println(linecnt + ". Line Should not be longer than 80 characters.");
                        }

                        //System.out.println(linecnt);
                        Pattern curr = Pattern.compile(".*[{$|}$|;$]+");
                        Pattern whit = Pattern.compile(".*\\s+$");
                        Pattern dquores = Pattern.compile(".*[']{2}");
                        Pattern singlestatement = Pattern.compile(".*\\;{1}[\\p{Alpha}\\p{Digit}\\p{Punct}]{1}+");

                        Matcher end = curr.matcher(line);
                        Boolean semi = end.find();
                        if (!(semi)) {
                            System.out.println(linecnt + ". Statement should end with a semicolon.");
                        }

                        Matcher trawhite = whit.matcher(line);
                        Boolean white = trawhite.find();
                        if (white) {
                            System.out.println(linecnt + ". Statement contains trailing white space");
                        }

                        Matcher dquote = dquores.matcher(line);
                        Boolean ifdquoates = dquote.find();
                        if (ifdquoates) {
                            System.out.println(linecnt + ". Should use single quotes");
                        }

                        Matcher singlestatem = singlestatement.matcher(line);
                        Boolean singlestate = singlestatem.find();
                        if (singlestate) {
                            System.out.println(linecnt + ". Use only one statement per line");
                        }

                        Pattern bractestO = Pattern.compile("[{]+");
                        Matcher bractestOM = bractestO.matcher(line);
                        if (bractestOM.find()) {
                            Pattern open = Pattern.compile("[\\p{Alpha}\\p{Punct}\\p{Digit}]{1}+[\\p{Blank}]*\\{");

                            Matcher openm = open.matcher(line);
                            Boolean openb = openm.find();
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
                    else if (java) {
                        Pattern njavadoc = Pattern.compile("\\*{1}/{1}");
                        Matcher njavado = njavadoc.matcher(line);
                        Boolean njava = njavado.find();
                        if (njava)
                            java = false;
                    }
                }
                linecnt++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
