package lesson01.exam02.multiserver;

import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {
private int port;
	
	public CalculatorServer(int port) {
		this.port = port;
	}
	
	public void service() throws Exception {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("CalculatorServer startup:");
		
		Socket socket = null;
		
		while(true) {
			try {
				socket = serverSocket.accept();
				System.out.println("connected to client.");
				
				// 이전에는 ClientServer가 클라이언트와 연결이 이루어지면
				// 직접 통신을 수행하여 요청을 처리했는데, 지금은 연결이 이루어지는 즉시
				// CalculatorWorker 스레드를 생성해서 작업을 위임한다. 
				// 그리고 다시, 대기열에 있는 다른 클라이언트와 연결을 승인한다. 
				new CalculatorWorker(socket).start();
				
			} catch (Throwable e) {
				System.out.println("connection error!");
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		CalculatorServer app = new CalculatorServer(8888);
		app.service();
	}

}
