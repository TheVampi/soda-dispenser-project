package com.dispensador.automata;

import com.dispensador.modelo.Moneda;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal que implementa el Autómata Finito Determinista (AFD)
 * M = (Q, Σ, δ, q0, F)
 */
public class AutomataAFD {
    private Estado estadoActual;
    private final TablaTransiciones tablaTransiciones;
    private final List<String> historial;

    /**
     * Constructor del autómata
     * Inicializa el autómata en el estado inicial q0
     */
    public AutomataAFD() {
        this.estadoActual = Estado.Q0;
        this.tablaTransiciones = new TablaTransiciones();
        this.historial = new ArrayList<>();
        registrarEvento("Autómata inicializado en " + estadoActual);
    }

    /**
     * Procesa la inserción de una moneda
     * Ejecuta la transición δ(q, σ) = q'
     * @param moneda Moneda insertada
     * @return true si la transición fue exitosa
     */
    public boolean insertarMoneda(Moneda moneda) {
        Estado estadoAnterior = estadoActual;
        Estado estadoNuevo = tablaTransiciones.obtenerTransicion(estadoActual, moneda);

        if (estadoNuevo == null) {
            registrarEvento("❌ Moneda " + moneda + " rechazada - Excedería el máximo de $25");
            return false;
        }

        estadoActual = estadoNuevo;
        registrarEvento("✓ Transición: " + estadoAnterior + " + " + moneda + " → " + estadoActual);
        return true;
    }

    /**
     * Reinicia el autómata al estado inicial
     */
    public void reiniciar() {
        estadoActual = Estado.Q0;
        registrarEvento("♻ Autómata reiniciado a " + estadoActual);
    }

    /**
     * Verifica si el autómata está en un estado final
     * @return true si está en un estado final (puede comprar)
     */
    public boolean estaEnEstadoFinal() {
        return estadoActual.esFinal();
    }

    /**
     * Obtiene el saldo acumulado actual
     * @return Saldo en pesos
     */
    public int getSaldoActual() {
        return estadoActual.getSaldo();
    }

    /**
     * Obtiene el estado actual del autómata
     * @return Estado actual
     */
    public Estado getEstadoActual() {
        return estadoActual;
    }

    /**
     * Verifica si se puede comprar un producto con el saldo actual
     * @param precioProducto Precio del producto
     * @return true si hay suficiente saldo
     */
    public boolean puedaComprar(int precioProducto) {
        return getSaldoActual() >= precioProducto;
    }

    /**
     * Calcula el cambio a devolver
     * @param precioProducto Precio del producto comprado
     * @return Cantidad de cambio
     */
    public int calcularCambio(int precioProducto) {
        return getSaldoActual() - precioProducto;
    }

    /**
     * Realiza la compra de un producto
     * @param precioProducto Precio del producto
     * @return Cambio devuelto
     */
    public int realizarCompra(int precioProducto) {
        if (!puedaComprar(precioProducto)) {
            registrarEvento("❌ Compra rechazada - Saldo insuficiente");
            return -1;
