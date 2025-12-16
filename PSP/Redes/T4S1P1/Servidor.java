package T4S1P1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Servidor {

    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream inStream = null;
    private ObjectOutputStream outStream = null;

    public void comunica() {
        try {
            // ServerSocket I EN ESPERA DE REBRE UNA CONNECIO
            serverSocket = new ServerSocket(4445);
            System.out.println("Servidor en espera de connexió");
            socket = serverSocket.accept();
            System.out.println("Connectat");

            // FLUX D'ENTRADA PER A OBJECTES
            inStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Rebut");

            // REP L'OBJECTE
            Persona persona = (Persona) inStream.readObject();
            System.out.println("Objecte rebut: " + persona.getNom() + " - " + persona.getEdat());

            // MODIFICA L'OBJECTE
            persona.setNom(persona.getNom() + " he estat al servidor");
            System.out.println("Objecte modificat: " + persona.getNom() + " - " + persona.getEdat());

            // FLUX D'EIXIDA PER A OBJECTES
            outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(persona);
            outStream.flush();
            System.out.println("Enviat");

            // TANQUE STREAMS I SOCKET
            inStream.close();
            socket.close();

        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.comunica();
    }
}