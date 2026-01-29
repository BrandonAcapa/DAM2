package T4S1P2ConcessionariSOLUCIÓ;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {

	private Socket socket = null;
	private ObjectOutputStream ObjectOS = null;
	private boolean estaConnectat = false;

	public Cliente() {
	}

	public void comunicar() {

		while (!estaConnectat) {

			String DNI, matricula, marca, model, combustible, any;

			// CAPTACI� DE DADES PER TECLAT
			Scanner in;
			in = new Scanner(System.in);
			System.out.println("Introdueix el DNI del client");
			DNI = in.nextLine();
			System.out.println("Introdueix la matr�cula");
			matricula = in.nextLine();
			System.out.println("Introdueix la marca del vehicle");
			marca = in.nextLine();
			System.out.println("Introdueix el model");
			model = in.nextLine();
			System.out.println("Introdueix el el tipus de combustible");
			combustible = in.nextLine();
			System.out.println("Introdueix l'any de matriculaci�");
			any = in.nextLine();
			in.close();

			// POSEM LES DADES EN UN OBJETE DE TIPUS Vehicle
			Vehicle vehicle = new Vehicle(DNI, matricula, marca, model, combustible, any);

			// ENVIAMENT
			try {
				// CONNECTE
				socket = new Socket("localhost", 4445);
				System.out.println("Connectat");
				estaConnectat = true;

				// CREE EL FLUX D'EIXIDA
				ObjectOS = new ObjectOutputStream(socket.getOutputStream());
				// NETEGE BUFFER
				ObjectOS.flush();
				// ESCRIC OBJECTE EN FLUX D'EIXIDA
				ObjectOS.writeObject(vehicle);
				System.out.println("Enviat.");

				// TANQUE STREAMS I SOCKET
				ObjectOS.close();
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
		Cliente client = new Cliente();
		client.comunicar();
	}
}
