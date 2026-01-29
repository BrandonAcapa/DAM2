package t4s3ex2multicastsocketudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/*
 * EMISSOR
 * ENVIA 5 DATAGRAMES O MISSATGES AL GRUP multicast ("224.0.0.3",8888)
 * NO FA FALTA UNIR-SE AL GRUP
 * */
public class HostEmissor {

	final static String ADREÇA_INET = "224.0.0.3";
	final static int PORT = 8888;

	public static void main(String[] args) throws UnknownHostException, InterruptedException {
		InetAddress adreça = InetAddress.getByName(ADREÇA_INET);

		// CREEM EL DatagramSocket PER ENVIAR DATAGRAMES UDP
		try (DatagramSocket serverSocket = new DatagramSocket()) {
			for (int i = 0; i < 5; i++) {
				String missatge = "--Missatge " + i + " del host emissor--";

				// CREEM EL DatagramPacket AMB EL MISSATGE I L'ENVIEM
				DatagramPacket datagrama = new DatagramPacket(missatge.getBytes(), missatge.getBytes().length, adreça,
						PORT);
				serverSocket.send(datagrama);

				System.out.println("Host emissor ha enviat datagrama amb el missatge: " + missatge);
				Thread.sleep(500);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}