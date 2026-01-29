package t2s3ex1xattcp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientXat extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	Socket socket = null;

	// FLUXOS
	DataInputStream fentrada;
	DataOutputStream feixida;

	String nom;
	static JTextField missatge = new JTextField();
	private JScrollPane scrollpanel;
	static JTextArea textarea1;
	JButton boto = new JButton("Enviar");
	JButton desconnectar = new JButton("Eixir");
	boolean repetir = true;

	// CONSTRUCTOR ------------------
	public ClientXat(Socket s, String nom) {

		// CONSTRUISC FINESTRA
		super("Connexió del client xat tcp");
		setLayout(null);
		missatge.setBounds(10, 10, 400, 30);
		add(missatge);
		textarea1 = new JTextArea();
		scrollpanel = new JScrollPane(textarea1);
		scrollpanel.setBounds(10, 50, 400, 300);
		add(scrollpanel);
		boto.setBounds(420, 10, 100, 30);
		add(boto);
		desconnectar.setBounds(420, 50, 100, 30);
		add(desconnectar);
		textarea1.setEditable(false);
		boto.addActionListener(this);
		desconnectar.addActionListener(this);

		// S'ANUL·LA EL TANCAMENT DE LA FINESTRA
		// PER A QUE LA FINALITZACIÓ DEL SERVIDOR ES FAÇA DES DEL BOTÓ EIXIR
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ASSIGNE VALOR A PARÀMETRES
		socket = s;
		this.nom = nom;

		// FLUXOS D'ENTRADA I EIXIDA
		try {
			fentrada = new DataInputStream(socket.getInputStream());
			feixida = new DataOutputStream(socket.getOutputStream());
			String texto = " > S'ha connectat... " + nom; // FORME CADENA
			feixida.writeUTF(texto); // ENVIE TEXT AL SERVIDOR
		} catch (IOException e) {
			System.out.println("Error d'E/S");
			e.printStackTrace();
			System.exit(0);
		}
	} // FI CONSTRUCTOR ------------------------------------------------

	// ACCIÓ QUAN POLSEM BOTONS
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == boto) // ES POLSA EL BOTÓ ENVIAR
		{
			String texto = nom + ">" + missatge.getText();
			try {
				feixida.writeUTF(texto); // ENVIE TEXT ESCRIT A SERVIDOR
				missatge.setText(""); // NETEGE ÀREA DE CLIENT
			} catch (IOException e) {
				e.printStackTrace();
			}

		} // FI BOTÓ ENVIAR

		if (arg0.getSource() == desconnectar) // ES POLSA EL BOTÓ EIXIR
		{
			String texto = nom + ">" + "se n'ha anat";
			try {
				feixida.writeUTF(texto); // ENVIE TEXT ESCRIT A SERVIDOR
				feixida.writeUTF("*"); // ENVIE COMODÍ DE DESCONNEXIÓ
				repetir = false; // PER EIXIR DEL BUCLE
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} // FI BOTÓ EIXIR

	}// FI actionPerformed ------------------------------------------------

	// ESTÀ CONTÍNUAMENT LLEGINT TEXT DE FilServidor I MOSTRANT-LO EN textarea1
	public void ejecutar() {
		String texto = "";
		while (repetir) {
			try {
				texto = fentrada.readUTF();// LLEGIR MISSATGES
				textarea1.setText(texto);// CARREGAR MISSATGES EN textarea1
			} catch (IOException e) {
				// ERROR QUAN SERVIDOR ES TANCA
				JOptionPane.showMessageDialog(null, "Impossible connectar amb el servidor\n");
				repetir = false; // PER EIXIR DEL BUCLE
				e.printStackTrace();
			}
		} // FI BUCLE repetir

		try {
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // FI executar ------------------------------------------------

	public static void main(String args[]) {

		int num_puerto = 44444;

		// DEMANA MALNOM
		String malnom = JOptionPane.showInputDialog("Introdueix el teu malnom (nickname)");

		// CONNECTE AMB SERVIDOR
		Socket s = null;
		try {
			s = new Socket("localhost", num_puerto); // cliente y servidor se ejecutan en la misma máquina
		} catch (IOException e) {
			// ERROR DE CONNEXIÓ AMB SERVIDOR
			JOptionPane.showMessageDialog(null, "Impossible connectar amb el servidor\n");
			System.exit(0);
			e.printStackTrace();
		}

		if (!malnom.trim().equals("")) // SI HA INTRODUÏT EL MALNOM
		{
			ClientXat cliente = new ClientXat(s, malnom);
			cliente.setBounds(0, 0, 540, 400);
			cliente.setVisible(true);
			cliente.ejecutar();
		} else {
			System.out.println("El nom està buit");
		}

	} // FI main ------------------------------------------------
}