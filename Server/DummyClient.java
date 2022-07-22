import java.io.*;
import java.net.*;

public class DummyClient {
    public static String host = "localhost";
    public static int port = 7777;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(host, port);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            try {
                // Example of Login
                oos.writeObject(new Message("HAOLIN ZHANG, hZhang", Type.Login));

                // Example of GetPlayerInfo
                oos.writeObject(new Message(Type.GetPlayerInfo));
                Message msg = (Message) ois.readObject();
                System.out.println("Message from getUserInfo():\n" + msg.getData());

                // Example of ReloadBalance
                oos.writeObject(new Message("1320", Type.ReloadBalance));

                // Example of GetLobbyManagerInfo
                oos.writeObject(new Message(Type.GetLobbyManagerInfo));

                // Example of CreateLobby
                oos.writeObject(new Message("First lobby", Type.CreateLobby));

                // Example of DeleteLobby
                oos.writeObject(new Message("Second lobby", Type.DeleteLobby));

                // Example of Logout
                oos.writeObject(new Message(Type.Logout));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
