package U4P3ChatUDPMulticast;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServidorChat extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	static JTextField mensaje = new JTextField("");
	static JTextField mensaje2 = new JTextField("");
	private JScrollPane scrollpanel;
	static JTextArea textarea;
	JButton salir = new JButton("Salir");

	// constructor
	// -----------------------------------------------------------------------
	public ServidorChat() {

		// construyo ventana servidor
		super("Servidor del chat");
		setLayout(null);

		mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);
		mensaje.setEditable(false);

		mensaje2.setBounds(10, 348, 400, 30);
		add(mensaje2);
		mensaje.setEditable(false);

		textarea = new JTextArea();
		scrollpanel = new JScrollPane(textarea);
		scrollpanel.setBounds(10, 50, 400, 300);
		add(scrollpanel);

		salir.setBounds(420, 10, 100, 30);
		add(salir);

		textarea.setEditable(false);
		salir.addActionListener(this);

		// Se anula el cierre de la ventana para que la finalización del servidor se
		// haga desde el botón Salir
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}// fin constructor
		// ------------------------------------------------------------------

	// acción cuando pulsamos botón salir
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == salir) // si se pulsa salir
			System.exit(0);
	}

	public static void main(String args[]) {

		String INET_ADDR = "224.0.0.3";
		int iPuerto = 44444;

		ServidorChat pantalla = new ServidorChat();
		pantalla.setBounds(0, 0, 540, 400);
		pantalla.setVisible(true);

		DatagramSocket dsServidor = null;
		try {
			dsServidor = new DatagramSocket(iPuerto);
			byte[] buffer = new byte[1000];
			String sTexto;
			while (true) {
				sTexto = null;
				buffer = new byte[1000];
				DatagramPacket dpCliente = new DatagramPacket(buffer, buffer.length);
				dsServidor.receive(dpCliente);

				// AÑADO EL TEXTO DEL CLIENTE A LA VENTANA DEL SERVIDOR
				ServidorChat.textarea.append(new String(dpCliente.getData()).trim() + "\n");

				// ENVÍO EL CHAT A TODOS A TRAVÉS DE LA DIRECCIÓN MULTICAST
				int iPuertoMultiCast = 55555;
				sTexto = ServidorChat.textarea.getText();
				InetAddress addr = InetAddress.getByName(INET_ADDR);
				//try (DatagramSocket serverSocket = new DatagramSocket()) {
					DatagramPacket msgPacket = new DatagramPacket(sTexto.getBytes(), sTexto.getBytes().length, addr,
							iPuertoMultiCast);
					dsServidor.send(msgPacket);
				//} catch (IOException ex) {
				//	ex.printStackTrace();
				//}
			}
		} catch (SocketException e) { // captura excepcion
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) { // captura excepcion
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (dsServidor != null) // si el socket existe
				dsServidor.close(); // cierro
		}
	}// fin main

}
