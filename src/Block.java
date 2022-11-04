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
        Main.app.rect(x, y, size, size);
        Main.app.fill(baseColor,150,255);
    }

    public int getBC(){
        return baseColor;
    }
}
