import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class EditFrame extends JFrame{
	private ImageIcon titleicon = new ImageIcon("castletitle.png"); //타이틀바 이미지
	private Image barimg = titleicon.getImage();
	private EditPanel editPanel = new EditPanel(); // 단어장 추가 패널
	private ImageIcon tmptitleBtnicon = new ImageIcon("back.png");
	private Image titleBtnimg = tmptitleBtnicon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private ImageIcon titleBtnicon = new ImageIcon(titleBtnimg);
	private JButton titleBtn = new JButton(titleBtnicon); //타이틀(메인화면)로 이동하는 버튼
	public EditFrame(int x,int y) {
		setTitle("왕국을 지켜라"); //제목 설정
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600); //사이즈 설정
		this.setLocation(x,y); //전 프레임이 위치했던 좌표 불러와서 그 위치에 생성
		
		setLayout(new BorderLayout());
		add(editPanel, BorderLayout.CENTER); //단어장 추가 패널 추가
		add(new titlePanel(), BorderLayout.SOUTH); //타이틀 버튼 패널 추가
		
		this.setResizable(false); //사이즈 변하지 못하게 함
		setVisible(true); //보여지게 함
	}
	
	
	
	private class titlePanel extends JPanel {
		public titlePanel() {
			setLayout(new FlowLayout(FlowLayout.LEFT)); //왼쪽 정렬로 버튼 왼쪽에 붙임
			titleBtn.addActionListener(new titleListener()); //이벤트 리스너 추가
			this.setBackground(new Color(30,52,75)); //배경 색깔 설정
			titleBtn.setBorderPainted(false); //버튼 테두리 지우기
			titleBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			titleBtn.setFocusPainted(false); //내부 테두리 지우기
			add(titleBtn); //타이틀 버튼 추가
		}
		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);
			g.drawImage(barimg, 0,0,this.getWidth(),this.getHeight(),null); //배경 그리기
		}
	}
	
	public class titleListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) { //x, y좌표 알아내서 그 위치에 불러내기
			int x = EditFrame.this.getX();  //좌표 가져오기
			int y = EditFrame.this.getY();
			new MainFrame(x,y); //메인 화면 프레임 부르기
			setVisible(false); //전에 있던 프레임 보이지 않게 하기
		}
	}
}
