# **Lunar Breakout**

### **Team Members**

* *Benjamin Mast*
* *Vanessa Senethong*

### **Java Version**

This project was developed and tested using **Java 24**.

---

## **Overview**

**Lunar Breakout** is an arcade-style JavaFX game where the player pilots a ship to survive waves of asteroids and enemy AI ships.

---

## **Design Patterns Used**

### **1. Strategy Pattern — Enemy AI Movement**

Each AI ship is given a movement strategy object, allowing different behaviors without changing the ship class.

### **2. Builder Pattern — AIShip Construction**

AI ships are created using a builder that configures all of its attributes.

### **3. Factory Pattern — Game Object Creation**

A central `GameObjectFactory` creates all objects in the world.

### **4. State Pattern — Game World Flow**

The game world uses separate states:

* **Running**
* **Paused**
* **Game Over**

### **5. Singleton Pattern — ResourceManager**

A `ResourceManager` singleton ensures that all images, sprites, and shared assets are loaded only once and accessed globally, preventing duplicate loading and reducing memory use.

---

## **How to Run**

1. Ensure you have **Java 24** and **JavaFX 21** installed.
2. Clone the repository:

   ```sh
   git clone <repo-url>
   ```
3. Run the game using Gradle:

   ```sh
   ./gradlew run
   ```

