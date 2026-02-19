import java.awt.*;



public class asteroid {
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle hitbox2;
    public boolean isCrashing;
    //a boolean to denote if the hero is alive or dead.


    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public asteroid(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx =8;
        dy =-3;
        width = 60;
        height = 60;
        isAlive = false;
        hitbox2=new Rectangle(xpos,ypos,width,height);
        isCrashing=false;
        isAlive=true;


    } // constructor

    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {


        if (xpos>=950){
            xpos=2;

        }
        if (xpos<=0){
            xpos=950;
        }
        if (ypos>=650){
            ypos=1;

        }
        if (ypos<=0){
            ypos=650;
        }
       // if (xpos>=1000-width) {//the right wall
            //dx=-dx; new


        //}
       // if (xpos<=0) {//the right wall
            //dx=-dx;


        //}
        //if (ypos>=700-height) {//the right wall
           // dy=-dy;


        //}
        //if (ypos<=0) {//the right wall
           // dy=-dy;


        //}

        xpos = xpos + dx;
        hitbox2=new Rectangle(xpos,ypos,width,height);
        ypos = ypos + dy;
        hitbox2=new Rectangle(xpos,ypos,width,height);
    }




}
