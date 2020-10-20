package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static final int PORT_NUMBER = 6013;
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

		//While the server is running
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				//Create the client thread with parameters sock, in, out
				ClientThread client = new ClientThread(socket, in, out);
				//Create new thread so that we can start it and run
				Thread thread = new Thread(client);
				thread.start();


		}


	}
	//What we do inside the thread
	final class ClientThread implements Runnable{
		InputStream in;
		OutputStream out;
		Socket sock;

		//Constructor
		public ClientThread(Socket sock, InputStream in, OutputStream out){
			this.sock = sock;
			this.in = in;
			this.out = out;
		}

		//Run method is where everything in the thread happens
		@Override
		public void run() {

			int line;
			//Reading from the input stream
			//while (true) {
				try {
					while(((line = in.read()) != -1)) {
						//Outputting back to the socket to return to the client
						out.write(line);
						out.flush();
					}
					sock.shutdownOutput();
					sock.shutdownInput();

				} catch (IOException e) {
					e.printStackTrace();
				}



		}
	}

}