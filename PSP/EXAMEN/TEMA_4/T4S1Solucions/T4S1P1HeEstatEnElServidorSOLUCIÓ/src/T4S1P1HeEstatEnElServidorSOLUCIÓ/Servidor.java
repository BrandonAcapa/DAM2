package T4S1P1HeEstatEnElServidorSOLUCIÓ;

import java.io.*;
import java.net.*;

public class Servidor {

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream ObjectIS = null;
	private ObjectOutputStream ObjectOS = null;

	public void communicar() {
		try {
			// ServerSocket I EN ESPERA DE REBRE UNA CONNEXIÓ
			serverSocket = new ServerSocket(4445);
			System.out.println("Servidor en espera de connexió");
			socket = serverSocket.accept();
			System.out.println("Connectat");

			// FLUX D'ENTRADA PER A OBJECTES
			ObjectIS = new ObjectInputStream(socket.getInputStream());
			System.out.println("Rebut");

			// REP L'OBJECTE
			Persona persona = (Persona) ObjectIS.readObject();
			System.out.println("Objecte rebut: " + persona.getNom() + "-" + persona.getEdat());

			// MODIFIQUE EL NOM A L'OBJECTE
			persona.setNom(persona.getNom() + " he estat al servidor");
			System.out.println("Objecte modificat Persona: " + persona.getNom() + "-" + persona.getEdat());

			// CREE EL FLUX D'EIXIDA
			ObjectOS = new ObjectOutputStream(socket.getOutputStream());
			// NETEGE BUFFER
			ObjectOS.flush();
			// ESCRIC OBJECTE EN FLUX D'EIXIDA
			ObjectOS.writeObject(persona);
			System.out.println("Enviat.");

			// TANQUE STREAMS I SOCKET
			ObjectOS.close();
			ObjectIS.close();
			socket.close();

		} catch (SocketException se) {
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cn) {
			cn.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Servidor servidor = new Servidor();
		servidor.communicar();
	}
}
