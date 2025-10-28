package com.dispensador.terminal;

import com.dispensador.automata.AutomataAFD;
import com.dispensador.controlador.GestorCambio;
import com.dispensador.modelo.Moneda;
import com.dispensador.modelo.Producto;

import java.util.Scanner;

/**
 * Simulador del dispensador por terminal
 * Permite probar toda la lógica del autómata antes de implementar UI
 */
public class SimuladorTerminal {
    private final AutomataAFD automata;
    private final Producto[] catalogo;
    private final Scanner scanner;

    public SimuladorTerminal() {
        this.automata = new AutomataAFD();
        this.catalogo = Producto.inicializarCatalogo();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el simulador
     */
    public void iniciar() {
        mostrarBanner();

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> comprarProducto();
                case 2 -> insertarMonedas();
                case 3 -> mostrarEstadoAutomata();
                case 4 -> mostrarCatalogo();
                case 5 -> mostrarTablaTransiciones();
                case 6 -> verHistorial();
                case 7 -> cancelarTransaccion();
                case 8 -> {
                    System.out.println("\n¡Gracias por usar el dispensador!");
                    continuar = false;
                }
                default -> System.out.println("Opcion invalida");
            }
        }

        scanner.close();
    }

    /**
     * Muestra el banner de bienvenida
     */
    private void mostrarBanner() {
        System.out.println("""
            ╔════════════════════════════════════════════════════════╗
            ║                                                        ║
            ║         DISPENSADOR DE REFRESCOS                       ║
            ║            Simulacion con Automata AFD                 ║
            ║             IntelleVend 2000                           ║
            ║        Proyecto de Lenguajes y autómatas I             ║
            ║                                                        ║
            ╚════════════════════════════════════════════════════════╝
            """);
    }


    private void mostrarMenuPrincipal() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SALDO ACTUAL: $" + automata.getSaldoActual() + " | Estado: " + automata.getEstadoActual());
        System.out.println("=".repeat(60));
        System.out.println("1. Comprar producto");
        System.out.println("2. Insertar monedas");
        System.out.println("3. Ver estado del automata");
        System.out.println("4. Ver historial de transiciones");
        System.out.println("5. Ver tabla de transiciones");
        System.out.println("6. Salir");
        System.out.println("7. Cancelar y devolver dinero");
        System.out.println("8. Salir");
        System.out.print("\nSeleccione una opcion: ");
    }


    private void comprarProducto() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("PROCESO DE COMPRA");
        System.out.println("=".repeat(60));

        if (!automata.estaEnEstadoFinal()) {
            System.out.println("Saldo insuficiente para comprar productos");
            System.out.println("El producto mas barato cuesta $15");
            return;
        }

        mostrarProductosDisponibles();

        System.out.print("\nIngrese el número del producto (0 para cancelar): ");
        int seleccion = leerOpcion();

        if (seleccion == 0) {
            return;
        }

        if (seleccion < 1 || seleccion > catalogo.length) {
            System.out.println("Producto no valido");
            return;
        }

        Producto productoSeleccionado = catalogo[seleccion - 1];

        if (!productoSeleccionado.hayStock()) {
            System.out.println("Producto agotado");
            return;
        }

        if (!automata.puedaComprar(productoSeleccionado.getPrecio())) {
            System.out.println("Saldo insuficiente");
            System.out.println("Saldo actual: $" + automata.getSaldoActual());
            System.out.println("Precio producto: $" + productoSeleccionado.getPrecio());
            System.out.println("Faltan: $" + (productoSeleccionado.getPrecio() - automata.getSaldoActual()));
            return;
        }

        // Realizar compra
        int cambio = automata.realizarCompra(productoSeleccionado.getPrecio());
        productoSeleccionado.reducirStock();

        System.out.println("\nCOMPRA EXITOSA!");
        System.out.println("Producto: " + productoSeleccionado.getNombre());
        System.out.println("Precio: $" + productoSeleccionado.getPrecio());

        if (cambio > 0) {
            GestorCambio.imprimirCambio(cambio);
        } else {
            System.out.println("Pago exacto - Sin cambio");
        }

        System.out.println("\nDisfruta tu bebida!");
    }


    private void insertarMonedas() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INSERTAR MONEDAS");
        System.out.println("=".repeat(60));
        System.out.println("Saldo actual: $" + automata.getSaldoActual());
        System.out.println("\nMonedas disponibles:");
        System.out.println("1. $1");
        System.out.println("2. $2");
        System.out.println("3. $5");
        System.out.println("4. $10");
        System.out.println("0. Regresar");

        boolean insertando = true;
        while (insertando) {
            System.out.print("\nSeleccione moneda a insertar (0 para terminar): ");
            int opcion = leerOpcion();

            if (opcion == 0) {
                insertando = false;
                continue;
            }

            Moneda moneda = switch (opcion) {
                case 1 -> Moneda.UNO_PESO;
                case 2 -> Moneda.DOS_PESOS;
                case 3 -> Moneda.CINCO_PESOS;
                case 4 -> Moneda.DIEZ_PESOS;
                default -> null;
            };

            if (moneda == null) {
                System.out.println("Opcion invalida");
                continue;
            }

            boolean exito = automata.insertarMoneda(moneda);

            if (exito) {
                System.out.println("Moneda aceptada: " + moneda);
                System.out.println("Nuevo saldo: $" + automata.getSaldoActual());

                if (automata.estaEnEstadoFinal()) {
                    System.out.println("Ya puede comprar productos");
                }
            } else {
                System.out.println("Moneda rechazada (excede maximo de $25)");
            }
        }
    }


    private void mostrarEstadoAutomata() {
        System.out.println("\n" + automata.getInformacion());
    }


    private void mostrarCatalogo() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CATÁLOGO DE PRODUCTOS");
        System.out.println("=".repeat(60));
        mostrarProductosDisponibles();
    }


    private void mostrarProductosDisponibles() {
        for (int i = 0; i < catalogo.length; i++) {
            Producto p = catalogo[i];
            String disponibilidad = p.hayStock() ? "OK" : "AGOTADO";
            String comprableCon = automata.puedaComprar(p.getPrecio()) ? "DISPONIBLE" : "SALDO INSUFICIENTE";

            System.out.printf("%d. %-30s $%-3d [%s] [%s] Stock: %d%n",
                    i + 1,
                    p.getNombre() + " " + p.getDescripcion(),
                    p.getPrecio(),
                    disponibilidad,
                    comprableCon,
                    p.getStock()
            );
        }
    }


    private void mostrarTablaTransiciones() {
        System.out.println("\n¿Desea ver la tabla completa? (Puede ser muy larga) [S/N]: ");
        String respuesta = scanner.nextLine().trim().toUpperCase();

        if (respuesta.equals("S")) {
            automata.imprimirTablaTransiciones();
        }
    }


    private void verHistorial() {
        automata.imprimirHistorial();
    }


    private void cancelarTransaccion() {
        int saldoDevuelto = automata.cancelarTransaccion();

        if (saldoDevuelto > 0) {
            System.out.println("\nTransaccion cancelada");
            GestorCambio.imprimirCambio(saldoDevuelto);
        } else {
            System.out.println("\nNo hay saldo para devolver");
        }
    }


    private int leerOpcion() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        SimuladorTerminal simulador = new SimuladorTerminal();
        simulador.iniciar();
    }
}