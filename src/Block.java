import processing.core.PImage;
public class Block{
    int x, y, size,baseColor;

    public Block(int _x, int _y, int _size, int _baseColor){
        x= _x;
        y= _y;
        size =_size;
        baseColor = _baseColor; // the changing color
    }

    public void display(){
        Main.app.fill(baseColor);
        Main.app.rect(x, y, size, size);
    }

    public int getBC(){
        return baseColor;
    }
}
