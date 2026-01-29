package t2s3ex1xattcp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServidorXat extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static final int port = 44444; // PORT PEL QUAL ESCOLTA
	static ServerSocket servidor;
	static int connexions = 0;
	static int actuals = 0;
	static int maximConnexions = 10; // MÀXIM NOMBRE DE CONNEXIONS QUE POT HAVER-HI

	static JTextField missatge = new JTextField("");
	static JTextField missatge2 = new JTextField("");
	private JScrollPane scrollpanel;
	static JTextArea textarea;
	JButton eixir = new JButton("Eixir");
	static Socket socketsClients[] = new Socket[maximConnexions]; // EMMAGATZEMA SOCKETS DE CLIENTS

	// CONSTRUCTOR ------------------------------------------------
	public ServidorXat() {

		// CONSTRUISC FINESTRA SERVIDOR
		super("Servidor del chat");
		setLayout(null);

		missatge.setBounds(10, 10, 400, 30);
		add(missatge);
		missatge.setEditable(false);

		missatge2.setBounds(10, 348, 400, 30);
		add(missatge2);
		missatge.setEditable(false);

		textarea = new JTextArea();
		scrollpanel = new JScrollPane(textarea);
		scrollpanel.setBounds(10, 50, 400, 300);
		add(scrollpanel);

		eixir.setBounds(420, 10, 100, 30);
		add(eixir);

		textarea.setEditable(false);
		eixir.addActionListener(this);

		// S'ANUL·LA EL TANCAMENT DE LA FINESTRA
		// PER A QUE LA FINALITZACIÓ DEL SERVIDOR ES FAÇA DES DEL BOTÓ EIXIR
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}// FI CONSTRUCTOR ------------------------------------------------

	// ACCIÓ QUAN POLSEM BOTÓ EIXIR
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == eixir) // SI ES POLSA EIXIR
		{
			try {
				servidor.close(); // TANQUE SERVIDOR
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		}

	}

	// main: INICIE SERVIDOR I VARIABLES ------------------------------------------------
	public static void main(String args[]) throws IOException {

		servidor = new ServerSocket(port); // CREE ServerSocket
		System.out.println("Servidor iniciat");

		ServidorXat pantalla = new ServidorXat();
		pantalla.setBounds(0, 0, 540, 400);
		pantalla.setVisible(true);
		missatge.setText("Nombre de connexions actuals: " + connexions);

		// ADMET FINS 10 CONNEXIONS
		while (connexions < maximConnexions) {
			Socket socket = new Socket();
			try {
				socket = servidor.accept(); // ESPERANT CLIENT
			} catch (SocketException ns) { // SI POLSE EL BOTÓ EIXIR ES PRODUEIX UNA EXCEPCIÓ SocketException
				break; // I IX PEL break
			}
			socketsClients[connexions] = socket; // EMMAGATZEME EL SOCKET
			connexions++; // AUGMENTE NOMBRE DE CONNEXIONS REALITZADES
			actuals++;
			FilServidor hilo = new FilServidor(socket);
			hilo.start();
		} // FI while

		// SI servidor NO S'HA TANCAT VOL DIR QUE S'HAN ALCANÇAT LES 10 CONNEXIONS MÀXS
		if (!(servidor.isClosed())) {
			try {
				missatge2.setForeground(Color.red);
				missatge2.setText("Màxim nombre de connexios establertes " + connexions);
				servidor.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}// FI main ------------------------------------------------
}
