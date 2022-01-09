# maze
2D and 3D maze navigation game written in Java, featuring Swing for GUI purposes.

## How it works
The program reads a text file of an ASCII maze. The main elements of the maze are differentiated by *(Asteriks) and ''(Spaces) wherein a * signifies a wall, and '' signifies walkable path. The code then transforms this information into a 2D and 3D experience. The user experience is enhanced with add-ons like markers, teleporters, and move counters.

## Program in Action
Below are images of the program in action

![](https://github.com/arjunUpatel/maze/blob/main/images/maze-startup.png)
This is the starting screen of the game. The red square is the player's location which will move forward when the up arrow is pressed and turn left and right when the left and right arrows are pressed respectively. The goal is to reach the yellow square.

![](https://github.com/arjunUpatel/maze/blob/main/images/maze-teleporter.png)
The player can place one teleporter on the map by pressing the x key. Once the player moves away from that square, pressing x again brings the player back to the square of the teleporter. Pressing x while standing on a placed teleporter removes the teleporter so that it can be placed again.

![](https://github.com/arjunUpatel/maze/blob/main/images/maze-marker.png)
The player can place up to 20 markers on the map by pressing z. Markers cannot be removed from the map.

![](https://github.com/arjunUpatel/maze/blob/main/images/maze-3D.png)
The 2D and 3D modes of the game can be toggled by pressing the space-bar. The 3D mode adds a lot of difficulty due to the absence of a map. Markers and teleporters are extremely important in this mode.

## Dependencies Used
- Java
- Swing
