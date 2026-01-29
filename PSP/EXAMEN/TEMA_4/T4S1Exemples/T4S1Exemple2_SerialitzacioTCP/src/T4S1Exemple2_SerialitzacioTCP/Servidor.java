package T4S1Exemple2_SerialitzacioTCP;

import java.io.*;
import java.net.*;

public class Servidor {

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inStream = null;

	public void comunicar() {
		try {
			// ServerSocket I EN ESPERA DE REBRE UNA CONNEXIÓ
			serverSocket = new ServerSocket(4445);
			System.out.println("Servidor en espera de connexió");
			socket = serverSocket.accept();
			System.out.println("Connectat");

			// FLUX D'ENTRADA PER A OBJECTES
			inStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("Rebut");

			// REP L'OBJECTE
			Persona persona = (Persona) inStream.readObject();
			System.out.println("Objecte rebut: " + persona.getNom() + "-" + persona.getEdat());

			// TANQUE STREAMS I SOCKET
			inStream.close();
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
		servidor.comunicar();
	}

}
