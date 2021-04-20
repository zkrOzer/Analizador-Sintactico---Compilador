package sintactico;

import java.util.Iterator;

public class Main {
    /** Semantica **/
    public static TablaSimbolo tSim;
    public static boolean declaraciones;
    
    public static SimTemporal sTemp; 

    public static void main(String[] args) {

        String datos= "int x;  x = 3+2; double y;  y=3-2; float z; z=4/2;";
        
        tSim = new TablaSimbolo();
        declaraciones=false;
        
        sTemp= new SimTemporal();
        
        Lector l = new Lector(datos);
        Lexico lex = new Lexico (l);
        Sintactico sin = new Sintactico(lex);
        //System.out.println(sin.getMensajes().toString());
        
        System.out.println("Gutierrez Loaeza Carlos de Jesus");
        System.out.println("=======< Tabla >========");
        for (Iterator it = tSim.getTabla().values().iterator(); it.hasNext();) {
            Simbolo s = (Simbolo)it.next();
            System.out.println(s.toString());
        }
        
//        System.out.println("=======< Cuadruplos >========");
//        System.out.println(sin.getCodigo().toString());
        
        System.out.println();
        System.out.println("=======< Ensamblador >========");
        System.out.println(sin.getEnsamblador().toString());
    }
    
}
























































