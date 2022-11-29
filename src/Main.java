import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    public static PApplet pApplet;
    public static PApplet app;
    int listSize = 15;

    int blockSize = 20;
    int margins = 10;
    int grayTarget = 100; // MAKE THIS USER INPUT
    int factor = 255/listSize; // this is teh value that each blcok will increase by

    int PAbottom = 0;
    int PAtop = listSize -1;
    String digits = "";

    boolean infoShow = false; //whether it shows the infomaton on how to play the "game"
    String info = "the background color is your target"+'\n'+
            "press 's' to sort the cells; press 'b' to search the cells"+'\n'+
            "if you want to restart, press 'r'"+'\n'+
            "if you want a new target, type the number (0-255) and press 'x'";
    boolean found = false;
    boolean endOfBS = false;
    int foundIndex;
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

    }

    public void draw(){
        background(grayTarget);
        for(int i = 0; i<arrL.size();i++){
            Block b = arrL.get(i);
            b.display(); //  normally a black stroke color
            //System.out.println(b.getBC());
        }
        if(endOfBS){
            if(grayTarget<130){
                fill(255);
            }else{
                fill(0);
            }
            textAlign(CENTER);
            if(found){
                text(foundIndex,width/2, blockSize + (margins*2) +10);
            }else{
                text("this value was not found", width/2, blockSize + (margins*2) +10);
            }
        }

        // creating info button
        fill(255,195,0);
        rect(width-(margins*4), height-(margins*4),20,20);
        if(infoShow) {
            if(grayTarget<130){
                fill(255);
            }else{
                fill(0);
            }
            textAlign(CENTER);
            text(info,width/2,(height/2) +10);
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

            arrL.get(center).wasComp(true); // a yellow stroke color

            if(cenV == gTarget){
                found = true;
                endOfBS = true;
                foundIndex = center;
                return center;
            }else if (cenV < gTarget){ // move up
                bottom = center +1;
                PAbottom = bottom;
            }else{ // if the center value is higher than tagret --> move down and get rid of top half
                top = center -1;
                PAtop = top;
            }
        }
        if(top < bottom){
            endOfBS = true;
        }
        return -1;
    }

    private void selectionSort(ArrayList<Block> arrL){

        for(int i= 0; i< arrL.size()-1;i++){ // -2 b/c teh last value does not need to be sorted
            int minI = i; // finding the minumum index

            int comp = i; // valeu ur comapring to
            for(int j = i+1; j<arrL.size();j++){
                if(arrL.get(j).getBC() < arrL.get(comp).getBC()){ // comparing the BC value of J to teh BC value of I
                    minI = j;
                    comp =j;
                }
            }

            int XofI = arrL.get(i).getX();
            int XofM = arrL.get(minI).getX();
            arrL.get(i).setX(XofM);
            arrL.get(minI).setX(XofI);
            // switch min with element at i
            Block tempMI = arrL.get(minI); // saving value at minumnet
            arrL.set(minI,arrL.get(i)); // setting cvaley at minI to teh element value at i
            arrL.set(i,tempMI); // setting teh value at i index to the minumnet element value



        }
    }


    public void keyPressed(){
        if(key =='b'){ // start seraching
            binarySearch(grayTarget);

        }

        if(key == 'r'){ // reseting
            found = false;
            endOfBS = false;
            for(int i = 0; i<arrL.size();i++){
                Block b = arrL.get(i);
                b.wasComp(false); //  normally a black stroke color
                //System.out.println(b.getBC());
            }
            // restarst binary serach
            PAbottom = 0;
            PAtop = listSize -1;


        }

        if(key =='s'){
            selectionSort(arrL);
            for(int i = 0; i<arrL.size();i++) {
                Block b = arrL.get(i);
                //System.out.println(b.getBC());
                //System.out.println("x:" + b.getX());
            }
        }
        if(Character.isDigit((key))){
            digits = digits + key;
        }else if (key =='x'){
            grayTarget = Integer.parseInt(digits);
            digits ="";
        }

    }
    public void mouseClicked(){
        if(mouseX>width-(margins*4) && mouseX<width-(margins*4)+20 && mouseY > height-(margins*4) && mouseY< height-(margins*4)+20){
            infoShow = ! infoShow;
        }
    }
}
