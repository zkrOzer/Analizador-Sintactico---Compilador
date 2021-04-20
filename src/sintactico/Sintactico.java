package sintactico;

public class Sintactico {

    private Lexico lexico;
    private Token token;
    private StringBuffer mensajes;
    private StringBuffer codigo;
    private StringBuffer ensamblador;

    boolean debug = false;

    public Sintactico(Lexico lexico) {
        this.lexico = lexico;
        /**
         * Obtener el primer token *
         */
        token = lexico.lex();
        mensajes = new StringBuffer();
        codigo = new StringBuffer();
        ensamblador = new StringBuffer();
        this.simS();
        mensajes.append("Fin del Sintáctico");
    }

    /**
     * S -> I; S S -> lamda
     */
    private void simS() {
        boolean salida = false;
        if (debug) {
            mensajes.append("simS: " + token + "\n");
        }
        while (!salida) {
            this.simI();
            if (token == Token.EOF) {
                salida = true;
            } else if (token != Token.PYC) {
                mensajes.append("Error-5: Se esperaba ';'\n");
                salida = true;
            } else {
                if (debug) {
                    mensajes.append("simS: " + token + "\n");
                }
                token = lexico.lex();
            }
        }
    }

    /**
     * I -> D I -> id=E
     */
    private void simI() {
        //SI-1
        Numero numero = new Numero();

        //S21 --> Codigo inter
        Cuadruplo cua = new Cuadruplo();

        //Ensamblador
        Ensamblador e = new Ensamblador();

        Simbolo sim = new Simbolo();
        //S19
        Simbolo id;
        if (token == Token.INT || token == Token.DOUBLE || token == Token.FLOAT) {
            if (debug) {
                mensajes.append("simI: " + token + "\n");
            }
            this.simD();
        } else if (token == Token.ID) {
            //S
            Main.sTemp.initSimTemporal();

            //S20
            id = Main.tSim.buscar(lexico.lexema);

            if (debug) {
                mensajes.append("simI: " + token + "\n");
            }
            token = lexico.lex();
            if (token == Token.ASIGNA) {
                if (debug) {
                    mensajes.append("simI: " + token + "\n");
                }
                token = lexico.lex();

                this.simE(sim, numero);

                //SI-2
                id.setValor(numero);
                //S21
                if (id.getTipo() != sim.getTipo()) {
                    mensajes.append("Error en la asignación tipos diferentes " + "\n");
                }

                //S22 --> Codigo inter
                if (sim.getNombre() == null) {
                    cua.arg1 = sim.getValor().getStringValor();
                } else {
                    cua.arg1 = sim.getNombre();
                }
                cua.resultado = id.getNombre();

                codigo.append(cua.toString() + "\n");
                e.resultado = id.getNombre();
                ensamblador.append(e.toString() + "\n");

            } else {
                if (debug) {
                    mensajes.append("simI: " + token + "\n");
                }
                mensajes.append("Error-7: Se esperaba operador '='\n");
            }
        } else {
            if (debug) {
                mensajes.append("simI: " + token + "\n");
            }
            if (token != Token.EOF) {
                mensajes.append("Error-6: Se esperaba 'variable' o 'int'\n");
            }
        }
    }

    /**
     * Analisis sintactico del simbolo D, desclaraciones tipo int
     *
     * D -> int K L->id K K->, L K->lamda
     *
     */
    private void simD() {
        boolean salida = false;
        //S1:
        Token tipo;

        if (debug) {
            mensajes.append(token + "\n");
        }
        if (token == Token.INT || token == Token.DOUBLE || token == Token.BYTE || token == Token.FLOAT) {
            /* LLegando a siguiente estado*/
            //S2
            tipo = token;
            //S11
            Main.declaraciones = true;

            while (!salida) {
                token = lexico.lex();
                if (debug) {
                    mensajes.append(token + "\n");
                }
                if (token == Token.ID) {
                    /* LLegando a siguiente estado*/
                    //S3
                    Simbolo s = Main.tSim.buscar(lexico.lexema);
                    if (s != null) {
                        s.setTipo(tipo);
                    }

                    token = lexico.lex();
                    if (debug) {
                        mensajes.append(token + "\n");
                    }
                    if (token != Token.COMA) // Ir al estado final
                    {
                        salida = true;
                    }
                } else {
                    mensajes.append("Error-2: Se esperaba 'variable'\n");
                    salida = true;
                }
            }
            //S12
            Main.declaraciones = false;
        } else {
            mensajes.append("Error-1: Se esperaba palabra reservada 'int'\n");
        }

    }

    /**
     * Analisis sintactico del simbolo F.
     *
     * F -> (E) F -> id F -> num
     *
     */
    private void simF(Simbolo sim, Numero numero) {
        if (debug) {
            mensajes.append("simF: " + token + "\n");
        }
        if (token == Token.ID) {

            Simbolo s = Main.tSim.buscar(lexico.lexema);
            sim.setNombre(s.getNombre());
            sim.setTipo(s.getTipo());

            //SI-3
            numero.setValor(s.getValor().getDoubleValor());

            //S21
            token = lexico.lex();
            if (debug) {
                mensajes.append("simF: " + token + "\n");
            }
        } else if (token == Token.NUM) {
            //SI-4
            numero.setValor(lexico.lexema);

            sim.setTipo(Token.INT);

            Numero num = new Numero();
            num.setValor(lexico.lexema);
            sim.setValor(num);
            sim.setNombre(null);

            token = lexico.lex();
            if (debug) {
                mensajes.append("simF: " + token + "\n");
            }
        } else if (token == Token.PIZQ) {
            token = lexico.lex();
            if (debug) {
                mensajes.append("simF: " + token + "\n");
            }
            Simbolo simA = new Simbolo();

            this.simE(simA, numero);
            //SI-5 numero solo se pasa como argumento

            //S
            sim.setNombre(simA.getNombre());
            sim.setValor(simA.getValor());

            sim.setTipo(simA.getTipo());

            if (token == Token.PDER) {
                token = lexico.lex();
                if (debug) {
                    mensajes.append("simF: " + token + "\n");
                }
            } else {
                mensajes.append("Error-4: Se esperaba ')'\n");
            }
        } else {
            mensajes.append("Error-3: Se esperaba 'num, variable o ('\n");
        }
    }

    /**
     * Analisis sintactico del simbolo T.
     *
     * T -> FT' T' -> *FT' T' -> lamda
     *
     */
    private void simT(Simbolo sim, Numero numero) {
        //SI-9
        Numero numeroPrevio = new Numero();
        //S30
        String op = "*";
        //S16
        boolean comparar = false;
        Simbolo simPrevio = new Simbolo();

        boolean salida = false;
        if (debug) {
            mensajes.append("simT: " + token + "\n");
        }
        while (!salida) {
            this.simF(sim, numero);
            //S17
            if (comparar) {
                if (simPrevio.getTipo() != sim.getTipo()) {
                    mensajes.append("Error-8: Tipos diferentes en la expresión\n");
                }
                //S31
                this.codigoMultiDivi(simPrevio, sim, op);
                //ensamblador
                String resultado = sim.getNombre();
                this.EnsambladorMultiplicacionDivision(simPrevio, sim, op, resultado);
                sim.setNombre(simPrevio.getNombre());
                sim.setValor(simPrevio.getValor());

                //SI-10
                this.interpreteMultiDivi(numeroPrevio, numero, op);
                numero.setValor(numeroPrevio.getDoubleValor());

            }
            if (token == Token.OPARIT) {
                if (lexico.lexema.equals("*") || lexico.lexema.equals("/")) {
                    //SI-11
                    numeroPrevio.setValor(numero.getDoubleValor());
                    //S31
                    op = lexico.lexema;
                    simPrevio.setNombre(sim.getNombre());
                    simPrevio.setValor(sim.getValor());

                    //S18
                    comparar = true;
                    simPrevio.setTipo(sim.getTipo());
                    token = lexico.lex();
                    if (debug) {
                        mensajes.append("simT: " + token + "\n");
                    }
                } else {
                    salida = true;
                }
            } else {
                salida = true;
            }
        }
    }

    /**
     * Analisis sintactico del simbolo E.
     *
     * E -> TE' E' -> +TE' E' -> lamda
     *
     */
    private void simE(Simbolo sim, Numero numero) {
        //SI-6
        Numero numeroPrevio = new Numero();

        //S
        String op = "+";

        //S13
        boolean comparar = false;
        Simbolo simPrevio = new Simbolo();

        boolean salida = false;
        if (debug) {
            mensajes.append("simE: " + token + "\n");
        }
        while (!salida) {
            this.simT(sim, numero);
            //S14
            if (comparar) {
                if (simPrevio.getTipo() != sim.getTipo()) {
                    mensajes.append("Error-8: Tipos diferentes en la expresión\n");
                }
                this.codigoSumaResta(simPrevio, sim, op);
                //codigo ensamblador
                //S31
                String resultado = sim.getNombre();
                this.EnsambladorSumaResta(simPrevio, sim, op, resultado);
                sim.setNombre(simPrevio.getNombre());
                sim.setValor(simPrevio.getValor());

                //SI-7
                this.interpreteSumaResta(numeroPrevio, numero, op);
                numero.setValor(numeroPrevio.getDoubleValor());
            }
            if (token == Token.OPARIT) {
                if (lexico.lexema.equals("+") || lexico.lexema.equals("-")) {
                    //SI-8
                    numeroPrevio.setValor(numero.getDoubleValor());

                    //S
                    op = lexico.lexema;
                    simPrevio.setNombre(sim.getNombre());
                    simPrevio.setValor(sim.getValor());

                    //S15
                    comparar = true;
                    simPrevio.setTipo(sim.getTipo());

                    token = lexico.lex();
                    if (debug) {
                        mensajes.append("simE: " + token + "\n");
                    }
                } else {
                    salida = true;
                }
            } else {
                salida = true;

            }
        }
    }

    public StringBuffer getMensajes() {
        return this.mensajes;
    }

    public StringBuffer getCodigo() {
        return this.codigo;
    }

    public StringBuffer getEnsamblador() {
        return this.ensamblador;
    }

    public void codigoMultiDivi(Simbolo simPrevio, Simbolo sim, String op) {

        Cuadruplo c = new Cuadruplo();
        c.resultado = Main.sTemp.getEtiqueta();
        if (simPrevio.getNombre() == null) {
            c.arg1 = simPrevio.getValor().getStringValor();
        } else {
            c.arg1 = simPrevio.getNombre();
        }
        if (sim.getNombre() == null) {
            c.arg2 = sim.getValor().getStringValor();
        } else {
            c.arg2 = sim.getNombre();
        }
        c.op = op;
        codigo.append(c.toString() + "\n");
        simPrevio.setNombre(c.resultado);

    }

    public void EnsambladorMultiplicacionDivision(Simbolo simPrevio, Simbolo sim, String op, String resultado) {
        Ensamblador e = new Ensamblador();
        e.resultado = resultado;
        if (simPrevio.getNombre() != null) {
            e.arg1 = simPrevio.getValor().getStringValor();
        } else {
            e.arg1 = simPrevio.getNombre();
        }
        if (sim.getNombre() == null) {
            e.arg2 = sim.getValor().getStringValor();
        } else {
            e.arg2 = sim.getNombre();
        }
        e.op = op;
        ensamblador.append(e.toString() + "\n");
        simPrevio.setNombre(e.resultado);
    }

    public void codigoSumaResta(Simbolo simPrevio, Simbolo sim, String op) {
        Cuadruplo c = new Cuadruplo();
        c.resultado = Main.sTemp.getEtiqueta();
        if (simPrevio.getNombre() == null) {
            c.arg1 = simPrevio.getValor().getStringValor();
        } else {
            c.arg1 = simPrevio.getNombre();
        }
        if (sim.getNombre() == null) {
            c.arg2 = sim.getValor().getStringValor();
        } else {
            c.arg2 = sim.getNombre();
        }
        c.op = op;
        codigo.append(c.toString() + "\n");
        simPrevio.setNombre(c.resultado);
    }

    public void EnsambladorSumaResta(Simbolo simPrevio, Simbolo sim, String op, String resultado) {
        Ensamblador e = new Ensamblador();
        e.resultado = resultado;
        if (simPrevio.getNombre() != null) {
            e.arg1 = simPrevio.getValor().getStringValor();
        } else {
            e.arg1 = simPrevio.getNombre();
        }
        if (sim.getNombre() == null) {
            e.arg2 = sim.getValor().getStringValor();
        } else {
            e.arg2 = sim.getNombre();
        }
        e.op = op;
        ensamblador.append(e.toString() + "\n");
        simPrevio.setNombre(e.resultado);
    }

    //SI-9
    private void interpreteSumaResta(Numero numeroPrevio, Numero numero, String op) {
        if (op.equals("+")) {
            numeroPrevio.setValor(numeroPrevio.getDoubleValor() + numero.getDoubleValor());
        } else {
            numeroPrevio.setValor(numeroPrevio.getDoubleValor() - numero.getDoubleValor());
        }
    }

    private void interpreteMultiDivi(Numero numeroPrevio, Numero numero, String op) {
        if (op.equals("*")) {
            numeroPrevio.setValor(numeroPrevio.getDoubleValor() * numero.getDoubleValor());
        } else {
            numeroPrevio.setValor(numeroPrevio.getDoubleValor() / numero.getDoubleValor());
        }
    }
}

