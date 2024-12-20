# Space-Shooter-Game
# PlanGame

PlanGame is a 2D space shooter game built using Java Swing. The game features a player-controlled spaceship, rockets as enemies, and bullets to destroy them. The game includes animations, collision detection, and explosion effects, making it visually engaging.

---

## Features

- **Player Movement**: Smooth controls to navigate the player spaceship.
- **Bullets**: Fire bullets to destroy incoming rockets.
- **Rockets**: Enemy rockets spawn at random locations.
- **Collision Detection**: Rockets and bullets collide accurately.
- **Explosion Effects**: Dynamic explosion visuals on collisions.
- **Frame Rate**: Runs at 60 FPS for a smooth experience.

---

## Getting Started

### Prerequisites

- JDK 8 or higher installed.
- IDE or text editor for Java (e.g., IntelliJ IDEA, Eclipse, or VS Code).

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/PlanGame.git
   ```

2. Navigate to the project directory:

   ```bash
   cd PlanGame
   ```

3. Compile and run the program:

   ```bash
   javac src/Game/Main/*.java
   java src/Game/Main/PlanGame
   ```

---

## How to Play

1. **Start the Game**: Run the `PlanGame` class to launch the game.
2. **Controls**:
   - `A`: Move left
   - `D`: Move right
   - `Space`: Speed up
   - `J`: Fire standard bullets
   - `K`: Fire powerful bullets
3. **Objective**: Destroy the rockets before they cross the screen. Each collision triggers an explosion effect.

---

## Code Structure

- **Main Components**:
  - `Player`: Represents the player's spaceship.
  - `Rocket`: Represents enemy rockets.
  - `Bullet`: Represents bullets fired by the player.
  - `Effect`: Handles explosion visuals.
- **Game Loop**:
  - Runs at 60 FPS.
  - Updates game objects, checks collisions, and renders graphics.
- **Collision Detection**:
  - Uses `java.awt.geom.Area` for precise collision detection.

---

## Screenshots

*(Add images or GIFs here to showcase gameplay)*

---

## Future Improvements

- Add sound effects for shooting and explosions.
- Introduce more enemy types with unique behaviors.
- Implement a scoring system.
- Create different levels with increasing difficulty.

---

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Acknowledgments

- Java Swing for GUI development.
- Inspiration from classic space shooter games.

