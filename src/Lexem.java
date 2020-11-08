public class Lexem {

    public Lexem(ClassesLexem type, String token) {
        String strtype = type.toString();
        int i = strtype.length();
        for(int k = 0; k < 10; k++){
            int ki = strtype.indexOf(String.valueOf(k));
            if(ki > -1 && ki < i)
                i = ki;
        }
        this.t1 = strtype.substring(0, i);
        this.type = type;
        this.token = token;
    }

    public ClassesLexem type() {
        return this.type;
    }

    @Override
    public String toString() {
        if(t1.equals("ONE_LINE_COMMENT") || t1.equals("BCOMMENT")) return "";
        return "<" + t1 + "> - <" + token + ">\n";
    }

    private ClassesLexem type;
    private String token;
    private String t1;
}
