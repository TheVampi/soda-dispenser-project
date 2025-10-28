package com.dispensador;

import com.dispensador.terminal.SimuladorTerminal;


public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Dispensador de Refrescos...\n");


        SimuladorTerminal simulador = new SimuladorTerminal();
        simulador.iniciar();
    }
5}