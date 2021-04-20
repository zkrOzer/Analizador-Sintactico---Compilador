package sintactico;

public class Numero {
    private double valor;
    
    public Numero(){
        valor=0;
    }

    public void setValor(int n) {
        this.valor = n;
    }

    public void setValor(double n) {
        this.valor = n;
    }

    public int getIntValor() {
        return (int) valor;
    }

    public double getDoubleValor() {
        return valor;
    }

    public void setValor(String n) {
        valor = Double.parseDouble(n);
    }

    public String getStringValor() {
        return "" + valor;
    }
}

