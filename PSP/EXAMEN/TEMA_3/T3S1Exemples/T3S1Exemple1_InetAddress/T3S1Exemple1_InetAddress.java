import java.net.InetAddress;
import java.net.UnknownHostException;

public class U3Exemple1_InetAddress {
	public static void main(String[] args) {
		try {
			// OBTINC OBJECTE InetAddress
			InetAddress host = InetAddress.getByName("www.gva.es");
			System.out.println("Host: " + host);
			// OBTINC IP
			System.out.println("IP: " + host.getHostAddress());
			// OBTINC NOM
			System.out.println("Nombre: " + host.getHostName());

			// NOM I IP DE L'EQUIP ON S'EXECUTA
			System.out.println("Localhost: " + InetAddress.getLocalHost());

		} catch (UnknownHostException ex) {
			System.err.println("Host desconocido");
			System.exit(0);
		}
	}
}
