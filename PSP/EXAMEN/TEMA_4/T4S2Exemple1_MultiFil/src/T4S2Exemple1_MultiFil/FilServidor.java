package T4S2Exemple1_MultiFil;
import java.io.*;
import java.net.*;

public class FilServidor extends Thread {
	BufferedReader fentrada;
	PrintWriter feixida;
	Socket socket = null;

	// constructor FilServidor
	public FilServidor(Socket s) throws IOException {
		System.out.println("Cree fil servidor");
		socket = s;
		// CREE FLUXOS D'ENTRADA I EIXIDA
		feixida = new PrintWriter(socket.getOutputStream(), true);
		fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	// LÒGICA DEL SERVIDOR. ATENC EL CLIENT
	public void run() {
		try {
			String cadena = "";
			boolean parar = false;

			while (!cadena.trim().equals("*") && !parar) {
				System.out.println("En fil: comunique amb: " + socket.toString());
				try {
					cadena = fentrada.readLine();
					System.out.println("En fil: llig cadena " + cadena);
					feixida.println(cadena.trim().toUpperCase());// ENVIE CADENA AMB MAJÚSCULES
				} catch (IOException e) {
					System.out.println("ERROR: client desconnectat");
					parar = true;
				}

			}

			System.out.println("FI AMB " + socket.toString());

			// TANQUE FLUXES I SOCKET
			feixida.close();
			try {
				fentrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		}
	}
}