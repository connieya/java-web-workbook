package lesson01.exam02.multiserver;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

// ������μ� ����� �����ϱ� ���� Thread�� ��� �޾Ҵ�.
public class CalculatorWorker extends Thread {
	static int count;
	
	Socket socket;
	Scanner in;
	PrintStream out;
	int workerId;
	
	public CalculatorWorker(Socket socket) throws Exception{
		// Ŭ���̾�Ʈ�� ������� �ϱ� ���� ��Ʈ�� ��ü
		workerId = ++count; // ������ �����带 �����ϱ� ����
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
						if (b == 0) throw new Exception("0 ���� ���� �� �����ϴ�!");
						r = a / b; 
						break;
					default:
						throw new Exception("�ش� ������ �������� �ʽ��ϴ�!");
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