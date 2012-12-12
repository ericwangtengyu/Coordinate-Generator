/**
 * 
 */
package coordinate;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.filechooser.FileFilter;

/**
 * @author Eric
 *
 */
public class Main {

	private JFrame frmCoordinateGenerator;
	private JTextField roomText;
	private BufferedImage image;
	private File file;
	private FileOutputStream fos;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmCoordinateGenerator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		file=new File("Coordinate.csv");
		try {
			fos=new FileOutputStream(file);
			String title="RoomID,X,Y\r\n";
			try {
				fos.write(title.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCoordinateGenerator = new JFrame();
		frmCoordinateGenerator.setResizable(false);
		frmCoordinateGenerator.setTitle("Coordinate Generator");
		frmCoordinateGenerator.setBounds(100, 100, 1018, 610);
		frmCoordinateGenerator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCoordinateGenerator.getContentPane().setLayout(null);
		
		roomText = new JTextField();
		roomText.setBounds(10, 80, 99, 28);
		frmCoordinateGenerator.getContentPane().add(roomText);
		roomText.setColumns(10);
		
		JLabel lblRoomNumber = new JLabel("Room Number");
		lblRoomNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblRoomNumber.setBounds(10, 61, 99, 21);
		lblRoomNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmCoordinateGenerator.getContentPane().add(lblRoomNumber);
		
		final JLabel lblCoordinate = new JLabel("");
		lblCoordinate.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoordinate.setBounds(10, 312, 99, 33);
		frmCoordinateGenerator.getContentPane().add(lblCoordinate);
		
		JLabel lblNewLabel = new JLabel("Coordinate");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 287, 99, 26);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmCoordinateGenerator.getContentPane().add(lblNewLabel);
		
		JButton btnSave = new JButton("Save (x,y)");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomID=roomText.getText();
				if(roomID.equals("")) return;
				String coordinate=lblCoordinate.getText();
				String []values=coordinate.split(",");
				StringBuilder sb=new StringBuilder();
				sb.append(roomID).append(",").append(values[0]).append(",").append(values[1]).append("\r\n");
				try {
					fos.write(sb.toString().getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(10, 356, 99, 23);
		frmCoordinateGenerator.getContentPane().add(btnSave);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(147, 11, 845, 550);
		frmCoordinateGenerator.getContentPane().add(scrollPane);
		
		final JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		final JLabel picLabel = new JLabel("");
		picLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
//              do not need relative coordinate!!!!!!
				lblCoordinate.setText(x+","+y+","+"0");
				
			}
		});
		picLabel.setVerticalAlignment(SwingConstants.TOP);
		picLabel.setBounds(0, 0, 843, 548);
		panel.add(picLabel);
		
		JButton btnChoose = new JButton("Choose Pic");
		btnChoose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser dlg=new JFileChooser();
				dlg.setDialogTitle("Open Picture File");
				dlg.setFileFilter(new FileFilter(){

					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						 if (f.isDirectory() || f.getPath().toLowerCase().endsWith(".png") || f.getPath().toLowerCase().endsWith(".bmp"))
						    return true;
						return false;
					}

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "(.png, .bmp)";
					}
					
				});
				int result = dlg.showOpenDialog(panel);
				if(result==JFileChooser.APPROVE_OPTION)
				{
					File imageFile=dlg.getSelectedFile();
					try {
						image=ImageIO.read(imageFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 ImageIcon ic=new ImageIcon(image);
					 picLabel.setSize(new Dimension(image.getWidth(), image.getHeight()));
					 panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
					 picLabel.setIcon(ic);
				}
				
			}
		});
		btnChoose.setBounds(10, 27, 99, 23);
		frmCoordinateGenerator.getContentPane().add(btnChoose);
		
		JRadioButton radioButton_1 = new JRadioButton("corner1");
		buttonGroup.add(radioButton_1);
		radioButton_1.setBounds(10, 115, 109, 23);
		frmCoordinateGenerator.getContentPane().add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("corner2");
		buttonGroup.add(radioButton_2);
		radioButton_2.setBounds(10, 141, 109, 23);
		frmCoordinateGenerator.getContentPane().add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("corner3");
		buttonGroup.add(radioButton_3);
		radioButton_3.setBounds(10, 167, 109, 23);
		frmCoordinateGenerator.getContentPane().add(radioButton_3);
		
		JRadioButton radioButton_4 = new JRadioButton("corner4");
		buttonGroup.add(radioButton_4);
		radioButton_4.setBounds(10, 193, 109, 23);
		frmCoordinateGenerator.getContentPane().add(radioButton_4);
	}
}
