import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    public static PApplet pApplet;
    public static PApplet app;
    int listSize = 10;

    int blockSize = 50;
    int margins = 10;
    int grayTarget = 75; // MAKE THIS USER INPUT
    int factor = 255/listSize; // this is teh value that each blcok will increase by

    int PAbottom = 0;
    int PAtop = listSize -1;

    boolean infoShow = false; //whether it shows the infomaton on how to play the "game"
    private ArrayList<Block> arrL;

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
        int heightC = blockSize + (margins*4); // the height of the block all teh way to where the text would appear
        size(widthC, heightC+150); // setting up a canvas
    }

    public void setup() { // TO DO; CREATE IMST FOR TEH X AND Y VALES
        background(grayTarget);
        arrL = new ArrayList<>();


        //int baseColor = 0;
        for(int i =0; i < listSize; i++){
            // random number from 0 to 250 but in intervals of 10
            int r = (int)(Math.random()*25);

            int xVal = (blockSize+margins)*i + margins;
            int yVal = margins;
            arrL.add(new Block(xVal, yVal, blockSize, r*10)); // this list will be sorted 0,1,2,3, etc.
            //baseColor = baseColor +factor;
        }


    // thie below used to be in draw but i moved to setup so teh colro change wodul work
        for(int i = 0; i<arrL.size();i++){
            Block b = arrL.get(i);
            b.display(0,0,0); //  normally a black stroke color
        }

    }

    public void draw(){
        // creating info button
        fill(255,195,0);
        rect(width-(margins*4), height-(margins*4),20,20);
        if(infoShow) {
            fill(255);
            text("infomation",width/2,(height/2) +50);
            // ADD HOW TO PLAY TEH GAME
            // FIX ERROWRS WITH THE TEXT AND HOW IT SHOWS UP
        }
        //I DON'T KNOW HOW TO GET THIS TO GO AWAY
    }

    private int binarySearch(int gTarget){
        int bottom = PAbottom;
        int top = PAtop;

        if (bottom <=top){

            int center = (bottom+top)/2;
            int cenV = arrL.get(center).getBC(); // teh color value of teh center block

            arrL.get(center).display(255,195,0); // a yellow stroke color
            // ISSUE. THE CORRECT INDEX IS NOT SHPOWING UP BUT
            // WHEN I GET RID OF TEH ABOVE LINE TEH INDEX DOES SHOW UP

            if(cenV == gTarget){
                text(center, width/2, blockSize+(margins*4));
                return center;
            }else if (cenV < gTarget){ // move up
                bottom = center +1;
                PAbottom = bottom;
            }else{ // if the center value is higher than tagret --> move down and get rid of top half
                top = center -1;
                PAtop = top;
            }
        }



       // text("this value was not found", width/2, height/2);
        return -1;
    }

    private void selectionSort(ArrayList<Block> arrL){
// THIS DOES NOT WORK ON FIRST CALL
        for(int i= 0; i< arrL.size()-2;i++){ // -2 b/c teh last value does not need to be sorted
            int minI = i; // finding the minumum index
            for(int j = i+1; j<arrL.size()-1;j++){
                if(arrL.get(j).getBC() < arrL.get(i).getBC()){ // comparing the BC value of J to teh BC value of I
                    minI = j;
                }
            }
            // switch min with element at i
            Block tempMI = arrL.get(minI); // saving value at minumnet
            arrL.set(minI,arrL.get(i)); // setting cvaley at minI to teh element value at i
            arrL.set(i,tempMI); // setting teh value at i index to the minumnet element value

        }
    }


    public void keyPressed(){
        if(key =='b'){
            binarySearch(grayTarget);
        }
        else if(key =='s'){ // AT THIS POINT YOU HAVE TO CLICK S MANY TIMES UNTIL IT DOESNT CHANGE ANYMORE
            selectionSort(arrL);
            //updating canvas
            for(int i=0;i<arrL.size()-1;i++){
                arrL.get(i).setX((blockSize+margins)*i + margins);
            }

            for(int i = 0; i<arrL.size();i++){
                Block b = arrL.get(i);
                b.display(0,0,0); //  normally a black stroke color
            }

        }
    }
    public void mouseClicked(){
        if(mouseX>width-(margins*4) && mouseX<width-(margins*4)+20 && mouseY > height-(margins*4) && mouseY< height-(margins*4)+20){
            infoShow = ! infoShow;
        }
    }
}
