package src;



import ast.ASTNode;
import token.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static ArrayList<String> lines=new ArrayList<>();
    public static String text="";
    public static String fileName="srcProg";
    public static int OffsetSum=-999;
    public static int CountI=0;
    public static String[] paramRegs={"rdi","rsi","rdx","rcx","r8","r9"};
    public static void main(String[] args) throws IOException {
        File file = new File("./"+fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String len;
        while ((len=br.readLine())!=null){
            lines.add(len);
            text+=len+'\n';//Java readLine不包含行位换行符
        }
        Lexer lexer=new Lexer(text);
        Parser parser=new Parser(lexer);
        List<ASTNode> tree=parser.parse();
        //for(ASTNode node:tree)node.dfs(0,0);
        SemanticAnalyzer analyzer=new SemanticAnalyzer();
        analyzer.analyze(tree);
        CodeGenerator generator=new CodeGenerator();
        generator.generate(tree);
    }
}
