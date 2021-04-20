
package sintactico;

/**
 * @author Clog_10
 */
public class Ensamblador {
    
    public String op;
    public String arg1;
    public String arg2;
    public String resultado;
    
    public String reg1="AX";
    public String reg2="BX";
    public String reg3="CX";
    public String reg4="DX";
    
    public Ensamblador(){
        op="";
        arg1="";
        arg2="";
        resultado="";
    }
    
    public String toString(){
        if(op.equals("+")){
            return "MOV "+reg1+","+arg1+"\n"+"MOV " +reg2+","+arg2+"\n"+"ADD "+reg1+","+reg2;
        }else if(op.equals("-")){
            return "MOV " +reg1+","+arg1+"\n"+"MOV " +reg2+","+arg2+"\n"+"SUB "+reg1+","+reg2;
        }else if(op.equals("*")){
            return "MOV " +reg1+","+arg1+"\n"+"MOV "+reg2+","+arg2+"\n"+"MUL "+reg2;
        }else if(op.equals("/")){
            return "MOV " +reg1+","+arg1+"\n"+"MOV " +reg2+","+arg2+"\n"+"DIV "+reg2;
        }
        return "MOV "+resultado+", " +reg1+"\n";
    }

}









