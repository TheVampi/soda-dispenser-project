package com.dispensador.automata;

import com.dispensador.modelo.Moneda;
import java.util.HashMap;
import java.util.Map;

/*
  Clase que implementa la función de transición δ del autómata
  δ: Q × Σ → Q
 */
public class TablaTransiciones {
    private final Map<String, Estado> transiciones;

    public TablaTransiciones() {
        transiciones = new HashMap<>();
        inicializarTransiciones();
    }


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


    public Estado obtenerTransicion(Estado estadoActual, Moneda moneda) {
        String clave = generarClave(estadoActual, moneda);
        return transiciones.get(clave);
    }


    public boolean esTransicionValida(Estado estadoActual, Moneda moneda) {
        String clave = generarClave(estadoActual, moneda);
        return transiciones.containsKey(clave);
    }


    private String generarClave(Estado estado, Moneda moneda) {
        return estado.name() + "_" + moneda.getValor();
    }


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


    public int getTotalTransiciones() {
        return transiciones.size();
    }
}