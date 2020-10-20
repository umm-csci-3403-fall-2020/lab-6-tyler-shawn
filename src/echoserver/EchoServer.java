package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013;
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);


		while (true) {
			Socket socket = serverSocket.accept();
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			ClientThread client = new ClientThread(in, out);
			Thread thread = new Thread(client);
			thread.start();
			// Put your code here.
			// This should do very little, essentially:
			//   * Construct an instance of your runnable class
			//   * Construct a Thread with your runnable
			//      * Or use a thread pool
			//   * Start that thread
			socket.close();

		}

	}
	final class ClientThread implements Runnable{
		InputStream in;
		OutputStream out;


		public ClientThread(InputStream in, OutputStream out){
			this.in = in;
			this.out = out;

		}

		@Override
		public void run() {


			int line;
			//Reading from the input stream
			while (true) {
				try {
					if (((line = in.read()) != -1)) {
						//Outputting back to the socket to return to the client
						out.write(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}


			}

		}
	}
}