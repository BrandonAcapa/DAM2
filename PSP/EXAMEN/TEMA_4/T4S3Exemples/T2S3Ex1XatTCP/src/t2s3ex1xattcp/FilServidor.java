package t2s3ex1xattcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * FIL QUE S'ENCARREGA DE GESTIONAR CADA CLIENT
 * ACTUALITZA NOMBRE DE CONNEXIONS
 * REP MISSATGE DE CLIENT I L'AFEGEIX A LA FINESTRA DE XAT DEL SERVIDOR
 * MANA EL XAT A TOTS ELS CLIENTS CONNECTATS
 * */
public class FilServidor extends Thread {
	DataInputStream fentrada;
	Socket socket = null;
	Socket s1 = null;

	public FilServidor(Socket s) {
		socket = s;
		try {
			fentrada = new DataInputStream(socket.getInputStream()); // CREE FLUX D'ENTRADA
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // FI constructor

	public void run() {
		// MOSTRE NOMBRE DE CONNEXIONS ACTUALS
		ServidorXat.missatge.setText("Nombre de connexions actuals " + ServidorXat.actuals);
		// ENVIE TOTS ELS MISSATGES AL CLIENT DESPRÉS DE CONNECTAR-SE
		String texto = ServidorXat.textarea.getText();
		EnviarMensajes(texto);

		// REP EL QUE EL CLIENT LI ENVIA
		while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF(); // LLIG EL QUE EL CLIENT ESCRIU

				// QUAN CLIENT VOL FINALITZAR, ENVIA *
				if (cadena.trim().equals("*")) {
					ServidorXat.actuals--; // DISMINUISC NOMBRE DE CONNEXIONS ACTUALS
					ServidorXat.missatge.setText("Nombre de connexions actuals " + ServidorXat.actuals);
					break; // EIXIR DEL BUCLE
				}

				// AFEGEISC LA CADENA A LA FINESTRA DEL SERVIDOR
				ServidorXat.textarea.append(cadena + "\n");
				texto = ServidorXat.textarea.getText();
				EnviarMensajes(texto); // ENVIE A TOTS ELS CLIENTS EL CONTINGUT DE textarea
			} catch (IOException e) {
				e.printStackTrace();
			}

		} // FI while
	}// FI run

	private void EnviarMensajes(String texto) {
		int i;
		// RECORREM EL VECTOR DE SOCKETS PER ENVIAR-LOS ELS MISSATGES 
		for (i = 0; i < ServidorXat.connexions; i++) {
			s1 = ServidorXat.socketsClients[i]; // recupero socket
			try {
				DataOutputStream fsalida = new DataOutputStream(s1.getOutputStream());
				fsalida.writeUTF(texto); // ESCRIC EL TEXT AL SOCKET
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} // FI EnviarMissatges
}