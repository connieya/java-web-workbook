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
		// main() 에서 설정한 포트 번호로 서버 소켓을 생성한다.
		
		System.out.println("CalculatorServer strartup: ");
		Socket socket = null;
		
		while(true) {
			try {

				System.out.println("waiting client...");
				
				socket = serverSocket.accept();
				// 클라이언트의 연결을 기다리다가 연결이 이루어 지면 클라이언트의 요청을 처리한다.
				// 이것을 무한 반복한다.
				System.out.println("connected to client.");
				
				processRequest(socket); // 클라이언트의 요청을 처리
				System.out.println("closed client.");
			} catch (Exception e) {
				System.out.println("connection error!");
			}
		}
		
		
		
	}


	private void processRequest(Socket socket) throws IOException {
		// 클라이언트 소켓으로부터 입출력을 위한 스트림 객체를 준비한다. 
		
		Scanner in = new Scanner(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		String operator = null;
		double a, b, r;
		
		while(true) {
			try {
				// 무한 반복하면서 클라이언트가 보낸 연산자와 값을 읽는다. 
				operator = in.nextLine();
				
				// 클라이언트가 보낸 연산자와 값을 읽어 계산을 수행하는데..
				// 만약 클라이언트로부터 "goodbye" 메세지를 받으면 연결을 종료한다.
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
						if (b == 0) throw new Exception("0 으로 나눌 수 없습니다!");
						r = a / b; 
						break;
					default:
						throw new Exception("해당 연산을 지원하지 않습니다!");
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
		// 통신 포드 번호를 8888로 설정하고 계산기 서비스를 실행한다.
	}
}
