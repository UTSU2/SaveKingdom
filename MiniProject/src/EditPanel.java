import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditPanel extends JPanel{
	private ImageIcon icon = new ImageIcon("castle.jpg"); //배경 이미지
	private Image img = icon.getImage();
	private JTextField edit = new JTextField(20); //단어 입력 텍스트 필드
	private JButton addButton = new JButton("add"); //단어 추가 버튼
	private TextSource textSource = new TextSource(); // 텍스트 파일에서 읽어오거나 추가하기 위해 선언
	
	public EditPanel() {
		this.setBackground(Color.CYAN); //배경 : 바꿀 예정
		setLayout(null); //절대 위치 배치위해서 선언
		edit.setLocation(200,200); //사이즈와 위치 설정 후 각각 추가
		edit.setSize(400,30);
		add(edit);
		addButton.setLocation(350,250); //버튼 위치와 사이즈 설정
		addButton.setSize(100,30);
		addButton.setFont(new Font("Serif",Font.BOLD,30)); //폰트 설정
		addButton.setForeground(Color.CYAN); //글씨 색깔 설정
		addButton.setBorderPainted(false); //버튼 테두리 지우기
		addButton.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
		addButton.setFocusPainted(false); //내부 테두리 지우기
		add(addButton);
		
		addButton.addActionListener(new AddListener()); //단어 추가용 이벤트 리스너
		addButton.addMouseListener(new mouseListener()); //버튼 이벤트용 마우스 리스너
	}
	public class AddListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			textSource = new TextSource(); //추가할 때마다 새로 불러서 바로 추가되게 함
			String addWord = edit.getText(); // 텍스트 필드에서 단어 읽어옴
			textSource.InputText(addWord); //단어장에 단어 추가
		}
	}
	public class mouseListener extends MouseAdapter {
		public void mouseEntered(MouseEvent e) { //add 버튼에 마우스가 올라가면 글씨 크기를 키워 눈에 잘 보이게 함
			JButton btn = (JButton)(e.getSource());
			btn.setFont(new Font("Serif",Font.BOLD,35)); //폰트 크기 키우기
		}
		public void mouseExited(MouseEvent e) { //add 버튼에서 마우스가 내려가면 글씨 크기를 원래대로 돌려 현재 지정되지 않았음을 보이게 함
			JButton btn = (JButton)(e.getSource());
			btn.setFont(new Font("Serif",Font.BOLD,30)); //폰트 크기 원래대로 바꾸기
		}
	}
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),null); //패널의 배경 바꾸기 위해 paintComponent 사용
	}
}
