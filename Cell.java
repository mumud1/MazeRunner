import processing.core.PApplet;
import processing.core.PImage;

public class Cell {
    PApplet sketch;
    private int cellX, cellY;
    private boolean topWall = true;
    private boolean bottomWall = true;
    private boolean leftWall = true;
    private boolean rightWall = true;
    private boolean visited = false;
    private int cellSize = 25;
    private boolean isPlayer = false;
    private boolean isExit = false;
    private boolean isHealthKit = false;
    private boolean isClock = false;
    private boolean isBishop = false;
    PImage clock;
    PImage healthKit;
    PImage player;

    public Cell(PApplet sketch, int x, int y)
    {
        this.sketch = sketch;
        this.cellX = x;
        this.cellY = y;
        this.isPlayer = false;
        this.isExit = false;
        this.isHealthKit = false;
        this.visited = false;
    }

    public void display()
    {
        sketch.stroke(0);
        if(isPlayer)
        {
            player = sketch.loadImage("images/player.png");
            sketch.tint(255,255);
            sketch.image(player, cellX * cellSize - 28, cellY * cellSize - 3, 80, 30);
        }
        else if(isExit)
        {
            sketch.fill(255, 0, 0);
            sketch.rect(cellX * cellSize, cellY * cellSize, cellX + 5, cellY + 5);
        }
        else if(isHealthKit)
        {
            healthKit = sketch.loadImage("images/healthKit.png");
            sketch.tint(255,255);
            sketch.image(healthKit, cellX * cellSize - 3, cellY * cellSize - 3, 30, 30);
        }
        else if(isClock)
        {
            clock = sketch.loadImage("images/clock.png");
            sketch.tint(255,255);
            sketch.image(clock, cellX * cellSize, cellY * cellSize, 25, 25);
        }
        else{
            sketch.noFill();
        }
        sketch.stroke(255,0,0);
        if(topWall)
        {
            sketch.line(cellX * cellSize, cellY * cellSize, (cellX + 1) * cellSize, cellY * cellSize);
        }
        if(bottomWall)
        {
            sketch.line(cellX * cellSize, (cellY + 1) * cellSize, (cellX + 1) * cellSize, (cellY + 1) * cellSize);
        }
        if(leftWall)
        {
            sketch.line(cellX * cellSize, (cellY + 1) * cellSize, cellX * cellSize, cellY * cellSize);
        }
        if(rightWall)
        {
            sketch.line((cellX + 1) * cellSize, cellY * cellSize, (cellX + 1) * cellSize, (cellY + 1) * cellSize);
        }
    }

    public void makePlayer()
    {

        this.isPlayer = true;
    }
    public void makeExit()
    {
        this.isExit = true;
        this.leftWall = false;
        this.rightWall = false;
        this.topWall = false;
        this.bottomWall = false;
    }

    public void removePlayer()
    {
        this.isPlayer = false;
    }

    public void makeVisited()
    {
        this.visited = true;
    }

    public void changeRightWall(boolean tf)
    {
        this.rightWall = tf;
    }

    public void changeLeftWall(boolean tf)
    {
        this.leftWall = tf;
    }

    public void changeTopWall(boolean tf)
    {
        this.topWall = tf;
    }

    public void changeBottomWall(boolean tf)
    {
        this.bottomWall = tf;
    }

    public boolean isVisited()
    {
        return this.visited;
    }

    public boolean isExit()
    {
        return this.isExit;
    }

    public boolean hasTopWall()
    {
        return this.topWall;
    }

    public boolean hasBottomWall()
    {
        return this.bottomWall;
    }

    public boolean hasRightWall()
    {
        return this.rightWall;
    }

    public boolean hasLeftWall()
    {
        return this.leftWall;
    }

    public boolean hasClock()
    {
        return this.isClock;
    }

    public boolean hasHealthKit()
    {
        return this.isHealthKit;
    }

    public void changeHealthKit(boolean tf)
    {
        this.isHealthKit = tf;
    }

    public void changeClock(boolean tf)
    {
        this.isClock = tf;
    }
    public void changeCellSize()
    {
        cellSize -= 5;
    }

    public void notVisited()
    {
        this.visited = false;
    }
}
