import java.io.*;

public class T2S1P1QuasibashQuispe {

	public static void main(String[] args) {
		Runtime r = Runtime.getRuntime();
		String comando = "ipconfig";
		Process p = null;

		try {
			p = r.exec(comando);
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String linea;
			while ((linea = br.readLine()) != null)
				System.out.println(linea);
			br.close();
		} catch (Exception e){
			System.out.println("Error al ejecutar el comando: " + comando);
			e.printStackTrace();
		}
	}
}
