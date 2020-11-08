import java.io.*;

public class Main {

    public static void main(String[] args) {
        try{
            LexicalAnalyzer analyzer = new LexicalAnalyzer("src/main.cpp");
            FileWriter writer = new FileWriter("output.txt");
            while (!analyzer.end()) {
                var lexem = analyzer.lexem();
                if (!lexem.type().equals(ClassesLexem.ONE_LINE_COMMENT) && !lexem.type().equals(ClassesLexem.BCOMMENT)) {
                    writer.write(lexem.toString());
                }
                analyzer.calc();
            }
            writer.close();
            if (analyzer.success()) {
                System.out.println("SUCCESS! File is ready.");
            } else {
                System.out.println(analyzer.getErrorMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
