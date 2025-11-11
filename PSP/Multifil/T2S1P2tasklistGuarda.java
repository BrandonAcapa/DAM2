import java.io.*;

public class T2S1P2tasklistGuarda {

	public static void main(String[] args) {
		Runtime r = Runtime.getRuntime(); // objecte runtime
		//String comando = "ps -ef"; //Linux. Comando a executar i params
		String comando = "cmd /c T2S1P2tasklist /v"; //Windows. Comando a executar i params
		Process p = null; //inicialitze vble process

		//control params entrada
		if (args.length < 1) {
			System.out.println("Cal un nom de fitxer...");
			System.exit(1);
		} //fi control paràmetres

		try {
			//fitxer al que s'enviarà l'eixida del programa Unasalutacio
			FileOutputStream fos = new FileOutputStream(args[0]);
			PrintWriter pw = new PrintWriter(fos);

			//execute comando
			p = r.exec(comando);

			//cree buffer
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String linia;
			//llegisc línia a línia i imprimisc en el fitxer
			while ((linia = br.readLine()) != null) //llegisc una línia
			{
				System.out.println("Inserte en " + args[0] + " > " + linia);
				pw.println(linia); //la inserisc en el fitxer
			} //fi bucle línies
			br.close();
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			InputStream er = p.getErrorStream();
			BufferedReader brer = new BufferedReader(new InputStreamReader(er));
			String liner = null;
			while ((liner = brer.readLine()) != null)
				System.out.println("ERROR >" + liner);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		//comprobació d'error: 0 bien - 1 malament
		int exitVal;
		try {
			exitVal = p.waitFor();
			System.out.println("Valor d'eixida " + exitVal);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}// fi main
}// T2S1P2tasklistGuarda
