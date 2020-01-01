import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphic extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawCanvas canvas;
	int width = 1500;
	int height = 800;
	int[] xcoord = new int[] { 410,705,1000,410,705,1000,130, 130,1260,1260 };
	int[] ycoord = new int[] { 140,140,140,660,660,660,300, 470, 300,470 };
	Table t;
	JButton again;

	Graphic(Table t) {
		this.t = t;
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(width, height));
		Container cp = getContentPane();
		cp.add(canvas);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Poker Solver");
		setVisible(true);
		setResizable(true);
	}
	Image getImage(Card c)
	{
		String name = "cards_png_zip/PNG/"+c+".png";
		try {
			return ImageIO.read(new File(name));
		} catch (IOException e) {
			System.out.println("RIP");
		}
		return null;
	}

	private class DrawCanvas extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			long f = System.currentTimeMillis();
			Rectangle r = canvas.getBounds();
			width = r.width;
			height = r.height;
			if(width<1200)width = 1200;
			if(height<600)height = 600;
			BufferedImage facedown = null;
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			try {
				facedown = ImageIO.read(new File("cards_png_zip/PNG/green_back.png"));
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Simply Complicated.ttf")));
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g.setFont(new Font("Simply Complicated", Font.PLAIN, 25));
			GradientPaint gb = new GradientPaint(0, 0, new Color(150, 150, 150), width, height, new Color(50, 50, 50));
			GradientPaint gw = new GradientPaint(0, 0, new Color(0, 120, 0), width, height, new Color(0, 120, 0));
			GradientPaint gr = new GradientPaint(0, 0, new Color(255, 0, 0), width, height, new Color(90, 0, 0));
			g2d.setPaint(gb);
			g2d.fillRect(0, 0, width, height);
			g2d.setPaint(gr);
			g2d.fillOval(-100, -100, width+200, height+200);
			g2d.setPaint(gw);
			g2d.fillOval(0, 0, width, height);
			g2d.setPaint(new Color(255, 255, 255));
			g2d.setStroke(new BasicStroke((float) 4.0));
			g2d.drawRoundRect(width / 5, height / 4, width * 3 / 5, height / 2, 100, 100);
			// draw Grids, delete after finished
			for (int i = 0; i < width; i += 100) {
				g2d.drawLine(i, 0, i, 10);
				g2d.drawString(Integer.toString(i), i - 10, 40);
			}
			for (int i = 0; i < height; i += 100) {
				g2d.drawLine(0, i, 10, i);
				g2d.drawString(Integer.toString(i), 20, i + 10);
			}
			Player button = t.button;
			Player player = button.next;
			int i = 0;
			while(player!=button) {
				paintCard(player,i,g2d);
				player =  player.next;
				i++;
			}
			paintCard(button,i,g2d);
			int xcomm = 450*width/1500;
			for(Card c:t.communityCard())
			{
				Image card = getImage(c);
				g2d.drawImage(card,xcomm,height*350/800,width*100/1500,height*100/800,this);
				g2d.drawRect((xcomm-10), height*340/800, width*120/1500, height*120/800);
				xcomm+=width*120/1500;
			}
			again = new JButton("Run it back");
			again.setBackground(Color.green);
			again.setBounds(width*650/1500, height*475/800, width*200/1500, height*100/800);
			this.add(again);
			again.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					t.runitback();
					repaint();
				}
			});
			System.out.println(System.currentTimeMillis()-f);
		}
		void paintCard(Player player, int i, Graphics2D g2d)
		{

			Image one = getImage(player.getList().get(0));
			Image two = getImage(player.getList().get(1));
			g2d.drawImage(one,width*xcoord[i]/1500 -width*55/1500, height*ycoord[i]/800-height*55/800, width*110/1500, height*110/800, this);
			g2d.drawImage(two,width*xcoord[i]/1500+width*55/1500, height*ycoord[i]/800-height*55/800, width*110/1500, height*110/800, this);
		}
	}
}
