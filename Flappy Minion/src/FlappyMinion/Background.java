package FlappyMinion;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Background
{
	private BufferedImage image;

	private double x;
	private double y;
	private double dx;
	private double dy;
	
	public Background()
	{
		try
		{
			image = ImageIO.read(new File("background.jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setVector(double dx, double dy)
	{
		this.dx = dx;
		this.dy = dy;
	}

	public void update()
	{
		x += dx;
		y += dy;
	}

	public void render(Graphics2D g)
	{
		if (!(FlappyMinion.fm.started))
		{
			g.drawImage(image, (int) x, (int) y, null);
		}
		
		else
		{			
			g.drawImage(image, (int) x, (int) y, null);
			
			if (x < 0) // if x is < 0 we have to draw an extra image on right 
			{
				g.drawImage(image, (int) x + FlappyMinion.WIDTH, (int) y, null);
			}
			
			if (x <= -FlappyMinion.WIDTH)
			{
				x = 0;
				g.drawImage(image, (int) x + FlappyMinion.WIDTH, (int) y, null);
			}
		}
	}
}