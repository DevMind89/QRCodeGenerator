package src.code;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@SuppressWarnings("serial")
public class QRCodeGenerator extends JFrame {

	private JTextField textField;
	private JButton generateButton;
	private JLabel imageLabel;

	public QRCodeGenerator() {
		super("Generador de código QR");

		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/assets/qr.png"));

		textField = new JTextField();
		generateButton = new JButton("Generar QR");
		imageLabel = new JLabel();

		textField.setPreferredSize(new Dimension(300, 30));
		textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textField.setForeground(Color.GRAY);
		textField.setText("Introduzca URL...");
		textField.setEditable(false);
		textField.addMouseListener(new GenerateTextFieldButtonListener());
		generateButton.addActionListener(new GenerateTextFieldButtonListener());
		imageLabel.setPreferredSize(new Dimension(300, 300));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imageLabel.setVerticalAlignment(SwingConstants.CENTER);

		add(textField, BorderLayout.NORTH);
		add(generateButton, BorderLayout.CENTER);
		add(imageLabel, BorderLayout.SOUTH);

		setVisible(true);
		setResizable(false);
	}

	private class GenerateTextFieldButtonListener implements ActionListener, MouseListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (textField.getText().contains("://")) {
				BufferedImage image = generateQRCode(textField.getText());
				imageLabel.setIcon(new ImageIcon(image));
			} else {
				JOptionPane.showMessageDialog(null, "Introduzca una URL correcta", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (textField.getText().equals("Introduzca URL...")) {
				textField.setText("");
				textField.setForeground(Color.BLACK);
				textField.setEditable(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (textField.getText().isEmpty()) {
				textField.setText("Introduzca URL...");
				textField.setForeground(Color.GRAY);
				textField.setEditable(false);
			}
		}
	}

	private BufferedImage generateQRCode(String texto) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 300, 300);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
				}
			}
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		new QRCodeGenerator();
	}
}