package server;

import java.io.*;
import java.net.*;

public class DummyClientSender {
    public static String host = "localhost";
    public static int port = 7777;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(host, port);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

            oos.writeObject(new Message("HAOLIN ZHANG, hZhang", Type.Login));
            Message LoginResult = (Message) ois.readObject();

            if (LoginResult.getType() == Type.Succeed) {
                System.out.println("Login Succeed");
                System.out.println("Role: " + LoginResult.getData());

                oos.writeObject(new Message(Type.ViewPlayerInfo));
                Message msg = (Message) ois.readObject();
                System.out.println("Message from getUserInfo():\n" + msg.getData());

                oos.writeObject(new Message("Lobby 1", Type.CreateLobby));

                oos.writeObject(new Message("Lobby 1", Type.JoinLobby));
                Thread.sleep(10000);

                // ------------------ GAME COMMAND, BROADCAST ------------------- //
                oos.writeObject(new Message("HAOLIN ZHANG", Type.StartGame));
                Thread.sleep(1000);

                oos.writeObject(new Message("HAOLIN ZHANG, CLUB ONE", Type.Hit));
                Thread.sleep(1000);

                oos.writeObject(new Message("HAOLIN ZHANG, 2000", Type.Bet));
                Thread.sleep(1000);
                // ------------------ GAME COMMAND, BROADCAST ------------------- //

                oos.writeObject(new Message(Type.Exit));

                oos.writeObject(new Message(Type.Logout));
            } else {
                System.out.println("Login Failed");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
