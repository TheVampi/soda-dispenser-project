package com.dispensador.automata;

import com.dispensador.modelo.Moneda;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que implementa la función de transición δ del autómata
 * δ: Q × Σ → Q
 */
public class TablaTransiciones {
    private final Map<String, Estado> transiciones;

    public TablaTransiciones() {
        transiciones = new HashMap<>();
        inicializarTransiciones();
    }

    /**
     * Inicializa todas las transiciones del autómata
     * Genera todas las combinaciones posibles de estados y monedas
     */
    private void inicializarTransiciones() {
        // Para cada estado
        for (Estado estadoActual : Estado.values()) {
            int saldoActual = estadoActual.getSaldo();

            // Para cada moneda
            for (Moneda moneda : Moneda.values()) {
                int nuevoSaldo = saldoActual + moneda.getValor();

                // Si el nuevo saldo no excede el máximo (25)
                if (nuevoSaldo <= 25) {
                    Estado estadoDestino = Estado.getEstadoPorSaldo(nuevoSaldo);
                    String clave = generarClave(estadoActual, moneda);
                    transiciones.put(clave, estadoDestino);
                }
            }
        }
    }

    /**
     * Obtiene el estado destino dada una transición
     * @param estadoActual Estado actual del autómata
     * @param moneda Moneda insertada
     * @return Estado destino o null si la transición no existe
     */
    public Estado obtenerTransicion(Estado estadoActual, Moneda moneda) {
        String clave = generarClave(estadoActual, moneda);
        return transiciones.get(clave);
    }

    /**
     * Verifica si existe una transición válida
     * @param estadoActual Estado actual
     * @param moneda Moneda a insertar
     * @return true si la transición existe
     */
    public boolean esTransicionValida(Estado estadoActual, Moneda moneda) {
        String clave = generarClave(estadoActual, moneda);
        return transiciones.containsKey(clave);
    }

    /**
     * Genera una clave única para identificar una transición
     * @param estado Estado actual
     * @param moneda Moneda insertada
     * @return Clave en formato "ESTADO_MONEDA"
     */
    private String generarClave(Estado estado, Moneda moneda) {
        return estado.name() + "_" + moneda.getValor();
    }

    /**
     * Imprime todas las transiciones del autómata (para debugging)
     */
    public void imprimirTransiciones() {
        System.out.println("=== TABLA DE TRANSICIONES δ ===");
        for (Estado estado : Estado.values()) {
            System.out.println("\nDesde " + estado + ":");
            for (Moneda moneda : Moneda.values()) {
                Estado destino = obtenerTransicion(estado, moneda);
                if (destino != null) {
                    System.out.println("  + " + moneda + " → " + destino);
                } else {
                    System.out.println("  + " + moneda + " → RECHAZADO (excede máximo)");
                }
            }
        }
    }

    /**
     * Obtiene el número total de transiciones definidas
     * @return Cantidad de transiciones
     */
    public int getTotalTransiciones() {
        return transiciones.size();
    }
}