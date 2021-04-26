package lesson01.exam02.multiserver;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

// 스레드로서 기능을 수행하기 위해 Thread를 상속 받았다.
public class CalculatorWorker extends Thread {
	static int count;
	
	Socket socket;
	Scanner in;
	PrintStream out;
	int workerId;
	
	public CalculatorWorker(Socket socket) throws Exception{
		// 클라이언트와 입출력을 하기 위한 스트림 객체
		workerId = ++count; // 생성된 스레드를 구분하기 위함
		this.socket = socket;
		in = new Scanner(socket.getInputStream());
		out = new PrintStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		System.out.println("[thread = "+ workerId + "] processing the client reqeuest.");
		
		String operator = null;
		double a , b, r;
		
		while(true) {
			try {
				operator = in.nextLine();
				
				if(operator.equals("goodbye")) {
					out.print("goodbye");
					break;
					
				}else {
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
			} catch (Exception e) {
				out.println("failure");
				out.println(e.getMessage());
			}
		}
		
		try { out.close(); } catch (Exception e) {}
		try { in.close(); } catch (Exception e) {}
		try { socket.close(); } catch (Exception e) {}
		
		System.out.println("[thread-" + workerId + "] closed client.");
	
	}
	

}
