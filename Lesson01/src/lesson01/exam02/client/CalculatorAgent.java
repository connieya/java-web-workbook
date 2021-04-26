package lesson01.exam02.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorAgent {
	Socket socket = null;
	PrintStream out = null;
	Scanner in = null;
	
	// ������ ���� ������� �� �ֵ��� ��Ʈ�� ��ü�� �غ��Ѵ�.
	public CalculatorAgent(String ip, int port) throws Exception {
		socket = new Socket(ip ,port);
		out = new PrintStream(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
	}
	

	public double compute(String operator, double a, double b) throws Exception {
		// ����ڰ� �Է��� �����ڿ� �� ���� �Է°��� ������ �����Ѵ�.
		
		
		try {
			out.println(operator);
			out.println(a);
			out.println(b);
			out.flush();
			// ������ ���� ��� ����� ������ ���� ���¸� �����Ѵ�.
			// ���� ���°� "success"�̸� ���������� ó���Ǿ��ٴ� ���̹Ƿ�
			String state = in.nextLine();
			if (state.equals("success")) {
				return Double.parseDouble(in.nextLine());
				// ���� ������ �о ������ ���� ���� double ������ ��ȯ�Ͽ� �����Ѵ�. 
			} else {
				throw new Exception(in.nextLine());
			}
		} catch (Exception e) {
			throw e;
		} 
	}
	
	public void close() {
		try {
			out.println("goodbye");
			System.out.println(in.nextLine());
		} catch (Exception e) {}
		
		try {out.close();} catch(Exception e) {}
		try {in.close();} catch(Exception e) {}
		try {socket.close();} catch(Exception e) {}
	}

}
