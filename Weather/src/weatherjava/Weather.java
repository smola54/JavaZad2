package weatherjava;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import sun.rmi.runtime.NewThreadAction;
import weatherjava.Weather.MyHandler;

import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

import java.awt.FlowLayout;
import javax.swing.JLabel;

public class Weather extends JFrame  {


	public static String weatherHtmlUrlWroclaw = "http://api.openweathermap.org/data/2.5/weather?q=Wroclaw&mode=html&apikey=5f517237b16a87e6f787d3ee7ab3149b" ;
	public static String weatherXmlUrlWroclaw  = "http://api.openweathermap.org/data/2.5/weather?q=Wroclaw&mode=xml&apikey=5f517237b16a87e6f787d3ee7ab3149b" ;
	
	public static String weatherHtmlUrl ;
			
	public  static String weatherXmlUrl ;
	public JTextPane textPaneWroclaw = new JTextPane();
	public static int flag = 0;
	String weatherHTML = "empty";
	public  String parsedXml = "empty";
	JButton btnNewButton = new JButton("Sprawdź");
	public StringBuilder result = new StringBuilder();
	public int  temperatreWroclaw;
	private JPanel contentPane;
	private JTextField textField;
	JTextPane textPane = new JTextPane();
	JPanel plotPanel = new JPanel();
	LinkedList<Integer> scores = new LinkedList<Integer>();

    /**
     * Create the frame.
     */
    class MojWatek extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                 //   System.out.println("ZYJE");
                weatherHTML = getHTML(weatherHtmlUrlWroclaw);

                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                parser.parse(weatherXmlUrlWroclaw, new MyHandler());
                textPaneWroclaw.setText(parsedXml);
                Thread.sleep(100000);
           	   scores.addFirst(temperatreWroclaw);
      	      if (scores.size() > 16 ) {
      	    	  scores.removeLast();
          	      scores.addFirst(temperatreWroclaw);
      	      }
              
              DrawPlot plot = new DrawPlot(scores);
    	      plotPanel.add(plot);
                   
                } catch (Exception a) {
                    a.printStackTrace();
                }                
                }
            }
        }
    
	public Weather() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 884);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btnNewButton.setBounds(12, 12, 137, 33);
		
		MojWatek mojwatek = new MojWatek();
		mojwatek.start();
		
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					String city= textField.getText().toString();
					String first = "http://api.openweathermap.org/data/2.5/weather?q=";
					String second1= "&mode=html&apikey=035913e957aab8fb7840c5ff1189597d";
					String second2= "&mode=xml&apikey=035913e957aab8fb7840c5ff1189597d";
					String html = new StringBuilder(first).append(city).append(second1).toString();
					String xml = new StringBuilder(first).append(city).append(second2).toString();
				weatherHtmlUrl = html;	
				weatherXmlUrl= xml;
				
					weatherHTML = getHTML(weatherHtmlUrl);
					textPane.setText(weatherHTML);

					SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
					parser.parse(weatherXmlUrl, new MyHandler());
					textPane.setText(parsedXml);
				} catch (Exception a) {
					a.printStackTrace();
				}
			}
		});
		
		
		textPane.setBounds(161, 12, 481, 58);
		contentPane.add(textPane);
		
		textField = new JTextField();
		textField.setBounds(12, 57, 137, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPogodaDlaWrocawia = new JLabel("Pogoda dla Wrocławia");
		lblPogodaDlaWrocawia.setBounds(12, 111, 185, 15);
		contentPane.add(lblPogodaDlaWrocawia);
		
		
		textPaneWroclaw.setBounds(297, 97, 220, 48);
		contentPane.add(textPaneWroclaw);
		
		
		plotPanel.setBounds(22, 138, 600, 450);
		contentPane.add(plotPanel);
	
		
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Weather frame = new Weather();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public String getHTML(String urlToRead) throws Exception {
		
		URL url = new URL(urlToRead);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			result.append(line);
		}
		
		reader.close();
		return result.toString();
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	class MyHandler extends DefaultHandler {
		Map<String, Boolean> elements = new HashMap<String, Boolean>();
		StringBuilder result = new StringBuilder();
		
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			elements.put(qName, true);
			if (qName.equals("city")) {
				result.append("Miasto: " + attributes.getValue("name") + "\n");
				
			}
			if (qName.equals("temperature")) {
			
					String temp ;
					temp = attributes.getValue("value").toString();
					float tempWro = Float.parseFloat(temp);
					temperatreWroclaw = (int)tempWro;
					System.out.println(temperatreWroclaw);
					System.out.println();
				
				
				
				
				result.append("Temperatura: " +  attributes.getValue("value") + "\n");
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);			
			if(elements.get("city")) {
				result.append("Państwo: " + new String(ch, start, length) + "\n");
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			elements.put(qName, false);
		}
		
		@Override
		public void endDocument() throws SAXException {
			super.endDocument();
			parsedXml = result.toString();
		}
	}
}
