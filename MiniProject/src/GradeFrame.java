import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GradeFrame extends JFrame{
	private ImageIcon topImg = new ImageIcon("Top20.png");
	private ImageIcon icon = new ImageIcon("castle.jpg"); //배경 이미지
	private Image img = icon.getImage();
	private ImageIcon titleicon = new ImageIcon("castletitle.png"); //타이틀바 이미지
	private Image barimg = titleicon.getImage();
	private ImageIcon tmpgradepaper = new ImageIcon("gradepaper.png"); //양피지 이미지
	private Image gradeimg = tmpgradepaper.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon gradepaper = new ImageIcon(gradeimg);
	private JLabel gradeLabel = new JLabel(gradepaper);
	private ImageIcon tmptitleBtnicon = new ImageIcon("back.png");
	private Image titleBtnimg = tmptitleBtnicon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private ImageIcon titleBtnicon = new ImageIcon(titleBtnimg);
	private JButton titleBtn = new JButton(titleBtnicon); //타이틀(메인화면)로 이동하는 버튼
	private GradeSource gradeSource  = new GradeSource();
	private JLabel GradeArray [] = new JLabel[20]; 
	public GradeFrame(int x, int y) {
		setTitle("왕국을 지켜라");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		this.setLocation(x,y); //전 프레임이 위치했던 좌표 불러와서 그 위치에 생성
		setLayout(new BorderLayout());
		add(new gradePanel(), BorderLayout.CENTER); //랭킹 패널 중앙에 추가
		add(new titlePanel(), BorderLayout.SOUTH); //타이틀바 패널 아래에 추가
		
		this.setResizable(false);
		setVisible(true);
	}
	private class gradePanel extends JPanel{
		public gradePanel() {
			JLabel gradeTitle = new JLabel(); //제목 이미지 레이블
			gradeTitle.setIcon(topImg);//레이블에 이미지 추가
			gradeTitle.setSize(200,70);
			gradeTitle.setLocation(310,60);
			add(gradeTitle);
			int size = gradeSource.getVectorSize(); //벡터 사이즈 가져오기
			if(size > 20) //최대 20등까지 보여줌
				size = 20;
			setLayout(null);
			
			if(size<10) { //텍스트 파일에 입력된 플레이어 수가 10명이 안될 경우 한줄에 출력
				for(int i=0;i<size;i++) {
					String name = gradeSource.getName(i);
					String grade = gradeSource.getGrade(i);
					GradeArray[i] = new JLabel(i+1 + " : " + name + " " + grade); //등수 : 이름 점수 순으로 레이블 설정
					GradeArray[i].setFont(new Font("Serif",Font.BOLD,15)); //폰트 설정
					GradeArray[i].setSize(200,20);
					GradeArray[i].setLocation(250,30*i+150); //순서대로 위치 설정
					add(GradeArray[i]); //패널에 추가
				}
			}else { //입력된 플레이어 수가 10명의 넘을 경우 20등까지 2줄에 나누어 출력
				for(int i=0;i<10;i++) {
					String name = gradeSource.getName(i);
					String grade = gradeSource.getGrade(i);
					GradeArray[i] = new JLabel(i+1 + " : " + name + " " + grade); //등수 : 이름 점수 순으로 레이블 설정
					GradeArray[i].setFont(new Font("Serif",Font.BOLD,15)); //폰트 설정
					GradeArray[i].setSize(200,20);
					GradeArray[i].setLocation(250,30*i+150); //순서대로 위치 설정
					add(GradeArray[i]); //패널에 추가
				}
				for(int i=10;i<size;i++) {
					String name = gradeSource.getName(i);
					String grade = gradeSource.getGrade(i);
					GradeArray[i] = new JLabel(i+1 + " : " + name + " " + grade); //등수 : 이름 점수 순으로 레이블 설정
					GradeArray[i].setFont(new Font("Serif",Font.BOLD,15)); //폰트 설정
					GradeArray[i].setSize(200,20);
					GradeArray[i].setLocation(400,30*(i-10)+150); //순서대로 위치 설정
					add(GradeArray[i]); //패널에 추가
				}
			}
		}
		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);
			gradeLabel.setSize(gradepaper.getIconWidth(),gradepaper.getIconHeight());
			gradeLabel.setLocation(200,0); //양피지 이미지 레이블 위치 설정
			add(gradeLabel);
			g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),null); //패널의 배경 바꾸기 위해 paintComponent 사용
		}
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
	public class titleListener implements ActionListener{ //메인 화면 불러오는 메소드
		@Override
		public void actionPerformed(ActionEvent e) {
			int x = GradeFrame.this.getX();
			int y = GradeFrame.this.getY();
			new MainFrame(x,y);
			setVisible(false);
		}
	}
}
