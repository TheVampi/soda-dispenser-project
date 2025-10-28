package com.dispensador.automata;


/*
 Enum que representa todos los estados del autómata
 */
public enum Estado {
    Q0(0, false),   // Estado inicial
    Q1(1, false),
    Q2(2, false),
    Q3(3, false),
    Q4(4, false),
    Q5(5, false),
    Q6(6, false),
    Q7(7, false),
    Q8(8, false),
    Q9(9, false),
    Q10(10, false),
    Q11(11, false),
    Q12(12, false),
    Q13(13, false),
    Q14(14, false),
    Q15(15, true),  // Estados finales (pueden comprar productos)
    Q16(16, true),
    Q17(17, true),
    Q18(18, true),
    Q19(19, true),
    Q20(20, true),
    Q21(21, true),
    Q22(22, true),
    Q23(23, true),
    Q24(24, true),
    Q25(25, true);  // Estado final máximo

    private final int saldo;
    private final boolean esFinal;


    Estado(int saldo, boolean esFinal) {
        this.saldo = saldo;
        this.esFinal = esFinal;
    }

    public int getSaldo() {
        return saldo;
    }

    public boolean esFinal() {
        return esFinal;
    }


    public static Estado getEstadoPorSaldo(int saldo) {
        for (Estado estado : Estado.values()) {
            if (estado.getSaldo() == saldo) {
                return estado;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name() + " ($" + saldo + ")" + (esFinal ? " [FINAL]" : "");
    }
}