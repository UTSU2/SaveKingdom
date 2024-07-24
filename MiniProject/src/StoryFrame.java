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

public class StoryFrame extends JFrame{
	private ImageIcon storyImg = new ImageIcon("story.png");
	private ImageIcon rule1Img = new ImageIcon("rule1.png");
	private ImageIcon rule2Img = new ImageIcon("rule2.png");
	private ImageIcon icon = new ImageIcon("castle.jpg"); //배경 이미지
	private Image img = icon.getImage();
	private ImageIcon tmpstorypaper = new ImageIcon("storypaper.png"); //양피지 이미지
	private Image storypaperimg = tmpstorypaper.getImage().getScaledInstance(780, 400, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon storypaper = new ImageIcon(storypaperimg);
	private JLabel storypaperLabel = new JLabel(storypaper);
	private ImageIcon titleicon = new ImageIcon("castletitle.png"); //타이틀바 이미지
	private Image barimg = titleicon.getImage();
	private JButton storyBtn = new JButton("story"); //스토리 보여주는 버튼
	private JButton ruleBtn = new JButton("rule"); //게임 룰 보여주는 버튼
	private ImageIcon tmptitleBtnicon = new ImageIcon("back.png");
	private Image titleBtnimg = tmptitleBtnicon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private ImageIcon titleBtnicon = new ImageIcon(titleBtnimg);
	private JButton titleBtn = new JButton(titleBtnicon); //타이틀(메인화면)로 이동하는 버튼
	public StoryFrame(int x, int y) {
		setTitle("왕국을 지켜라");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		this.setLocation(x,y); //전 프레임이 위치했던 좌표 불러와서 그 위치에 생성
		setLayout(new BorderLayout());
		add(new storyPanel(), BorderLayout.CENTER);
		add(new titlePanel(), BorderLayout.SOUTH);
		
		this.setResizable(false);
		setVisible(true);
	}
	private class storyPanel extends JPanel {
		private JLabel storyLabel = null;
		private JLabel rule1Label = null;
		private JLabel rule2Label = null;
		public storyPanel() {
			storyLabel = new JLabel(storyImg);
			rule1Label = new JLabel(rule1Img);
			rule2Label = new JLabel(rule2Img);
			setLayout(null);
			
			storyLabel.setSize(350,300);
			storyLabel.setLocation(225,80);
			add(storyLabel);
			rule1Label.setSize(350,300);
			rule1Label.setLocation(350,90);
			rule2Label.setSize(350,300);
			rule2Label.setLocation(55,60);

			storyBtn.setSize(120,30);
			storyBtn.setLocation(125,350);
			storyBtn.setFont(new Font("Serif",Font.BOLD,30)); //폰트 설정
			storyBtn.setBorderPainted(false); //버튼 테두리 지우기
			storyBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			storyBtn.setFocusPainted(false); //내부 테두리 지우기
			storyBtn.addActionListener(new storyListener());
			storyBtn.addMouseListener(new mouseListener());
			
			ruleBtn.setSize(120,30);
			ruleBtn.setLocation(550,350);
			ruleBtn.setFont(new Font("Serif",Font.BOLD,30)); //폰트 설정
			ruleBtn.setBorderPainted(false); //버튼 테두리 지우기
			ruleBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			ruleBtn.setFocusPainted(false); //내부 테두리 지우기
			ruleBtn.addActionListener(new ruleListener());
			ruleBtn.addMouseListener(new mouseListener());

			add(ruleBtn);
		}
		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);
			storypaperLabel.setSize(storypaper.getIconWidth(),storypaper.getIconHeight()); //양피지 사이즈 설정
			storypaperLabel.setLocation(0,50); //양피지 위치 설정
			add(storypaperLabel); //양피지 배경에 추가
			g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),null); //패널의 배경 바꾸기 위해 paintComponent 사용
		}
		public void showStory() { //스토리만 보여주게 함
			this.remove(rule1Label); //룰과 스토리 버튼 지우기
			this.remove(rule2Label);
			this.remove(storyBtn);
			this.add(storyLabel); //스토리와 룰 버튼 추가
			this.add(ruleBtn);
			this.repaint();
		}
		public void showRule() {
			this.remove(storyLabel); //스토리와 룰 버튼 지우기
			this.remove(ruleBtn);
			this.add(rule1Label); //룰과 스토리 버튼 추가
			this.add(rule2Label);
			this.add(storyBtn);
			this.repaint();
		}
		public class storyListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				showStory();
				ruleBtn.setFont(new Font("Serif",Font.BOLD,30));
			}
		}
		public class ruleListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				showRule();
				storyBtn.setFont(new Font("Serif",Font.BOLD,30));
			}
		}
	}
	
	private class titlePanel extends JPanel {
		public titlePanel() {
			setLayout(new FlowLayout(FlowLayout.LEFT)); //왼쪽 정렬로 버튼 왼쪽에 붙임
			titleBtn.addActionListener(new titleListener()); //이벤트 리스너 추가
			this.setBackground(new Color(30,52,75)); //배경 색깔 설정
			titleBtn.setSize(50,50);
			titleBtn.setLocation(0,0);
			titleBtn.setBorderPainted(false); //버튼 테두리 지우기
			titleBtn.setContentAreaFilled(false); //버튼 내부영역 투명하게 하기
			titleBtn.setFocusPainted(false); //내부 테두리 지우기
			add(titleBtn);
		}
		@Override
		public void paintComponent(Graphics g) { 
			super.paintComponent(g);
			g.drawImage(barimg, 0,0,this.getWidth(),this.getHeight(),null); //배경 그리기
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
	public class titleListener implements ActionListener{ //타이틀로 이동하는 리스너
		@Override
		public void actionPerformed(ActionEvent e) {
			int x = StoryFrame.this.getX();
			int y = StoryFrame.this.getY();
			new MainFrame(x,y);
			setVisible(false);
		}
	}
}
