package lesson01.exam02.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CalculatorAgent {
	Socket socket = null;
	PrintStream out = null;
	Scanner in = null;
	
	// 소켓을 통해 입출력할 수 있도록 스트림 객체를 준비한다.
	public CalculatorAgent(String ip, int port) throws Exception {
		socket = new Socket(ip ,port);
		out = new PrintStream(socket.getOutputStream());
		in = new Scanner(socket.getInputStream());
	}
	

	public double compute(String operator, double a, double b) throws Exception {
		// 사용자가 입력한 연산자와 두 개의 입력값을 서버에 전달한다.
		
		
		try {
			out.println(operator);
			out.println(a);
			out.println(b);
			out.flush();
			// 서버로 부터 계산 결과를 받으면 먼저 상태를 조사한다.
			// 만약 상태가 "success"이면 정상적으로 처리되었다는 것이므로
			String state = in.nextLine();
			if (state.equals("success")) {
				return Double.parseDouble(in.nextLine());
				// 다음 라인을 읽어서 서버가 보낸 값을 double 형으로 변환하여 리턴한다. 
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
