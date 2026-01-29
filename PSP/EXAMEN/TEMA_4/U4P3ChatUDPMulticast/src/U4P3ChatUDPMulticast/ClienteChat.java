package U4P3ChatUDPMulticast;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteChat extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	String nombre;
	static JTextField mensaje = new JTextField();
	private JScrollPane scrollpanel;
	static JTextArea textarea1;
	JButton boton = new JButton("Enviar");
	JButton desconectar = new JButton("Salir");

	// constructor ------------------
	public ClienteChat(String nom) {

		// construyo ventana
		super("Conexión del cliente chat udp");
		setLayout(null);
		mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);
		textarea1 = new JTextArea();
		scrollpanel = new JScrollPane(textarea1);
		scrollpanel.setBounds(10, 50, 400, 300);
		add(scrollpanel);
		boton.setBounds(420, 10, 100, 30);
		add(boton);
		desconectar.setBounds(420, 50, 100, 30);
		add(desconectar);
		textarea1.setEditable(false);
		boton.addActionListener(this);
		desconectar.addActionListener(this);
		// Se anula el cierre de la ventana para que la finalización del servidor se
		// haga desde el botón Salir
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// asigno valor a parámetros
		this.nombre = nom;

		// fin constructor ------------------------------------------------
	}

	// accion cuando pulsamos botones
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == boton) // se pulsa el boton enviar
		{
			String texto = nombre + ">" + mensaje.getText();
			enviarUDP(texto);
			mensaje.setText(""); // limpio area de cliente
		} // fin boton enviar

		if (arg0.getSource() == desconectar) // se pulsa el boton salir
		{
			String texto = nombre + ">" + "se ha ido";
			enviarUDP(texto);
			System.exit(0);
		} // fin boton salir

	}// fin actionPerformed ------------------------------------------------

	public void enviarUDP(String sMensaje) {
		int iPuerto = 44444;
		DatagramSocket dsCliente = null;
		try {
			dsCliente = new DatagramSocket();
			byte[] m = sMensaje.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost");
			DatagramPacket dpNickname = new DatagramPacket(m, sMensaje.length(), aHost, iPuerto);
			dsCliente.send(dpNickname);
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (dsCliente != null)
				dsCliente.close();
		}
	}

	public void ejecutar() {

		// ENVÍO NICKNAME A TRAVÉS DE UDP (NO MULTICAST)
		enviarUDP(nombre + ">" + "se ha conectado");

		// RECIBO DEL SERVIDOR A TRAVÉS DEL GRUPO MULTICAST ETERNAMENTE
		byte[] buf = new byte[1024];
		String iDireccionMultiCast = "224.0.0.3";
		int iPuertoMultiCast = 55555;
		MulticastSocket msCliente = null;
		try {
			InetAddress address = InetAddress.getByName(iDireccionMultiCast);
			msCliente = new MulticastSocket(iPuertoMultiCast);
			msCliente.joinGroup(address);
			System.out.println("cliente join a grupo " + address);
			System.out.println("Inicio recepción paquetes");
			while (true) {
				//buf = new byte[1024];
				DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
				msCliente.receive(msgPacket);

				String msg = new String(buf, 0, buf.length);
				textarea1.setText(msg);
				// System.out.println("Socket 1 received msg: " + msg);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (msCliente != null) // si el socket existe
				msCliente.close(); // cierro
		}

	} // fin ejecutar

	public static void main(String args[]) {
		// pido nombre usuario
		String nickname = JOptionPane.showInputDialog("Introduce tu nick");

		if (!nickname.trim().equals("")) // si ha introducido el nickname
		{
			ClienteChat cliente = new ClienteChat(nickname);
			cliente.setBounds(0, 0, 540, 400);
			cliente.setVisible(true);
			cliente.ejecutar();
		} else {
			System.out.println("El nombre está vacio");
		}

	} // fin main

}
