package token;

import java.util.HashMap;
import java.util.Map;

public class util {
    public static final Map<String,TK> MAP=new HashMap<>(){{
        put("+",TK.PLUS);
        put("-",TK.MINUS);
        put("*",TK.MUL);
        put("/",TK.DIV);
        put("unary-",TK.NEG);
        put("<",TK.LT);
        put(">",TK.GT);
        put("==",TK.EQ);
        put("<=",TK.LE);
        put(">=",TK.GE);
        put("!=",TK.NE);
        put("(",TK.LPAREN);
        put(")",TK.RPAREN);
        put("{",TK.LBRACE);
        put("}",TK.RBRACE);
        put("[",TK.LBRACKET);
        put("]",TK.RBRACKET);
        put(",",TK.COMMA);
        put(";",TK.SEMICOLON);

        put("return",TK.RETURN);
        put("if",TK.IF);
        put("else",TK.ELSE);
        put("then",TK.THEN);
        put("int",TK.INT);

        put("ID",TK.ID);
        put("INTEGER_CONST",TK.INTEGER_CONST);
        put("EOF",TK.EOF);
    }};
    public static boolean isId1(char ch){
        return Character.isLetter(ch)||ch=='_';
    }
    public static boolean isId2(char ch){
        return isId1(ch)||Character.isDigit(ch);
    }
    public static boolean isSinglePunctuation(char ch){
        String p="!#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\"";
        return p.contains(String.valueOf(ch));
    }
}
