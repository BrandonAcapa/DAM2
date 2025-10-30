import java.io.*;
public class Exemple2{
public static void main(String[] args) {
	Runtime r = Runtime.getRuntime();
//	String comando = "ls";
	String comando = "cmd /c dirñ";
	Process p=null;

	try{
		p = r.exec(comando);
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String linea;
		while((linea = br.readLine()) != null)
			System.out.println(linea);
		br.close();
	} catch (Exception e){
		System.out.println("Error en " + comando);
		e.printStackTrace();
	}

	// Comprbación de error: 0 bien, 1 mal
	// int exitVal;
	// try{
	// 	exitVal = p.waitFor();
	// 	System.out.println("Valor de salida: " + exitVal);
	// } catch (InterruptedException e){
	// 	e.printStackTrace();
	// }
	try{
		InputStream er = p.getErrorStream();
		BufferedReader brer = new BufferedReader(new InputStreamReader(er));
		String liner = null;
		while((liner = brer.readLine()) != null)
			System.out.println("Error: " + liner);
		brer.close();
	} catch (IOException ioe){
		ioe.printStackTrace();
	}
}
}