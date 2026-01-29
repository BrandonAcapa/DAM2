package T4S1P2ConcessionariSOLUCIÓ;

import java.io.*;
import java.net.*;

public class Servidor {

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream ObjectIS = null;
	private ObjectOutputStream ObjectOS = null;

	public void communicar() {
		try {

			// ServerSocket I EN ESPERA DE REBRE UNA CONNEXI�
			serverSocket = new ServerSocket(4445);
			System.out.println("Servidor en espera de connexi�");

			while (true) {

				socket = serverSocket.accept();
				System.out.println("Connectat");

				// FLUX D'ENTRADA PER A OBJECTES
				ObjectIS = new ObjectInputStream(socket.getInputStream());
				System.out.println("Rebut");

				// REP L'OBJECTE
				Vehicle vehicle = (Vehicle) ObjectIS.readObject();
				System.out.println("Objecte rebut vehicle: DNI: " + vehicle.getDNI() 
									+ ". Matr�cula: " + vehicle.getMatricula()
									+ ". Marca: " + vehicle.getMarca()
									+ ". Model: " + vehicle.getModel()
									+ ". Combustible: " + vehicle.getCombustible()
									+ ". Any: " + vehicle.getAny());

				// GUARDE L'OBJECTE EN EL FITXER
				File file = new File("T4S1P2Concessionari.txt");
				FileOutputStream FOStream = new FileOutputStream(file, true);
				ObjectOS = new ObjectOutputStream(FOStream);
				ObjectOS.writeObject(vehicle);

				// TANQUE STREAMS I SOCKET
				ObjectOS.close();
				ObjectIS.close();
				socket.close();

			}
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
