import processing.core.PImage;
public class Block{
    int x, y, size,baseColor,outlineColor,RV;
    boolean yellow = false;
// RV is what value you will retuen when ist selected. for regualr one, RV is justteh index
    public Block(int _x, int _y, int _size, int _baseColor, int returnValue){
        x= _x;
        y= _y;
        size =_size;
        baseColor = _baseColor; // the changing color
        outlineColor = 0;
        RV=returnValue;
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
    public int getRV(){ return RV;}
    public void setRV(int _RV){
        RV = _RV;
    }

}
