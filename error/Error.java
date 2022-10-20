package error;

import src.Main;

import java.security.ProtectionDomain;
import java.util.Collections;

public class Error {
    public int line=1;
    public int col=1;
    public void showError(String msg){
        System.out.printf("error at line %d\n",line);
        System.out.println(Main.lines.get(line-1).trim());
        System.out.println(String.join("", Collections.nCopies(col,"^")));
        System.out.println(msg);
        System.exit(0);
    }
}
