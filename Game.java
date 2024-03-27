import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.sound.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends PApplet {
    float tintNum = 210;
    private static int coins;
    private static int windowLength = 500;
    private static int windowWidth = 650;
    private static int gameState = 0;
    private static int startingScreen = 0;
    private static int instructScreen = 1;
    private static int playScreen = 2;
    private static int shopScreen = 3;
    private static int endingScreen = 4;
    private static int lastTickTime = 0;
    private static int timer = 80;
    private static int mazeWidth = 20;
    private static int mazeLength = 20;
    private static Cell[][] gameGrid;
    private static int health = 1000;
    private static int playerX = 0;
    private static int playerY = 0;
    private static int cellSize = 20;
    private static int bishopMoves = 0;
    private static boolean bishopBought = false;
    private static boolean tpBought = false;
    private static int boosterBought = 0;
    private static boolean reductionBought = false;
    final int healthBooster = 50;
    private static int bandageReduction = 0;
    private static int damage = 5;
    private static int efficientSteps = 0;
    private static int levelPassed = 1;
    private static int timeDelay = 200;
    private static int totalTime = 80;
    private static int totalSteps = 0;
    PImage background;
    SoundFile music;


    public void startingScreen()
    {
        tint(255, tintNum);
        image(background, 0, 0, 650, 500);
        fill(110);
        rect(150, 300, 350, 80);
        fill(255,0,0);
        textSize(50);
        text("Maze survivor", 180, 180);
        textSize(30);
        text("Instructions", 250, 350);
    }

    public void instructScreen()
    {
        tint(255, tintNum);
        image(background, 0, 0, 650, 500);
        fill(110);
        rect(150, 300, 350, 80);
        fill(255,0,0);
        textSize(18);
        text("You suffered a horrible injury that causes you to bleed whenever you move, \nbut you are stuck in this maze and you have to be precise with your movements and\nsteps. When you finish a maze, your remaining health converts to coins which can be\nused to purchase many powerups in the shop. GOODLUCK.\n\nControls are WAXD for vertical and horizontal\nQEZC for diagonal for bishop potion\nThere's random kits and clocks to boost you.\nCollect them if you can.", 10, 120);
        textSize(30);
        text("Play", 300, 350);
    }

    public void shopScreen()
    {
        tint(255, tintNum);
        image(background, 0, 0, 650, 500);
        fill(110);
        rect(40, 280, 250, 70);
        rect(360, 280, 250, 70);
        rect(40, 390, 250, 70);
        rect(360, 390, 250, 70);
        fill(255,0,0);
        textSize(50);
        text("Coins: " + coins, 40, 80);
        textSize(80);
        text("SHOP", 230, 200);
        textSize(28);
        text("Healthboost $250", 55, 325);
        text("Bandage $250", 400, 325);
        text("Teleportation $100", 55, 435);
        text("Bishop Potion $800", 365, 435);
        fill(255, 0, 0);
        rect(265, 220, 120, 40);
        fill(255);
        text("Exit", 305, 250);
        textSize(20);
        text("That maze could've taken "+ efficientSteps + " amount of steps", 10, 20);
    }

    public void gameScreenPlay()
    {
        if (health > 0 && (this.key == 'w' || this.key == 'W') && playerY > 0 && (!gameGrid[playerY][playerX].hasTopWall() || !gameGrid[playerY - 1][playerX].hasBottomWall())) {
            if (gameGrid[playerY - 1][playerX].isExit()) {
                coins += health;
                gameState = shopScreen;
                gameGrid[playerY][playerX].removePlayer();
                timer = totalTime;
                levelPassed++;
                timeDelay += 20;
                totalTime--;
                totalSteps++;
            }
            else if(gameGrid[playerY - 1][playerX].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY - 1][playerX].changeHealthKit(false);
            }
            else if(gameGrid[playerY - 1][playerX].hasClock())
            {
                timer += 15;
                gameGrid[playerY - 1][playerX].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerY--;
            health -= (damage - bandageReduction);
            gameGrid[playerY][playerX].makePlayer();
            totalSteps++;
            delay(timeDelay);
        }
        if (health > 0 && (this.key == 'x' || this.key == 'X')  && playerY < mazeLength - 1 && (!gameGrid[playerY][playerX].hasBottomWall() || !gameGrid[playerY + 1][playerX].hasTopWall())) {
            if (gameGrid[playerY + 1][playerX].isExit()) {
                System.out.println("i did it");
                coins += health;
                gameState = shopScreen;
                gameGrid[playerY][playerX].removePlayer();
                timer = totalTime;
                levelPassed++;
                timeDelay += 20;
                totalTime--;
                totalSteps++;
            }
            else if(gameGrid[playerY + 1][playerX].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY + 1][playerX].changeHealthKit(false);
            }
            else if(gameGrid[playerY + 1][playerX].hasClock())
            {
                timer += 15;
                gameGrid[playerY + 1][playerX].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerY++;
            health -= (damage - bandageReduction);
            gameGrid[playerY][playerX].makePlayer();
            totalSteps++;
            delay(timeDelay);
        }
        if (health > 0 && (this.key == 'd' || this.key == 'D') && playerX < mazeWidth - 1 && (!gameGrid[playerY][playerX].hasRightWall() || !gameGrid[playerY][playerX + 1].hasLeftWall())) {
            if (gameGrid[playerY][playerX + 1].isExit()) {
                System.out.println("i did it");
                coins += health;
                gameState = shopScreen;
                gameGrid[playerY][playerX].removePlayer();
                timer = totalTime;
                levelPassed++;
                timeDelay += 20;
                totalTime--;
                totalSteps++;
            }
            else if(gameGrid[playerY][playerX + 1].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY][playerX + 1].changeHealthKit(false);
            }
            else if(gameGrid[playerY][playerX + 1].hasClock())
            {
                timer += 15;
                gameGrid[playerY][playerX + 1].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerX++;
            gameGrid[playerY][playerX].makePlayer();
            health -= (damage - bandageReduction);
            totalSteps++;
            delay(timeDelay);
        }
        if (health > 0 && (this.key == 'a' || this.key == 'A') && playerX > 0 && (!gameGrid[playerY][playerX].hasLeftWall() || !gameGrid[playerY][playerX - 1].hasRightWall())) {
            if (gameGrid[playerY][playerX - 1].isExit()) {
                System.out.println("i did it");
                coins += health;
                gameState = shopScreen;
                gameGrid[playerY][playerX].removePlayer();
                timer = totalTime;
                levelPassed++;
                timeDelay += 20;
                totalTime--;
                totalSteps++;
            }
            else if(gameGrid[playerY][playerX - 1].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY][playerX - 1].changeHealthKit(false);
            }
            else if(gameGrid[playerY][playerX - 1].hasClock())
            {
                timer += 15;
                gameGrid[playerY][playerX - 1].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerX--;
            gameGrid[playerY][playerX].makePlayer();
            health -= (damage - bandageReduction);
            totalSteps++;
            delay(timeDelay);
        }
        if(health > 0 && (this.key == 'q' || this.key == 'Q') && bishopMoves > 0 && playerX > 0 && playerY > 0)
        {
            if(gameGrid[playerY - 1][playerX - 1].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY - 1][playerX - 1].changeHealthKit(false);
            }
            else if(gameGrid[playerY - 1][playerX - 1].hasClock())
            {
                timer += 15;
                gameGrid[playerY - 1][playerX - 1].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerX--;
            playerY--;
            gameGrid[playerY][playerX].makePlayer();
            health -= (damage - bandageReduction);
            bishopMoves--;
            if(bishopMoves == 0)
            {
                bishopBought = false;
            }
            totalSteps++;
            delay(timeDelay);
        }
        if(health > 0 && (this.key == 'e' || this.key == 'E') && bishopMoves > 0 && playerX < mazeWidth - 1 && playerY > 0)
        {
            if(gameGrid[playerY - 1][playerX + 1].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY - 1][playerX + 1].changeHealthKit(false);
            }
            else if(gameGrid[playerY - 1][playerX + 1].hasClock())
            {
                timer += 15;
                gameGrid[playerY - 1][playerX + 1].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerX++;
            playerY--;
            gameGrid[playerY][playerX].makePlayer();
            health -= (damage - bandageReduction);
            bishopMoves--;
            if(bishopMoves == 0)
            {
                bishopBought = false;
            }
            totalSteps++;
            delay(timeDelay);
        }
        if(health > 0 && (this.key == 'z' || this.key == 'Z') && bishopMoves > 0 && playerX > 0 && playerY < mazeLength - 1)
        {
            if(gameGrid[playerY + 1][playerX - 1].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY + 1][playerX - 1].changeHealthKit(false);
            }
            else if(gameGrid[playerY + 1][playerX - 1].hasClock())
            {
                timer += 15;
                gameGrid[playerY + 1][playerX - 1].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerX--;
            playerY++;
            gameGrid[playerY][playerX].makePlayer();
            health -= (damage - bandageReduction);
            bishopMoves--;
            if(bishopMoves == 0)
            {
                bishopBought = false;
            }
            totalSteps++;
            delay(timeDelay);
        }
        if(health > 0 && (this.key == 'c' || this.key == 'C') && bishopMoves > 0 && playerX < mazeWidth - 1 && playerY < mazeLength - 1)
        {
            if (gameGrid[playerY + 1][playerX + 1].isExit()) {
                System.out.println("i did it");
                coins += health;
                gameState = shopScreen;
                gameGrid[playerY][playerX].removePlayer();
                timer = totalTime;
                levelPassed++;
                timeDelay += 20;
                totalTime--;
                totalSteps++;
            }
            else if(gameGrid[playerY + 1][playerX + 1].hasHealthKit())
            {
                health += 80;
                gameGrid[playerY + 1][playerX + 1].changeHealthKit(false);
            }
            else if(gameGrid[playerY + 1][playerX + 1].hasClock())
            {
                timer += 15;
                gameGrid[playerY + 1][playerX + 1].changeClock(false);
            }
            gameGrid[playerY][playerX].removePlayer();
            playerX++;
            playerY++;
            gameGrid[playerY][playerX].makePlayer();
            health -= (damage - bandageReduction);
            bishopMoves--;
            if(bishopMoves == 0)
            {
                bishopBought = false;

            }
            totalSteps++;
            delay(timeDelay);
        }
        background(220);
        tint(255, 255);
        image(background, 0, 0, 650, 500);
        for (Cell[] row : gameGrid) {
            for (Cell c : row) {
                c.display();
            }
        }
        textSize(16);
        fill(0, 255, 0);
        text("Health: " + health, 515, 25);
        fill(255, 215, 0);
        text("Coins: " + coins, 515, 50);
        fill(255);
        text("Level: " + levelPassed, 515, 75);
        text("Steps taken: " + totalSteps, 515, 100);
        text("Timer: " + timer, 500, 500);
        timerTick();
        if(timer == 0 || health == 0)
        {
            gameState = endingScreen;
        }
    }

    public void endingScreen()
    {
        fill(0);
        rect(0,0,650,500);
        textSize(100);
        fill(255,0,0);
        text("YOU DIED.", 100, 250);
    }


    public void draw() {
        switch(gameState)
        {
            case 0:
                startingScreen();
                break;

            case 1:
                instructScreen();
                break;

            case 2:
                background(255);
                noTint();
                gameScreenPlay();
                break;

            case 3:
                shopScreen();
                break;

            case 4:
                endingScreen();
                break;
        }
    }

    public void mousePressed()
    {
        if(gameState == startingScreen)
        {
            if(mouseX > 150 && mouseX < 500 && mouseY > 300 && mouseY < 380)
            {
                gameState = instructScreen;
            }
        }
        else if(gameState == instructScreen)
        {
            if(mouseX > 150 && mouseX < 500 && mouseY > 300 && mouseY < 380)
            {
                gameState = playScreen;
                setup();
            }
        }
        else if(gameState == shopScreen)
        {
            if(mouseX > 40 && mouseX < 290 && mouseY > 280 && mouseY < 350)
            {
                if(coins >= 250)
                {
                    boosterBought++;
                    coins -= 250;
                    System.out.println("I bought booster");
                }
            }
            if(mouseX > 360 && mouseX < 610 && mouseY > 280 && mouseY < 350)
            {
                if(coins >= 250)
                {
                    if(bandageReduction < 5 )
                    {
                        reductionBought = true;
                        coins -= 250;
                        bandageReduction++;
                        System.out.println("I bought bandage");
                    }
                }
            }
            if(mouseX > 40 && mouseX < 290 && mouseY > 390 && mouseY < 460)
            {
                if(coins >= 100)
                {
                    tpBought = true;
                    coins -= 100;
                    System.out.println("I bought tp");
                }
            }
            if(mouseX > 360 && mouseX < 610 && mouseY > 390 && mouseY < 460)
            {
                if(coins >= 800)
                {
                    bishopBought = true;
                    bishopMoves += 3;
                    coins -= 800;
                    System.out.println("I bought bishop");
                }
            }
            if(mouseX > 265 && mouseX < 385 && mouseY > 220 && mouseY < 260)
            {
                gameState = playScreen;
                playerX = 0;
                playerY = 0;
                efficientSteps = 0;
                setup();
            }
        }
    }
    public void generateMaze(int x, int y) {
        gameGrid[y][x].makeVisited(); // This is to mark that this cell is visited, prevents future visits to this cell
        List<int[]> neighbors = new ArrayList<int[]>(); // This is to create a list to keep track of every neighboring cells
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (Math.abs(i) + Math.abs(j) == 1) // This goes through every single neighboring cell and makes sure its not checking diagonal cells
                {
                    int newX = x + i;
                    int newY = y + j;

                    if (newX >= 0 && newY >= 0 && newX < mazeWidth && newY < mazeLength && !gameGrid[newY][newX].isVisited()) // I'm using this to check for boundaries (to see if the new coordinates can be there)
                    {
                        neighbors.add(new int[]{newX, newY}); // I'm adding the newCoordinates part of the visited list 
                    }
                }
            }
        }
        Collections.shuffle(neighbors); // This will randomize the order of the neighbor list (basically making sure every maze will be random)
        for (int i = 0; i < neighbors.size(); i++) { //Loops through the whole neighbor lists and gets their coordinates
            int newX = neighbors.get(i)[0];
            int newY = neighbors.get(i)[1];
            if (!gameGrid[newY][newX].isVisited()) { //If the new coordinates are x different from the original coordinates, it will change which wall is blocked or not
                if (newX == x + 1) {
                    gameGrid[y][x].changeRightWall(false);
                    gameGrid[newY][newX].changeLeftWall(false);
                } else if (newX == x - 1) {
                    gameGrid[y][x].changeLeftWall(false);
                    gameGrid[newY][newX].changeRightWall(false);
                } else if (newY == y + 1) {
                    gameGrid[y][x].changeBottomWall(false);
                    gameGrid[newY][newX].changeTopWall(false);
                } else if (newY == y - 1) {
                    gameGrid[y][x].changeTopWall(false);
                    gameGrid[newY][newX].changeBottomWall(false);
                }
                generateMaze(newX, newY); //Recursive call to do it again
            }
        }
    }

    public int recursiveSteps(int x, int y, int steps, int bishopRemaining) {
        if(x < 0 || x >= mazeWidth || y < 0 || y >= mazeLength || gameGrid[y][x].isVisited()) // Base case (if its out of bounds)
        {
            return Integer.MAX_VALUE;
        }
        if(gameGrid[y][x].isExit()) // Base case again (if exit is found)
        {
            return steps;
        }
        gameGrid[y][x].makeVisited(); // Mark visited to prevent future cycles
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // This is all possible directions of neighboring cells could be
        int minSteps = Integer.MAX_VALUE; // Takes into account if the maze ever would take max integer value (it won't)
        for(int[] direction : directions) // Loops through every single possibilities of where the path should go
        {
            int newX = x + direction[0]; // Makes the new coordinates to easily use again later in the method
            int newY = y + direction[1];
            int currSteps;
            if(newX >= 0 && newY >= 0 && newX < mazeWidth && newY < mazeLength) // Checks for the boundaries again
            {
                Cell currCell = gameGrid[y][x]; // Initializing these so its easier to access later in the method
                Cell newCell = gameGrid[newY][newX];
                if((direction[0] == 1 && direction[1] == 0  &&!currCell.hasRightWall() && !newCell.hasLeftWall())
                        || (direction[0] == -1 && direction[1] == 0 && !currCell.hasLeftWall() && !newCell.hasRightWall()) ||
                        (direction[0] == 0 && direction[1] == 1 && !currCell.hasBottomWall() && !newCell.hasTopWall())
                        || (direction[0] == 0 && direction[1] == -1 && !currCell.hasTopWall() && !newCell.hasBottomWall())
                        || (bishopRemaining > 0 && Math.abs(direction[0]) == 1 && Math.abs(direction[1]) == 1)) // if its possible to move to certain place (walls considered)
                {
                    int newSteps = steps + 1; // Adds in the new step
                    if(Math.abs(direction[0]) == 1 && Math.abs(direction[1]) == 1 && bishopRemaining > 0) // Checks if its moving diagonally
                    {
                        currSteps = recursiveSteps(newX, newY, newSteps, bishopRemaining - 1); // If it is diagonally moving, minus 1 bishop move
                    }
                    else{
                        currSteps = recursiveSteps(newX, newY, newSteps, bishopRemaining); // If horizontal or vertical movement, do this
                    }
                    if(currSteps < minSteps) // Checks if this new path is efficient and make it the temp. shortest path until a new one is found
                    {
                        minSteps = currSteps;
                    }
                }
            }
        }
        gameGrid[y][x].notVisited(); // Key backtracking call, so that when we go back, we can go back to this cell without rejecting it
        return minSteps; // returns the shortest
    }

    public void gridStart() {
        for (int row = 0; row < mazeWidth; row++) {
            for (int col = 0; col < mazeLength; col++) {
                gameGrid[row][col] = new Cell(this, col, row);
            }
        }
    }

    public void gridAllFalse()
    {
        for (int row = 0; row < mazeWidth; row++) {
            for (int col = 0; col < mazeLength; col++) {
                gameGrid[row][col].notVisited();
            }
        }
    }

    public void settings() {
        size(windowWidth, windowLength);
        pixelDensity(displayDensity());
    }

    public void setup() {
        music = new SoundFile(this,"assets/music.mp3");
        music.play();
        size(650, 500);
        background = loadImage("images/game background.jpeg");
        tint(255, tintNum);
        image(background, 0, 0, 650, 500);
        gameGrid = new Cell[mazeWidth][mazeLength];
        gridStart();
        if(tpBought)
        {
            playerX = (int) (Math.random() * mazeWidth - 1);
            playerY = (int) (Math.random() * mazeLength - 1);
            gameGrid[playerY][playerX].makePlayer();
            tpBought = false;
        }
        else{
            gameGrid[0][0].makePlayer();
        }
        int ranX1 = (int)(Math.random() * mazeWidth - 1);
        int ranY1 = (int)(Math.random() * mazeLength - 1);
        int ranX2 = (int)(Math.random() * mazeWidth - 1);
        int ranY2 = (int)(Math.random() * mazeLength - 1);
        while((ranX1 == playerX && ranY1 == playerY) || (ranX1 == mazeWidth - 1) && (ranY1 == mazeLength - 1))
        {
            ranX1 = (int)(Math.random() * mazeWidth - 1);
            ranY1 = (int)(Math.random() * mazeLength - 1);
        }
        while((ranX2 == playerX && ranY2 == playerY) || (ranX2 == mazeWidth - 1) && (ranY2 == mazeLength - 1))
        {
            ranX2 = (int)(Math.random() * mazeWidth - 1);
            ranY2 = (int)(Math.random() * mazeLength - 1);
        }
        gameGrid[ranY1][ranX1].changeHealthKit(true);
        gameGrid[ranY2][ranX2].changeClock(true);
        gameGrid[mazeLength - 1][mazeWidth - 1].makeExit();
        health = 1000 + (boosterBought * 150);
        generateMaze(0, 0);
        gridAllFalse();
        if(bishopBought)
        {
            System.out.println("hello");
            efficientSteps = recursiveSteps(playerX, playerY, 0, bishopMoves);
        }
        else{
            System.out.println("hello");
            efficientSteps = recursiveSteps(playerX, playerY, 0, 0);
        }
    }

    public void timerTick() {
        if (millis() - lastTickTime >= 1000) {
            timer--;
            lastTickTime = millis();
            if (timer <= 0) {
                health = 0;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}

