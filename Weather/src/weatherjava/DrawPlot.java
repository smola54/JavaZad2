package weatherjava;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class DrawPlot extends JPanel {
	 private static final int MAX_SCORE = 40;
	 private static final int BORDER_GAP = 20;
	 private static final int Y_HATCH_CNT = 20;
	 private static final int GRAPH_POINT_WIDTH = 12;
	 private static final Color GRAPH_COLOR = Color.green;
	 private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
	 private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
	 private static final int PREF_W = 400;
	   private static final int PREF_H = 250;
	   private static final int scoreSize = 16;
	   
	 public LinkedList<Integer> scores ;

	 public DrawPlot(LinkedList<Integer> scores) {
	      this.scores = scores;
	   }

	 
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
	          int y1 = (int) (((MAX_SCORE - 1)-scores.get(i)) * yScale + BORDER_GAP);
	          graphPoints.add(new Point(x1, y1));
	      }
	      
	      	// create x and y axes 
	          g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
	          g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);
	          
	          // create hatch marks for y axis. 
	          for (int j = 0; j < Y_HATCH_CNT; j++) {
	             int x_0 = BORDER_GAP;
	             int x_1 = GRAPH_POINT_WIDTH + BORDER_GAP;
	             int y_0 = getHeight() - (((j + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
	             int y_1 = y_0;
	             g2.drawLine(x_0, y_0, x_1, y_1);
	          }
	          // and for x axis
	          for (int a = 0; a < scores.size()-1; a++) {
	             int X0 = (a + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size()-1) + BORDER_GAP;
	             int X1 = X0;
	             int Y0 = getHeight() - BORDER_GAP;
	             int Y1 = Y0 - GRAPH_POINT_WIDTH;
	             g2.drawLine(X0, Y0, X1, Y1);
	          }
	        
	          Stroke oldStroke = g2.getStroke();
	          g2.setColor(GRAPH_COLOR);
	          g2.setStroke(GRAPH_STROKE);
	          for (int c = 0; c < graphPoints.size() - 1; c++) {
	             int x11 = graphPoints.get(c).x;
	             int y11 = graphPoints.get(c).y;
	             int x22 = graphPoints.get(c + 1).x;
	             int y22 = graphPoints.get(c + 1).y;
	             g2.drawLine(x11, y11, x22, y22);         
	          } 
	          
	          g2.setStroke(oldStroke);      
	          g2.setColor(GRAPH_POINT_COLOR);
	          for (int i = 0; i < graphPoints.size(); i++) {
	             int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
	             int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
	             int ovalW = GRAPH_POINT_WIDTH;
	             int ovalH = GRAPH_POINT_WIDTH;
	             g2.fillOval(x, y, ovalW, ovalH);
	          }   
	 }
	  @Override
	   public Dimension getPreferredSize() {
	      return new Dimension(PREF_W, PREF_H);
	   }

}
