package sintactico;

public class Cuadruplo {
    public String op;
    public String arg1;
    public String arg2;
    public String resultado;
    
    public Cuadruplo(){
        op="";
        arg1="";
        arg2="";
        resultado="";
    }
    
    public String toString(){
        return resultado + " := " + arg1 + ' ' + op + ' ' + arg2;
    }
}
