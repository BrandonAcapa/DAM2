package T4S1P1;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

public class Cliente {

    private Socket socket = null;
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private boolean estaConnectat = false;

    public Cliente() {
    }

    public void communicate() {
        while (!estaConnectat) {
            try {
                // CONNECTE
                socket = new Socket("localhost", 4445);
                System.out.println("Connectat");
                estaConnectat = true;

                // CREA EL FLUX D'EIXIDA
                outputStream = new ObjectOutputStream(socket.getOutputStream());

                // CARREGUE DADES EN OBJECTE
                Persona persona = new Persona("Pepe", 25);
                System.out.println("Objecte a enviar: " + persona.getNom() + " - " + persona.getEdat());

                // NETEGE BUFFER
                outputStream.flush();

                // ESCRIU OBJECTE EN FLUX D'EIXIDA
                outputStream.writeObject(persona);
                System.out.println("Enviat.");

                // ESCRIU OBJECTE MODIFICAT
                inputStream = new ObjectInputStream(socket.getInputStream());
                Persona personaModificada = (Persona) inputStream.readObject();
                System.out.println("Objecte modificat rebut del servidor: " + personaModificada.getNom() + " - " + personaModificada.getEdat());

                // TANQUE STREAMS I SOCKET
                outputStream.close();
                socket.close();

            } catch (SocketException se) {
                se.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException cn) {
                cn.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Cliente client = new Cliente();
        client.communicate();
    }
}