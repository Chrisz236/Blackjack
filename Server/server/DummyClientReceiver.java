package server;

import java.io.*;
import java.net.*;

public class DummyClientReceiver {
    public static String host = "localhost";
    public static int port = 7777;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(host, port);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);

                oos.writeObject(new Message("ALICE BOB, aBob", Type.Login));

                Message LoginResult = (Message) ois.readObject();

                if (LoginResult.getType() == Type.Succeed) {
                    System.out.println("Login Succeed");

                    System.out.println("Role: " + LoginResult.getData());
                    oos.writeObject(new Message("Lobby 1", Type.JoinLobby));

                    Thread.sleep(5000);

                    for (int i = 0 ; i < 20 ; i++) {
                        Message message = (Message) ois.readObject();
                        System.out.println("Data: " + message.getData());
                        System.out.println("Type: " + message.getType());
                        System.out.println();
                        Thread.sleep(1000);
                    }
                } else {
                    System.out.println("Login Failed");
                }
                oos.writeObject(new Message(Type.Logout));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
