package sintactico;

/*  Maneja el programa fuente, aloja la información en un bufer para recorrerlo
    símbolo por símbolo
*/
public class Lector {
    private char[] bufer;
    int posini;             // Apunta al inicio de la lectura 
    int posfin;             // Apunta al final de la lectura
    int maxbuf;             // Tamaño del programa fuente
    
    
    public Lector(String datos){
        this.maxbuf=datos.length();
        this.bufer=new char[this.maxbuf];
        this.bufer=datos.toCharArray();
        posini=0;
        posfin=0;
    }
    
    /** Regresa el siguiente simbolo en el bufer **/
    public char lector(){
        char c;
        if (posfin<maxbuf){
            c = bufer[posfin];
            posfin++;
            return c;
        }
        return 0;
    }
    
    /** Regresa la cadena actual y actualiza indices **/
    public String regCadena(){
        String s=""; 
        posfin--;   // Regresar caracter adelantado al bufer
        for (int i=posini; i<posfin; i++)
            s += bufer[i];
        return s;
    }
    
    public void avanza(){
        posini=posfin;
    }
 
}
