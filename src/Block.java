import processing.core.PImage;
public class Block{
    int x, y, size,baseColor;

    public Block(int _x, int _y, int _size, int _baseColor){
        x= _x;
        y= _y;
        size =_size;
        baseColor = _baseColor; // the changing color
    }

    public void display(int r, int g, int b){
        Main.app.stroke(r,g,b);
        Main.app.fill(baseColor);
        Main.app.rect(x, y, size, size);

    }

    public int getBC(){
        return baseColor;
    }
    public void setX(int _x){x =_x;}
    public void setY(int _y){y =_y;}
}
