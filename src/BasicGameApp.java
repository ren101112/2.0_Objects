//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image astroPic;
    public Image asteroidpic;


   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
    private Astronaut astro2;
    private asteroid asteroid1;
    private asteroid asteroid2;




   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();

       //the int is casting and saying that you want a whole number, and the math.random says to pick a random number and the 10 is how many numbers there are ALWAYS STARTING AT 0
      //variable and objects
      //create (construct) the objects needed for the game and load up
        int randx = (int)(Math.random()*999)+1;
        int randy = (int)(Math.random()*699)+1;
		astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png");
        asteroidpic = Toolkit.getDefaultToolkit().getImage("asteroidimg.jpg");//load the picture
		astro = new Astronaut(500,350);
        astro2 = new Astronaut(randx,randy);
        asteroid1 = new asteroid(100,200);
        asteroid2= new asteroid(883,322);


	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		astro.move();
        astro2.move();
        asteroid1.move();
        asteroid2.dx=-5;
        asteroid2.dy=-10;
        stroidCrashing();
        asteroid2.move();
        crashing();


	}
    public void stroidCrashing(){
        if(asteroid1.hitbox2.intersects(asteroid2.hitbox2)){
            System.out.println("NASA: asteroid collision");
            asteroid2.dx=-asteroid2.dx;
            asteroid1.dx=-asteroid1.dx;
            asteroid2.dy=-asteroid2.dy;
            asteroid1.dy=-asteroid1.dy;

        }


    }

    public void crashing(){
        //check to see if astros are crashing into each other
        if(astro.hitbox.intersects(astro2.hitbox)){
            System.out.println("crash!!!!!!!!");
            astro.dx=-astro.dx;
            astro2.dx=-astro2.dx;
            astro.dy=-astro.dy;
            astro2.dy=-astro2.dy;


        }



    }
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);//we dont want to draw an image before we make our background first

      //draw the image of the astronaut
		g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
        g.drawImage(asteroidpic, asteroid1.xpos, asteroid1.ypos, asteroid1.width, asteroid1.height, null);
        g.drawImage(astroPic, astro2.xpos,astro2.ypos, astro2.width, astro2.height,null);
        g.drawImage(asteroidpic,asteroid2.xpos,asteroid2.ypos,asteroid2.width,asteroid2.height,null);
        g.drawRect(astro.hitbox.x,astro.hitbox.y,astro.hitbox.width,astro.hitbox.height);
        //g.fillRect(100,300,200,200);

		g.dispose();//do not draw anything after here__ END DRAWING

		bufferStrategy.show();
	}
}