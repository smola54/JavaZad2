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
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import sun.rmi.runtime.NewThreadAction;
import weatherjava.Weather.MyHandler;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import java.awt.GridLayout;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class Weather extends JFrame  {


	public static String weatherHtmlUrlWroclaw = "http://api.openweathermap.org/data/2.5/weather?q=Wroclaw&mode=html&apikey=035913e957aab8fb7840c5ff1189597d" ;
	public static String weatherXmlUrlWroclaw  = "http://api.openweathermap.org/data/2.5/weather?q=Wroclaw&mode=xml&apikey=035913e957aab8fb7840c5ff1189597d" ;
	public static int flag = 0;
	public static String weatherHtmlUrl ;
			
	public  static String weatherXmlUrl ;
	public JTextPane textPaneWroclaw = new JTextPane();
	public static Weather frame;
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
	private JTable table = new JTable();
	private final JButton btnDeleteRecord = new JButton("Delete record");
	private final JButton btnDeleteDatabase = new JButton("Delete DataBase");
	public JLabel lblBrakPoaczeniaZ = new JLabel("Brak połaczenia z siecią");
	public static String actualSearchingCityName;	
	public static String actualSearchingCityCountry;	
	public static int actualSearchingCityTemperature;
	DBConnect dbConnect = new DBConnect();
	LinkedList<Weatherinfo> weatherinfoList = new LinkedList<>();

    /**
     * Create the frame.
     */
    class MojWatek extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    System.out.println("ZYJE");
                weatherHTML = getHTML(weatherHtmlUrlWroclaw);

                SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
                parser.parse(weatherXmlUrlWroclaw, new MyHandler());
                textPaneWroclaw.setText(parsedXml);
                Thread.sleep(1000);
               
      	      if (scores.size() > 16 ) {
      	    	  scores.removeLast();
          	      scores.addFirst(Integer.valueOf(temperatreWroclaw));
          	  
      	      }else {
      	    	  scores.addFirst(Integer.valueOf(temperatreWroclaw));
      	      }
      	      DrawPlot plot = new DrawPlot(scores);
    	      plotPanel.add(plot);
    	      lblBrakPoaczeniaZ.setVisible(false);
                   
                } catch (Exception a) {
                	
                  
                    lblBrakPoaczeniaZ.setVisible(true);
                    a.printStackTrace();
                    
                }  
                
                }
            }
        }
    
	public Weather() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 450);
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
					flag=1;
					weatherHTML = getHTML(weatherHtmlUrl);
					textPane.setText(weatherHTML);

					SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
					parser.parse(weatherXmlUrl, new MyHandler());
					textPane.setText(parsedXml);
					flag = 0;
					DBConnect.DBInsert(actualSearchingCityName,actualSearchingCityCountry,actualSearchingCityTemperature);
					showJTable();
				} catch (Exception a) {
					JDialog d = new JDialog(frame, "Error", true);
    				JLabel label = new JLabel("Error",JLabel.CENTER);
    				d.getContentPane().add(label);
    				d.setSize(200, 100);
    			    d.setLocationRelativeTo(frame);
    			    d.setVisible(true);
                    a.printStackTrace();
				}
			}
		});
		
		
		textPane.setBounds(186, 12, 220, 58);
		contentPane.add(textPane);
		
		textField = new JTextField();
		textField.setBounds(12, 57, 137, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblPogodaDlaWrocawia = new JLabel("Pogoda dla Wrocławia:");
		lblPogodaDlaWrocawia.setBounds(12, 111, 185, 15);
		contentPane.add(lblPogodaDlaWrocawia);
		
		
		textPaneWroclaw.setBounds(188, 98, 220, 47);
		contentPane.add(textPaneWroclaw);
		
		
		plotPanel.setBounds(26, 157, 400, 250);
		contentPane.add(plotPanel);
		
		table.setBounds(457, 12, 496, 297);
		contentPane.add(table);
		btnDeleteRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				int idToDelete = (Integer)table.getModel().getValueAt(selectedIndex, 0);
				System.out.println(idToDelete);
				DBConnect.DBDeleteRow(idToDelete);
				showJTable();
				
			}
		});
		
		btnDeleteRecord.setBounds(457, 334, 246, 25);
		
		contentPane.add(btnDeleteRecord);
		btnDeleteDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBConnect.DBDelete();
				showJTable();
				
			}
		});
		btnDeleteDatabase.setBounds(707, 334, 246, 25);
		
		contentPane.add(btnDeleteDatabase);
		lblBrakPoaczeniaZ.setBounds(128, 408, 170, 15);
		contentPane.add(lblBrakPoaczeniaZ);
		lblBrakPoaczeniaZ.setVisible(false);
		showJTable();
		
		
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 frame = new Weather();
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
	public void showJTable() {
		String columnNames[] = { "id","City", "Country", "Temperature"};
		LinkedList<Weatherinfo> list = DBConnect.DBSelect();
		for( Weatherinfo wl : list) {
			System.out.println(wl.toString());	
		}
	
		DefaultTableModel model = new DefaultTableModel();
	    table.setModel(model);
	    model.setColumnIdentifiers(columnNames);
		
		
		for(int i = 0; i < list.size() ; i++) {
			Object[] row = new Object[4];
			row[0] = list.get(i).getid();
			row[1] = list.get(i).getCity();
			row[2] = list.get(i).getCountry();
			row[3] = list.get(i).getTemperature();
				
			model.addRow(row);
		}
		
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
				actualSearchingCityName = attributes.getValue("name");
				result.append("Miasto: " +actualSearchingCityName  + "\n");
				System.out.println(attributes.getValue("name").toString());
				String warunek = new String(attributes.getValue("name"));
				
				
			}
			if (qName.equals("temperature")) {
				
				if (flag == 0 ) {
					String temp ;
					temp = attributes.getValue("value").toString();
					float tempWro = Float.parseFloat(temp);
					temperatreWroclaw = (int)tempWro-273;			
				}
				String temp1 ;
				temp1 = attributes.getValue("value").toString();
				float temp = Float.parseFloat(temp1);
				actualSearchingCityTemperature =  (int)temp-273;
				
				result.append("Temperatura: " + Integer.toString(actualSearchingCityTemperature)  + "\n");
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);			
			if(elements.get("city")) {
				actualSearchingCityCountry = new String(ch, start, length);
				result.append("Państwo: " + actualSearchingCityCountry + "\n");
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
