package src;



import token.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static ArrayList<String> lines=new ArrayList<>();
    public static String text="";
    public static String fileName="srcProg";
    public static void main(String[] args) throws IOException {
        File file = new File("./"+fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len=br.readLine())!=null){
            lines.add(len);
            text+=len+'\n';
        }
        Lexer lexer=new Lexer(text);
        ArrayList<Token> tokens=lexer.gatherAllTokens();
        for(Token t:tokens) System.out.println(t);
    }
}
