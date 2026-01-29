package T4S1P1HeEstatEnElServidorSOLUCIÓ;

import java.io.*;
import java.net.*;

import T4S1P1HeEstatEnElServidorSOLUCIÓ.Persona;

public class Client {

	private Socket socket = null;
	private ObjectInputStream ObjectIS = null;
	private ObjectOutputStream ObjectOS = null;
	private boolean estaConnectat = false;

	public Client() {
	}

	public void comunicar() {

		while (!estaConnectat) {
			try {
				// CONNECTE
				socket = new Socket("localhost", 4445);
				System.out.println("Connectat");
				estaConnectat = true;

				// ENVIAMENT
				// CREE EL FLUX D'EIXIDA
				ObjectOS = new ObjectOutputStream(socket.getOutputStream());

				// CARREGUE DADES EN OBJECTE
				Persona persona = new Persona("Pepe", 25);
				System.out.println("Objecte a enviar: " + persona.getNom() + "-" + persona.getEdat());
				// NETEGE BUFFER
				ObjectOS.flush();
				// ESCRIC OBJECTE EN FLUX D'EIXIDA
				ObjectOS.writeObject(persona);
				System.out.println("Enviat.");

				// RECEPCIÓ
				try {
					// FLUX D'ENTRADA PER A OBJECTES
					InputStream is = socket.getInputStream();
					ObjectIS = new ObjectInputStream(is);

					// REP L'OBJECTE
					Persona persona2 = (Persona) ObjectIS.readObject();
					System.out.println("Objecte rebut: " + persona2.getNom() + "-" + persona2.getEdat());

				} catch (SocketException se) {
					System.exit(0);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException cn) {
					cn.printStackTrace();
				}

				// TANQUE STREAMS I SOCKET
				ObjectOS.close();
				ObjectIS.close();
				socket.close();

			} catch (SocketException se) {
				se.printStackTrace();
				// System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.comunicar();
	}

}
