package sintactico;

public class Lexico {
    private Lector lec;
    public String lexema;
    
    public Lexico (Lector lector){
        this.lec=lector;
    }
    
    /** Reconocer letras minusculas **/
    private boolean letra(char c){
        if (c  >= 'a' && c <= 'z' )
            return true;
        else
            return false;
    }
    
    /** Reconocer digitos **/
    private boolean digito(char c){
        if (c  >= '0' && c <= '9')
            return true;
        else
            return false;
    }
    
    /** Reconocer id, expresión regular: letra(letra|digito)* **/
    private boolean id(char c){
        if (letra(c)){
           c = lec.lector();
           while(letra(c) || digito(c))
               c=lec.lector();           
           return true;
        }
        return false;
    }
    
    /** Reconocer num entero, expresión regular: digito(digito)* **/
    private boolean num(char c){
        if (digito(c)){
           c = lec.lector();
           while(digito(c))
               c=lec.lector();
           return true;
        }
        return false;
    }
    
    /** Cada invocación regresa el siguiente token **/
    public Token lex(){
        char c;
        while(true){
            c=lec.lector();
            if (c==0)break;
            else if (id(c)){
                lexema = lec.regCadena();
                lec.avanza();
                /* verificar si el id representa palabra reservada. **/
                if (lexema.equals("int"))
                    return Token.INT;
                else if(lexema.equals("double")){
                    return Token.DOUBLE;
                }
                else if(lexema.equals("byte")){
                    return Token.BYTE;
                }
                else if(lexema.equals("float")){
                    return Token.FLOAT;
                }
                else
                {
                    Main.tSim.inserta(new Simbolo(lexema));
                    return Token.ID;
                }
            }
            else if (num(c)){
                lexema = lec.regCadena();
                lec.avanza();
                //System.out.println("lex-num="+lexema);
                return Token.NUM;
            }
            else{
                switch(c){
                    case '=':   lexema="=";
                                lec.avanza();
                                return Token.ASIGNA;
                                
                    case ';':   lexema=";";
                                lec.avanza();
                                return Token.PYC;
                                
                    case ',':   lexema=",";
                                lec.avanza();
                                return Token.COMA;
                                
                    case '(':   lexema="(";
                                lec.avanza();
                                return Token.PIZQ;
                                
                    case ')':   lexema=")";
                                lec.avanza();
                                return Token.PDER;           
                                
                    case '+':
                    case '-':
                    case '/':
                    case '*':   lexema=""+c;
                                lec.avanza();
                                return Token.OPARIT;
                    default: lec.avanza();
                }
               
            }
        }
        return Token.EOF;
    }
}


