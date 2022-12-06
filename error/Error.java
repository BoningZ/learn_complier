package error;

import src.Main;
import token.TK;
import token.Token;

import java.security.ProtectionDomain;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Error {
    public static final Map<ErrorCode, String> MAP=new HashMap<>(){{
            put(ErrorCode.UNEXPECTED_TOKEN, "Unexpected token");
        }};

    public int line=1;
    public int col=1;
    public ErrorCode errorCode;
    public void showError(String msg){
        System.out.printf("%s error at [line %d,col %d]\n",msg,line,col);
        System.out.println(Main.lines.get(line-1));
        System.out.println(String.join("", Collections.nCopies(col,"^")));
        System.exit(0);
    }
    public void showParserError(String msg){
        showError(MAP.get(errorCode)+": "+msg);
    }

    public Error(){}
    public Error(Token token,ErrorCode errorCode){
        line=token.line; col=token.col;
        this.errorCode=errorCode;
    }
    public Error(Token token){
        line=token.line; col=token.col;
    }


}
