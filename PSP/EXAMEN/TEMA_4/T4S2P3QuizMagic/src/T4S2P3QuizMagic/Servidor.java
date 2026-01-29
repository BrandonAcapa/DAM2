package T4S2P3QuizMagic;

import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String args[]) throws IOException {

		ServerSocket servidor; // CREE serverSocket
		servidor = new ServerSocket(60000);
		System.out.println("Inici Servidor en 60000");

		while (true) {
			Socket socketClient = new Socket();
			socketClient = servidor.accept(); // ESPERE AL CLIENT
			FilServidor fil = new FilServidor(socketClient); // FIL ATÉN AL CLIENT
			fil.start(); // INICIE EL FIL
			System.out.println("Inici FilServidor");
		}

	}
}
