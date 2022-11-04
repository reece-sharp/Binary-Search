import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    public static PApplet pApplet;
    public static PApplet app;
    int listSize = 10;

    static int  greenV = 150;
    static int blueV = 255;
    int blockSize = 30;
    int margins = 10;
    int target = 75;
    private ArrayList<Block> arrL;
    private ArrayList<Integer> test;

    public Main(){ // need to inatiate the variable in thsi consturcter
        pApplet = this;
        app = this;
    }
    public static void main(String[] args){
        PApplet.main("Main"); // the name of our class
    }

    public void settings(){
        // teh first bit is teh spaces inbetween and on outside of teh blocks
        int widthC = ((listSize+1)*margins)+(listSize)*blockSize;
        int heightC = blockSize + (margins*2);
        size(widthC, heightC); // setting up a canvas
    }

    public void setup() { // TO DO; CREATE IMST FOR TEH X AND Y VALES

        arrL = new ArrayList<>();
        test = new ArrayList<>();

        int baseColor = 0;
        for(int i =0; i < listSize; i++){
            int xVal = (blockSize+margins)*i + margins;
            int yVal = margins;
            arrL.add(i, new Block(xVal, yVal, blockSize, baseColor)); // this list will be sorted 0,1,2,3, etc.
            test.add(i,baseColor);
            baseColor = baseColor +25;
        }

        System.out.println(binarySearch(target));
        System.out.println(test);

    }

    public void draw() { // has all teh panle objects in it
        background(target,greenV,blueV);// teh bakgroudn charcter is dependnetd on teh target color
        for(int i = 0; i<arrL.size();i++){
            Block b = arrL.get(i);
            b.display();
        }
    }

    private int binarySearch(int target){
        int bottom = 0;
        int top = arrL.size()-1;

        while (bottom <=top){

            int center = (bottom+top)/2;
            int cenV = arrL.get(center).getBC(); // teh color value of teh center block


            if(cenV == target){
                return center;
            }else if (cenV < target){ // move up
                bottom = center +1;
            }else{ // if the center value is higher than tagret --> move down and get rid of top half
                top = center -1;
            }
            System.out.println(arrL);
        }

        return -1;
    }

    public void keyPressed(){
    }
}
