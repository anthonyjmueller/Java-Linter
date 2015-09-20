import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Linter {

    public static boolean whitespace = false;
    public static void main(String[] args) {

        int linecnt = 1;

        File program = new File(args[0]);
        try {
            Scanner code = new Scanner(program);

            while (code.hasNextLine())
            {
                whitespace = false;
                String line = code.nextLine();
                if (line.length() != 0){
                    String last = line.substring(line.length()-1, line.length());
                    //System.out.println(last.length() + last);
                    check(last, line, linecnt);
                }
                linecnt++;
                if (whitespace){
                    System.out.println(linecnt + ". Statement contains trailing white space");
                }

            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void check(String last, String line, int linecnt) {
        int casenum = 0;
        if (last.equals("{"))
            casenum = 1;
        else if (last.equals("}"))
            casenum = 1;
        else if (last.equals("{"))
            casenum = 1;
        else if (last.equals(";"))
            casenum = 1;
        else if (last.equals(")"))
            casenum = 1;
        else if (last.equals(" "))
            casenum = 2;
        else
            System.out.println(linecnt + ". Statement should end with a semicolon.");


        switch (casenum)
        {

            case 1 :
                break;
            case 2 :
                last = line.substring(line.length()-2, line.length()-1);
                //System.out.println(line.length());
                if (line.length() != 0){
                    line = line.substring(0, line.length()-1);
                }
                //System.out.println(line.length());
                whitespace = true;
                check(last, line, linecnt);
                break;

        }

    }

}
