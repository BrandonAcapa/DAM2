import java.io.*;
public class TicTac{
public static void main(String[] args){
	Runtime r = Runtime.getRuntime();
	String comando1 = "java TicFil";
	String comando2 = "java TacFil";
	Process p = null;

	p=r.exec(comando1);
	p=r.exec(comando2);
}
}