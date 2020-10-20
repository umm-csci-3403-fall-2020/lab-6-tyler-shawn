package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}
         
	private void start() throws IOException {
		Socket socket = new Socket(PORT_NUMBER):
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();
                
		WriteToServer toServer = new WriteToServer(output, socket);
		Thread outputThread = new Thread(toServer);
		outputThread.start();

		ReadFromServer response = new ReadFromServer(input, socket);
		Thread inputThread = new Thread(response);
		inputThread.start();
	}

	public class ReadFromServer implements Runnable {
		InputStream input
		int recieve;
		Socket sock;

		public ReadFromServer(InputStream input, Socket sock){
			this.input = input;
			this.sock = sock;
		}

		@Override
		public void run(){
			try { // Keep reading until the user stops sending data
				while ((receive = input.read()) != -1){
					System.out.write(receive);
					System.out.flush();
				}
				sock.shutdownInput(); //Close connection

			}
			catch (IOException ioe){
				System.out.println("Caught an unexpected exception");
			}
		}
	}
	
	public class WriteToServer implements Runnable {
		OutputStream output;
		int send;
		Socket sock;

		public WriteToServer(OutputStream output, Socket sock) throws IOException {
			this.output = output
			this.sock = sock;
		}

		@Override
		public void run(){
			try {	// Keep reading until the user stops sending data
				while ((send = System.in.read()) != -1){ 
					output.write(send);
					output.flush();
				}
				sock.shutdownOutput(); // Close connection 
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception");
			}
		}
	}
}
