package topologyV2;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;

public class ResultJFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected ResultJFrame that = this;

	// public static void main(String[] args) throws ClassNotFoundException,
	// InstantiationException, IllegalAccessException,
	// UnsupportedLookAndFeelException {
	// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ResultJFrame frame = new ResultJFrame(null);
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public ResultJFrame(BufferedImage result) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inpainting");
		setBounds(315, 100, 430, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel image = new JLabel();
		image.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		image.setOpaque(true);
		image.setBackground(Color.white);

		int x, y, x1=0;
		ImageIcon icon = new ImageIcon(result);
		image.setBounds(371, 71, icon.getIconWidth(), icon.getIconHeight());
		if(icon.getIconHeight()>icon.getIconWidth()) {
			x1 = this.getWidth()/2-130; x=260; y=330;
		} else { x1 =this.getWidth()/2-165; x=330; y=260; }
		
		Image newimg = icon.getImage().getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		image.setBounds(x1, 10, x, y);
		image.setIcon(new ImageIcon(newimg));
		contentPane.add(image);

		JButton btnDL = new JButton("T�l�charger");
		btnDL.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnDL.setBounds(28, 362, 165, 40);
		contentPane.add(btnDL);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnQuitter.setBounds(237, 362, 165, 40);
		contentPane.add(btnQuitter);

		btnDL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("./Image");
				chooser.setAcceptAllFileFilterUsed(false);
				FileFilter imagesFilter = new FileNameExtensionFilter("Images", "bmp");
				chooser.setDialogTitle("Choisir destination");
				chooser.addChoosableFileFilter(imagesFilter);

				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File image = new File(chooser.getSelectedFile().getPath() + ".bmp");
					try {
						Graphics g = result.createGraphics();
						g.drawImage(result,0,0,null);
						ImageIO.write(result, "bmp", image);
						that.setVisible(false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}