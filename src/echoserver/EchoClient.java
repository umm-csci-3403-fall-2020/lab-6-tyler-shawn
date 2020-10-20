package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {

		EchoClient client = new EchoClient();
		client.start();
	}
         
	private void start() throws IOException {
		//Create socket
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();

		//create the client output thread and start it
		WriteToServer write = new WriteToServer(output, socket);
		Thread outputThread = new Thread(write);
		outputThread.start();

		//create the client input thread and start it
		ReadFromServer read = new ReadFromServer(input, socket);
		Thread inputThread = new Thread(read);
		inputThread.start();
	}

	public class ReadFromServer implements Runnable {
		InputStream input;
		int read;
		Socket sock;

		//Constructor
		public ReadFromServer(InputStream input, Socket sock){
			this.input = input;
			this.sock = sock;
		}

		//Run method is where everything in the thread happens
		@Override
		public void run(){
			try { // Keep reading until the user stops sending data
				while ((read = input.read()) != -1){
					System.out.write(read);
					System.out.flush();
				}
				sock.shutdownInput(); //Close connection to input

			}
			//Error Handling
			catch (IOException ioe){
				System.out.println("Caught an unexpected exception");
			}
		}
	}
	
	public class WriteToServer implements Runnable {
		OutputStream output;
		int send;
		Socket sock;

		//Constructor
		public WriteToServer(OutputStream output, Socket sock) throws IOException {
			this.output = output;
			this.sock = sock;
		}

		//Run method is where everything in the thread happens
		@Override
		public void run(){
			try {
				// Keep reading until the user stops sending data
				while ((send = System.in.read()) != -1){ 
					output.write(send);
					output.flush();
				}
				sock.shutdownOutput(); // Close connection to the output
			}
			//Error Handling
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception");
			}
		}
	}
}
