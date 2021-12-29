import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MazeProject extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    JFrame frame;
    int moveCounter;
    boolean canDo;
    int c;
    int r;
    int dir;
    int size2D;
    int size3D;
    int shrink;
    int markerCount;
    int teleporterCount;
    int teleportR;
    int teleportC;
    int dirAtTele;
    boolean draw2D;
    Explorer explorer;
    BufferedReader reader;
    ArrayList<Wall> walls;
    char[][] maze = new char[30][70];
    Font font;

    public MazeProject() {
        canDo = true;
        c = 500;
        r = 500;
        dir = 2;
        size3D = 50;
        size2D = 10;
        shrink = 50;
        markerCount = 0;
        teleporterCount = 0;
        teleportR = 0;
        teleportC = 0;
        draw2D = true;
        font = new Font("arial", Font.BOLD, 20);
        explorer = new Explorer(new Location(2, 2), dir, size2D, new Color(252, 118, 106));
        walls = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(new File("MazeFile.txt")));
            String text = "";
            int i = 0;
            while ((text = reader.readLine()) != null) {
                maze[i] = text.toCharArray();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame = new JFrame("A-Mazing Program");
        frame.setSize(1000, 600);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (draw2D)
            drawMaze2D(g);
        else
            drawMaze3D(g);
    }

    public void keyPressed(KeyEvent e) {
        if (canDo) {
            if (e.getKeyCode() == 38) {
                int previousR = explorer.getLocation().getRow();
                int previousC = explorer.getLocation().getCol();
                explorer.move(e.getKeyCode(), maze);
                if (explorer.getLocation().getRow() != previousR || explorer.getLocation().getCol() != previousC) {
                    moveCounter++;
                }
                repaint();
            }
            if (e.getKeyCode() == 39) {
                explorer.turnRight();
                repaint();
            }
            if (e.getKeyCode() == 37) {
                explorer.turnLeft();
                repaint();
            }
            if (e.getKeyCode() == 32) {
                draw2D = !draw2D;
                repaint();
            }
            if (e.getKeyCode() == 90) {
                if (markerCount < 20 && maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] != 'z') {
                    maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] = 'z';
                    markerCount++;
                } else {

                    if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] == 'z') {
                        System.out.println("Marker already placed in location");
                    }
                    if (markerCount == 20) {

                    }
                }
                repaint();
            }
            if (e.getKeyCode() == 88) {

                if (teleporterCount == 0
                        && maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] != 'x') {
                    maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] = 'x';
                    teleporterCount++;
                    teleportR = explorer.getLocation().getRow();
                    teleportC = explorer.getLocation().getCol();
                    dirAtTele = explorer.getDirection();
                } else if (teleporterCount == 1
                        && maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] == 'x') {
                    maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] = ' ';
                    teleporterCount--;
                    teleportR = 0;
                    teleportC = 0;
                }
                if (teleporterCount == 1
                        && maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] != 'x') {
                    explorer.getLocation().setRow(teleportR);
                    explorer.getLocation().setCol(teleportC);
                    explorer.setDirection(dirAtTele);
                }
                repaint();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void drawMaze2D(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        // if(maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()]=='s'){

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(.1f));
        g2.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] == ' ') {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(col * size2D, row * size2D, size2D, size2D);
                }

                else if (maze[row][col] == 's') {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(col * size2D, row * size2D, size2D, size2D);
                }

                else if (maze[row][col] == 'e') {
                    g2.setColor(Color.YELLOW);
                    g2.fillRect(col * size2D, row * size2D, size2D, size2D);
                }

                else if (maze[row][col] == 'z') {
                    g2.setColor(Color.CYAN);
                    g2.fillRect(col * size2D, row * size2D, size2D, size2D);
                }

                else if (maze[row][col] == 'x') {
                    g2.setColor(Color.ORANGE);
                    g2.fillRect(col * size2D, row * size2D, size2D, size2D);
                }

                else {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.drawRect(col * size2D, row * size2D, size2D, size2D);
                }
            }
        }
        g2.setColor(Color.RED);
        g2.drawRect(explorer.getLocation().getCol() * size2D, explorer.getLocation().getRow() * size2D, size2D, size2D);
        g2.fillRect(explorer.getLocation().getCol() * size2D, explorer.getLocation().getRow() * size2D, size2D, size2D);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Number of moves:" + moveCounter, 700, 50);
        g2.drawString("Z to place marker", 600, 560);
        g2.drawString("X to place teleporter", 600, 500);
        g2.drawString("X to remove to placed teleporter", 600, 540);
        g2.drawString("X to teleport to placed teleporter", 600, 520);
        if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] == 'e') {
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString("You made it out of the maze!", 500, 400);
            canDo = false;
        }
        if (markerCount == 20) {
            g2.drawString("All markers placed", 700, 70);
        }
        if (teleporterCount == 1) {
            g2.drawString("All teleporters placed", 700, 90);
            g2.drawString("Return to teleporter to", 700, 110);
            g2.drawString("remove", 700, 130);
        }
    }

    public void drawMaze3D(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Polygon p;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        switch (explorer.getDirection()) {
            case 0:
                for (int front = 4; front >= 0; front--) {
                    try {
                        p = new Polygon(getLeftPath(front).getCols(), getLeftPath(front).getRows(), 4);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getRightPath(front).getCols(), getRightPath(front).getRows(), 4);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getTopLeftTriangles(front).getCols(), getTopLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol() - 1] == ' '
                                || maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                        - 1] == 'z'
                                || maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                        - 1] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomLeftTriangles(front).getCols(),
                                getBottomLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol() - 1] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                - 1] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                - 1] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getTopRightTriangles(front).getCols(), getTopRightTriangles(front).getRows(),
                                3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol() + 1] == ' '
                                || maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                        + 1] == 'z'
                                || maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                        + 1] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomRightTriangles(front).getCols(),
                                getBottomRightTriangles(front).getRows(), 3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol() + 1] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                + 1] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()
                                + 1] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getCeiling(front).getCols(), getCeiling(front).getRows(), 4);
                        g2.setPaint(getCeiling(front).getCeilingPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getFloor(front).getCols(), getFloor(front).getRows(), 4);
                        if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - front][explorer.getLocation()
                                .getCol()] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else {
                            g2.setPaint(getFloor(front).getFloorPaint(front));
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        if (maze[explorer.getLocation().getRow() - front][explorer.getLocation().getCol()] == '*') {
                            p = new Polygon(getFront(front).getCols(), getFront(front).getRows(), 4);
                            g2.setColor(
                                    new Color(getFront(front).getR(), getFront(front).getG(), getFront(front).getB()));
                            g2.fillPolygon(p);
                        }

                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    }
                }
                break;
            case 1:
                for (int front = 4; front >= 0; front--) {
                    try {
                        p = new Polygon(getLeftPath(front).getCols(), getLeftPath(front).getRows(), 4);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getRightPath(front).getCols(), getRightPath(front).getRows(), 4);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getTopLeftTriangles(front).getCols(), getTopLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol() + front] == ' '
                                || maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                        + front] == 'z'
                                || maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                        + front] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomLeftTriangles(front).getCols(),
                                getBottomLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol() + front] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                + front] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                + front] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }
                        p = new Polygon(getTopRightTriangles(front).getCols(), getTopRightTriangles(front).getRows(),
                                3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol() + front] == ' '
                                || maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                        + front] == 'z'
                                || maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                        + front] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }
                        p = new Polygon(getBottomRightTriangles(front).getCols(),
                                getBottomRightTriangles(front).getRows(), 3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol() + front] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                + front] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                + front] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getCeiling(front).getCols(), getCeiling(front).getRows(), 4);
                        g2.setPaint(getCeiling(front).getCeilingPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getFloor(front).getCols(), getFloor(front).getRows(), 4);
                        if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol() + front] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()
                                + front] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else {
                            g2.setPaint(getFloor(front).getFloorPaint(front));
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol() + front] == '*') {

                            p = new Polygon(getFront(front).getCols(), getFront(front).getRows(), 4);
                            g2.setColor(
                                    new Color(getFront(front).getR(), getFront(front).getG(), getFront(front).getB()));
                            g2.fillPolygon(p);
                        }

                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    }
                }
                break;
            case 2:
                for (int front = 4; front >= 0; front--) {
                    try {
                        p = new Polygon(getLeftPath(front).getCols(), getLeftPath(front).getRows(), 4);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getRightPath(front).getCols(), getRightPath(front).getRows(), 4);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getTopLeftTriangles(front).getCols(), getTopLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol() + 1] == ' '
                                || maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                        + 1] == 'z'
                                || maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                        + 1] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomLeftTriangles(front).getCols(),
                                getBottomLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol() + 1] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                + 1] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                + 1] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getTopRightTriangles(front).getCols(), getTopRightTriangles(front).getRows(),
                                3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol() - 1] == ' '
                                || maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                        - 1] == 'z'
                                || maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                        - 1] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomRightTriangles(front).getCols(),
                                getBottomRightTriangles(front).getRows(), 3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol() - 1] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                - 1] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()
                                - 1] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getCeiling(front).getCols(), getCeiling(front).getRows(), 4);
                        g2.setPaint(getCeiling(front).getCeilingPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getFloor(front).getCols(), getFloor(front).getRows(), 4);
                        if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() + front][explorer.getLocation()
                                .getCol()] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else {
                            g2.setPaint(getFloor(front).getFloorPaint(front));
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }
                        if (maze[explorer.getLocation().getRow() + front][explorer.getLocation().getCol()] == '*') {
                            p = new Polygon(getFront(front).getCols(), getFront(front).getRows(), 4);
                            g2.setColor(
                                    new Color(getFront(front).getR(), getFront(front).getG(), getFront(front).getB()));
                            g2.fillPolygon(p);
                        }

                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    }
                }
                break;
            case 3:
                for (int front = 4; front >= 0; front--) {
                    try {
                        p = new Polygon(getLeftPath(front).getCols(), getLeftPath(front).getRows(), 4);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getRightPath(front).getCols(), getRightPath(front).getRows(), 4);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getTopLeftTriangles(front).getCols(), getTopLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol() - front] == ' '
                                || maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                        - front] == 'z'
                                || maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                        - front] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomLeftTriangles(front).getCols(),
                                getBottomLeftTriangles(front).getRows(), 3);
                        g2.setPaint(getLeftPath(front).getLeftPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol() - front] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                - front] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        else if (maze[explorer.getLocation().getRow() + 1][explorer.getLocation().getCol()
                                - front] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getTopRightTriangles(front).getCols(), getTopRightTriangles(front).getRows(),
                                3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        g2.drawPolygon(p);
                        if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol() - front] == ' '
                                || maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                        - front] == 'z'
                                || maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                        - front] == 'x') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getBottomRightTriangles(front).getCols(),
                                getBottomRightTriangles(front).getRows(), 3);
                        g2.setPaint(getRightPath(front).getRightPaint(front));
                        g2.fillPolygon(p);
                        if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol() - front] == ' ') {
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                - front] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow() - 1][explorer.getLocation().getCol()
                                - front] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        p = new Polygon(getCeiling(front).getCols(), getCeiling(front).getRows(), 4);
                        g2.setPaint(getCeiling(front).getCeilingPaint(front));
                        g2.fillPolygon(p);
                        g2.setColor(Color.BLACK);
                        g2.drawPolygon(p);

                        p = new Polygon(getFloor(front).getCols(), getFloor(front).getRows(), 4);
                        if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol() - front] == 'x') {
                            g2.setColor(Color.ORANGE);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()
                                - front] == 'z') {
                            g2.setColor(Color.CYAN);
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        } else {
                            g2.setPaint(getFloor(front).getFloorPaint(front));
                            g2.fillPolygon(p);
                            g2.setColor(Color.BLACK);
                            g2.drawPolygon(p);
                        }

                        if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol() - front] == '*') {
                            p = new Polygon(getFront(front).getCols(), getFront(front).getRows(), 4);
                            g2.setColor(
                                    new Color(getFront(front).getR(), getFront(front).getG(), getFront(front).getB()));
                            g2.fillPolygon(p);
                        }

                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {

                    }
                }
                break;
        }
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("Number of moves:" + moveCounter, 700, 50);
        g2.drawString("Z to place marker", 600, 560);
        g2.drawString("X to place teleporter", 600, 500);
        g2.drawString("X to remove to placed teleporter", 600, 540);
        g2.drawString("X to teleport to placed teleporter", 600, 520);
        if (markerCount == 20) {
            g2.drawString("All markers placed", 700, 70);
        }
        if (teleporterCount == 1) {
            g2.drawString("All teleporters placed", 700, 90);
            g2.drawString("Return to teleporter to", 700, 110);
            g2.drawString("remove", 700, 130);
        }
        if (maze[explorer.getLocation().getRow()][explorer.getLocation().getCol()] == 'e') {
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString("You made it out of the maze!", 700, 400);
            canDo = false;
        }
    }

    public Wall getLeftPath(int n) {
        int[] rLocs = new int[] { 50 + shrink * n, 50 + shrink * n, 500 - shrink * n, 500 - shrink * n };
        int[] cLocs = new int[] { 50 + shrink * n, 100 + shrink * n, 100 + shrink * n, 50 + shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "LeftPath", size3D);
    }

    public Wall getRightPath(int n) {
        int[] rLocs = new int[] { 500 - shrink * n, 500 - shrink * n, 50 + shrink * n, 50 + shrink * n };
        int[] cLocs = new int[] { 550 - shrink * n, 600 - shrink * n, 600 - shrink * n, 550 - shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "RightPath", size3D);
    }

    public Wall getTopLeftTriangles(int n) {
        int[] rLocs = new int[] { 0 + shrink * n, 50 + shrink * n, 50 + shrink * n };
        int[] cLocs = new int[] { 50 + shrink * n, 50 + shrink * n, 100 + shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "TopLeftTriangle", size3D);
    }

    public Wall getBottomLeftTriangles(int n) {
        int[] rLocs = new int[] { 550 - shrink * n, 500 - shrink * n, 500 - shrink * n };
        int[] cLocs = new int[] { 50 + shrink * n, 50 + shrink * n, 100 + shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "BottomLeftTriangle",
                size3D);
    }

    public Wall getTopRightTriangles(int n) {
        int[] rLocs = new int[] { 50 + shrink * n, 0 + shrink * n, 50 + shrink * n };
        int[] cLocs = new int[] { 550 - shrink * n, 600 - shrink * n, 600 - shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "TopRightTriangle", size3D);
    }

    public Wall getBottomRightTriangles(int n) {
        int[] rLocs = new int[] { 500 - shrink * n, 500 - shrink * n, 550 - shrink * n };
        int[] cLocs = new int[] { 550 - shrink * n, 600 - shrink * n, 600 - shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "BottomRightTriangle",
                size3D);
    }

    public Wall getCeiling(int n) {
        int[] rLocs = new int[] { 0 + shrink * n, 50 + shrink * n, 50 + shrink * n, 0 + shrink * n };
        int[] cLocs = new int[] { 50 + shrink * n, 100 + shrink * n, 550 - shrink * n, 600 - shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "Ceiling", size3D);
    }

    public Wall getFloor(int n) {
        int[] rLocs = new int[] { 550 - shrink * n, 500 - shrink * n, 500 - shrink * n, 550 - shrink * n };
        int[] cLocs = new int[] { 50 + shrink * n, 100 + shrink * n, 550 - shrink * n, 600 - shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "Floor", size3D);
    }

    public Wall getFront(int n) {
        int[] rLocs = new int[] { 0 + shrink * n, 550 - shrink * n, 550 - shrink * n, 0 + shrink * n };
        int[] cLocs = new int[] { 50 + shrink * n, 50 + shrink * n, 600 - shrink * n, 600 - shrink * n };
        return new Wall(rLocs, cLocs, 255 - shrink * n, 255 - shrink * n, 255 - shrink * n, "Floor", size3D);
    }

    public static void main(String[] args) {
        MazeProject mazeProject = new MazeProject();
    }
}