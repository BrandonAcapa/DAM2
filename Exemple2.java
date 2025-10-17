import java.io.*;
public class Exemple2{
public static void main(String[] args) {
	Runtime = r.Runtime.getRuntime();
//	String comando = "ls";
	String comando = "cmd /c dir";
	Process p=null;

	try{
		p = r.exec(comando);
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String linea;
		while ((linea=br.readLine()) != null)
			System.out.Println(linea);
		br.close;
	}
}
}