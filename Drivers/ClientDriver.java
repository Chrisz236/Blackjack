
import java.io.IOException;
import java.util.Scanner;

import client.Client;

class ClientDriver {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		System.out.println("Enter ip address: ");
		Scanner input = new Scanner(System.in);
		
		String ip = input.nextLine();
		ip = ip.trim();
		// inputs its own IP address when creating client to connect multiple clients
		Client client = new Client(ip);
		client.run();
		input.close();
	}
}