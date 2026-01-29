import java.net.*;
import java.io.*;

public class T3S2Exemple1_UDPClient {
	// REBEM EN args MISSATGE, HOST DESTINACIÓ I PORT
	public static void main(String args[]) {

		DatagramSocket dSocket = null;

		// CONTROL ENTRADA ARGUMENTS
		if (args.length < 3) {
			System.out.println("Utilització: java T3S2Exemple1_UDPClient <missatge> <nom del Host> <número de port>");
			System.exit(1);
		}
		try {

			// ENVIAMENT DEL DATAGRAMA
			dSocket = new DatagramSocket();
			byte[] missatgeEnviat = args[0].getBytes();
			InetAddress aHost = InetAddress.getByName(args[1]); // RECUPERE EL HOST DES DE L'ARGUMENT
			int serverPort = Integer.valueOf(args[2]).intValue(); // RECUPERE EL PORT DES DE L'ARGUMENT
			DatagramPacket dpEnviament = new DatagramPacket(missatgeEnviat, args[0].length(), aHost, serverPort); // DATAGRAMA A ENVIAR
			dSocket.send(dpEnviament); // ENVIE EL DATAGRAMA

			// RECEPCIÓ DEL DATAGRAMA
			byte[] missatgeRebut = new byte[1000];
			DatagramPacket dpResposta = new DatagramPacket(missatgeRebut, missatgeRebut.length);
			dSocket.receive(dpResposta); // REP EL DATAGRAMA
			System.out.println("Resposta: " + new String(dpResposta.getData()));

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (dSocket != null) // SI EL SOCKET EXISTEIX
				dSocket.close(); // TANQUE
		}
	}
}