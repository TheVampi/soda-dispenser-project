package com.dispensador.modelo;

/**
 * Clase que representa un producto disponible en el dispensador
 */
public class Producto {
    private final String id;
    private final String nombre;
    private final int precio;
    private int stock;
    private final String descripcion;

    public Producto(String id, String nombre, int precio, int stock, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean hayStock() {
        return stock > 0;
    }

    public void reducirStock() {
        if (stock > 0) {
            stock--;
        }
    }

    public void incrementarStock(int cantidad) {
        this.stock += cantidad;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - $%d (%s) - Stock: %d",
                id, nombre, precio, descripcion, stock);
    }

    /**
     * Inicializa el catálogo de productos disponibles
     * @return Array con todos los productos
     */
    public static Producto[] inicializarCatalogo() {
        return new Producto[] {
                new Producto("P1", "Coca-Cola 600mL", 20, 10, "600mL"),
                new Producto("P2", "Coca-Cola Lata 355mL", 25, 8, "355mL"),
                new Producto("P3", "Sprite 600mL", 18, 12, "600mL"),
                new Producto("P4", "Fanta 600mL", 17, 15, "600mL"),
                new Producto("P5", "FuzeTea 600mL", 18, 10, "600mL"),
                new Producto("P6", "Sidral Mundet 600mL", 19, 9, "600mL"),
                new Producto("P7", "Agua Ciel 1L", 15, 20, "1 Litro"),
                new Producto("P8", "Predator Gold Lata", 17, 7, "Lata")
        };
    }
}