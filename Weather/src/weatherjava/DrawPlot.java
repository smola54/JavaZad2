package weatherjava;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class DrawPlot extends JPanel {
	 private static final int MAX_SCORE = 20;
	 public List<int> scores = new ArrayList<>(10);
	 private static final int BORDER_GAP = 30;
	 
	 
	 @Override
	   public void paintComponent(Graphics g) {
	 
		  super.paintComponent(g);
	      Graphics2D g2 = (Graphics2D)g;
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	 
	      double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (scores.size() - 1);
	      double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE - 1);
	      
	      List<Point> graphPoints = new ArrayList<Point>();
	      
	      for (int i = 0; i < scores.size(); i++) {
	          int x1 = (int) (i * xScale + BORDER_GAP);
	          int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
	          graphPoints.add(new Point(x1, y1));
	       }
	 }
	 
}
