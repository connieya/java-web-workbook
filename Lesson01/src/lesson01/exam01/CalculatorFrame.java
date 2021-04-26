package lesson01.exam01;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// ������ ����� ���� ���� JFrame�� ��� ���� 
public class CalculatorFrame extends JFrame implements ActionListener {
	// '=' ��ư �� 'clear' ��ư�� Ŭ�� �̺�Ʈ�� ó���ϴ� ������ ���� ActionListner
	// �������̽� ����

	JTextField operand1 = new JTextField(4);
	JTextField operand2 = new JTextField(4);

	String[] opeartorData = { "+", "-", "*", "/" };
	JComboBox<String> operator = new JComboBox<>(opeartorData);
	JButton equal = new JButton("=");
	JTextField result = new JTextField(6);
	JButton clear = new JButton("Clear");

	public CalculatorFrame() {
		this.setTitle("Lesson01 - Exam01");

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		contentPane.add(Box.createVerticalGlue());
		contentPane.add(this.createInputForm());
		contentPane.add(this.createToolBar());
		contentPane.add(Box.createVerticalGlue());

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);

	}



	@Override // ��ư�� Ŭ�� ���� �� ȣ�� �ȴ�.
	public void actionPerformed(ActionEvent event) {
		// ���� ���� � ��ư�� Ŭ�� �ƴ��� �����ϱ� ���� evnet ������ getSourece ��ȯ���� �˻��Ѵ�.
		if (event.getSource() == equal) {
			compute(); // '=' ��ư Ŭ�� �� ȣ�� �Ǵ� �޼���
		} else {
			clearForm(); // �� �ۿ��� clear ��ư �� ȣ��Ǵ� �޼���
		}

	}

	private void compute() {
		double a = Double.parseDouble(operand1.getText());
		double b = Double.parseDouble(operand2.getText());
		double r = 0;

		try {
			switch (operator.getSelectedItem().toString()) {
			case "+":
				r = a + b;
				break;
			case "-":
				r = a - b;
				break;
			case "*":
				r = a * b;
				break;
			case "/":
				if (b == 0)
					throw new Exception("0���� ���� �� �����ϴ�.");
				r = a / b; 
				break;
			}
			result.setText(Double.toString(r));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void clearForm() {
		this.operand1.setText("");
		this.operand2.setText("");
		this.result.setText("");
	}

	private Box createInputForm() {
		Box box = Box.createHorizontalBox();
		box.setMaximumSize(new Dimension(300, 30));
		box.setAlignmentY(Box.CENTER_ALIGNMENT);
		box.add(operand1);
		box.add(operator);
		box.add(operand2);
		box.add(equal);
		box.add(result);
		equal.addActionListener(this);
		return box;
	}

	private Box createToolBar() {
		Box box = Box.createHorizontalBox();
		box.add(clear);
		clear.addActionListener(this);
		return box;
	}
	
	public static void main(String[] args) {
		CalculatorFrame app = new CalculatorFrame();
		app.setVisible(true);

	}

}