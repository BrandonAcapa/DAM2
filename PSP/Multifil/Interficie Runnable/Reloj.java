import java.awt.*;
import java.applet.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Reloj extends Applet implements Runnable {

	//atributos
	private Thread fil = null;
	private Font font;
	private String horaActual = "";

	public void init(){
		font = new Font("Verdana", Font.BOLD, 26);	
	}

	public void start(){
		if(fil == null){
			fil = new Thread(this);
			fil.start();
		}
	}

	public void run(){
		Thread filActual = Thread.currentThread();
		while(fil == filActual){
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			horaActual = sdf.format(cal.getTime());

			repaint();

			try{
				Thread.sleep(1000);
			} catch(InterruptedException e){}
		}
	}

	public void paint(Graphics g){
		g.clearRect(1, 1, getSize().width, getSize().height);
		setBackground(Color.white);
		g.setFont(font);
		g.drawString(horaActual,20,50);
	}
}