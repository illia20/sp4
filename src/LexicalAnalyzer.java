import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {
    private final StringBuilder text = new StringBuilder();
    private final Set<Character> whitespaceCharacters = new HashSet<>();
    private Pattern reservedpttrn;
    private Matcher reservedmatch;
    private String errorMessage = "";
    private Lexem lexem;
    private boolean end = false;

    LexicalAnalyzer(String path) {
        reservedpttrn = Pattern.compile("auto|break|case|char|const|continue|default|do|double|else|enum|extern|float|for|goto|if|int|long|register|return|short|signed|sizeof|string|static|struct|switch|typedef|union|unsigned|using|void|volatile|while");
        getFile(path);
        createWhitespace();
        calc();
    }

    private void getFile(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNext()) {
                String q = sc.next();
                if(q.trim().startsWith("//")){
                    sc.nextLine();
                }
                if(q.contains("/*")){
                    text.append(q.substring(0, q.indexOf("/*"))).append('\n');
                    while(sc.hasNext()){
                        String a = sc.next();
                        if(a.contains("*/")){
                            text.append(a.substring(a.indexOf("*/") + 2, a.length()));
                            break;
                        }
                    }

                    continue;
                }
                text.append(q).append("\n");
            }
        } catch (FileNotFoundException e) {
            this.end = true;
            this.errorMessage = e.getMessage();
        }
    }

    private void createWhitespace() {
        this.whitespaceCharacters.add('\r');
        this.whitespaceCharacters.add('\n');
        this.whitespaceCharacters.add((char)8);
        this.whitespaceCharacters.add((char)9);
        this.whitespaceCharacters.add((char)11);
        this.whitespaceCharacters.add((char)12);
        this.whitespaceCharacters.add((char)32);
    }


    public void calc() {
        if (end) {
            return;
        }
        if (text.length() == 0) {
            end = true;
            return;
        }
        cleanWhitespaces();
        if (correctLexem()) {
            return;
        }
        if (text.length() > 0) {
            errorLexem();
        }
    }

    private void errorLexem() {
        int counter = 0;
        String eLexem = new String();
        do {
            eLexem += text.charAt(counter);
            ++counter;
        } while (!Character.isWhitespace(text.charAt(counter)));
        lexem = new Lexem(ClassesLexem.UNEXP, eLexem);
        text.delete(0, counter);
    }
    private void cleanWhitespaces() {
        int count = 0;
        while (count < text.length() && whitespaceCharacters.contains(text.charAt(count))) {
            ++count;
            if (text.length() == 1) {
                break;
            }
        }
        if (count > 0) {
            text.delete(0, count);
        }
    }

    private boolean correctLexem() {
        for (var lexem : ClassesLexem.values()) {
            int matchEnd = lexem.matchEnd(text.toString());
            if (matchEnd != -1) {
                var tokenString = text.substring(0, matchEnd);
                if (lexem == ClassesLexem.IDENTIFIER) {
                    reservedmatch = reservedpttrn.matcher(tokenString);
                    if (reservedmatch.matches()) lexem = ClassesLexem.RESERVED;
                }
                /*
                Pattern pattern = Pattern.compile(String.valueOf(lexem));
                var matcher = pattern.matcher(inputText.toString());
                String mstr = matcher.group();
                System.out.println(mstr);
                */
                this.lexem = new Lexem(lexem, tokenString);
                text.delete(0, matchEnd);
                return true;
            }
        }
        return false;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean success() {
        return errorMessage.isEmpty();
    }

    public Lexem lexem() {
        return lexem;
    }

    public boolean end() {
        return end;
    }

}
