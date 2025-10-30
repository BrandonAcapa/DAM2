import java.io.*;
public class Processos{
public static void main(String[] args){
	Runtime r = Runtime.getRuntime();
	String comando = "tasklist /v";
	Process p = null;

	if (args.length < 1){
	
	}

	try{
		p=r.exec(comando);
		InputStream is = p.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader (is));
		String linea;
		while ((linea = br.readLine()) != null)
			System.out.println(linea);
		br.close();
	} catch (Exception e) {
		System.out.println("Error en " + comando);
		e.printStackTrace();
	}

	int exitVal;
	try{
		exitVal = p.waitFor();
		System.out.println("Valor de salida" + exitVal);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
}