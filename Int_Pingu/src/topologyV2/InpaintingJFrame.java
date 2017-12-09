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
import javax.swing.JComponent;
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
import java.awt.SystemColor;
import javax.swing.border.SoftBevelBorder;

public class InpaintingJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static InpaintingJFrame frame;
	private boolean r = true, g = true, b = true;
	public static boolean traitement = false;
	
	private JPanel contentPane;
	private JTextField txtLienImage;
	private JTextField txtLienMasque;
	private JTextField txtR;
	private JTextField txtG;
	private JTextField txtB;
	private JButton btnApply;
	private JButton btnColorPicker;
	private JButton btnExec;
	private JComponent lblCouleur;
	private JLabel lblTitreCouleur;
	private JSeparator separator;
	private JLabel masque;
	private JLabel image;
	private JButton btnSearchMasque;
	private JLabel lblMasque;
	private JLabel lblImage;
	private JButton btnSearchImage;
	private JLabel lblTitreImages;
	private JLabel lblLoading;
	private JLabel lblTitre;
	

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new InpaintingJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public InpaintingJFrame() {
		setBackground(Color.GRAY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inpainting");
		setLocationRelativeTo(null);
		setBounds(100, 100, 960, 521);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblTitre = new JLabel("Inpainting");
		lblTitre.setForeground(new Color(210, 105, 30));
		lblTitre.setBackground(Color.WHITE);
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblTitre.setBounds(358, 11, 199, 62);
		contentPane.add(lblTitre);

		lblLoading = new JLabel();
		lblLoading.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblLoading.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoading.setForeground(new Color(255, 255, 255));
		lblLoading.setFocusable(false);
		lblLoading.setVisible(false);
		lblLoading.setIcon(new ImageIcon("./Image/loading.gif"));
		lblLoading.setBounds(286, 95, 398, 313);
		contentPane.add(lblLoading);

		lblTitreImages = new JLabel("Sélection des 2 images");
		lblTitreImages.setForeground(new Color(210, 105, 30));
		lblTitreImages.setBackground(Color.WHITE);
		lblTitreImages.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTitreImages.setBounds(42, 99, 243, 30);
		contentPane.add(lblTitreImages);

		btnSearchImage = new JButton("Browse");
		btnSearchImage.setBackground(SystemColor.textInactiveText);
		btnSearchImage.setBounds(242, 169, 86, 30);
		contentPane.add(btnSearchImage);

		txtLienImage = new JTextField();
		txtLienImage.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLienImage.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 128, 128)));
		txtLienImage.setForeground(new Color(192, 192, 192));
		txtLienImage.setBackground(null);
		txtLienImage.setFocusable(false);
		txtLienImage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLienImage.setColumns(10);
		txtLienImage.setBounds(31, 169, 206, 30);
		contentPane.add(txtLienImage);

		lblImage = new JLabel("Image");
		lblImage.setForeground(new Color(210, 105, 30));
		lblImage.setBackground(Color.WHITE);
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblImage.setBounds(31, 143, 53, 22);
		contentPane.add(lblImage);

		txtLienMasque = new JTextField();
		txtLienMasque.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLienMasque.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(128, 128, 128)));
		txtLienMasque.setForeground(new Color(192, 192, 192));
		txtLienMasque.setBackground(null);
		txtLienMasque.setFocusable(false);
		txtLienMasque.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtLienMasque.setColumns(10);
		txtLienMasque.setBounds(31, 256, 206, 30);
		contentPane.add(txtLienMasque);

		lblMasque = new JLabel("Masque");
		lblMasque.setForeground(new Color(210, 105, 30));
		lblMasque.setBackground(Color.WHITE);
		lblMasque.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMasque.setBounds(31, 230, 53, 22);
		contentPane.add(lblMasque);

		btnSearchMasque = new JButton("Browse");
		btnSearchMasque.setBackground(SystemColor.textInactiveText);
		btnSearchMasque.setBounds(242, 256, 86, 30);
		contentPane.add(btnSearchMasque);

		image = new JLabel();
		image.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(105, 105, 105)));
		image.setOpaque(true);
		image.setBounds(388, 143, 250, 250);
		image.setBackground(null);
		contentPane.add(image);

		masque = new JLabel();
		masque.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(105, 105, 105)));
		masque.setOpaque(true);
		masque.setBounds(682, 143, 250, 250);
		masque.setBackground(null);
		contentPane.add(masque);

		separator = new JSeparator();
		separator.setBackground(new Color(192, 192, 192));
		separator.setBounds(10, 310, 329, 1);
		contentPane.add(separator);

		lblTitreCouleur = new JLabel("Couleur du Masque");
		lblTitreCouleur.setForeground(new Color(210, 105, 30));
		lblTitreCouleur.setBackground(Color.WHITE);
		lblTitreCouleur.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTitreCouleur.setBounds(62, 322, 214, 38);
		contentPane.add(lblTitreCouleur);

		lblCouleur = new JLabel();
		lblCouleur.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
				Color.LIGHT_GRAY));
		lblCouleur.setBackground(Color.BLACK);
		lblCouleur.setBounds(263, 371, 65, 62);
		lblCouleur.setOpaque(true);
		contentPane.add(lblCouleur);

		btnExec = new JButton("Restaurer");
		btnExec.setBackground(new Color(255, 255, 255));
		btnExec.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExec.setBounds(518, 419, 228, 39);
		contentPane.add(btnExec);

		btnColorPicker = new JButton("Color Picker");
		btnColorPicker.setBackground(SystemColor.textInactiveText);
		btnColorPicker.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnColorPicker.setBounds(52, 371, 158, 39);
		contentPane.add(btnColorPicker);

		txtR = new JTextField();
		txtR.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtR.setSelectionColor(new Color(184, 134, 11));
		txtR.setForeground(new Color(255, 0, 0));
		txtR.setBackground(new Color(192, 192, 192));
		txtR.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setText("255");
		txtR.setBounds(37, 428, 40, 30);
		contentPane.add(txtR);
		txtR.setColumns(10);

		txtG = new JTextField();
		txtG.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtG.setSelectionColor(new Color(184, 134, 11));
		txtG.setForeground(new Color(0, 128, 0));
		txtG.setBackground(new Color(192, 192, 192));
		txtG.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtG.setHorizontalAlignment(SwingConstants.CENTER);
		txtG.setText("255");
		txtG.setColumns(10);
		txtG.setBounds(82, 428, 40, 30);
		contentPane.add(txtG);

		txtB = new JTextField();
		txtB.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtB.setSelectionColor(new Color(184, 134, 11));
		txtB.setForeground(new Color(0, 0, 255));
		txtB.setBackground(new Color(192, 192, 192));
		txtB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtB.setHorizontalAlignment(SwingConstants.CENTER);
		txtB.setText("255");
		txtB.setColumns(10);
		txtB.setBounds(132, 427, 40, 30);
		contentPane.add(txtB);

		btnApply = new JButton("Apply");
		btnApply.setBackground(SystemColor.textInactiveText);
		btnApply.setBounds(182, 427, 65, 30);
		contentPane.add(btnApply);

		btnSearchImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser("./Image");
				chooser.setAcceptAllFileFilterUsed(false);
				FileFilter imagesFilter = new FileNameExtensionFilter("Image", "bmp");
				chooser.setDialogTitle("Choisir une image");
				chooser.addChoosableFileFilter(imagesFilter);

				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						Image img = ImageIO.read(chooser.getSelectedFile());
						int x = 0, y = 0;

						if (img.getHeight(null) < img.getWidth(null)) {
							x = 250;
							y = 180;
						} else {
							x = 180;
							y = 250;
						}
						Image newimg = img.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
																									// way
						image.setBounds(388, 143, x, y);
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
				FileFilter imagesFilter = new FileNameExtensionFilter("Image", "bmp");
				chooser.setDialogTitle("Choisir une image");
				chooser.addChoosableFileFilter(imagesFilter);

				int returnVal = chooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						Image img = ImageIO.read(chooser.getSelectedFile());
						int x = 0, y = 0;

						if (img.getHeight(null) < img.getWidth(null)) {
							x = 250;
							y = 180;
						} else {
							x = 180;
							y = 250;
						}
						Image newimg = img.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
																									// way
						masque.setBounds(682, 143, x, y);
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
				lblCouleur.setBackground(new Color(Integer.parseInt(txtR.getText()), Integer.parseInt(txtG.getText()),
						Integer.parseInt(txtB.getText())));
			}
		});
		btnExec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!txtLienImage.getText().isEmpty() || !txtLienMasque.getText().isEmpty() && !traitement) {
						
						traitement = true;
						lblLoading.setVisible(true);
						JFrame result = new ResultJFrame(doTheIntpainting());
						result.setVisible(true);
						frame.setAllTrue();
						
					} else
						JOptionPane.showMessageDialog(that, "Erreur : Image et/ou masque incorrect",
								"Erreur de traitement", 0);
				} catch (Exception exce) {
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
			@Override
			public void mouseExited(MouseEvent e) {
				if (txtR.getText().isEmpty())
					txtR.setText("0");
			}
		});
		txtG.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtG.getText().equals("255") && g) {
					txtG.setText("");
					g = false;
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (txtG.getText().isEmpty())
					txtG.setText("0");
			}
		});
		txtB.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (txtB.getText().equals("255") && b) {
					txtB.setText("");
					b = false;
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if (txtB.getText().isEmpty())
					txtB.setText("0");
			}
		});

	}
	
	private BufferedImage doTheIntpainting() throws IOException {
		Matrix matrix = new Matrix(txtLienImage.getText());
		Matrix matrix2 = new Matrix(txtLienMasque.getText());
		topologyV2.Color color = new topologyV2.Color(lblCouleur.getBackground().getBlue(),
				lblCouleur.getBackground().getGreen(), lblCouleur.getBackground().getRed());
		Mask mask = new Mask(matrix2, color);
		Inpainting inpainting = new Inpainting(matrix, mask);
		inpainting.restore(5, 5);
		BufferedImage img = new BufferedImage(inpainting.image.width, inpainting.image.height,
				BufferedImage.TYPE_3BYTE_BGR);
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		int k = 0;
		for (int j = 0; j < matrix.height; j++)
			for (int i = 0; i < matrix.width; i++)
				for (int c = 0; c < 3; c++)
					pixels[k++] = matrix.val[i][j].val[c];
		
		return img;
	}	
	
	public void setAllTrue() {
		lblTitreImages.setVisible(true);
		btnSearchImage.setVisible(true);
		txtLienImage.setVisible(true);
		lblImage.setVisible(true);
		txtLienMasque.setVisible(true);
		lblMasque.setVisible(true);
		btnSearchMasque.setVisible(true);
		image.setVisible(true);
		masque.setVisible(true);
		separator.setVisible(true);
		lblTitreCouleur.setVisible(true);
		lblCouleur.setVisible(true);
		btnExec.setVisible(true);
		btnColorPicker.setVisible(true);
		txtR.setVisible(true);
		txtG.setVisible(true);
		txtB.setVisible(true);
		btnApply.setVisible(true);
		
		lblLoading.setVisible(false);
	}
}
