import java.io.*;
import java.net.*;

//SERVIDOR ESPERA CONNEXIONS A LES QUE ATENDRÀ
//SERVIDOR SALUDARÀ AMB LA FRASE "Hola client"
class Servidor {

	// PORT EN EL QUE ESPERA CONNEXIONS
	static final int PORT = 5000;

	public Servidor() {
		try {
			// CREA SOCKET I ESPERA
			ServerSocket ssServidor = new ServerSocket(PORT);
			System.out.println("Escolte al port " + PORT);

			while (true) {
				// MÈTODE accept() CREA UN NOU Socket PER A COMUNICAR-SE AMB EL CLIENT
				Socket sCliente = ssServidor.accept();
				System.out.println("Serveisc al client");

				// CREE ELS FLUXOS D'ENTRADA I EIXIDA PER AL SOCKET
				// ASSOCIE FLUX EIXIDA DE DADES AL SOCKET DEL CLIENT
				InputStream isEntrada = sCliente.getInputStream();
				// ASSOCIE FLUX DE DADES A UN ALTRE FLUX TIPUS DataInputStream
				DataInputStream disEntrada = new DataInputStream(isEntrada);
				// ASSOCIE FLUX EIXIDA DE DADES AL SOCKET DEL CLIENT
				OutputStream osEixida = sCliente.getOutputStream();
				// ASSOCIE FLUX DE DADES A UN ALTRE FLUX TIPUS DataOutputStream
				DataOutputStream dosEixida = new DataOutputStream(osEixida);

				// REP CADENA DEL CLIENT I ENVIE RESPOSTA AL CLIENT
				System.out.println("Client: " + disEntrada.readUTF());
				dosEixida.writeUTF("Hola soc el servidor");
				System.out.println("Client: " + disEntrada.readUTF());
				dosEixida.writeUTF("Molt be");
				System.out.println("Client: " + disEntrada.readUTF());
				dosEixida.writeUTF("Fins despres");

				// TANQUE CONNEXIONS
				System.out.println("Tancant connexió...");
				sCliente.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] arg) {
		new Servidor();
	}
}
