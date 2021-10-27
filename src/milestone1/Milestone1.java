package milestone1;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.event.AncestorListener;
import net.proteanit.sql.DbUtils;
import javax.swing.event.AncestorEvent;
import javax.swing.JDesktopPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class Milestone1 {

	private JFrame frmMilestone;
	private JTextField NameTextF;
	private JTextField IDTextF;
	private JTextField URLTextF;
	private static	SQLite DB;
	private JTable table;
	private String filename=null;
	private byte[] IDPic=null;
	private ImageIcon format = null;
	private JTextField SearchTextF;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DB = new SQLite();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Milestone1 window = new Milestone1();
					window.frmMilestone.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Milestone1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMilestone = new JFrame();
		frmMilestone.setTitle("Milestone1");
		frmMilestone.setResizable(false);
		frmMilestone.setBounds(100, 100, 899, 537);
		frmMilestone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMilestone.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 865, 480);
		frmMilestone.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Data entery", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel NameLabel = new JLabel("Please enter your name");
		NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NameLabel.setFont(new Font("Gadugi", Font.BOLD, 22));
		NameLabel.setBounds(277, 48, 267, 34);
		panel_1.add(NameLabel);
		
		NameTextF = new JTextField();
		NameTextF.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		NameLabel.setLabelFor(NameTextF);
		NameTextF.setBounds(193, 94, 445, 34);
		panel_1.add(NameTextF);
		NameTextF.setColumns(10);
		
		JLabel IDLabel = new JLabel("Please enter your social ID");
		IDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IDLabel.setFont(new Font("Gadugi", Font.BOLD, 22));
		IDLabel.setBounds(266, 138, 289, 34);
		panel_1.add(IDLabel);
		
		IDTextF = new JTextField();
		IDTextF.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		IDTextF.setColumns(10);
		IDTextF.setBounds(193, 185, 445, 34);
		panel_1.add(IDTextF);
		
		JButton SubmitBtn = new JButton("Submit");
		SubmitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i;
				String Nametemp = NameTextF.getText();
				String FName="" ;
				String LName="" ;
				
				if(Nametemp.indexOf(' ')==-1)
					for( i=0; i<Nametemp.length();i++)
						FName+=Nametemp.charAt(i);

				
				else	 
					for( i=0; i<Nametemp.indexOf(' ');i++) 
						FName+=Nametemp.charAt(i);
				
				
				if(Nametemp.indexOf(' ',1)== -1)
					for(i=FName.length(); i<Nametemp.indexOf(' ',1);i++)
						LName+=Nametemp.charAt(i);
				else
					for( i=Nametemp.indexOf(' ')+1; i<Nametemp.length();i++)
						LName+=Nametemp.charAt(i);
				
				String SocialIDSt = IDTextF.getText() ;
				long SocialID = Long.parseLong(SocialIDSt);
				String query ="Insert into PersonalData (FirstName,LastName,SocialID,IDPic) values (?,?,?,?)";
				if(SocialIDSt.length() == 14) {
					DB.ConnectDB();
				try {
					
					DB.pst = DB.c.prepareStatement(query);
					DB.pst.setString(1,FName);
					DB.pst.setString(2,LName);
					DB.pst.setLong(3,SocialID);
					DB.pst.setBytes(4,IDPic);
					DB.pst.execute();
					JOptionPane.showMessageDialog(null,"Added !!");
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null,"did not excuted\n"+query);
					
					
				}
				DB.CloseDB();
				}
				else if(SocialIDSt.length() != 14)
					JOptionPane.showInternalMessageDialog(null,"Please Enter Your Name and Your Social ID Which contain 14 number");
			}
		});
		SubmitBtn.setFont(new Font("Gadugi", Font.BOLD, 15));
		SubmitBtn.setBounds(279, 335, 122, 34);
		panel_1.add(SubmitBtn);
		
		JButton ClearBtn = new JButton("Clear");
		ClearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NameTextF.setText("");
				IDTextF.setText("");
				URLTextF.setText("");
				
			}
		});
		ClearBtn.setFont(new Font("Gadugi", Font.BOLD, 15));
		ClearBtn.setBounds(422, 335, 122, 34);
		panel_1.add(ClearBtn);
		
		JButton PickBtn = new JButton("...");
		PickBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser =new JFileChooser();
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				filename =f.getAbsolutePath();
				URLTextF.setText(filename);
				try {
					File image = new File(filename);
					FileInputStream fis = new FileInputStream(image);
					ByteArrayOutputStream bos =new ByteArrayOutputStream();
					byte[] buf =new byte[1024];
					for(int readNum;(readNum=fis.read(buf))!=-1;) {
						bos.write(buf,0,readNum);
					}
					fis.close();
					IDPic=bos.toByteArray();
					
				}catch(Exception e2) {
					
					JOptionPane.showMessageDialog(null,"Error !!");
				}
				
			}
		});
		PickBtn.setBounds(600, 273, 38, 32);
		panel_1.add(PickBtn);
		
		URLTextF = new JTextField();
		URLTextF.setEnabled(false);
		URLTextF.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		URLTextF.setColumns(10);
		URLTextF.setBounds(193, 273, 397, 34);
		panel_1.add(URLTextF);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your social ID picture");
		lblPleaseEnterYour.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseEnterYour.setFont(new Font("Gadugi", Font.BOLD, 22));
		lblPleaseEnterYour.setBounds(220, 229, 387, 34);
		panel_1.add(lblPleaseEnterYour);
		
		JPanel panel_2 = new JPanel();
		
		
		tabbedPane.addTab("Admin", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 605, 433);
		panel_2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(615, 10, 235, 150);
		panel_2.add(desktopPane);
		
		JLabel ImageLabel = new JLabel("");
		ImageLabel.setBounds(10, 10, 215, 130);
		desktopPane.add(ImageLabel);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String IDInTable = (table.getModel().getValueAt(row, 2).toString());
					String query="select IDPic from PersonalData where SocialID ='"+IDInTable+"'";
					
					DB.ConnectDB();
					DB.pst=DB.c.prepareStatement(query);
					
					DB.rs = DB.pst.executeQuery();
					if(DB.rs.next()) {
						byte[] imageData = DB.rs.getBytes("IDPic");
						format = new ImageIcon(imageData);
						ImageLabel.setIcon(format);
						
					}
					DB.CloseDB();
				}catch(Exception e2){
					
					JOptionPane.showMessageDialog(null,"Error !!");
				}
				
			}
		});
		JCheckBox DecChB = new JCheckBox("dec");
		DecChB.setFont(new Font("Gadugi", Font.BOLD, 12));
		DecChB.setBounds(803, 174, 47, 13);
		panel_2.add(DecChB);
		
		JComboBox<String> SortCB = new JComboBox<String>();
		SortCB.setBounds(744, 193, 106, 21);
		panel_2.add(SortCB);
		SortCB.addItem("FirstName");
		SortCB.addItem("LastName");
		SortCB.addItem("SocialID");
		
		JLabel SortLabel = new JLabel("Sort By");
		SortLabel.setFont(new Font("Gadugi", Font.BOLD, 15));
		SortLabel.setBounds(748, 170, 60, 21);
		panel_2.add(SortLabel);
		
		JButton SortBtn = new JButton("Sort");
		SortBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dec="";
				String ord =SortCB.getSelectedItem().toString();
				if(DecChB.isSelected())
					dec+="DESC";
				String query = "select FirstName,LastName,SocialID  from PersonalData  order by "+ord+" "+dec;
				try {
					DB.ConnectDB();
					DB.s=DB.c.createStatement();
					DB.rs=DB.s.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(DB.rs));
					
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(null,"did not excuted"+query);
				}
				
			}
		});
		SortBtn.setFont(new Font("Gadugi", Font.BOLD, 15));
		SortBtn.setBounds(615, 170, 122, 46);
		panel_2.add(SortBtn);
		
		
		JButton SearchBtn = new JButton("Search");
		SearchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DB.CloseDB();
				String SearchF = SearchTextF.getText()+'%';
				String query = "select FirstName,LastName,SocialID  from PersonalData  where FirstName like '"+SearchF+"'";
				
				try {
					DB.ConnectDB();
					DB.s=DB.c.createStatement();
					DB.rs=DB.s.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(DB.rs));
				} catch (SQLException e3) {
					JOptionPane.showMessageDialog(null,"Error !!");
				}
			}
		});
		SearchBtn.setFont(new Font("Gadugi", Font.BOLD, 15));
		SearchBtn.setBounds(615, 276, 235, 46);
		panel_2.add(SearchBtn);
		
		SearchTextF = new JTextField();
		SearchTextF.setFont(new Font("Gadugi", Font.BOLD, 15));
		SearchTextF.setBounds(615, 237, 235, 30);
		panel_2.add(SearchTextF);
		SearchTextF.setColumns(10);
		
		JButton ClickMeBtn = new JButton("Click me");
		ClickMeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String TheTeam = "                 Our Team\n1- Mohamed Salah Mohamed\n";
				TheTeam+="2- Abdulrahman Khaled Hassan\n3- Karim Gharib Mohamed\n";
				TheTeam+="4- Mahmoud Ashraf Mohamed\n5- Ahmed Abbas Mohamed\n";
				TheTeam+="6- Abdulrahman Abdulhalem Mohamed\n7- Mo'men Ashraf Mohamed\n";
				TheTeam+="8- Youssef Wael Elsayed";
				JOptionPane.showMessageDialog(null,TheTeam);
			}
		});
		ClickMeBtn.setFont(new Font("Gadugi", Font.BOLD, 15));
		ClickMeBtn.setBounds(615, 362, 235, 58);
		panel_2.add(ClickMeBtn);
		panel_2.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				String query = "select FirstName,LastName,SocialID  from PersonalData ";
				DB.ConnectDB();
				try {
					DB.s=DB.c.createStatement();
					DB.rs=DB.s.executeQuery(query);
					table.setModel(DbUtils.resultSetToTableModel(DB.rs));
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,"Error !!");
				}
				
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
				DB.CloseDB();
			}
		});
		
	}
}
