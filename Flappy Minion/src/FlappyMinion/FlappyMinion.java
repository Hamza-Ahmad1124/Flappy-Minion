package FlappyMinion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyMinion implements ActionListener , MouseListener , KeyListener
{
	public static FlappyMinion fm ;
	public Renderer renderer ;
	public Minion minion;
	public Background bg ;
	public Random rand ;
	
	public ArrayList<Rectangle> columns ;
	
	public final static int WIDTH = 1024 ;
	public final static int HEIGHT = 650 ;
	
	public int ticks ;
	public int score;
	public static int mWidth ;
	public static int mHeight ;
	public static int ranScore;
	
	public boolean gameOver , started ;
	
	@SuppressWarnings("static-access")
	public FlappyMinion()
	{
		JFrame J = new JFrame ("Flappy Minion");
		Timer T = new Timer (20 , this);  // THE GAME LOOP
		
		J.setSize(WIDTH, HEIGHT);
		J.setLocationRelativeTo(null);
		J.setDefaultCloseOperation(J.EXIT_ON_CLOSE);
		J.setResizable(false);
		J.addMouseListener(this);
		J.addKeyListener(this);
		J.setFocusable(true);;
		
		renderer = new Renderer ();
		rand = new Random();
		bg = new Background ();
		
		J.add(renderer);
		J.setVisible(true);
		
		checkWidthHeight();
		
		minion = new Minion (WIDTH/2 - 10 , HEIGHT/2 - 10 , mWidth , mHeight); 
		
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		T.start();	// Starting THE GAME LOOP
	}
	
	public void checkWidthHeight()  // To select the fixed height and width of different minion images so that intersection can be more precise
	{		
		if (ranScore <= 5)
		{
			mHeight = 30 ; // Height of the Hover Image
			mWidth = 88 ; // Width of Hover Image
		}
		
		else if (ranScore <= 10)
		{
			mHeight = 50 ; // Height of all other Images
			mWidth = 59 ; // Width of superman Image
		}
		
		else if (ranScore <= 15)
		{
			mHeight = 50 ; // Height of all other Images
			mWidth = 60 ; // Width of superman2 Image
		}
		
		else if (ranScore <= 20)
		{
			mHeight = 50 ; // Height of all other Images
			mWidth = 65 ; // Width of green Image
		}
		
		else if (ranScore <= 25)
		{
			mHeight = 50 ; // Height of all other Images
			mWidth = 65 ; // Width of ironman Image
		}
	}
	
	public void render(Graphics2D g2d) // Drawing everything on Panel
	{	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON ); // Used to make the text and Images more Clearer
		
		bg.render(g2d);
		
		checkWidthHeight();
		
		minion.width = mWidth ;
		minion.height = mHeight ;
		
		minion.loadPic();     // Loading Minion images
		minion.render(g2d);  // Drawing Minion Images
		
		g2d.setColor(new Color (0 , 0 , 125 , 64));
		g2d.fillRect(0, HEIGHT - 100, WIDTH , 100);
		
		g2d.setColor(new Color (0 , 0 , 200 , 100));
		g2d.fillRect(0, HEIGHT - 100, WIDTH , 15);
		
		for (Rectangle column : columns)
		{
			paintColumn(g2d , column);
		}
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font ("Arial" , 1 , 100));
		
		if (!started) 
		{
			g2d.drawString("Click To Start !" , 100 ,( HEIGHT / 2 -25));
		}
		
		if (gameOver)
		{
			g2d.drawString("GAME OVER !" , 200 ,( HEIGHT / 2 -25));
		}
		
		if ((!gameOver) && (started))
		{
			g2d.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}
	
	public void paintColumn(Graphics g , Rectangle columns)
	{
		g.setColor(new Color (0 , 0 , 255 , 180));
		g.fillRect(columns.x, columns.y, columns.width, columns.height);
	}
	
	public void addColumn(boolean start)
	{
		int space = 300 ;
		int width = 100 ;
		int height = 50 + rand.nextInt(300);
		
		if (start)
		{
			columns.add(new Rectangle (WIDTH + width + columns.size() * 300 , HEIGHT - height - 100 , width , height)); // Lower Rectangle
			columns.add(new Rectangle (WIDTH + width + (columns.size() -1 ) * 300 , 0 , width , HEIGHT - height - space ));  // Upper Rectangle
		}
		
		else
		{
			columns.add(new Rectangle (columns.get(columns.size() - 1).x + 600 , HEIGHT - height - 100 , width , height)); // Lower Rectangle
			columns.add(new Rectangle (columns.get(columns.size() - 1).x , 0 , width , HEIGHT - height - space )); // Upper Rectangle
		}
	}
	
	public void actionPerformed(ActionEvent A) // Called after every 20 milliseconds (Value is set at the declaration of THE GAME LOOP)
	{		
		checkWidthHeight();
		bg.update();

		int speed = 10 ; // speed of the Game
		
		ticks ++ ;
		
		if (started)
		{
			bg.setVector(-4, 0);
			
			for (int i = 0 ; i < columns.size() ; i++) // For the first four columns
			{
				Rectangle column = columns.get(i);
				column.x -= speed ;
			}
			
			if (ticks % 2 == 0 && minion.yMotion < 5) // GRAVITY  Takes The Ball Down
			{
				minion.yMotion += 2 ;
			}
			
			for (int i = 0 ; i < columns.size() ; i++) // Removing Columns
			{
				Rectangle column = columns.get(i);
				
				if (column.x + column.width < 0)
				{
					columns.remove(column);
					
					if (column.y == 0)
					{
						addColumn(false);		
					}
				}
			}
			
			minion.y += minion.yMotion ;
						
			for (Rectangle column : columns)
			{
				// Checking For Score
				if ( column.y == 0 && minion.x + minion.width / 2 > column.x + column.width / 2 - 5 && minion.x + minion.width / 2 < column.x + column.width / 2 + 5)
				{
					score ++ ;
					ranScore ++ ;
					
					if (ranScore > 25)
					{
						ranScore = 0 ;
					}
				}
				
				if (column.intersects(new Rectangle (minion.x , minion.y , minion.width , minion.height))) // Checking for Intersection
				{
					gameOver = true ;
					
					if (minion.x <= column.x)  // Process to be done when minion hits the columns horizontally
					{
						minion.x = column.x - minion.width ;						
					}
					
					else // Process to be done when minion hits the columns vertically
					{
						if (column.y != 0) // For the Lower Columns
						{
							minion.y = column.y - minion.height ;
						}
						
						else if (minion.y < column.height) // For the Upper columns
						{
							minion.y = column.height ;
						}
					}
				} 
			}
			
			if (minion.y + minion.height > HEIGHT - 100 || minion.y < 0)
			{
				gameOver = true ;
			}
			
			if (minion.y + minion.height >= HEIGHT - 100)
			{
				minion.y = HEIGHT - 100 - minion.height ;
			}
		}
		renderer.repaint();
	}
	
	public void jump() 
	{	
		if (gameOver)
		{
			minion = new Minion (WIDTH/2 -10 , HEIGHT/2 - 10 , mWidth , mHeight);
			
			columns.clear();
			minion.yMotion = 0 ;
			score = 0 ;
			ranScore = 0 ;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false ;
		}
		
		if (!started)
		{
			started = true ; 
		}
		
		else if (!gameOver)
		{
			if (minion.yMotion > 0)
			{
				minion.yMotion = 0 ;
			}
			
			minion.yMotion -= 10 ;
		}
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		jump();
	}
	
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			jump ();
		}
	}

	public static void main (String [] args)
	{
		fm = new FlappyMinion();
	}
	
	public void mouseEntered(MouseEvent e) 
	{}

	public void mouseExited(MouseEvent e) 
	{}

	public void mousePressed(MouseEvent e) 
	{}

	public void mouseReleased(MouseEvent e) 
	{}

	public void keyPressed(KeyEvent e) 
	{}

	public void keyTyped(KeyEvent arg0) 
	{}
}