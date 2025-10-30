public class TicFil extends Thread{
public void run(){
	try{
		while(1>0){
			Thread.sleep(1000);
			System.out.println("Tic");
		}
	} catch (InterruptedException e){
		System.out.println("El hilo fue interrumpido");
		Thread.currentThread().interrupt();
	}
}
}