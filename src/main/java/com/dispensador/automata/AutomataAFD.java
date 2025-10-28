package com.dispensador.automata;

import com.dispensador.modelo.Moneda;
import java.util.ArrayList;
import java.util.List;

/*
 M = (Q, Σ, δ, q0, F)
 */
public class AutomataAFD {
    private Estado estadoActual;
    private final TablaTransiciones tablaTransiciones;
    private final List<String> historial;

    /*
     Inicializa el autómata en el estado inicial q0
     */
    public AutomataAFD() {
        this.estadoActual = Estado.Q0;
        this.tablaTransiciones = new TablaTransiciones();
        this.historial = new ArrayList<>();
        registrarEvento("Autómata inicializado en " + estadoActual);
    }


    public boolean insertarMoneda(Moneda moneda) {
        Estado estadoAnterior = estadoActual;
        Estado estadoNuevo = tablaTransiciones.obtenerTransicion(estadoActual, moneda);

        if (estadoNuevo == null) {
            registrarEvento("Moneda " + moneda + " rechazada - Excederia el maximo de $25");
            return false;
        }

        estadoActual = estadoNuevo;
        registrarEvento("Transicion: " + estadoAnterior + " + " + moneda + " -> " + estadoActual);
        return true;
    }


    public void reiniciar() {
        estadoActual = Estado.Q0;
        registrarEvento("Automata reiniciado a " + estadoActual);
    }


    public boolean estaEnEstadoFinal() {
        return estadoActual.esFinal();
    }


    public int getSaldoActual() {
        return estadoActual.getSaldo();
    }


    public Estado getEstadoActual() {
        return estadoActual;
    }


    public boolean puedaComprar(int precioProducto) {
        return getSaldoActual() >= precioProducto;
    }


    public int calcularCambio(int precioProducto) {
        return getSaldoActual() - precioProducto;
    }


    public int realizarCompra(int precioProducto) {
        if (!puedaComprar(precioProducto)) {
            registrarEvento("Compra rechazada - Saldo insuficiente");
            return -1;
        }

        int cambio = calcularCambio(precioProducto);
        registrarEvento("Compra realizada - Producto: $" + precioProducto + " - Cambio: $" + cambio);
        reiniciar();
        return cambio;
    }


    private void registrarEvento(String evento) {
        historial.add(evento);
    }


    public List<String> getHistorial() {
        return new ArrayList<>(historial);
    }


    public void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL DE TRANSICIONES ===");
        for (String evento : historial) {
            System.out.println(evento);
        }
        System.out.println("==================================\n");
    }


    public void imprimirHistorial() {
        mostrarHistorial();
    }


    public String getInformacion() {
        StringBuilder info = new StringBuilder();
        info.append("\n").append("=".repeat(60)).append("\n");
        info.append("ESTADO DEL AUTÓMATA (AFD)\n");
        info.append("=".repeat(60)).append("\n");
        info.append("Estado actual: ").append(estadoActual).append("\n");
        info.append("Saldo acumulado: $").append(getSaldoActual()).append("\n");
        info.append("Estado final: ").append(estaEnEstadoFinal() ? "SI (Puede comprar)" : "NO (Necesita mas dinero)").append("\n");
        info.append("Saldo minimo para comprar: $15\n");
        info.append("Saldo máximo permitido: $25\n");
        info.append("=".repeat(60)).append("\n");
        return info.toString();
    }


    public void imprimirTablaTransiciones() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("TABLA DE TRANSICIONES DEL AUTÓMATA δ(q, σ)");
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.printf("%-10s | %-10s | %-10s | %-10s | %-10s%n",
                "Estado", "$1", "$2", "$5", "$10");
        System.out.println("-".repeat(80));

        for (Estado estado : Estado.values()) {
            System.out.printf("%-10s | ", estado);

            for (Moneda moneda : Moneda.values()) {
                Estado siguiente = tablaTransiciones.obtenerTransicion(estado, moneda);
                String transicion = (siguiente != null) ? siguiente.toString() : "---";
                System.out.printf("%-10s | ", transicion);
            }

            System.out.println();
        }

        System.out.println("=".repeat(80));
        System.out.println("Estados finales (F): Q15, Q16, Q17, Q18, Q19, Q20, Q21, Q22, Q23, Q24, Q25");
        System.out.println("=".repeat(80));
        System.out.println();
    }


    public int cancelarTransaccion() {
        int saldoDevuelto = getSaldoActual();

        if (saldoDevuelto > 0) {
            registrarEvento("Transaccion cancelada - Devolviendo: $" + saldoDevuelto);
            reiniciar();
        }

        return saldoDevuelto;
    }
}
