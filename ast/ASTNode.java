package ast;

import token.Token;

import java.lang.reflect.Field;
import java.util.List;

public class ASTNode {
    public void dfs(int dep,int last){
        String name=this.getClass().getSimpleName();
        for(int i=0;i<dep;i++)System.out.print("\t"+((last&(1<<i))>0?"":"|"));
        System.out.print("|-NODE:"+name);
        if(name.equals("FunctionCallNode")) System.out.println("["+((FunctionCallNode)this).functionName+"]");
        else if(name.equals("FunctionDefNode")) System.out.println("["+((FunctionDefNode)this).functionName+"]");
        else if(name.equals("ReturnNode")) System.out.println("["+((ReturnNode)this).functionName+"]");
        else System.out.println();
        Field[] fields=this.getClass().getDeclaredFields();
        List<Field> fieldList= List.of(fields);
        for(Field field:fields){
            if(fieldList.indexOf(field)==fieldList.size()-1)last|=(1<<dep);
            try {
                String fName=field.getType().getSimpleName();
                if (fName.equals("Token")){
                    for(int i=0;i<dep+1;i++)System.out.print("\t"+((last&(1<<i))>0?"":"|"));
                    System.out.println("-token:" + field.get(this));
                }
                else if (fName.equals("ASTNode"))((ASTNode)(field.get(this))).dfs(dep + 1,last);
                else if (fName.equals("List")){
                    List<ASTNode> nodes= (List<ASTNode>) field.get(this);
                    for(ASTNode node:nodes)node.dfs(dep+1,last);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
