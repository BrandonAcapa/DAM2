import java.io.*;
import java.net.*;

// SERVIDOR ESPERA CONNEXIONS A LES QUE ATENDRÀ
// SERVIDOR SALUDARÀ AMB LA FRASE "Hola client"
class Servidor {

	// PORT EN EL QUE ESPERA CONNEXIONS
	static final int PORT = 5000;

	public Servidor() {
		try {
			// CREA SOCKET I ESPERA
			ServerSocket ssServidor = new ServerSocket(PORT);
			System.out.println("Escolte al port " + PORT);

			for (int numCli = 0; numCli < 3; numCli++) {
				// MÈTODE accept() CREA UN NOU Socket PER A COMUNICAR-SE AMB EL CLIENT
				Socket sCliente = ssServidor.accept();

				System.out.println("Serveisc al client");
				// ASSOCIE FLUX EIXIDA DE DADES AL SOCKET DEL CLIENT
				OutputStream aux = sCliente.getOutputStream();
				// ASSOCIE FLUX DE DADES A UN ALTRE FLUX TIPUS DataOutputStream
				DataOutputStream flux = new DataOutputStream(aux);
				// ENVIE AL CLIENT
				flux.writeUTF("Hola client");
				// TANQUE CONNEXIONS
				sCliente.close();
			}
			System.out.println("Massa clients per hui");
			ssServidor.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] arg) {
		new Servidor();
	}
}
