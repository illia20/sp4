import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ClassesLexem {
    ONE_LINE_COMMENT("\\/\\/.*"),
    BCOMMENT("/(.*?)\\*\\/"),
    NUMBER4("[-]?[0][xX][0-9a-fA-F]+[uUlL]?"),
    NUMBER5("[-]?[1-9][.]?[0-9]*[eE][-]?[0-9]+"),
    NUMBER1("[-]?[1-9][0-9]?+[uUlL]?"),
    NUMBER2("[-]?[0-9]+[.][0-9]*"),
    NUMBER3("[-]?[0][0-7]+[uUlL]?"),
    IDENTIFIER("[A-Za-z_]([a-zA-Z_0-9])*"),

    STRCONST("([\"'`])(.*?)([\"'`])"),
    UNEXP,
    //DIRECTIVE("#define|#undef|#include|#if|#ifdef|#ifndef|#else|#elif|#endif|#line|#error|#warning"),
    DIRECTIVE1("#define"),
    DIRECTIVE2("#undef"),
    DIRECTIVE3("#include"),
    DIRECTIVE4("#if"),
    DIRECTIVE5("#ifdef"),
    DIRECTIVE6("#ifndef"),
    DIRECTIVE7("#else"),
    DIRECTIVE8("#elif"),
    DIRECTIVE9("#endif"),
    DIRECTIVE10("#line"),
    DIRECTIVE11("#error"),
    DIRECTIVE12("#warning"),
    DELIMITER1("\\("),
    DELIMITER2("\\)"),
    DELIMITER3("\\{"),
    DELIMITER4("\\}"),
    DELIMITER5("\\["),
    DELIMITER6("\\]"),
    DELIMITER7(":"),
    DELIMITER8("\\."),
    DELIMITER9(","),
    DELIMITER10(";"),
    NUMBER,
    DELIMITER,
    RESERVED,
    //DELIMITER("\\(|\\)|\\{|\\}|\\[|\\]|;|,|:|\\."),
    OPERATOR("\\|\\||&&|<|<=|>|>=|!=|==|=|\\+\\+|--|\\+|-|\\*|/|\\+=|-=|/=|\\*=");


    private final Pattern pattern;
    ClassesLexem(String s) {
        this.pattern = Pattern.compile("^" + s);
    }
    ClassesLexem() {
        this.pattern = Pattern.compile(" ");
    }

    int matchEnd(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.end();
        }
        return -1;
    }

}
