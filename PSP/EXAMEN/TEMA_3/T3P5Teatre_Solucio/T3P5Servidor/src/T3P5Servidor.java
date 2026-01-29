import java.io.*;
import java.net.*;

//SERVIDOR ESPERA CONNEXIONS A LES QUE ATENDRÃ€
//SERVIDOR SALUDARÀ AMB LA FRASE "Hola client"
class T3P5Servidor {

	// PORT EN EL QUE ESPERA CONNEXIONS
	static final int PORT = 5000;
	static final String NOM_BUTAQUES[] = { "VIP1", "VIP2", "LAT1", "LAT2", "CEN", "GAL" };
	static final int PREU_ENTRADES[] = { 250, 250, 100, 100, 80, 150 };

	int numeroEntrades[] = { 3, 3, 4, 4, 54, 8 };

	public T3P5Servidor() {

		try {
			// CREA SOCKET I ESPERA
			ServerSocket ssServidor = new ServerSocket(PORT);
			System.out.println("Escolte al port " + PORT);

			while (true) {
				// MÈTODE accept() CREA UN NOU Socket PER A COMUNICAR-SE AMB EL CLIENT
				Socket sCliente = ssServidor.accept();
				System.out.println("Serveisc al client");

				// CREE ELS FLUXOS D'ENTRADA I EIXIDA PER AL SOCKET
				// ASSOCIE FLUX EIXIDA DE DADES AL SOCKET DEL CLIENT
				InputStream isEntrada = sCliente.getInputStream();
				// ASSOCIE FLUX DE DADES A UN ALTRE FLUX TIPUS DataInputStream
				DataInputStream disEntrada = new DataInputStream(isEntrada);
				// ASSOCIE FLUX EIXIDA DE DADES AL SOCKET DEL CLIENT
				OutputStream osEixida = sCliente.getOutputStream();
				// ASSOCIE FLUX DE DADES A UN ALTRE FLUX TIPUS DataOutputStream
				DataOutputStream dosEixida = new DataOutputStream(osEixida);

				// COMUNICACIÓ INICIAL
				System.out.println("Client: " + disEntrada.readUTF());
				dosEixida.writeUTF("Hola sóc el servidor");

				String cadena, verbutaques;
				Boolean eixir;
				int i;
				do {
					cadena = disEntrada.readUTF();
					System.out.println("Client: " + cadena);
					eixir = false;

					// CADENA "Fi" ACABA
					if (cadena.equals("Fi"))
						dosEixida.writeUTF("Fi");

					// CADENA "Veure butaques" MOSTRA INFORMACIÓ
					else if (cadena.equals("Veure butaques")) {
						verbutaques = "\nVeure butaques:\n";
						for (i = 0; i < NOM_BUTAQUES.length; i++) {
							verbutaques = verbutaques + "Tipus butaca: " + NOM_BUTAQUES[i] + ". Preu: "
									+ PREU_ENTRADES[i] + "€. Disponibles: " + numeroEntrades[i] + ".\n";
						}
						dosEixida.writeUTF(verbutaques);
					} else {

						// RECORREC EL BUCLE A LA CERCA DE LA CADENA
						i = 0;
						do {
							if (cadena.equals(NOM_BUTAQUES[i])) {
								// TROBAT EL TIPUS DE BUTACA
								eixir = true;
								if (numeroEntrades[i] > 0) {
									// RESERVE
									numeroEntrades[i]--;
									dosEixida.writeUTF("Reserva " + NOM_BUTAQUES[i] + " " + numeroEntrades[i]);
								} else
									// BUTAQUES ESGOTADES
									dosEixida.writeUTF("Butaques esgotades per a " + NOM_BUTAQUES[i]);
							} else
								i++;
						} while (!eixir && i != NOM_BUTAQUES.length);

						// HE RECORREGUT PERÒ NO HE TROBAT LA CADENA. ERROR!
						if ((i == NOM_BUTAQUES.length) && (!eixir))
							dosEixida.writeUTF("Cadena no reconeguda pel servidor");
					}
				} while (!cadena.equals("Fi"));

				// TANQUE CONNEXIÓ
				System.out.println("Tancant connexió...");
				sCliente.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] arg) {
		new T3P5Servidor();
	}
}
