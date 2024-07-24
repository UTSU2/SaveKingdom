import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	private ImageIcon titleImg = new ImageIcon("title.png"); //타이틀 제목 이미지
	private ImageIcon startImg = new ImageIcon("startbtn.png"); //시작 버튼 이미지
	private ImageIcon prologueImg = new ImageIcon("prologuebtn.png"); //설명 버튼 이미지
	private ImageIcon gradeImg = new ImageIcon("gradebtn.png"); //랭킹 버튼 이미지
	private ImageIcon wordbookImg = new ImageIcon("wordbookbtn.png"); //단어장 버튼 이미지
	private JButton startBtn = new JButton(startImg); //시작 버튼
	private JButton prologueBtn = new JButton(prologueImg); //설명 버튼
	private JButton gradeBtn = new JButton(gradeImg); //랭킹 버튼
	private JButton wordbookBtn = new JButton(wordbookImg); //단어장 버튼
	private ImageIcon icon = new ImageIcon("castle.jpg"); //배경 이미지
	private Image img = icon.getImage();
	
	public MainFrame() {
		setTitle("왕국을 지켜라");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600); //사이즈 설정
		setLocationRelativeTo(null); //화면 중앙에 위치하게 함
		setContentPane(new MainPanel());
		
		this.setResizable(false);
		setVisible(true);
		
		startBtn.addActionListener(new startListener()); //각 버튼에 리스너 추가
		startBtn.addMouseListener(new mouseListener());
		prologueBtn.addActionListener(new prologueListener());
		prologueBtn.addMouseListener(new mouseListener());
		gradeBtn.addActionListener(new gradeListener());
		gradeBtn.addMouseListener(new mouseListener());
		wordbookBtn.addActionListener(new wordbookListener());
		wordbookBtn.addMouseListener(new mouseListener());
	}
	public MainFrame(int x, int y) { //좌표 입력 시 실행되는 생성자
		setTitle("왕국을 지켜라");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600); //사이즈 설정
		this.setLocation(x,y); //전 프레임이 위치했던 좌표 불러와서 그 위치에 생성
		setContentPane(new MainPanel());
		
		this.setResizable(false);
		setVisible(true);
		
		startBtn.addActionListener(new startListener()); //각 버튼에 리스너 추가
		startBtn.addMouseListener(new mouseListener());
		prologueBtn.addActionListener(new prologueListener());
		prologueBtn.addMouseListener(new mouseListener());
		gradeBtn.addActionListener(new gradeListener());
		gradeBtn.addMouseListener(new mouseListener());
		wordbookBtn.addActionListener(new wordbookListener());
		wordbookBtn.addMouseListener(new mouseListener());
	}
	private class MainPanel extends JPanel{
		public MainPanel() {
			setLayout(null);
			JLabel title = new JLabel(); //제목 이미지 레이블
			title.setIcon(titleImg);//레이블에 이미지 추가
			title.setSize(600,100);
			title.setLocation(250,50);
			add(title);
			
			startBtn.setLocation(600,250); //각각 사이즈와 위치 설정하고 추가
			startBtn.setSize(160,40);
			startBtn.setBorderPainted(false); //버튼 테두리 지우기
			startBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			startBtn.setFocusPainted(false); //내부 테두리 지우기
			add(startBtn);
			prologueBtn.setLocation(600,300);
			prologueBtn.setSize(160,40);
			prologueBtn.setBorderPainted(false); //버튼 테두리 지우기
			prologueBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			prologueBtn.setFocusPainted(false); //내부 테두리 지우기
			add(prologueBtn);
			gradeBtn.setLocation(600,350);
			gradeBtn.setSize(160,40);
			gradeBtn.setBorderPainted(false); //버튼 테두리 지우기
			gradeBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			gradeBtn.setFocusPainted(false); //내부 테두리 지우기
			add(gradeBtn);
			wordbookBtn.setLocation(600,400);
			wordbookBtn.setSize(160,40);
			wordbookBtn.setBorderPainted(false); //버튼 테두리 지우기
			wordbookBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			wordbookBtn.setFocusPainted(false); //내부 테두리 지우기
			add(wordbookBtn);
		}
		
		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);
			g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),null); //배경 이미지 그리기
		}
		 
	}
	
	public class startListener implements ActionListener{  //게임 프레임으로 이동
		@Override
		public void actionPerformed(ActionEvent e) {
			int x = MainFrame.this.getX();
			int y = MainFrame.this.getY();
			new GameFrame(x,y);
			setVisible(false);
		}
	}
	public class prologueListener implements ActionListener{ //설명 프레임으로 이동
		@Override
		public void actionPerformed(ActionEvent e) {
			int x = MainFrame.this.getX();
			int y = MainFrame.this.getY();
			new StoryFrame(x,y);
			setVisible(false);
		}
	}
	public class gradeListener implements ActionListener{ //랭킹 프레임으로 이동
		@Override
		public void actionPerformed(ActionEvent e) {
			int x = MainFrame.this.getX();
			int y = MainFrame.this.getY();
			new GradeFrame(x,y);
			setVisible(false);
		}
	}
	public class wordbookListener implements ActionListener{ //단어장 프레임으로 이동
		@Override
		public void actionPerformed(ActionEvent e) {
			int x = MainFrame.this.getX();
			int y = MainFrame.this.getY();
			new EditFrame(x,y);
			setVisible(false);
		}
	}
	public class mouseListener extends MouseAdapter { //마우스가 각 버튼에 올라가고 내려왔을 때 버튼 변화를 통해 어디를 가리키고 있는지 명확하게 함
		public void mouseEntered(MouseEvent e) {
			JButton btn = (JButton)(e.getSource());
			btn.setSize(160,30);
		}
		public void mouseExited(MouseEvent e) {
			JButton btn = (JButton)(e.getSource());
			btn.setSize(160,40);
		}
	}
}
