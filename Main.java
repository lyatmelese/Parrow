import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.UIManager;
 
import java.awt.*;
class Main {
   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      
      }
      catch (Exception e) {
         System.out.println("Look and Feel not set");
      }
   
      JFrame gameBox = new JFrame ("Parrow");
      gameBox.setSize(600,600);
      gameBox.setLocation(0,0);
      gameBox.setBackground(Color.BLACK);
      gameBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameBox.setContentPane( new Intro());
      gameBox.setVisible(true);
   }
   public static class Intro extends JPanel
   {
      private JLabel label1;
      private JLabel label2;
      private JButton generate;
      public JPanel center;
      public JPanel south;
      public ImageIcon logo;
      public ImageIcon instructions;
      public Intro()
      {
         setBackground(Color.BLACK);
         setLayout(new BorderLayout());
         JPanel north = new JPanel();
         //north.setLayout(new FlowLayout());
         add(north, BorderLayout.NORTH);
         
         logo = new ImageIcon ("logo.PNG");
         label1 = new JLabel (logo);
         north.add(label1);
         
      
         south = new JPanel();
         add(south, BorderLayout.SOUTH);
         generate = new JButton ("Create");
         generate.addActionListener(new Intro1 ());
         south.add(generate);      
         
         center = new JPanel();
         center.setLayout(new GridLayout(1,1));
         add (center,BorderLayout.CENTER);
         
         instructions= new ImageIcon("instructions.PNG");
         label2 = new JLabel(instructions);
         center.add(label2);
         
      }
      public class Intro1 implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            //label1.setText("");
            center.remove(label2);
            diamondPanel panel = new diamondPanel();
            center.add(panel);
            south.remove(generate);
         }
      }         
   
   }
   public static class diamondPanel extends JPanel
   {
      private static final int FRAMEX = 600;
      private static final int FRAMEY = 600;
      private static final Color BACKGROUND = Color.BLACK;
      private static final Color TILE_COLOR = Color.GRAY;      
      private static final int BUMPER_WIDTH = 75;
      private static final int BUMPER_HEIGHT = 125;
      
      private int[][] matrix;
      private BufferedImage myImage;
      private Graphics myBuffer;      
      private Timer timer;   
      private Tile tile, tile1, tile2, tile3;  
      private int tileNum, tile1Num, tile2Num, tile3Num;
      private int originalHeight;
      private Tile marker;
      private JButton reset;
      private int streak;
                         
      public diamondPanel()
      {
         streak = 0;
         matrix = random();
         tileNum = tile1Num = tile2Num = tile3Num = 0;
         originalHeight = 60;
         myImage =  new BufferedImage(FRAMEX, FRAMEY, BufferedImage.TYPE_INT_RGB);
         myBuffer = myImage.getGraphics();
         myBuffer.setColor(BACKGROUND);
         myBuffer.fillRect(0, 0, FRAMEX, FRAMEY);
         
         reset = new JButton("Reset");
         reset.addActionListener( new resetListener() );
         reset.setEnabled(false);
         add(reset, BorderLayout.SOUTH);       
              
         marker = new Tile(50, 440, 20, 20, Color.GREEN);
         tile = new Tile(90, -440, 60, originalHeight, Color.GRAY);
         tile1 = new Tile(210, -520, 60, originalHeight, Color.GRAY);
         tile2 = new Tile(330, -240, 60, originalHeight, Color.GRAY);
         tile3 = new Tile(450, -300, 60, originalHeight, Color.GRAY);
         
         tile.setHeight(originalHeight * matrix[0][tileNum]);
         tile1.setHeight(originalHeight * matrix[1][tile1Num]);
         tile2.setHeight(originalHeight * matrix[2][tile2Num]);
         tile3.setHeight(originalHeight * matrix[3][tile3Num]);
         tileNum++; tile1Num++; tile2Num++; tile3Num++;
         
         tile.move();
         tile1.move();
         tile2.move();
         tile3.move();  
         addKeyListener(new Key());
         setFocusable(true);
                      
         timer = new Timer(1, new Listener());
         timer.start();
      }
   
      public void paintComponent(Graphics g)
      {
         g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
         
      
      /*g.setColor(Color.GRAY);
      g.fillRect(90, 20, 60, 60);
      g.fillRect(210, 20, 60, 60);
      g.fillRect(330, 20, 60, 60);
      g.fillRect(450, 20, 60, 60);*/
      }   
      
      private class resetListener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            reset.setText("Reset");
            reset.setEnabled(false);            
            tileNum = tile1Num = tile2Num = tile3Num = 0;
            marker.setColor(Color.GREEN);
            tile.setY(-440); tile1.setY(-520); tile2.setY(-240); tile3.setY(-300);
            timer.start();
         }
      }
   
         
      private class Listener implements ActionListener
      {
         public void actionPerformed(ActionEvent e)
         {
            myBuffer.setColor(BACKGROUND);
            myBuffer.fillRect(0,0,FRAMEX,FRAMEY); 
            myBuffer.setColor(Color.WHITE);
            myBuffer.drawLine (120, 0, 120, 600);
            myBuffer.drawLine (240, 0, 240, 600);
            myBuffer.drawLine (360, 0, 360, 600);
            myBuffer.drawLine (480, 0, 480, 600);
            myBuffer.drawLine (0, 450, 600, 450);
            
             //left arrow
            myBuffer.fillRect(180,485,15,10);
            int x1Points[] = {181, 170, 181};
            int y1Points[] = {500, 490, 477};
            myBuffer.fillPolygon(x1Points, y1Points, 3);
            //down arrow
            myBuffer.fillRect(300,475,5,25);
            int x2Points[] = {295, 302, 309};
            int y2Points[] = {496, 510, 496};
            myBuffer.fillPolygon(x2Points, y2Points, 3);
            //up arrow
            myBuffer.fillRect(420,485,5,23);
            int x3Points[] = {415, 422, 429};
            int y3Points[] = {485, 471, 485};
            myBuffer.fillPolygon(x3Points, y3Points, 3);
            // right arrow
            myBuffer.fillRect(540,490,15,12);
            int x4Points[] = {555, 566, 555};
            int y4Points[] = {480, 495, 510};
            myBuffer.fillPolygon(x4Points, y4Points, 3);
         
             
            tile.move();
            tile1.move();
            tile2.move();
            tile3.move();
            tile.draw(myBuffer);
            tile1.draw(myBuffer);
            tile2.draw(myBuffer);
            tile3.draw(myBuffer);
            
            if((450 < tile.getY() && tile.getPressed() == false) || (450 < tile1.getY() && tile1.getPressed() == false) ||
            (450 < tile2.getY() && tile2.getPressed() == false) || (450 < tile3.getY() && tile3.getPressed() == false))
               marker.setColor(Color.RED);    
            
            if(tile.getY() > FRAMEY && tileNum < 3) {
               tile.setY(-360);
               tile.setHeight(originalHeight * matrix[0][tileNum]);
               tileNum++;
            }           
            if(tile1.getY() > FRAMEY && tile1Num < 3) {
               tile1.setY(-480);
               tile1.setHeight(originalHeight * matrix[1][tile1Num]);
               tile1Num++;
            }         
            if(tile2.getY() > FRAMEY && tile2Num < 3) {
               tile2.setY(-300);
               tile2.setHeight(originalHeight * matrix[2][tile2Num]);
               tile2Num++;
            }               
            if(tile3.getY() > FRAMEY && tile3Num < 3) {
               tile3.setY(-540);
               tile3.setHeight(originalHeight * matrix[3][tile3Num]);
               tile3Num++;
            }  
            
            if(tileNum >= 3 && tile1Num >= 3 && tile2Num >= 3 && tile3Num >= 3 
            && tile.getY() > FRAMEY && tile1.getY() > FRAMEY && tile2.getY() > 
            FRAMEY && tile3.getY() > FRAMEY && marker.getColor() == Color.RED) {
               streak = 0;
               timer.stop();
               reset.setText("Please try again.");
               reset.setEnabled(true);
            } 
            
            if(tileNum >= 3 && tile1Num >= 3 && tile2Num >= 3 && tile3Num >= 3 
            && tile.getY() > FRAMEY && tile1.getY() > FRAMEY && tile2.getY() > 
            FRAMEY && tile3.getY() > FRAMEY && marker.getColor() == Color.GREEN && streak < 3) {
               streak++;
               timer.stop();
               reset.setText("Good Job! Do it " + (3-streak) + "more times in a row!");
               reset.setEnabled(true);
            } 
            
            if(tileNum >= 3 && tile1Num >= 3 && tile2Num >= 3 && tile3Num >= 3 
            && tile.getY() > FRAMEY && tile1.getY() > FRAMEY && tile2.getY() > 
            FRAMEY && tile3.getY() > FRAMEY && marker.getColor() == Color.GREEN && streak >= 3) {
               streak++;
               timer.stop();
               reset.setText("Amazing! You've hopefully memorized your pattern based password!");
            }     
                            
            marker.draw(myBuffer);    
            
            repaint();
         }
      }
      
      private class Key extends KeyAdapter
      {
         public void keyPressed(KeyEvent e)
         {
            if(tile.getY()+tile.getHeight() >= 450 && 450 > tile.getY() && e.getKeyCode() == KeyEvent.VK_LEFT)
               tile.setPressed(true); 
            else if((tile.getY()+tile.getHeight() <= 450 || 450 < tile.getY()) && e.getKeyCode() == KeyEvent.VK_LEFT)
               marker.setColor(Color.RED);
               
            if(tile1.getY()+tile1.getHeight() >= 450 && 450 > tile1.getY() && e.getKeyCode() == KeyEvent.VK_DOWN)
               tile1.setPressed(true); 
            else if((tile1.getY()+tile1.getHeight() <= 450 || 450 < tile1.getY()) && e.getKeyCode() == KeyEvent.VK_DOWN)
               marker.setColor(Color.RED);
            
            if(tile2.getY()+tile2.getHeight() >= 450 && 450 > tile2.getY() && e.getKeyCode() == KeyEvent.VK_UP)
               tile2.setPressed(true); 
            else if((tile2.getY()+tile2.getHeight() <= 450 || 450 < tile2.getY()) && e.getKeyCode() == KeyEvent.VK_UP)
               marker.setColor(Color.RED);
               
            if(tile3.getY()+tile3.getHeight() >= 450 && 450 > tile3.getY() && e.getKeyCode() == KeyEvent.VK_RIGHT)
               tile3.setPressed(true); 
            else if((tile3.getY()+tile3.getHeight() <= 450 || 450 < tile3.getY()) && e.getKeyCode() == KeyEvent.VK_RIGHT)
               marker.setColor(Color.RED);                          
         }
      
      }
      
      public int[][] random() {
         
         int m = 4;
         int n = 3;
         int[][] matrix = new int[m][n];
         
         for(int r = 0; r < m; r++)
         {        
            for(int c = 0; c < n; c++)
            { 
               matrix[r][c] = (int)(Math.random() * 3 + 1);
               System.out.print("" + matrix[r][c] + ",");
            }
            System.out.print("\n");
         } 
         return matrix;       
      }   
   
      public static class Tile
      {
         private double myX;
         private double myY;
         private double myWidth;
         private double myHeight;
         private Color myColor;
         private boolean pressed;
      
         public Tile()
         {
            myX = 200;
            myY = 200;
            myWidth = 50;
            myHeight = 75;
            myColor = Color.GRAY;
            pressed = false;
         }
      
         public Tile(int x, int y, int w, int h, Color c)
         {
            myX = x;
            myY = y;
            myWidth = w;
            myHeight = h;
            myColor = c;
            pressed = false;
         }
      
         public void draw(Graphics myBuffer) 
         {
            myBuffer.setColor(getColor());
            myBuffer.fillRect((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
         }
      
         public double getX() 
         { 
            return myX;
         }
         public double getY()      
         { 
            return myY;
         }
         public double getWidth() 
         { 
            return myWidth;
         }
         public double getHeight()
         {
            return myHeight;
         }
         public Color getColor() 
         { 
            return myColor;
         }
         public boolean getPressed() 
         { 
            return pressed;
         }
      
         public void setX(double x)
         {
            myX = x;
         } 
         public void setY(double y)
         {
            myY = y;
         } 
         public void setWidth(double w)
         {
            myWidth = w;
         }
         public void setHeight(double h)
         {
            myHeight = h;
         }
         public void setColor(Color c)
         {
            myColor = c;
         }
         public void setPressed(boolean p)
         {
            pressed = p;
         }
      
      
         public void move()
         {
            setY(getY() + 2);
         }
      }
   }
 
}

