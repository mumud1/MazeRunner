import processing.core.PApplet;
public class Screen {
    PApplet sketch;

    public Screen()
    {

    }
    public void display()
    {
        sketch.textSize(25);
        sketch.background(220);
        sketch.text("Maze Survivor", 300, 300);
    }
}
