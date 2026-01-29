import java.io.*;
import java.net.*;

class Client {
	// NOM MAQUINA I PORT
	static final String HOST = "localhost";
	static final int PORT = 5000;

	public Client() {
		try {
			// ES CREA EL SOCKET
			Socket sCliente = new Socket(HOST, PORT);

			// ASSOCIE FLUX EIXIDA DE DADES AL SOCKET DEL CLIENT
			InputStream aux = sCliente.getInputStream();

			// ASSOCIE FLUX DE DADES A UN ALTRE FLUX TIPUS DataInputStream
			DataInputStream flux = new DataInputStream(aux);

			// LLEGIM LA CADENA DEL FLUX AMB readUTF I MOSTRE PER PANTALLA
			System.out.println(flux.readUTF());

			// Cierro el socket
			sCliente.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] arg) {
		new Client();
	}
}
