package topologyV2;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.border.MatteBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;

public class InpaintingJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtLienImage;
	private JTextField txtLienMasque;
	private JTextField txtR;
	private JTextField txtG;
	private JTextField txtB;
	private boolean r, g ,b;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InpaintingJFrame frame = new InpaintingJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InpaintingJFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inpainting");
		setLocationRelativeTo(null);
		setBounds(100, 100, 960, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitre = new JLabel("Inpainting");
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTitre.setBounds(395, 7, 170, 49);
		contentPane.add(lblTitre);
		
		JLabel lblTitreImages = new JLabel("Sélection des 2 images");
		lblTitreImages.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitreImages.setBounds(65, 35, 191, 22);
		contentPane.add(lblTitreImages);
		
		JButton btnSearchImage = new JButton("Browse");
		btnSearchImage.setBounds(236, 96, 86, 30);
		contentPane.add(btnSearchImage);
		
		txtLienImage = new JTextField();
		txtLienImage.setFocusable(false);
		txtLienImage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLienImage.setColumns(10);
		txtLienImage.setBounds(25, 96, 206, 30);
		contentPane.add(txtLienImage);
		
		JLabel lblImage = new JLabel("Image");
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblImage.setBounds(25, 70, 53, 22);
		contentPane.add(lblImage);
		
		txtLienMasque = new JTextField();
		txtLienMasque.setFocusable(false);
		txtLienMasque.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLienMasque.setColumns(10);
		txtLienMasque.setBounds(25, 183, 206, 30);
		contentPane.add(txtLienMasque);
		
		JLabel lblMasque = new JLabel("Masque");
		lblMasque.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMasque.setBounds(25, 157, 53, 22);
		contentPane.add(lblMasque);
		
		JButton btnSearchMasque = new JButton("Browse");
		btnSearchMasque.setBounds(236, 183, 86, 30);
		contentPane.add(btnSearchMasque);
		
		JLabel image = new JLabel();
		image.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		image.setOpaque(true);
		image.setBounds(352, 70, 250, 250);
		image.setBackground(null);
		contentPane.add(image);
		
		JLabel masque = new JLabel();
		masque.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		masque.setOpaque(true);
		masque.setBounds(662, 70, 250, 250);
		masque.setBackground(null);
		contentPane.add(masque);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 236, 329, 2);
		contentPane.add(separator);
		
		JLabel lblTitreCouleur = new JLabel("Couleur du Masque");
		lblTitreCouleur.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitreCouleur.setBounds(73, 249, 161, 22);
		contentPane.add(lblTitreCouleur);
		
		JLabel lblCouleur = new JLabel();
		lblCouleur.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY));
		lblCouleur.setBackground(Color.BLACK);
		lblCouleur.setBounds(253, 298, 65, 62);
		lblCouleur.setOpaque(true);
		contentPane.add(lblCouleur);
		
		JButton btnExec = new JButton("Restaurer");
		btnExec.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExec.setBounds(514, 346, 228, 39);
		contentPane.add(btnExec);
		
		JButton btnColorPicker = new JButton("Color Picker");
		btnColorPicker.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnColorPicker.setBounds(46, 298, 158, 39);
		contentPane.add(btnColorPicker);
		
		txtR = new JTextField();
		txtR.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setText("255");
		txtR.setBounds(31, 355, 40, 30);
		contentPane.add(txtR);
		txtR.setColumns(10);
		
		txtG = new JTextField();
		txtG.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtG.setHorizontalAlignment(SwingConstants.CENTER);
		txtG.setText("255");
		txtG.setColumns(10);
		txtG.setBounds(76, 355, 40, 30);
		contentPane.add(txtG);
		
		txtB = new JTextField();
		txtB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtB.setHorizontalAlignment(SwingConstants.CENTER);
		txtB.setText("255");
		txtB.setColumns(10);
		txtB.setBounds(126, 354, 40, 30);
		contentPane.add(txtB);
		
		JButton btnApply = new JButton("=>");
		btnApply.setBounds(182, 354, 49, 30);
		contentPane.add(btnApply);
		
		
		btnSearchImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser("./Image");
				chooser.setAcceptAllFileFilterUsed(false);
				FileFilter imagesFilter = new FileNameExtensionFilter("Image", "bmp");
				chooser.setDialogTitle("Choisir une image");
				chooser.addChoosableFileFilter(imagesFilter);
				
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//File image1 = new File(chooser.getSelectedFile().getPath());
					try {
						Image img = ImageIO.read(chooser.getSelectedFile());
						int x=0, y=0;
						
						if(img.getHeight(null)<img.getWidth(null)) {
							x=250; y = 180;
						}
						else {
							x=180; y = 250;
						}
						Image newimg = img.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
						image.setBounds(371, 71, x, y);
						image.setIcon(new ImageIcon(newimg));
						image.setVisible(true);
						txtLienImage.setText(chooser.getSelectedFile().getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnSearchMasque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser("./Image");
				chooser.setAcceptAllFileFilterUsed(false);
				FileFilter imagesFilter = new FileNameExtensionFilter("Image", "bmp"); // TODO
				chooser.setDialogTitle("Choisir une image");
				chooser.addChoosableFileFilter(imagesFilter);
				
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//File image1 = new File(chooser.getSelectedFile().getPath());
					try {
						Image img = ImageIO.read(chooser.getSelectedFile());
						int x=0, y=0;
						
						if(img.getHeight(null)<img.getWidth(null)) {
							x=250; y = 180;
						}
						else {
							x=180; y = 250;
						}
						Image newimg = img.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
						masque.setBounds(670, 71, x, y);
						masque.setIcon(new ImageIcon(newimg));
						masque.setVisible(true);
						txtLienMasque.setText(chooser.getSelectedFile().getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		InpaintingJFrame that = this;
		btnColorPicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCouleur.setBackground(JColorChooser.showDialog(that, "Color Picker", Color.BLACK));
			}
		});
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCouleur.setBackground(new Color(Integer.parseInt(txtR.getText()), Integer.parseInt(txtG.getText()), Integer.parseInt(txtB.getText())));
			}
		});
		btnExec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(!txtLienImage.getText().isEmpty() || !txtLienMasque.getText().isEmpty()) {
						Matrix matrix = new Matrix(txtLienImage.getText());
						Matrix matrix2 = new Matrix(txtLienMasque.getText());
						topologyV2.Color color = new topologyV2.Color(lblCouleur.getBackground().getBlue(),lblCouleur.getBackground().getGreen(),lblCouleur.getBackground().getRed());
						Mask mask = new Mask(matrix2,color);
						Inpainting inpainting = new Inpainting(matrix,mask);
						inpainting.restore(5, 5);
						BufferedImage img = new BufferedImage(inpainting.image.width, inpainting.image.height,BufferedImage.TYPE_3BYTE_BGR);
						byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
						int k=0;
						for(int j=0;j<matrix.height;j++)
							for(int i=0;i<matrix.width;i++)
								for(int c=0;c<3;c++)
									pixels[k++]=matrix.val[i][j].val[c];
						JFrame result = new ResultJFrame(img);
						result.setVisible(true);
					}
					else JOptionPane.showMessageDialog(that, "Erreur : Image et/ou masque incorrect", "Erreur de traitement", 0);
				}catch(Exception exce){
					exce.printStackTrace();
				}
			}
		});
		
		txtR.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtR.getText().equals("255") && r) {
					txtR.setText("");
					r = false;
				}
			}
		});
		txtG.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtG.getText().equals("255") && g) {
					txtG.setText("");
					g=false;
				}
			}
		});
		txtB.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtB.getText().equals("255") && b) {
					txtB.setText("");
					b=false;
				}
					
			}
		});
		
		
	}
}
