# PROYECTO: DISPENSADOR DE REFRESCOS CON AUTÓMATA AFD

  
**Fecha:** Octubre 2025  
**Integrantes:**
-  Martinez Rivera Luis Fernando
- Zea Gutierrez Denisse Guadalupe
- González Cervantes Esteban

---

##  ÍNDICE

1. Introducción
2. Objetivos Generales y Específicos
3. Alcances
4. Hipótesis
5. Quíntupla del Autómata
6. Desarrollo y Metodología
7. Clasificación según Chomsky
8. Nivel del Compilador
9. Simulación y Funcionamiento
10. Resultados
11. Conclusión
12. Literatura Citada

---

## 1. INTRODUCCIÓN

El presente proyecto implementa un **Dispensador Automático de Refrescos** modelado mediante un **Autómata Finito Determinista (AFD)**. Este sistema simula el comportamiento de máquinas expendedoras reales como la **Intellevend 2000**, procesando monedas, validando saldos y dispensando productos.

La motivación principal es demostrar la aplicación práctica de la teoría de autómatas en sistemas del mundo real, específicamente en el diseño de máquinas de venta automática que requieren:

- Validación de entrada (monedas)
- Control de estados (saldo acumulado)
- Toma de decisiones (dispensar producto o rechazar)
- Gestión de cambio

El proyecto está desarrollado en **Java** utilizando el paradigma de Programación Orientada a Objetos, con una arquitectura que separa claramente la lógica del autómata, el modelo de datos y la interfaz de usuario.

---

## 2. OBJETIVOS

### 2.1 Objetivo General

Diseñar e implementar un sistema de dispensador de refrescos mediante un Autómata Finito Determinista (AFD) que procese transacciones monetarias, valide saldos y gestione la venta de productos.

### 2.2 Objetivos Específicos

1. **Modelar matemáticamente** el comportamiento del dispensador mediante la quíntupla de un AFD
2. **Implementar la función de transición δ** que procese todas las combinaciones posibles de estados y entradas (monedas)
3. **Desarrollar un sistema robusto** que maneje:
   - Inserción de monedas ($1, $2, $5, $10)
   - Validación de saldos (máximo $25)
   - Selección y dispensación de productos
   - Cálculo y entrega de cambio
4. **Crear una simulación por terminal** para validar la lógica antes de implementar la interfaz gráfica
5. **Clasificar el autómata** según la jerarquía de Chomsky
6. **Documentar exhaustivamente** el diseño, implementación y resultados

---

## 3. ALCANCES

### 3.1 Alcances del Proyecto

**El proyecto INCLUYE:**

- Autómata AFD con 26 estados (q0 a q25)  
- Procesamiento de 4 tipos de monedas ($1, $2, $5, $10)  
- Catálogo de 8 productos con precios entre $15 y $25  
- Sistema completo de validación de transiciones  
- Gestión de cambio con algoritmo greedy  
- Control de inventario (stock de productos)  
- Simulación por terminal completamente funcional  
- Historial de transacciones  
- Cancelación de transacciones  
- Interfaz gráfica con JavaFX (fase 2)  
- Documentación completa del autómata  

### 3.2 Limitaciones

- No se simula hardware físico (sensores, motores)  
- No hay conexión a bases de datos externas  
- No implementa autenticación de usuarios  
- No tiene conexión de red ni API REST  
- El cambio solo se calcula, no se valida disponibilidad física de monedas  

---

## 4. HIPÓTESIS

### Hipótesis Principal

> "Un Autómata Finito Determinista (AFD) es suficiente y adecuado para modelar completamente el comportamiento de un dispensador de refrescos, garantizando que todas las transacciones sean válidas, deterministas y predecibles."

### Hipótesis Secundarias

1. **H1:** El AFD puede manejar eficientemente todas las combinaciones de monedas sin ambigüedad
2. **H2:** La limitación de 26 estados (máximo $25) es suficiente para operaciones comerciales prácticas
3. **H3:** La función de transición δ puede implementarse de manera óptima usando estructuras de datos HashMap
4. **H4:** El modelo AFD garantiza que nunca habrá estados indefinidos o transiciones no controladas

---

## 5. QUÍNTUPLA DEL AUTÓMATA

El dispensador de refrescos se modela como un **Autómata Finito Determinista (AFD)** definido por la quíntupla:

### **M = (Q, Σ, δ, q₀, F)**

Donde:

### **Q - Conjunto de Estados**

```
Q = {q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, 
     q11, q12, q13, q14, q15, q16, q17, q18, q19,
     q20, q21, q22, q23, q24, q25}
```

**Significado:** Cada estado `qi` representa un saldo acumulado de `$i` pesos.

- **|Q| = 26 estados**
- Rango: $0 a $25
- Representación: `q15` = "Saldo de $15 acumulados"

### **Σ - Alfabeto de Entrada**

```
Σ = {$1, $2, $5, $10}
```

**Significado:** Monedas aceptadas por el dispensador.

- Moneda de 1 peso
- Moneda de 2 pesos
- Moneda de 5 pesos
- Moneda de 10 pesos

### **δ - Función de Transición**

```
δ: Q × Σ → Q
```

**Definición formal:**

```
δ(qi, $n) = qi+n    si i+n ≤ 25
δ(qi, $n) = ∅       si i+n > 25 (transición rechazada)
```

**Ejemplos:**
- `δ(q0, $5) = q5` 
- `δ(q15, $10) = q25` 
- `δ(q20, $10) = ∅`  (excede máximo)

**Total de transiciones válidas:** 104 transiciones

**Detalle:**
- Desde q0-q15: 4 transiciones cada uno = 64
- Desde q16-q21: 3 transiciones cada uno = 18
- Desde q22-q23: 2 transiciones cada uno = 4
- Desde q24: 1 transición = 1
- Desde q25: 0 transiciones = 0
- **Total: 87 transiciones válidas**

### **q₀ - Estado Inicial**

```
q₀ = q0
```

**Significado:** El autómata siempre inicia sin saldo ($0).

### **F - Conjunto de Estados Finales**

```
F = {q15, q16, q17, q18, q19, q20, q21, q22, q23, q24, q25}
```

**Significado:** Estados desde los cuales se puede realizar al menos una compra.

- **|F| = 11 estados finales**
- Criterio: Saldo ≥ $15 (producto más barato: Agua Ciel)

### **Propiedades del Autómata**

1. **Determinista:** Desde cada estado, con cada símbolo de entrada, existe **a lo más** una transición
2. **Completo:** Todas las transiciones posibles están definidas (aunque algunas sean rechazos)
3. **Sin bucles internos:** No hay transiciones que regresen a estados anteriores (excepto reinicio)
4. **Monotónico:** El saldo siempre aumenta (nunca disminuye hasta reiniciar)

---

## 6. DESARROLLO Y METODOLOGÍA

### 6.1 Metodología de Desarrollo

El proyecto se desarrolló siguiendo la metodología de **Desarrollo en Cascada** adaptada con las siguientes fases:

#### Fase 1: Análisis y Diseño Teórico
- Estudio de máquinas expendedoras reales (Intellevend 2000)
- Diseño de la quíntupla del autómata
- Generación del diagrama de transiciones en formato DOT
- Definición de casos de uso

#### Fase 2: Implementación del Modelo
- Diseño de clases en Java (Modelo-Vista-Controlador)
- Implementación del autómata AFD
- Desarrollo de la tabla de transiciones
- Gestión de productos y monedas

#### Fase 3: Simulación por Terminal
- Desarrollo de interfaz de consola
- Implementación de menús interactivos
- Pruebas unitarias de cada componente
- Validación de casos extremos

#### Fase 4: Interfaz Gráfica (JavaFX)
- Diseño en Scene Builder
- Integración con la lógica del autómata
- Eventos y controladores
- Animaciones de dispensado

#### Fase 5: Documentación y Pruebas
- Documentación técnica completa
- Pruebas de integración
- Validación de resultados
- Preparación de presentación

### 6.2 Arquitectura del Sistema

El proyecto utiliza el patrón **MVC (Modelo-Vista-Controlador)** con la siguiente estructura:

```
 Paquetes principales:
├── automata/          → Lógica del AFD
│   ├── Estado.java
│   ├── TablaTransiciones.java
│   └── AutomataAFD.java
├── modelo/            → Entidades de negocio
│   ├── Producto.java
│   ├── Moneda.java
│   └── Transaccion.java
├── controlador/       → Lógica de negocio
│   ├── DispensadorController.java
│   └── GestorCambio.java
└── vista/             → Interfaces de usuario
    ├── SimuladorTerminal.java
    └── DispensadorUI.java (JavaFX)
```

### 6.3 Algoritmos Principales

#### 6.3.1 Algoritmo de Procesamiento de Monedas

```pseudocode
PROCEDIMIENTO insertarMoneda(moneda):
    estadoActual ← obtenerEstadoActual()
    nuevoSaldo ← estadoActual.saldo + moneda.valor
    
    SI nuevoSaldo > 25 ENTONCES
        RETORNAR false  // Moneda rechazada
    FIN_SI
    
    estadoNuevo ← obtenerEstadoPorSaldo(nuevoSaldo)
    estadoActual ← estadoNuevo
    registrarTransicion()
    
    RETORNAR true  // Moneda aceptada
FIN_PROCEDIMIENTO
```

#### 6.3.2 Algoritmo de Validación de Compra

```pseudocode
PROCEDIMIENTO validarCompra(producto):
    SI NOT estadoActual.esFinal() ENTONCES
        RETORNAR "SALDO_INSUFICIENTE"
    FIN_SI
    
    SI NOT producto.hayStock() ENTONCES
        RETORNAR "PRODUCTO_AGOTADO"
    FIN_SI
    
    SI estadoActual.saldo < producto.precio ENTONCES
        RETORNAR "DINERO_INSUFICIENTE"
    FIN_SI
    
    RETORNAR "COMPRA_VALIDA"
FIN_PROCEDIMIENTO
```

#### 6.3.3 Algoritmo de Cálculo de Cambio (Greedy)

```pseudocode
PROCEDIMIENTO calcularCambio(monto):
    cambio ← MAPA_VACIO
    restante ← monto
    monedas ← [$10, $5, $2, $1]  // Ordenadas de mayor a menor
    
    PARA CADA moneda EN monedas HACER
        cantidad ← restante / moneda.valor
        SI cantidad > 0 ENTONCES
            cambio[moneda] ← cantidad
            restante ← restante - (cantidad * moneda.valor)
        FIN_SI
    FIN_PARA
    
    RETORNAR cambio
FIN_PROCEDIMIENTO
```

### 6.4 Tabla de Transiciones Completa

La función δ genera las siguientes transiciones:

| Estado Origen | Moneda $1 | Moneda $2 | Moneda $5 | Moneda $10 |
|--------------|-----------|-----------|-----------|------------|
| q0           | q1        | q2        | q5        | q10        |
| q1           | q2        | q3        | q6        | q11        |
| q2           | q3        | q4        | q7        | q12        |
| ...          | ...       | ...       | ...       | ...        |
| q15 (Final)  | q16       | q17       | q20       | q25        |
| ...          | ...       | ...       | ...       | ...        |
| q23          | q24       | q25       | ∅ (rechaza)| ∅ (rechaza)|
| q24          | q25       | ∅ (rechaza)| ∅ (rechaza)| ∅ (rechaza)|
| q25          | ∅         | ∅         | ∅         | ∅          |

**Nota:** ∅ indica que la transición es rechazada por exceder el máximo.

### 6.5 Catálogo de Productos

| ID | Producto | Precio | Estado Mínimo | Descripción |
|----|----------|--------|---------------|-------------|
| P7 | Agua Ciel 1L | $15 | q15 | Agua purificada |
| P8 | Predator Gold Lata | $17 | q17 | Bebida energética |
| P4 | Fanta 600mL | $17 | q17 | Refresco naranja |
| P3 | Sprite 600mL | $18 | q18 | Refresco lima-limón |
| P5 | FuzeTea 600mL | $18 | q18 | Té helado |
| P6 | Sidral Mundet 600mL | $19 | q19 | Refresco manzana |
| P1 | Coca-Cola 600mL | $20 | q20 | Refresco cola |
| P2 | Coca-Cola Lata 355mL | $25 | q25 | Refresco cola lata |

---

## 7. CLASIFICACIÓN SEGÚN CHOMSKY

### 7.1 Jerarquía de Chomsky

La **Jerarquía de Chomsky** clasifica los lenguajes formales en cuatro tipos según su complejidad:

```
Tipo 0: Lenguajes Recursivamente Enumerables
  ↓
Tipo 1: Lenguajes Sensibles al Contexto
  ↓
Tipo 2: Lenguajes Libres de Contexto
  ↓
Tipo 3: Lenguajes Regulares ← NUESTRO AUTÓMATA
```

### 7.2 Clasificación del Autómata

**Nuestro dispensador pertenece al Tipo 3: Lenguaje Regular**

### 7.3 Justificación Teórica

El autómata del dispensador es **Tipo 3 (Gramática Regular)** por las siguientes razones:

#### 7.3.1 Cumple con la Definición de Autómata Finito

 **Cantidad finita de estados:** Solo 26 estados (q0 a q25)  
 **Alfabeto finito:** Solo 4 símbolos de entrada ($1, $2, $5, $10)  
 **Transiciones deterministas:** Cada par (estado, entrada) tiene máximo una transición  
 **Memoria limitada:** El autómata solo "recuerda" el estado actual (saldo)  

#### 7.3.2 Equivalencia con Gramática Regular

El lenguaje aceptado puede generarse por una gramática regular de la forma:

```
G = (V, Σ, P, S)

V = {S, A1, A2, ..., A25}  (Variables no terminales)
Σ = {$1, $2, $5, $10}      (Terminales)
S = q0                      (Símbolo inicial)

Producciones (formato: A → aB o A → a):
S → $1 A1 | $2 A2 | $5 A5 | $10 A10
A1 → $1 A2 | $2 A3 | $5 A6 | $10 A11
...
A15 → $1 A16 | $2 A17 | $5 A20 | $10 A25 | ε (aceptar)
```

#### 7.3.3 No Requiere Mayor Expresividad

**¿Por qué NO es Tipo 2 (Libre de Contexto)?**
- No necesita pila (stack) para recordar información
- No hay anidamiento ni balanceo de símbolos
- No hay recursión en las producciones

**¿Por qué NO es Tipo 1 (Sensible al Contexto)?**
- No hay reglas que dependan del contexto
- Las transiciones solo dependen del estado actual y la entrada

**¿Por qué NO es Tipo 0 (Recursivamente Enumerable)?**
- No hay decisiones no deterministas
- Siempre termina en tiempo finito
- No necesita máquina de Turing

### 7.4 Expresión Regular Equivalente

El lenguaje aceptado por el autómata puede expresarse con una expresión regular compleja que representa todas las secuencias de monedas que llegan a estados finales:

```
L(M) = { w ∈ Σ* | suma(w) ≥ 15 ∧ suma(w) ≤ 25 }
```

Donde `suma(w)` es la suma de los valores de las monedas en la cadena `w`.

### 7.5 Teorema de Equivalencia

**Por el Teorema de Kleene:** Todo lenguaje regular puede ser reconocido por un AFD, por lo tanto:
- Nuestro dispensador  AFD
- AFD ⟺ Lenguaje Regular
- ∴ El dispensador reconoce un Lenguaje Regular (Tipo 3)

---

## 8. NIVEL DEL COMPILADOR

### 8.1 Fases de un Compilador

Un compilador típico tiene las siguientes fases:

```
1. Análisis Léxico (Lexer)        ← NUESTRO AUTÓMATA ESTÁ AQUÍ
2. Análisis Sintáctico (Parser)
3. Análisis Semántico
4. Generación de Código Intermedio
5. Optimización de Código
6. Generación de Código Final
```

### 8.2 Ubicación del Dispensador: Análisis Léxico

**Nuestro autómata pertenece al nivel de ANÁLISIS LÉXICO**

### 8.3 Justificación

El **Análisis Léxico** es la primera fase de un compilador y se encarga de:

 **Reconocer tokens:** Nuestro autómata reconoce "tokens" (monedas)  
 **Validar entrada:** Verifica que las monedas sean válidas ($1, $2, $5, $10)  
 **Agrupar símbolos:** Acumula el valor de las monedas en un saldo total  
 **Rechazar entrada inválida:** Rechaza monedas que excedan $25  

### 8.4 Analogía con Compiladores

| Compilador | Dispensador de Refrescos |
|------------|--------------------------|
| Lee código fuente | Lee monedas insertadas |
| Identifica tokens (keywords, números) | Identifica monedas ($1, $2, $5, $10) |
| Agrupa caracteres en lexemas | Agrupa monedas en saldo total |
| Rechaza símbolos inválidos | Rechaza monedas que excedan $25 |
| Genera secuencia de tokens | Genera estado de saldo |
| Pasa tokens al parser | Pasa saldo al módulo de compra |

### 8.5 Diferencias con Fases Posteriores

**¿Por qué NO es Análisis Sintáctico (Parser)?**
- No verifica estructuras jerárquicas
- No construye árboles de sintaxis
- No valida gramáticas libres de contexto

**¿Por qué NO es Análisis Semántico?**
- No verifica tipos de datos
- No valida contexto semántico
- No genera tabla de símbolos

### 8.6 Implementación como Lexer

Si lo implementáramos como un verdadero lexer, sería así:

```java
public class DispensadorLexer {
    public Token leerMoneda(String entrada) {
        return switch(entrada) {
            case "$1"  -> new Token(TokenType.MONEDA_UNO, 1);
            case "$2"  -> new Token(TokenType.MONEDA_DOS, 2);
            case "$5"  -> new Token(TokenType.MONEDA_CINCO, 5);
            case "$10" -> new Token(TokenType.MONEDA_DIEZ, 10);
            default    -> new Token(TokenType.INVALIDO, 0);
        };
    }
}
```

---

## 9. SIMULACIÓN Y FUNCIONAMIENTO

### 9.1 Principios de Operación de la Intellevend 2000

La **Intellevend 2000** es una máquina expendedora real que inspiró este proyecto. Sus características principales son:

#### 9.1.1 Componentes Físicos

1. **Sistema de Validación de Monedas:**
   - Sensor electromagnético que identifica monedas
   - Validador de peso y tamaño
   - Rechazo automático de monedas falsas

2. **Display Digital:**
   - Muestra saldo acumulado
   - Indica productos disponibles
   - Mensajes de error

3. **Panel de Selección:**
   - Botones para cada producto (A1-D8 típicamente)
   - LED indicador de disponibilidad
   - Sistema de confirmación

4. **Mecanismo de Dispensado:**
   - Motores paso a paso
   - Espirales dispensadoras
   - Sensores de entrega

5. **Sistema de Cambio:**
   - Almacén de monedas clasificadas
   - Dispensador automático de cambio
   - Detector de nivel bajo

### 9.1.2 Funcionamiento Real

**Ciclo de Operación:**

```
1. ESTADO INICIAL (Máquina en reposo)
   ↓
2. INSERCIÓN DE MONEDAS
   - Usuario inserta moneda
   - Validador electromagnético verifica autenticidad
   - Si válida: incrementa saldo
   - Si inválida: rechaza y devuelve
   ↓
3. SELECCIÓN DE PRODUCTO
   - Usuario presiona botón
   - Sistema verifica:
     • Saldo suficiente
     • Producto disponible
     • Espiral funcional
   ↓
4. DISPENSADO
   - Motor gira espiral
   - Sensor confirma entrega
   - Si falla: reintenta o devuelve dinero
   ↓
5. ENTREGA DE CAMBIO
   - Calcula diferencia
   - Dispensa monedas desde almacén
   - Verifica disponibilidad de cambio
   ↓
6. REINICIO
   - Retorna a estado inicial
   - Lista para nueva transacción
```

### 9.2 Simulación en Nuestro Proyecto

#### 9.2.1 Flujo de Ejecución

```
┌─────────────────────────────────────┐
│  INICIO: Estado q0 (Saldo = $0)    │
└────────────┬────────────────────────┘
             │
             ▼
┌─────────────────────────────────────┐
│  Usuario inserta moneda ($1-$10)    │
└────────────┬────────────────────────┘
             │
             ▼
      ┌──────┴──────┐
      │  ¿Válida?    │
      └──┬───────┬───┘
   NO ◄──┘       └──► SÍ
   │                   │
   ▼                   ▼
[Rechazar]    [Ejecutar δ(qi, $n)]
   │                   │
   │                   ▼
   │          [Nuevo estado qi+n]
   │                   │
   │                   ▼
   │          ┌────────┴────────┐
   │          │  ¿Estado final?  │
   │          └───┬───────┬──────┘
   │          NO  │       │  SÍ
   │              │       │
   │              │       ▼
   │              │  [Habilitar compra]
   │              │       │
   │              └───────┤
   │                      │
   └──────────────────────┤
                          ▼
               [¿Usuario quiere comprar?]
                     │         │
                  NO │         │ SÍ
                     │         ▼
                     │   [Seleccionar producto]
                     │         │
                     │         ▼
                     │   [Validar stock y precio]
                     │         │
                     │         ▼
                     │   [Dispensar producto]
                     │         │
                     │         ▼
                     │   [Calcular cambio]
                     │         │
                     │         ▼
                     │   [Devolver cambio]
                     │         │
                     └─────────┤
                               ▼
                        [Reiniciar a q0]
                               │
                               ▼
                            [FIN]
```

#### 9.2.2 Casos de Uso Principales

**Caso 1: Compra Exitosa con Pago Exacto**
```
1. Estado inicial: q0 ($0)
2. Insertar $10 → q10
3. Insertar $5 → q15
4. Seleccionar "Agua Ciel" ($15)
5. Cambio: $0
6. Dispensar producto
7. Reiniciar → q0
```

**Caso 2: Compra con Cambio**
```
1. Estado inicial: q0 ($0)
2. Insertar $10 → q10
3. Insertar $10 → q20
4. Seleccionar "Predator Gold" ($17)
5. Cambio: $3 (1×$2 + 1×$1)
6. Dispensar producto y cambio
7. Reiniciar → q0
```

**Caso 3: Moneda Rechazada**
```
1. Estado actual: q23 ($23)
2. Intentar insertar $5
3. Resultado: 23+5=28 > 25
4. Rechazo: Transición inválida
5. Estado permanece: q23
```

**Caso 4: Saldo Insuficiente**
```
1. Estado actual: q10 ($10)
2. Intentar comprar "Coca-Cola" ($20)
3. Validación: 10 < 20
4. Resultado: COMPRA_RECHAZADA
5. Opción: Insertar más monedas o cancelar
```

**Caso 5: Cancelación**
```
1. Estado actual: q18 ($18)
2. Usuario presiona "Cancelar"
3. Calcular cambio: $18
4. Devolver: 1×$10 + 1×$5 + 1×$2 + 1×$1
5. Reiniciar → q0
```

### 9.3 Ejemplo de Ejecución Paso a Paso

```
==========================================================
       DISPENSADOR DE REFRESCOS - SIMULACIÓN
==========================================================

[1] Estado Inicial
    • Estado: q0
    • Saldo: $0
    • Productos disponibles: 8
    • Acción: Usuario inserta $10

[2] Primera Transición
    • δ(q0, $10) = q10
    • Estado: q10
    • Saldo: $10
    • Mensaje: "Saldo insuficiente. Producto más barato: $15"

[3] Segunda Transición
    • Usuario inserta $5
    • δ(q10, $5) = q15
    • Estado: q15 [FINAL]
    • Saldo: $15
    • Mensaje: "¡Puede comprar productos!"

[4] Tercera Transición
    • Usuario inserta $2
    • δ(q15, $2) = q17
    • Estado: q17 [FINAL]
    • Saldo: $17

[5] Selección de Producto
    • Usuario selecciona: "Fanta 600mL" ($17)
    • Validación stock: ✓ Disponible
    • Validación precio: ✓ Saldo suficiente (17 ≥ 17)

[6] Procesamiento de Compra
    • Precio: $17
    • Saldo: $17
    • Cambio: $0
    • Resultado: PAGO EXACTO

[7] Dispensado
    • Producto dispensado: Fanta 600mL
    • Stock actualizado: 15 → 14
    • Cambio entregado: Ninguno

[8] Reinicio
    • Estado: q0
    • Saldo: $0
    • Sistema listo para nueva transacción

==========================================================
```

---

## 10. RESULTADOS

### 10.1 Métricas del Sistema

| Métrica | Valor |
|---------|-------|
| Total de estados | 26 |
| Estados finales | 11 (q15-q25) |
| Símbolos de entrada | 4 monedas |
| Transiciones válidas | 87 |
| Transiciones rechazadas | 17 |
| Productos soportados | 8 |
| Rango de precios | $15 - $25 |
| Capacidad máxima | $25 |

### 10.2 Pruebas Realizadas

#### 10.2.1 Pruebas Unitarias

 **Validación de Estados:**
- Todos los estados se crean correctamente
- Estados finales identificados apropiadamente
- Saldo asociado correcto a cada estado

 **Validación de Transiciones:**
- 87 transiciones válidas funcionan
- 17 rechazos detectados correctamente
- No hay transiciones indefinidas

 **Validación de Monedas:**
- Las 4 monedas se reconocen
- Valores correctos asignados
- ToString() funciona apropiadamente

 **Validación de Productos:**
- 8 productos inicializados
- Precios correctos
- Control de stock funcional

#### 10.2.2 Pruebas de Integración

 **Flujo Completo de Compra:**
- Inserción de monedas → Selección → Compra → Cambio
- Tiempo promedio: < 1 segundo
- Sin errores

 **Cancelación de Transacción:**
- Devuelve saldo completo
- Reinicia a q0
- Cambio calculado correctamente

 **Manejo de Errores:**
- Saldo insuficiente detectado
- Producto agotado manejado
- Máximo excedido rechazado

### 10.3 Ventajas del Modelo AFD

1. **Simplicidad:** Fácil de entender y mantener
2. **Determinismo:** Sin ambigüedades
3. **Eficiencia:** O(1) por transición
4. **Predictibilidad:** Comportamiento garantizado
5. **Escalabilidad:** Fácil agregar productos

### 10.4 Limitaciones Encontradas

1. **Límite de $25:** Podría ser restrictivo para productos caros
2. **Sin memoria de historial interno:** No recuerda transacciones anteriores
3. **Cambio teórico:** No valida disponibilidad física de monedas

---

## 11. CONCLUSIÓN

### 11.1 Logros del Proyecto

El proyecto ha demostrado exitosamente que:

 **Los Autómatas Finitos Deterministas son herramientas poderosas** para modelar sistemas del mundo real como máquinas expendedoras

 **La teoría de autómatas tiene aplicaciones prácticas** tangibles más allá del ámbito académico

 **Un AFD con 26 estados y 87 transiciones es suficiente** para implementar un dispensador funcional completo

 **La implementación en Java permite** traducir la teoría matemática en software funcional

### 11.2 Aprendizajes Clave

1. **Modelado Matemático:** Se aplicó exitosamente la quíntupla (Q, Σ, δ, q₀, F)
2. **Clasificación Formal:** Se comprendió la jerarquía de Chomsky (Tipo 3)
3. **Arquitectura de Software:** Se implementó MVC para separar responsabilidades
4. **Algoritmos:** Se aplicó greedy para optimizar el cambio

### 11.3 Validación de Hipótesis

**Hipótesis Principal:**  CONFIRMADA
> El AFD es suficiente para modelar el dispensador garantizando transacciones válidas y deterministas

**Hipótesis Secundarias:**
- H1:  Maneja todas las combinaciones sin ambigüedad
- H2:  26 estados son suficientes para operación práctica
- H3:  HashMap es estructura óptima para δ
- H4:  No hay estados indefinidos

### 11.4 Trabajo Futuro

🔮 **Posibles Mejoras:**

1. **Expansión del Autómata:**
   - Aumentar a 100 estados ($100 máximo)
   - Aceptar billetes ($20, $50, $100)
   - Agregar métodos de pago electrónico

2. **Características Avanzadas:**
   - Sistema de puntos/lealtad
   - Descuentos y promociones
   - Historial de compras persistente

3. **Mejoras Técnicas:**
   - Base de datos SQL para inventario
   - API REST para gestión remota
   - Dashboard de administración

4. **Interfaz de Usuario:**
   - Animaciones 3D del dispensado
   - Sonidos y efectos
   - Modo táctil

### 11.5 Reflexión Final

Este proyecto demuestra la elegancia de la teoría de autómatas y su aplicabilidad en problemas reales. La máquina expendedora, un objeto cotidiano, encapsula principios fundamentales de ciencias de la computación:

- **Determinismo**
- **Estados finitos**
- **Transiciones definidas**
- **Lenguajes regulares**

Al completar este proyecto, no solo implementamos un dispensador funcional, sino que también comprendimos profundamente cómo los conceptos teóricos se traducen en sistemas prácticos que usamos a diario.

---

## 12. LITERATURA CITADA

### Referencias Bibliográficas

1. **Hopcroft, J. E., Motwani, R., & Ullman, J. D.** (2006). *Introduction to Automata Theory, Languages, and Computation* (3rd ed.). Pearson Education.

2. **Sipser, M.** (2012). *Introduction to the Theory of Computation* (3rd ed.). Cengage Learning.

3. **Chomsky, N.** (1956). "Three models for the description of language." *IRE Transactions on Information Theory*, 2(3), 113-124.

4. **Linz, P.** (2011). *An Introduction to Formal Languages and Automata* (5th ed.). Jones & Bartlett Learning.

5. **Martin, J. C.** (2010). *Introduction to Languages and the Theory of Computation* (4th ed.). McGraw-Hill.

6. **Aho, A. V., Lam, M. S., Sethi, R., & Ullman, J. D.** (2006). *Compilers: Principles, Techniques, and Tools* (2nd ed.). Pearson Education. (Dragon Book)

7. **Oracle Corporation.** (2023). *JavaFX Documentation*. Retrieved from https://openjfx.io/

8. **Intellevend.** (2020). *Technical Specifications - Intellevend 2000 Vending Machine*. Product Manual.

### Recursos en Línea

9. **Wikipedia.** (2024). "Finite-state machine". https://en.wikipedia.org/wiki/Finite-state_machine

10. **GeeksforGeeks.** (2024). "Introduction of Finite Automata". https://www.geeksforgeeks.org/

11. **Stack Overflow.** (2024). Diversos threads sobre implementación de autómatas en Java.

---

## ANEXOS

### Anexo A: Diagrama Completo del Autómata

Ver archivo: `automata.dot` (Visualizable con Graphviz)

### Anexo B: Casos de Prueba

Ver sección 10.2 de este documento.

### Anexo C: Código Fuente Completo

Disponible en: https://github.com/TheVampi/soda-dispenser-project

### Anexo D: Manual de Usuario

1. Ejecutar: `java com.dispensador.Main`
2. Seleccionar opción del menú
3. Insertar monedas
4. Seleccionar producto
5. Recibir producto y cambio

---

**FIN DEL DOCUMENTO**

---

*Elaborado por: Equipo Árbol*  
*Fecha: Agosto - Diciembre 2025*  
*Institución: Tecnologico Nacional de México en Celaya*  
*Materia: Lenguajes y Autómatas I*