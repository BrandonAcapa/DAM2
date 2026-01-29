package T4S2P3QuizMagic;
import java.io.*;
import java.net.*;

public class FilServidor extends Thread {
	BufferedReader fentrada;
	PrintWriter feixida;
	Socket socket = null;

	// constructor FilServidor
	public FilServidor(Socket s) throws IOException {
		System.out.println("Cree fil servidor");
		socket = s;
		// CREE FLUXOS D'ENTRADA I EIXIDA
		feixida = new PrintWriter(socket.getOutputStream(), true);
		fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	// LÒGICA DEL SERVIDOR. ATENC EL CLIENT
	public void run() {
		String nom = "";
		System.out.println("En fil: comunique amb: " + socket.toString());

		// LLIG L'ALIAS O MALNOM
		try {
			nom = fentrada.readLine();
			System.out.println("En fil: llig nom: " + nom);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// OBRIC EL FITXER DE PREGUNTES I RESPOSTES
		File file = new File("PreguntesRespostes.txt");
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			System.out.println("No se encontro el archivo " + file.getName());
		}

		try {
			String pregunta, respostafitxer, respostaclient;
			int puntuacio = 0;

			// PER CADA PREGUNTA QUE TROBE EN EL FITXER
			while ((pregunta = br.readLine()) != null) {
				// ENVIE LA LÍNIA AL CLIENT
				feixida.println(pregunta);
				// REP LA RESPOSTA DEL CLIENT
				respostaclient = fentrada.readLine();
				// LLIG LA RESPOSTA DEL FITXER
				respostafitxer = br.readLine();
				// COMPARE. SI ÉS IGUAL SUME 1 A LA PUNTUACIÓ
				if (respostafitxer.equals(respostaclient))
					puntuacio++;
			}

			// ENVIE LA PUNTUACIÓ
			feixida.println("Puntuació: " + puntuacio);

			// FIQUE LA PUNTUACIÓ AL FITXER
			File fPuntuacion = new File("Puntuacions.txt");
			BufferedWriter bw = null;
			if (fPuntuacion.exists())
				bw = new BufferedWriter(new FileWriter(fPuntuacion, true)); // MODE append
			else
				bw = new BufferedWriter(new FileWriter(fPuntuacion));
			bw.write(nom + " " + puntuacio + "\n");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("FI AMB " + socket.toString());

		// TANQUE FLUXES I SOCKET
		feixida.close();
		try {
			fentrada.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}