package t4s3ex2multicastsocketudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Random;

/*
 * HOST DEL GRUP MulticastSocket UDP 
 * S'UNEIX AL GRUP Multicast ("224.0.0.3",8888) I ENTRA EN BUCLE INFINIT REBENT PAQUETS DEL GRUP
 * EXECUTAR TANTES VEGADES COM HOST RECEPTORS VULLGAM (AL MENYS 2)
 * */

public class HostReceptorGrupMulticastSocket {

	final static String ADREÇA_INET = "224.0.0.3";
	final static int PORT = 8888;

	public static void main(String[] args) throws UnknownHostException {
		// IDENTIFICADOR DE CLIENT GENERAT ALEATÒRIAMENT
		Random aleatori = new Random();
		int num = (int) (aleatori.nextDouble() * 100 + 1);
		System.out.println("Inici host receptor " + num);

		InetAddress adreça = InetAddress.getByName(ADREÇA_INET);
		InetSocketAddress grup = new InetSocketAddress(adreça, PORT);

		// PER A EMMAGATZEMAR EL MISSATGE
		byte[] buf = new byte[256];

		// CREEM EL Multicastsocket
		try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {

			// S'UNIM AL GRUP MULTICAST UDP. DONA IGUAL SER EL PRIMER O NO
			clientSocket.joinGroup(grup, null);
			System.out.println("Host receptor s'ha unit al grup " + adreça);

			System.out.println("Inici recepció paquets");
			while (true) {
				DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
				clientSocket.receive(msgPacket); // REBEM MISSATGE

				String msg = new String(buf, 0, buf.length);
				System.out.println("Missatge rebut: " + msg); // MOSTREM MISSATGE
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}