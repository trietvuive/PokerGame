import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graphic extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawCanvas canvas;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int originalwidth = screenSize.width;
	int originalheight = screenSize.height;
	int width, height;
	double widthratio, heightratio;
	int[] xcoord = new int[] { 410, 705, 1000, 410, 705, 1000, 130, 130, 1300, 1300 };
	int[] ycoord = new int[] { 140, 140, 140, 720, 720, 720, 300, 470, 300, 470 };
	Table t;
	JButton all, flop, turn, river, again;
	JFrame frame = this;

	Graphic(Table t) {
		this.t = t;
		canvas = new DrawCanvas();
		canvas.setPreferredSize(new Dimension(originalwidth, originalheight - 50));
		Container cp = getContentPane();
		cp.add(canvas);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Poker Solver");
		setResizable(true);
		setVisible(true);
		createButton();

	}

	void createButton() {
		all = new JButton("Run it all");
		all.setBackground(Color.green);
		all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.runitall();
				repaint();
			}
		});
		flop = new JButton("Deal Flop");
		flop.setBackground(Color.green);
		flop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.dealFlop();
				repaint();
			}
		});
		turn = new JButton("Deal Turn");
		turn.setBackground(Color.green);
		turn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.dealTurn();
				repaint();
			}
		});
		river = new JButton("Deal River");
		river.setBackground(Color.green);
		river.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.dealRiver();
				repaint();
			}
		});
		again = new JButton("Run it again");
		again.setBackground(Color.green);
		again.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.runitback();
				;
				repaint();
			}
		});
		this.add(all);
		this.add(flop);
		this.add(turn);
		this.add(river);
	}

	Image getImage(Card c) {
		String name = "cards_png_zip/PNG/" + c + ".png";
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

		BufferedImage facedown = null;

		public void paintComponent(Graphics g) {
			Rectangle r = frame.getBounds();
			width = r.width;
			height = r.height;
			if (width < 1200)
				width = 1200;
			if (height < 600)
				height = 600;
			widthratio = (double) width / originalwidth;
			heightratio = (double) height / originalheight;
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
			paintTable(g2d);
			/*/ draw Grids, delete after finished
			for (int i = 0; i < width; i += 100) {
				g2d.drawLine(i, 0, i, 10);
				g2d.drawString(Integer.toString(i), i - 10, 40);
			}
			for (int i = 0; i < height; i += 100) {
				g2d.drawLine(0, i, 10, i);
				g2d.drawString(Integer.toString(i), 20, i + 10);
			}
			// draw Grids, delete after finish*/
			paintEveryoneCard(t, g2d);
			paintCommunity(g2d, t);
			paintButton();
		}

		void paintTable(Graphics2D g2d) {
			GradientPaint gb = new GradientPaint(0, 0, new Color(150, 150, 150), width, height, new Color(50, 50, 50));
			GradientPaint gw = new GradientPaint(0, 0, new Color(0, 120, 0), width, height, new Color(0, 120, 0));
			GradientPaint gr = new GradientPaint(0, 0, new Color(255, 0, 0), width, height, new Color(90, 0, 0));
			g2d.setPaint(gb);
			g2d.fillRect(0, 0, width, height);
			g2d.setPaint(gr);
			g2d.fillOval(-100, -100, width + 200, height + 200);
			g2d.setPaint(gw);
			g2d.fillOval(0, 0, width, height);
			g2d.setPaint(new Color(255, 255, 255));
			g2d.setStroke(new BasicStroke((float) 4.0));
			g2d.drawRoundRect(width / 5, height / 4, width * 3 / 5, (height - 40) / 2, 100, 100);
		}

		void paintEveryoneCard(Table t, Graphics2D g2d) {
			Player first = t.first;
			Player next = first.next;
			int i = 0;
			paintFaceUpCard(first, i, g2d);
			i++;
			while (next != first) {
				if (t.finishDealing())
					paintFaceUpCard(next, i, g2d);
				else
					paintBlankCard(next, i, g2d);
				next = next.next;
				i++;
			}
		}

		void paintFaceUpCard(Player player, int i, Graphics2D g2d) {
			Image one = getImage(player.getList().get(0));
			Image two = getImage(player.getList().get(1));
			g2d.drawImage(one, (int) ((xcoord[i] - 55) * widthratio), (int) ((ycoord[i] - 55) * heightratio),
					(int) (110 * widthratio), (int) (110 * heightratio), this);
			g2d.drawImage(two, (int) ((xcoord[i] + 55) * widthratio), (int) ((ycoord[i] - 55) * heightratio),
					(int) (110 * widthratio), (int) (110 * heightratio), this);
			g2d.drawString(player.getName(), (int) ((xcoord[i] - 55) * widthratio),
					(int) ((ycoord[i] - 65) * heightratio));
			g2d.setColor(new Color(255, 0, 0));
			if (player == t.button) {
				g2d.drawOval((int) ((xcoord[i] + 30) * widthratio), (int) ((ycoord[i] - 25) * heightratio),
						(int) (widthratio * 45), (int) (heightratio * 45));
				g2d.drawString("B", (int) ((xcoord[i] + 45) * widthratio), (int) ((ycoord[i] + 5) * heightratio));
			}
			g2d.setColor(new Color(255, 255, 255));
		}

		void paintBlankCard(Player player, int i, Graphics2D g2d) {
			Image blank = facedown;
			g2d.drawImage(blank, (int) ((xcoord[i] - 55) * widthratio), (int) ((ycoord[i] - 55) * heightratio),
					(int) (110 * widthratio), (int) (110 * heightratio), this);
			g2d.drawImage(blank, (int) ((xcoord[i] + 55) * widthratio), (int) ((ycoord[i] - 55) * heightratio),
					(int) (110 * widthratio), (int) (110 * heightratio), this);
			g2d.drawString(player.getName(), (int) ((xcoord[i] - 55) * widthratio),
					(int) ((ycoord[i] - 65) * heightratio));
			g2d.setColor(new Color(255, 0, 0));
			if (player == t.button) {
				g2d.drawOval((int) ((xcoord[i] + 30) * widthratio), (int) ((ycoord[i] - 25) * heightratio),
						(int) (widthratio * 45), (int) (heightratio * 45));
				g2d.drawString("B", (int)((xcoord[i] + 45)*widthratio),
						(int)((ycoord[i] +5)*heightratio));
			}
			g2d.setColor(new Color(255, 255, 255));
		}

		void paintButton() {
			all.setBounds((int) (widthratio * 953), (int) (heightratio * 475), (int) (widthratio * 200),
					(int) (heightratio * 100));
			flop.setBounds((int)(widthratio * 323), (int) (heightratio * 475),
					(int) (widthratio * 200), (int) (heightratio * 100));
			turn.setBounds((int)(widthratio * 533), (int) (heightratio * 475),
					(int) (widthratio * 200), (int) (heightratio * 100));
			river.setBounds((int)(widthratio * 743), (int) (heightratio * 475),
					(int) (widthratio * 200), (int) (heightratio * 100));
			again.setBounds((int)(widthratio*650), (int) (heightratio * 225), (int) (widthratio * 200),
					(int) (heightratio * 100));
			this.add(all);
			this.add(flop);
			this.add(turn);
			this.add(river);
			this.add(again);
		}

		void paintCommunity(Graphics2D g2d, Table t) {
			int xcomm = (int) (widthratio * 450);
			for (int i = 0; i < 5; i++) {
				int xrect = xcomm + (int) (widthratio * 120 * i);
				g2d.drawRect((xrect - 10), (int) (heightratio * 340), (int) (widthratio * 120),
						(int) (heightratio * 120));
			}
			for (Card c : t.communityCard()) {
				Image card = getImage(c);
				g2d.drawImage(card, xcomm, (int) (heightratio * 350), (int) (widthratio * 100),
						(int) (heightratio * 100), this);
				xcomm += (int) (widthratio * 120);
			}
		}
	}
}
