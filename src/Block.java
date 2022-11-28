import processing.core.PImage;
public class Block{
    int x, y, size,baseColor,outlineColor;
    boolean yellow = false;

    public Block(int _x, int _y, int _size, int _baseColor){
        x= _x;
        y= _y;
        size =_size;
        baseColor = _baseColor; // the changing color
        outlineColor = 0;
    }

    public void display(){
        if(yellow){
            Main.app.stroke(255,195,0);
        }else{
            Main.app.stroke(outlineColor);
        }
        Main.app.fill(baseColor);
        Main.app.rect(x, y, size, size);

    }
    public void wasComp(boolean y){
        yellow = y;
    }

    public int getBC(){
        return baseColor;
    }
    public int getX(){ return x;}
    public void setX(int _x){x =_x;}
    public void setY(int _y){y =_y;}
}
