package lesson01.exam02.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorServer {
	
	private int port;

	public CalculatorServer(int port) {
		this.port = port;
	}
	
	
	@SuppressWarnings("resource")
	public void service() throws Exception{
		ServerSocket serverSocket = new ServerSocket(port);
		// main() ���� ������ ��Ʈ ��ȣ�� ���� ������ �����Ѵ�.
		
		System.out.println("CalculatorServer strartup: ");
		Socket socket = null;
		
		while(true) {
			try {

				System.out.println("waiting client...");
				
				socket = serverSocket.accept();
				// Ŭ���̾�Ʈ�� ������ ��ٸ��ٰ� ������ �̷�� ���� Ŭ���̾�Ʈ�� ��û�� ó���Ѵ�.
				// �̰��� ���� �ݺ��Ѵ�.
				System.out.println("connected to client.");
				
				processRequest(socket); // Ŭ���̾�Ʈ�� ��û�� ó��
				System.out.println("closed client.");
			} catch (Exception e) {
				System.out.println("connection error!");
			}
		}
		
		
		
	}


	private void processRequest(Socket socket) throws IOException {
		// Ŭ���̾�Ʈ �������κ��� ������� ���� ��Ʈ�� ��ü�� �غ��Ѵ�. 
		
		Scanner in = new Scanner(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		String operator = null;
		double a, b, r;
		
		while(true) {
			try {
				// ���� �ݺ��ϸ鼭 Ŭ���̾�Ʈ�� ���� �����ڿ� ���� �д´�. 
				operator = in.nextLine();
				
				// Ŭ���̾�Ʈ�� ���� �����ڿ� ���� �о� ����� �����ϴµ�..
				// ���� Ŭ���̾�Ʈ�κ��� "goodbye" �޼����� ������ ������ �����Ѵ�.
				if (operator.equals("goodbye")) {
					out.println("goodbye");
					break;
					
				} else {
					a = Double.parseDouble(in.nextLine());
					b = Double.parseDouble(in.nextLine());
					r = 0;
				
					switch (operator) {
					case "+": r = a + b; break;
					case "-": r = a - b; break;
					case "*": r = a * b; break;
					case "/": 
						if (b == 0) throw new Exception("0 ���� ���� �� �����ϴ�!");
						r = a / b; 
						break;
					default:
						throw new Exception("�ش� ������ �������� �ʽ��ϴ�!");
					}
					out.println("success");
					out.println(r);
				}
				
			} catch (Exception err) {
				out.println("failure");
				out.println(err.getMessage());
			}
		}
		try {out.close();} catch(Exception e) {}
		try {in.close();} catch(Exception e) {}
		try {socket.close();} catch(Exception e) {}
	}
	
	
	public static void main(String[] args) throws Exception {
		CalculatorServer app = new CalculatorServer(8888);
		app.service();
		// ��� ���� ��ȣ�� 8888�� �����ϰ� ���� ���񽺸� �����Ѵ�.
	}
}
