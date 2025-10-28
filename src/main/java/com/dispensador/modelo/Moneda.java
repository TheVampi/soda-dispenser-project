package com.dispensador.modelo;

/**
 * Enum que representa las monedas aceptadas por el dispensador
 * Alfabeto Σ del autómata
 */
public enum Moneda {
    UNO_PESO(1, "1 Peso"),
    DOS_PESOS(2, "2 Pesos"),
    CINCO_PESOS(5, "5 Pesos"),
    DIEZ_PESOS(10, "10 Pesos");

    private final int valor;
    private final String descripcion;

    Moneda(int valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public int getValor() {
        return valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la moneda por su valor numérico
     * @param valor Valor de la moneda
     * @return Moneda correspondiente o null si no existe
     */
    public static Moneda getMonedaPorValor(int valor) {
        for (Moneda moneda : Moneda.values()) {
            if (moneda.getValor() == valor) {
                return moneda;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "$" + valor;
    }
}