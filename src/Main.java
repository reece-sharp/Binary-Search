import processing.core.PApplet;
import java.util.ArrayList;
import processing.data.Table;
import processing.data.TableRow;


public class Main extends PApplet {
    public static PApplet pApplet;
    public static PApplet app;
    int listSize = 15;

    int blockSize = 50;
    int margins = 10;
    int grayTarget = 100; // MAKE THIS USER INPUT
    int factor = 255/listSize; // this is teh value that each blcok will increase by

    int PAbottom = 0;
    int PAtop = listSize -1;
    String digits = "";
    boolean usingDataTable = false;

    boolean infoShow = false; //whether it shows the infomaton on how to play the "game"
    String info;
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

    int widthC; // making it a global variable
    public void settings(){
        // teh first bit is teh spaces inbetween and on outside of teh blocks
        widthC = ((listSize+1)*margins)+(listSize)*blockSize;
        int heightC = blockSize + (margins*4) +200; // the height of the block all teh way to where the text would appear
        size(widthC, heightC); // setting up a canvas
    }

    public void setup() { // TO DO; CREATE IMST FOR TEH X AND Y VALES
        arrL = new ArrayList<>();

 //this below is data takes from statistics for how many indi books store there are per year in US
        if(usingDataTable){
            Table table = loadTable("bookData.csv", "header");
            int index = 0;
            int xVal;
            int yVal;


            //THE NEXT THREE STATMNETS FIX THE SPACING ISSUE
            // THAT OCCURS WHEN THE LIST SIZE IN RANDOM IS DIFFERNET THAN THE AMOUNT OF DATA IN THE TABLE
            int aodr = 0; //amount of data rows
            // is the same number as index but is just calulated before we instatiate each Block
            for (TableRow row : table.rows()) {
                aodr++;
            }
            //((blockSize*aodr)+((aodr-1)*margins)) is how much space teh blocks and their INTERNAL margisn take up
            //(widthC - ((blockSize*aodr)+((aodr-1)*margins)))/2 --> how much space will be on each side of block groups left and right
            int outsideMargMinusMarg= ((widthC - ((blockSize*aodr)+((aodr-1)*margins)))/2)-margins;


            //thsi makes teh blcoks that hold teh data tabel info
            for (TableRow row : table.rows()) {
                xVal = (blockSize+margins)*index + margins + outsideMargMinusMarg;
                yVal = margins;
                int year = row.getInt("year"); // obtain year data
                int books = row.getInt("books"); // obtain quantity
                arrL.add(new Block(xVal, yVal, blockSize,(year%100)*10, books));
                index ++;
            }
        }


//THIS BELOW IS MY RANDOM GENERATED LIST OF BLOCKS
        if(usingDataTable==false){
            for(int i =0; i < listSize; i++){
                // random number from 0 to 250 but in intervals of 10
                int r = (int)(Math.random()*25);
                int xVal = (blockSize+margins)*i + margins;
                int yVal = margins;
                arrL.add(new Block(xVal, yVal, blockSize, r*10,i)); // this list will be sorted 0,1,2,3, etc.
                //baseColor = baseColor +factor;
            }
        }
    }


    public void draw(){
        background(grayTarget);

        for(int i = 0; i<arrL.size();i++){
            Block b = arrL.get(i);
            b.display(); //  normally a black stroke color
        }

        if(grayTarget<130){
            fill(255);
        }else{
            fill(0);
        }

        if(usingDataTable==true){
            textAlign(LEFT);
            text("DATA: from data table", 20, height-20);
        }else if(usingDataTable==false){
            textAlign(LEFT);
            text("DATA: random data", 20, height-20);
        }

        if(endOfBS){
            textAlign(CENTER);
            if(found){
                text(foundIndex,width/2, blockSize + (margins*2) +10);
            }else{
                text("this value was not found", width/2, blockSize + (margins*2) +10);
            }
        }

        // creating info button
        fill(255,195,0);

        rect(width-(20+10), height-(20+10),20,20);

        int infoHeight = height/2;
        if(usingDataTable==false){
            infoHeight = height/2;
            info = "PRESS 'u' TO GET TABLE DATA" +'\n'+
                    "the background color is your target"+'\n'+
                    "press 's' to sort the cells; press 'b' to search the cells"+'\n'+
                    "if you want to restart, press 'r'"+'\n'+
                    "if you want a new target, type the number (0-255) and press 'x'";

        }else if(usingDataTable == true){
            infoHeight = (height/2)-20;
            info = "PRESS 'u' TO GET RANDOM DATA" +'\n'+
                    "this visualiser contains data on the amount of independent bookstores in the U.S. from 2009-2022 (excluding 2020)"+'\n'+
                    "each block represents a different year of data (the color is a variation on the year)"+'\n'+
                    "first press 's' to sort the data by year"+'\n'+
                    "then type the year of data you want to find and then press 'x'"+'\n'+
                    "if you want to find 2015 data, type '150' then 'x'; 2009 = '90' & 'x'"+'\n'+
                    "if you want to restart, press 'r'"+'\n'+
                    "if you want a new target year, type in a new number and press 'x'";
        }
        if(infoShow) {
            if(grayTarget<130){
                fill(255);
            }else{
                fill(0);
            }
            textAlign(CENTER);
            text(info,width/2,infoHeight);
        }
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
                foundIndex = arrL.get(center).getRV();
                //System.out.println(arrL.get(center).getRV());
                //System.out.println(center);
                return arrL.get(center).getRV();
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
                    minI = j;// minumin valeuy is now at index j
                    comp =j; // now comapring with thsi nee index's value
                }
            }
            // changing place on canvas
            int XofI = arrL.get(i).getX();
            int XofM = arrL.get(minI).getX();
            arrL.get(i).setX(XofM);
            arrL.get(minI).setX(XofI);

            // switch min with element at i (in array not canvas)
            Block tempMI = arrL.get(minI); // saving value at minumnet
            arrL.set(minI,arrL.get(i)); // setting cvaley at minI to teh element value at i
            arrL.set(i,tempMI); // setting teh value at i index to the minumnet element value
        }


        if(usingDataTable == false){ // if im not using data tabel, updat eteh retuen value to the new index
           for(int i =0; i < listSize; i++){
               //resteting teh return valeu to teh now new correct index
               arrL.get(i).setRV(i); // resetting teh RV to the new index place
           }
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
        if(key=='u') {
            usingDataTable = ! usingDataTable;
            setup(); // call setup again so that it will recreat teh block with either table data or random stuff in them
        }



        //Implement a feature to allow the user to enter the target value via the keyboard before the search begins.
        if(Character.isDigit((key))){
            digits = digits + key;
        }else if (key =='x'){
            grayTarget = Integer.parseInt(digits);
            digits ="";
        }


    }
    public void mouseClicked(){
        if(mouseX>width-(20+10) && mouseX<width-(20+10)+20 && mouseY > height-(20+10) && mouseY< height-(20+10)+20){
            infoShow = ! infoShow;
        }
    }
}
