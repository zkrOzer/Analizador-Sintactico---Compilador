package sintactico;

public class Simbolo {

    private String nombre;
    private Numero valor;
    private Token tipo;

    public Simbolo() {
        this("");
    }

    public Simbolo(String nombre) {
        this(nombre, new Numero(), Token.EOF);
    }

    public Simbolo(String nombre, Numero valor, Token tipo) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Numero getValor() {
        return valor;
    }

    public void setValor(Numero valor) {
        this.valor = valor;
    }

    public Token getTipo() {
        return tipo;
    }

    public void setTipo(Token tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        if (tipo == Token.INT) {
            return "Simbolo{" + "nombre=" + nombre + ", valor=" + valor.getIntValor() + ", tipo=" + tipo + '}';
        } else if (tipo == Token.DOUBLE) {
            return "Simbolo{" + "nombre=" + nombre + ", valor=" + valor.getDoubleValor() + ", tipo=" + tipo + '}';
        } else if (tipo == Token.FLOAT) {
            return "Simbolo{" + "nombre=" + nombre + ", valor=" + valor.getDoubleValor() + ", tipo=" + tipo + '}';
        }
        return "Simbolo{" + "nombre=" + nombre + ", valor=" + valor.getDoubleValor() + ", tipo=" + tipo + '}';
    }

}

