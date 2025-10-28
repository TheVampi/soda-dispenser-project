package com.dispensador.controlador;

import com.dispensador.modelo.Moneda;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona el cálculo y entrega de cambio
 * Utiliza algoritmo greedy para minimizar cantidad de monedas
 */
public class GestorCambio {

    /**
     * Calcula el desglose de cambio en monedas
     * @param cambio Cantidad total de cambio a devolver
     * @return Map con la cantidad de cada tipo de moneda
     */
    public static Map<Moneda, Integer> calcularDesgloseCambio(int cambio) {
        Map<Moneda, Integer> desglose = new HashMap<>();

        if (cambio <= 0) {
            return desglose;
        }

        int restante = cambio;

        // Monedas ordenadas de mayor a menor valor (greedy)
        Moneda[] monedasOrdenadas = {
                Moneda.DIEZ_PESOS,
                Moneda.CINCO_PESOS,
                Moneda.DOS_PESOS,
                Moneda.UNO_PESO
        };

        for (Moneda moneda : monedasOrdenadas) {
            int cantidad = restante / moneda.getValor();
            if (cantidad > 0) {
                desglose.put(moneda, cantidad);
                restante -= cantidad * moneda.getValor();
            }
        }

        return desglose;
    }

    /**
     * Genera un mensaje descriptivo del cambio
     * @param cambio Cantidad de cambio
     * @return String con el desglose
     */
    public static String generarMensajeCambio(int cambio) {
        if (cambio == 0) {
            return "No hay cambio (pago exacto)";
        }

        Map<Moneda, Integer> desglose = calcularDesgloseCambio(cambio);
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Cambio: $").append(cambio).append("\n");
        mensaje.append("Desglose:\n");

        for (Map.Entry<Moneda, Integer> entry : desglose.entrySet()) {
            mensaje.append("  • ")
                    .append(entry.getValue())
                    .append(" x ")
                    .append(entry.getKey().getDescripcion())
                    .append("\n");
        }

        return mensaje.toString();
    }

    /**
     * Imprime el cambio en formato tabular
     * @param cambio Cantidad de cambio
     */
    public static void imprimirCambio(int cambio) {
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║      CAMBIO A DEVOLVER        ║");
        System.out.println("╠═══════════════════════════════╣");

        if (cambio == 0) {
            System.out.println("║  Pago exacto - Sin cambio     ║");
        } else {
            System.out.printf("║  Total: $%-20d║%n", cambio);
            System.out.println("╠═══════════════════════════════╣");

            Map<Moneda, Integer> desglose = calcularDesgloseCambio(cambio);
            for (Map.Entry<Moneda, Integer> entry : desglose.entrySet()) {
                System.out.printf("║  %d x %-23s║%n",
                        entry.getValue(),
                        entry.getKey().getDescripcion()
                );
            }
        }

        System.out.println("╚═══════════════════════════════╝");
    }
}