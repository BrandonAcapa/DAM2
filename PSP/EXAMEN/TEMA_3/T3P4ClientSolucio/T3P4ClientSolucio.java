import java.net.*;
import java.util.Scanner;
import java.io.*;

public class T3P4ClientSolucio {
	// REBEM EN args MISSATGE, HOST DESTINACIO I PORT
	public static void main(String args[]) {

		DatagramSocket dSocket = null;

		// CONTROL ENTRADA ARGUMENTS
		if (args.length < 2) {
			System.out.println("Utilitzacio: java T3P4ClientSolucio <nom del Host> <numero de port>");
			System.exit(1);
		}
		try {

			// ENVIAMENT DEL DATAGRAMA
			dSocket = new DatagramSocket();
			System.out.println("Introduzca el mensaje a enviar:");
			Scanner in = new Scanner(System.in);
			String cadena = in.nextLine();
			in.close();
			byte[] missatgeEnviat = cadena.getBytes();

			InetAddress aHost = InetAddress.getByName(args[0]); // RECUPERE EL HOST DES DE L'ARGUMENT
			int serverPort = Integer.valueOf(args[1]).intValue(); // RECUPERE EL PORT DES DE L'ARGUMENT
			DatagramPacket dpEnviament = new DatagramPacket(missatgeEnviat, cadena.length(), aHost, serverPort); // DATAGRAMA A ENVIAR
			dSocket.send(dpEnviament); // ENVIE EL DATAGRAMA

			// RECEPCIO DEL DATAGRAMA
			byte[] missatgeRebut = new byte[1000];
			DatagramPacket dpResposta = new DatagramPacket(missatgeRebut, missatgeRebut.length);
			dSocket.receive(dpResposta); // REP EL DATAGRAMA
			System.out.println("Resposta: " + new String(dpResposta.getData()));

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (dSocket != null)  // SI EL SOCKET EXISTEIX
				dSocket.close(); // TANQUE
		}
	}
}