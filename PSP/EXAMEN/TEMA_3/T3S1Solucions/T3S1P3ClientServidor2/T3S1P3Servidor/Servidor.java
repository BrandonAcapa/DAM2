import java.io.*;
import java.net.*;

//SERVIDOR ESPERA CONNEXIONS A LES QUE ATENDRÃ€
//SERVIDOR SALUDARÃ€ AMB LA FRASE "Hola client"
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

				String cadena;
				do {
					cadena = disEntrada.readUTF();
					System.out.println("Client: " + cadena);
					switch (cadena) {
					case "Hola":
						dosEixida.writeUTF("Hola soc el servidor");
						break;
					case "Com estÃ s":
						dosEixida.writeUTF("Molt be");
						break;
					case "Adeu":
						dosEixida.writeUTF("Fins despres");
						break;
					default:
						dosEixida.writeUTF("Cadena no reconeguda pel servidor");
						break;
					}
				} while (!cadena.equals("Adeu"));

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