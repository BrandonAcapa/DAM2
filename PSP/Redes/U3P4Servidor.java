import java.net.*;
import java.io.*;

public class U3P4Servidor {
    public static void main(String[] args) {
        DatagramSocket dSocket = null;

        if (args.length < 1){
            System.out.println("Uso: java U3P4Servidor <puerto>");
            System.exit(1);
        }
        try{
            int socket_no = Integer.valueOf(args[0]).intValue();
            dSocket = new DatagramSocket(socket_no);
            byte[] missatgeRebut = new byte[1000];

            while (true) {
                DatagramPacket dpRebut = new DatagramPacket(missatgeRebut, missatgeRebut.length);
                dSocket.receive(dpRebut);
                System.out.println("Rep del client: " + new String(dpRebut.getData()));

                DatagramPacket dpResposta = new DatagramPacket(dpRebut.getData(), dpRebut.getLength(), dpRebut.getAddress(), dpRebut.getPort());
                dSocket.send(dpResposta);
                
            }
        }
        catch (SocketException e) {
            System.out.println("Error de socket: " + e.getMessage());
        } 
        catch (IOException e) {
            System.out.println("Error IO: " + e.getMessage());
        } 
        finally {
            if (dSocket != null && !dSocket.isClosed()) {
                dSocket.close();
            }
        }
    }
}