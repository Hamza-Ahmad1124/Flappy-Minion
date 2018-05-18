package FlappyMinion;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Minion
{
	protected int x ;
	protected int y ;
	protected double yMotion ;
	protected int width ;
	protected int height ;
	protected BufferedImage minion ;

	public Minion (int x , int y , int width , int height)
	{
		this.x = x ;
		this.y = y ;
		this.width = width ;
		this.height = height ;	
	}
	
	public void loadPic()
	{
		try
		{
			if (FlappyMinion.ranScore <= 5)
			{
				minion = ImageIO.read(new File ("hover.png"));				
			}
			
			else if (FlappyMinion.ranScore <= 10)
			{
				minion = ImageIO.read(new File ("superman.png"));				
			}
			
			else if (FlappyMinion.ranScore <= 15)
			{
				minion = ImageIO.read(new File ("superman2.png"));				
			}
			
			else if (FlappyMinion.ranScore <= 20)
			{
				minion = ImageIO.read(new File ("green.png"));				
			}
			
			else if (FlappyMinion.ranScore <= 25)
			{
				minion = ImageIO.read(new File ("ironman.png"));				
			}	
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	public void render(Graphics2D g2d)
	{
		g2d.drawImage(minion , x , y , null);
	}
}