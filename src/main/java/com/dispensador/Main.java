package com.dispensador;

import com.dispensador.terminal.SimuladorTerminal;

/**
 * Clase principal del proyecto
 * Punto de entrada de la aplicación
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Dispensador de Refrescos...\n");

        // Por ahora ejecutamos la versión terminal
        // Más adelante se agregará la versión JavaFX
        SimuladorTerminal simulador = new SimuladorTerminal();
        simulador.iniciar();
    }
}