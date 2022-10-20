package error;

import src.Main;

import java.security.ProtectionDomain;
import java.util.Collections;

public class Error {
    public int line=1;
    public int col=1;
    public void showError(String msg){
        System.out.printf("%s error at [line %d,col %d]\n",msg,line,col);
        System.out.println(Main.lines.get(line-1));
        System.out.println(String.join("", Collections.nCopies(col,"^")));
        System.exit(0);
    }
}
