package sintactico;

import java.util.Hashtable;

public class TablaSimbolo {
    private Hashtable<String, Simbolo> tSim;
    
    public TablaSimbolo(){
        tSim = new Hashtable<>();
    }
    
    public void inserta(Simbolo sim){
        if (Main.declaraciones){
            if (!tSim.containsKey(sim.getNombre()))
                tSim.put(sim.getNombre(), sim);
            else
                System.out.println("La variable ya fue declarada: " + sim.getNombre());
        }
        else   if (!tSim.containsKey(sim.getNombre()))
            System.out.println("La variable no ha sido declarada: " + sim.getNombre());
    }
    
    public Simbolo buscar(String s){
        if (tSim.containsKey(s))
            return tSim.get(s);
        else
            return null;
    }
    
    public Hashtable getTabla(){
        return tSim;
    }
}

