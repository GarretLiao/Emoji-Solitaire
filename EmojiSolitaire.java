/*EmojiSolitaire.java
Garret Liao
4/12/23
*/

//Java FX imports
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;
import javafx.event.*;

public class EmojiSolitaire extends Application //Child Class of Application
{   
   //Initialing the borderpane and its components
   BorderPane bp = new BorderPane();
   GridPane gp=new GridPane();
   HBox top=new HBox();
   
   //Initializing variables for the header
   int ballsLeft=15;
   int moveCounter=2;
   Label l = new Label(); //Game label
     
   GamePane[][] griddy= new GamePane[4][4]; //2D Gamepane array, used to reference specific Gamepanes for math
   
   public void start(Stage stage) //Starting point
   {  
      //Adding the gridpane to the center of the borderpane, centering it, and setting up proper spacing
      gp.setAlignment(Pos.CENTER);  
      gp.setHgap(10);
      gp.setVgap(10);
      bp.setCenter(gp);
      
      
      l.setFont(new Font("Berlin Sans FB", 30)); //Setting font
      l.setText("Balls left: "+ballsLeft+"   Possible moves: "+moveCounter);
      
      //Adding the gridpane to the center of the borderpane, centering it, and adding a header
      top.setAlignment(Pos.CENTER); 
      top.getChildren().add(l); 
      bp.setTop(top);      
      
      for(int i=0;i<4;i++) //Increments through 4 rows of double array
      {
         for(int j=0;j<4;j++) //Increments through 4 columns of double array
         {
            GamePane gamep=new GamePane(i, j); //Creating a gamepane
            gp.add(gamep, j, i); //Adding the gamepane to the grid for display
            griddy[i][j]=gamep; //Adding the gamepane to the 2D array for math
         }
      }
      
      griddy[2][0].setVisible(false); //Creates a vacancy to start out
      buttonGenerator(); //Initially generates buttons, which listen to the ButtonListener to carry out the game     
      
      Scene scene = new Scene(bp, 600, 600); //Window starts out 600 x 600, borderplane added to scene     
      stage.setScene(scene); //Sets scene
      stage.setTitle("Emoji Solitaire"); //Adds a title
      stage.show(); //Shows the stage once program is ran
   }
   
   public void buttonGenerator() //Generates the buttons
   {   
      moveCounter=0; //Resetting the move counter
      
      for(int i=0;i<4;i++) //Increments through 4 rows of double array
      {
         for(int j=0;j<4;j++) //Increments through 4 columns of double array
         {      
            //Resets all buttons to invisible
            griddy[i][j].getTop().setVisible(false);
            griddy[i][j].getBottom().setVisible(false);
            griddy[i][j].getLeft().setVisible(false);
            griddy[i][j].getRight().setVisible(false);
         }
      }
      
      for(int i=0;i<4;i++) //Increments through 4 rows of double array
      {
         for(int j=0;j<4;j++) //Increments through 4 columns of double array
         {
            if(griddy[i][j].isVisible()==false) //If a gamepane is currently invisible
            {
               if(i-2>=0) //Bounds check, checks that it is possible to have two gamepanes above the invisible gamepane
               {
                  if(griddy[i-1][j].isVisible()==true && griddy[i-2][j].isVisible()==true) //Checks if both panes above are visible
                  {
                     griddy[i-2][j].getTop().setVisible(true); //Makes top button visible of the gamepane two panes above the invisible gamepane
                     moveCounter++; //Increments move counter since a button was made visible
                  }
               }
               
               if(i+2<=3) //Bounds check, checks that it is possible to have two gamepanes below the invisible gamepane
               {
                  if(griddy[i+1][j].isVisible()==true && griddy[i+2][j].isVisible()==true) //Checks if both panes below are visible
                  {
                     griddy[i+2][j].getBottom().setVisible(true); //Makes bottom button visible of the gamepane two panes below the invisible gamepane
                     moveCounter++; //Increments move counter since a button was made visible
                  }
               }
               
               if(j-2>=0) //Bounds check, checks that it is possible to have two gamepanes left of the invisible gamepane
               {
                  if(griddy[i][j-1].isVisible()==true && griddy[i][j-2].isVisible()==true) //Checks if both panes to the left are visible
                  {
                     griddy[i][j-2].getLeft().setVisible(true); //Makes left button visible of the gamepane two panes left of the invisible gamepane
                     moveCounter++; //Increments move counter since a button was made visible
                  }
               }
               
               if(j+2<=3) //Bounds check, checks that it is possible to have two gamepanes right of the invisible gamepane
               {
                  if(griddy[i][j+1].isVisible()==true && griddy[i][j+2].isVisible()==true) //Checks if both panes to the right are visible
                  {
                     griddy[i][j+2].getRight().setVisible(true); //Makes top button visible of the gamepane two panes right of the invisible gamepane
                     moveCounter++; //Increments move counter since a button was made visible
                  }
               }
            }
         }
      }
   }
   
   public void jump(int y, int x, Object clicked) //Takes in two ints and an object
   {
      if(clicked==griddy[y][x].getTop()) //If a top button is hit
      {
         griddy[y+1][x].setVisible(false); //One below invisible
         griddy[y+2][x].setVisible(true); //Two below visible
      }
      
      if(clicked==griddy[y][x].getBottom()) //If a bottom button is hit
      {
         griddy[y-1][x].setVisible(false); //One above invisible
         griddy[y-2][x].setVisible(true); //Two above visible
      }
      
      if(clicked==griddy[y][x].getLeft()) //If a left button is hit
      {
         griddy[y][x+1].setVisible(false); //One to right invisible
         griddy[y][x+2].setVisible(true); //Two to right visible
      }
      
      if(clicked==griddy[y][x].getRight()) //If a right button is hit
      {
         griddy[y][x-1].setVisible(false); //One to left invisible
         griddy[y][x-2].setVisible(true); //Two to left visible
      }
      
      griddy[y][x].setVisible(false); //Sets gamepane of button clicked invisible
   }   
   
   public static void main(String[] args) //Launches program
   {
      launch(args); //Launches!!!
   }
   
   public class CircleCanvas extends Canvas //Circle class
   {
      public CircleCanvas() //Canvas constructor, 80 x 80
      {
         setWidth(80);
         setHeight(80);
      }
      
      public void drawSmiley() //Draws the default smiley face
      {
         GraphicsContext gc = getGraphicsContext2D(); //Creating a graphics context
         gc.clearRect(0,0,80,80);
         gc.setFill(Color.GOLD);
         gc.fillOval(0,0,80,80);
         
         gc.setFill(Color.BLACK);
         gc.fillOval(15,15,50,50);
         
         gc.setFill(Color.GOLD);
         gc.fillOval(20,20,40,40);
         gc.fillRect(15,15,50,30);
         
         gc.setFill(Color.BLACK);
         gc.fillOval(20,20,10,10);
         gc.fillOval(50,20,10,10);
      }
      
      public void drawMad() //Draws an mad face if game is lost
      {
         GraphicsContext gc = getGraphicsContext2D(); //Creating a graphics context
         gc.clearRect(0,0,80,80);
         gc.setFill(Color.GOLD);
         gc.fillOval(0,0,80,80);
         
         gc.setFill(Color.BLACK);
         gc.fillRect(15,25,20,4);
         gc.fillRect(45,25,20,4);
         gc.fillRect(15,51,50,4);
      }
      
      public void drawGrinning() //Draws a grinning face if the game is won
      {
         GraphicsContext gc = getGraphicsContext2D(); //Creating a graphics context
         gc.clearRect(0,0,80,80);
         gc.setFill(Color.GOLD);
         gc.fillOval(0,0,80,80);
         
         gc.setFill(Color.BLACK);
         gc.fillOval(15,15,50,50);
         
         gc.setFill(Color.WHITE);
         gc.fillOval(18,18,44,44);
         
         gc.setFill(Color.GOLD);
         gc.fillRect(15,15,50,25);
         
         gc.setFill(Color.BLACK);
         gc.fillRect(15,37,50,3);
         gc.fillOval(20,18,12,17);
         gc.fillOval(48,18,12,17);
         
         gc.setFill(Color.GOLD);
         gc.fillOval(20,27,12,10);
         gc.fillOval(48,27,12,10);
         
         gc.setFill(Color.BLACK);
         gc.fillRect(15,37,50,3);
      }
   }
   
   public class GamePane extends GridPane //Gamepane is a child class of gridpane
   {
      //Initializing objects and ints
      Button bt;
      Button bb;
      Button bl;
      Button br;
      int y;
      int x;
      
      public GamePane(int y, int x) //Gamepane constructor
      {   
         //Takes in parameters to use for jump method
         this.y=y;
         this.x=x;
         
         //Creating a circle canvas and drawing a smiley on it
         CircleCanvas cc=new CircleCanvas();
         cc.drawSmiley();
         
         //Face tests
         //cc.drawMad();
         //cc.drawGrinning();
         
         //Creating a top, bottom, left and right button
         bt=new Button();
         bb=new Button();
         bl=new Button();
         br=new Button();
         
         //Setting button sizes
         bt.setPrefSize(80,20); 
         bb.setPrefSize(80,20); 
         bl.setPrefSize(20,80); 
         br.setPrefSize(20,80); 
         
         //Setting buttons to listener
         bt.setOnAction(new ButtonListener()); 
         bb.setOnAction(new ButtonListener()); 
         bl.setOnAction(new ButtonListener()); 
         br.setOnAction(new ButtonListener()); 
         
         //Adding buttons and canvas to gamepane
         add(bt, 1, 0);   
         add(bb, 1, 2);
         add(bl, 0, 1);
         add(br, 2, 1);
         add(cc, 1, 1);
      }
      
      public class ButtonListener implements EventHandler<ActionEvent> //Buttonlistener
      {
         public void handle(ActionEvent e) //Handle method
         {
            jump(y, x, e.getSource()); //Calls jump method
            buttonGenerator(); //Regenerates buttons
            ballsLeft--; //One ball removed every time a move is made
            l.setText("Balls left: "+ballsLeft+"   Possible moves: "+moveCounter); //Game label
            
            if(moveCounter==0) //No moves left, no buttons to click
            {
               //l.setTextFill(Color.WHITE);
               l.setText("YOU LOSE!!!"); //Lose message
               bp.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY))); //Background turns red
               
               for(int i=0;i<4;i++) //Increments through 4 rows of double array
               {
                  for(int j=0;j<4;j++) //Increments through 4 columns of double array
                  {
                     if(griddy[i][j].isVisible()==true) //If a gamepane is currently invisible
                     {
                        CircleCanvas cc1=new CircleCanvas(); //Creates a new circle canvas
                        cc1.drawMad(); //Draws mad face
                        griddy[i][j].add(cc1, 1, 1); //Adds mad face to gamepane
                     }
                  }
               }
            }
            
            if(ballsLeft==1) //Overrides if there are no moves but only one ball is left
            {
               //l.setTextFill(Color.WHITE);
               l.setText("YOU WIN!!!"); //Win message
               bp.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY))); //Background turns green
               
               for(int i=0;i<4;i++) //Increments through 4 rows of double array
               {
                  for(int j=0;j<4;j++) //Increments through 4 columns of double array
                  {
                     if(griddy[i][j].isVisible()==true) //If a gamepane is currently invisible
                     {
                        CircleCanvas cc2=new CircleCanvas(); //Creates a new circle canvas
                        cc2.drawGrinning(); //Draws mad face
                        griddy[i][j].add(cc2, 1, 1); //Adds mad face to gamepane
                     }
                  }
               }
            }
         }
      }
         
      //Accessors
      public Button getTop()
      {return bt;}
      
      public Button getBottom()
      {return bb;}
      
      public Button getLeft()
      {return bl;}
      
      public Button getRight()
      {return br;}
   }
}