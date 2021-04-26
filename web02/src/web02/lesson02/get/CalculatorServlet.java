package web02.lesson02.get;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Hashtable<String, Operator> operatorTable = new Hashtable<String, Operator>();

	public CalculatorServlet() {
		// ������ ó���⸦ ���
		operatorTable.put("+", new PlusOperator());
		operatorTable.put("-", new MinusOperator());
		operatorTable.put("*", new MultipleOperator());
		operatorTable.put("/", new DivideOperator());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Ŭ���̾�Ʈ���� ���� ���� ������.
		String op = request.getParameter("op");
		double v1 = Double.parseDouble(request.getParameter("v1"));
		double v2 = Double.parseDouble(request.getParameter("v2"));

		// Ŭ���̾�Ʈ�� ����ϱ� ���� �غ��Ѵ�.
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html><body>");
		out.println("<h1>��� ���</h1>");
		out.println("���: ");

		// ������ ������ �� ����� Ŭ���̾�Ʈ�� ����Ѵ�.
		try {
			Operator operator = operatorTable.get(op);
			if (operator == null) {
				out.println("�������� �ʴ� ������ �Դϴ�.");
			} else {
				double result = operator.execute(v1, v2);
				out.println(String.format("%.3f", result));
			}
		} catch (Exception e) {
			out.println("���� ������ �߻��Ͽ����ϴ�.");
		}

		out.println("</body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
