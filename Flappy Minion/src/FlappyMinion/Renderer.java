package FlappyMinion;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Renderer extends JPanel
{
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g ;
		
		FlappyMinion.fm.render(g2d);
	}
}