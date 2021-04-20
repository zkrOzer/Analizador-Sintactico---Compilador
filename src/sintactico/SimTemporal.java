package sintactico;

public class SimTemporal {
    private int indice;

    public void initSimTemporal() {
        indice=0;
    }
    
    public String getEtiqueta(){
        indice++;
        return "t"+indice;
    }
}
