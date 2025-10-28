# Soda Dispenser Project - DFA Automaton Simulator

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![JavaFX](https://img.shields.io/badge/JavaFX-21-green.svg)](https://openjfx.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Phase%201-brightgreen.svg)](https://github.com)

A theoretical computer science project that simulates a **soda vending machine** using a **Deterministic Finite Automaton (DFA)**. This project demonstrates practical applications of automata theory in real-world systems.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Project Phases](#project-phases)
- [Automaton Specifications](#automaton-specifications)
- [Product Catalog](#product-catalog)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technical Details](#technical-details)
- [Examples](#examples)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

This project implements a **soda vending machine simulator** modeled as a **Deterministic Finite Automaton (DFA)**. The system processes coin inputs, validates balances, dispenses products, and calculates change automatically.

**Academic Context:**
- **Course:** Languages and Automata I
- **Topic:** Finite Automata and Regular Languages
- **Institution:** Tecnológico Nacional de México en Celaya
- **Date:** Ago-Dec 2025

---

## Features

### Phase 1 - Terminal Version (Work in Progress)

- **26-State DFA** (q0 through q25)
- **Coin Processing:** Accepts $1, $2, $5, and $10 coins
- **8 Products Available** (prices ranging from $15 to $25)
- **Automatic Change Calculation** using greedy algorithm
- **Inventory Management** with stock control
- **Transaction History** with event logging
- **Transaction Cancellation** with refund capability
- **State Validation** ensuring deterministic behavior
- **Complete Transition Table** with 87 valid transitions
- **Interactive Console Interface** for simulation

### Phase 2 - GUI Version (Coming Soon)

- **JavaFX Graphical Interface**
- **Product Images and Visual Displays**
- **Interactive Coin Buttons**
- **Dispensing Animations**
- **Sound Effects**
- **Touch-Screen Ready Design**

---

## Project Phases

### Phase 1: Console Implementation (CURRENT)

**Status:** Work in Progress

**Deliverables:**
- DFA logic fully implemented
- Terminal-based simulator operational
- Complete automaton quintuple documented
- Unit tests for core functionality
- Technical documentation

**Timeline:** October 28-31, 2025

---

### Phase 2: Graphical User Interface (COMING SOON)

**Status:** Planned

**Planned Features:**
- Modern JavaFX interface
- Product images and animations
- Visual state indicators
- Interactive coin insertion
- Real-time balance display
- Dispensing animations
- Admin panel for inventory

**Expected Timeline:** November 2025

---

## Automata Specifications

### DFA Quintuple: M = (Q, Σ, δ, q₀, F)

**Q - States:**
```
Q = {q0, q1, q2, ..., q25}
```
- Each state `qi` represents accumulated balance of `$i`
- Total: 26 states

**Σ - Input Alphabet:**
```
Σ = {$1, $2, $5, $10}
```
- Four coin denominations accepted

**δ - Transition Function:**
```
δ(qi, $n) = qi+n  if i+n ≤ 25
δ(qi, $n) = ∅     if i+n > 25 (rejected)
```
- **Total Valid Transitions:** 87
- **Rejected Transitions:** 17 (exceed maximum)

**q₀ - Initial State:**
```
q₀ = q0 (balance = $0)
```

**F - Final States:**
```
F = {q15, q16, q17, q18, q19, q20, q21, q22, q23, q24, q25}
```
- States from which purchases can be made
- Minimum balance: $15 (cheapest product)

### Transition Table

| Current State | +$1 | +$2 | +$5 | +$10 |
|---------------|-----|-----|-----|------|
| q0            | q1  | q2  | q5  | q10  |
| q1            | q2  | q3  | q6  | q11  |
| q2            | q3  | q4  | q7  | q12  |
| ...           | ... | ... | ... | ...  |
| q15 (Final)   | q16 | q17 | q20 | q25  |
| ...           | ... | ... | ... | ...  |
| q23           | q24 | q25 | ∅   | ∅    |
| q24           | q25 | ∅   | ∅   | ∅    |
| q25           | ∅   | ∅   | ∅   | ∅    |

*∅ indicates transition is rejected (would exceed $25 maximum)*

---

## Product Catalog

| ID | Product | Price | Min. State | Description |
|----|---------|-------|------------|-------------|
| P7 | Ciel Water 1L | $15 | q15 | Purified water |
| P8 | Predator Gold Can | $17 | q17 | Energy drink |
| P4 | Fanta 600mL | $17 | q17 | Orange soda |
| P3 | Sprite 600mL | $18 | q18 | Lemon-lime soda |
| P5 | FuzeTea 600mL | $18 | q18 | Iced tea |
| P6 | Sidral Mundet 600mL | $19 | q19 | Apple soda |
| P1 | Coca-Cola 600mL | $20 | q20 | Cola soda |
| P2 | Coca-Cola Can 355mL | $25 | q25 | Cola can |

**Total Products:** 8  
**Price Range:** $15 - $25  
**Initial Stock:** 7-20 units per product

---

## 🔧 Installation

### Prerequisites

- **Java Development Kit (JDK):** 21 or higher
- **Maven:** 3.9 or higher
- **Git:** For cloning the repository

### Clone the Repository

```bash
git clone https://github.com/TheVampi/soda-dispenser-project.git
cd soda-dispenser-project
```

### Build the Project

```bash
# Download dependencies
mvn clean install

# Compile
mvn compile
```

### Run the Application

```bash
# Using Maven
mvn exec:java -Dexec.mainClass="com.dispensador.Main"

# Or using Java directly
java -cp target/classes com.dispensador.Main
```

---

## Usage

### Starting the Simulator

```bash
mvn exec:java -Dexec.mainClass="com.dispensador.Main"
```

### Main Menu

```
╔════════════════════════════════════════════════════════╗
║        SODA DISPENSER                                  ║
║            DFA Automaton Simulation                    ║
╚════════════════════════════════════════════════════════╝

============================================================
CURRENT BALANCE: $0 | State: Q0
============================================================
1.  Purchase product
2.  Insert coins
3.  View automaton state
4.  View product catalog
5.  View transition table
6.  View history
7.  Cancel and return money
8.  Exit

Select an option: _
```

### Basic Operations

#### 1. Insert Coins
```
Select option: 2
Available coins:
1. $1
2. $2
3. $5
4. $10
0. Return

Select coin to insert: 4
 Coin accepted: $10
 New balance: $10
```

#### 2. Purchase Product
```
Select option: 1

1. [P7] Ciel Water 1L - $15 (1 Liter) - Stock: 20
2. [P8] Predator Gold Can - $17 (Can) - Stock: 7
...

Enter product number: 1
✓ PURCHASE SUCCESSFUL!
 Product: Ciel Water 1L
 Price: $15
✓ Exact payment - No change
```

#### 3. View Automaton State
```
=== DFA AUTOMATON STATE ===
Current state: Q10
Accumulated balance: $10
Is final state: NO
Total transitions: 87
```

---

##  Project Structure

```
soda-dispenser-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/dispensador/
│   │   │       ├── Main.java                    # Main entry point
│   │   │       ├── automata/                    # DFA logic
│   │   │       │   ├── Estado.java              # State enum (q0-q25)
│   │   │       │   ├── TablaTransiciones.java   # Transition function δ
│   │   │       │   └── AutomataAFD.java         # DFA implementation
│   │   │       ├── modelo/                      # Data models
│   │   │       │   ├── Moneda.java              # Coin enum
│   │   │       │   └── Producto.java            # Product class
│   │   │       ├── controlador/                 # Business logic
│   │   │       │   └── GestorCambio.java        # Change calculator
│   │   │       ├── terminal/                    # Console interface
│   │   │       │   └── SimuladorTerminal.java   # Terminal simulator
│   │   │       └── vista/                       # GUI (Phase 2)
│   │   │           └── [Coming soon...]
│   │   └── resources/
│   │       ├── fxml/                            # JavaFX layouts
│   │       ├── css/                             # Stylesheets
│   │       ├── imagenes/                        # Images
│   │       └── docs/
│   │           └── automata.dot                 # DFA diagram
│   └── test/
│       └── java/                                # Unit tests
├── docs/                                        # Documentation
├── pom.xml                                      # Maven configuration
├── .gitignore                                   # Git ignore rules
└── README.md                                    # This file
```

---

##  Technical Details

### Chomsky Hierarchy Classification

**Type 3: Regular Language**

**Justification:**
- Finite number of states (26 states)
- Finite input alphabet (4 symbols)
- Deterministic transitions
- No memory beyond current state
- Can be expressed as regular grammar

### Compiler Level

**Lexical Analysis (Scanner)**

**Reasoning:**
- Recognizes tokens (coins)
- Validates input ($1, $2, $5, $10)
- Groups symbols into balance
- Rejects invalid input
- Passes to next phase (purchase logic)

### Algorithms

#### Greedy Change Algorithm
```java
// Minimizes number of coins returned
Coins[] = {$10, $5, $2, $1}  // Descending order
for each coin in Coins:
    count = change / coin.value
    change = change % coin.value
```

**Time Complexity:** O(n) where n = number of coin types  
**Space Complexity:** O(1)

---

## Examples

### Example 1: Exact Payment

```
Initial State: q0 ($0)
Insert $10 → q10
Insert $5 → q15
Select "Ciel Water" ($15)
Change: $0
Result: Product dispensed
Final State: q0 (reset)
```

### Example 2: Purchase with Change

```
Initial State: q0 ($0)
Insert $10 → q10
Insert $10 → q20
Select "Predator Gold" ($17)
Change: $3 (1×$2 + 1×$1)
Result: Product dispensed + change returned
Final State: q0 (reset)
```

### Example 3: Rejected Coin

```
Current State: q23 ($23)
Attempt to insert $5
Calculation: 23 + 5 = 28 > 25
Result: δ(q23, $5) = ∅ (REJECTED)
State remains: q23
Message: "Coin rejected - Would exceed maximum"
```

### Example 4: Transaction Cancellation

```
Current State: q18 ($18)
User presses "Cancel"
Calculate refund: $18
Return: 1×$10 + 1×$5 + 1×$2 + 1×$1
Final State: q0 (reset)
```

---

##  Roadmap

### Phase 1 - Terminal Version (Work in progress)

-  DFA implementation with 26 states
-  Transition table with 87 transitions
-  Coin processing ($1, $2, $5, $10)
-  8-product catalog
-  Change calculation algorithm
-  Inventory management
-  Transaction history
-  Console-based interface
-  Complete documentation

###  Phase 2 - GUI Version (Comming Soon)

-  JavaFX application architecture
-  FXML layouts design
-  Product images integration
-  CSS styling
-  Interactive coin buttons
-  Visual state indicators
-  Dispensing animations
-  Sound effects
-  Admin panel

###  Phase 3 - Advanced Features (PLANNED)

-  Database integration (H2/SQLite)
-  Sales reports
-  Multi-language support
-  Payment methods (NFC, QR codes)
-  Remote monitoring dashboard
-  API REST for management
-  Mobile app integration

---

##  Contributing

This is an academic project, but suggestions and improvements are welcome!

### How to Contribute

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Follow Java naming conventions
- Add Javadoc comments
- Include unit tests
- Format code with IntelliJ IDEA formatter

---

##  License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

##  Team

**Project Team:**
- Martinez Rivera Luis Fernando - Lead Developer
- Zea Gutierrez Denisse Guadalupe - Documentation
- González Cervantes Esteban - Testing

**Institution:** Tecnológico Nacional de México en Celaya  
**Course:** Languages and Automata I 
**Professor:** Ing. Luis Gerardo López Ruiz
**Semester:** Aug-Dec 2025

---

##  References

1. Hopcroft, J. E., Motwani, R., & Ullman, J. D. (2006). *Introduction to Automata Theory, Languages, and Computation* (3rd ed.).
2. Sipser, M. (2012). *Introduction to the Theory of Computation* (3rd ed.).
3. Chomsky, N. (1956). "Three models for the description of language."
4. Oracle JavaFX Documentation: https://openjfx.io/

---

##  Contact

**Project Repository:** https://github.com/TheVampi/soda-dispenser-project

**Issues & Questions:** [Open an issue](https://github.com/TheVampi/soda-dispenser-project/issues)

---

##  Academic Notes

This project demonstrates:
- Practical application of automata theory
- DFA design and implementation
- Regular language recognition
- State machine modeling
- Object-oriented programming in Java
- Software architecture patterns (MVC)

**Keywords:** Deterministic Finite Automaton, DFA, Regular Languages, Vending Machine, Automata Theory, Theory of Computation, JavaFX, Maven

---

<div align="center">

</div>
