import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel{
	private ImageIcon tmpexplosion = new ImageIcon("explosion.png"); //설명용 이미지
	private Image explosionImg = tmpexplosion.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon explosion = new ImageIcon(explosionImg);
	private ImageIcon tmpslow = new ImageIcon("slow.png");
	private Image slowImg = tmpslow.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon slow = new ImageIcon(slowImg);
	private ImageIcon tmprestore = new ImageIcon("restore.png");
	private Image restoreImg = tmprestore.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon restore = new ImageIcon(restoreImg);
	private ImageIcon tmpscorepaper = new ImageIcon("gradepaper.png"); //양피지 이미지
	private Image scorepaperimg = tmpscorepaper.getImage().getScaledInstance(170, 300, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon scorepaper = new ImageIcon(scorepaperimg);
	private JLabel scorepaperLabel = new JLabel(scorepaper); //양피지 이미지 부착된 레이블
	private int score = 0; //점수 표시
	private int Heart = 3; //생명 표시
	private JLabel textLabel = new JLabel("점수");
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private JLabel HeartTextLabel = new JLabel("내구도");
	private JLabel HeartLabel = new JLabel(Integer.toString(Heart));
	private JLabel explosionTextLabel = new JLabel("익스플로전");
	private JLabel slowTextLabel = new JLabel("슬로우");
	private JLabel restoreTextLabel = new JLabel("리스토어");
	
	public ScorePanel() {
		JLabel explosionLabel = new JLabel(explosion);
		JLabel slowLabel = new JLabel(slow);
		JLabel restoreLabel = new JLabel(restore);
		this.setBackground(Color.DARK_GRAY); //배경색 설정
		setLayout(null);
		
		textLabel.setSize(50,20); //각 레이블 사이즈와 위치 입력하고 추가
		textLabel.setLocation(10,30);
		textLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(textLabel);
		scoreLabel.setSize(100,20);
		scoreLabel.setLocation(70,30);
		scoreLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(scoreLabel);
		HeartTextLabel.setSize(50,20);
		HeartTextLabel.setLocation(10,50);
		HeartTextLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(HeartTextLabel);
		HeartLabel.setSize(100,20);
		HeartLabel.setLocation(70,50);
		HeartLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(HeartLabel);
		explosionTextLabel.setSize(100,20);
		explosionTextLabel.setLocation(10,90);
		explosionTextLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(explosionTextLabel);
		explosionLabel.setSize(50, 50); //각 레이블 사이즈와 위치만 설정
		explosionLabel.setLocation(90,70);
		add(explosionLabel);
		slowTextLabel.setSize(100,20);
		slowTextLabel.setLocation(10,150);
		slowTextLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(slowTextLabel);
		slowLabel.setSize(50, 50); //각 레이블 사이즈와 위치만 설정
		slowLabel.setLocation(90,130);
		add(slowLabel);
		restoreTextLabel.setSize(100,20);
		restoreTextLabel.setLocation(10,210);
		restoreTextLabel.setFont(new Font("Serif",Font.BOLD,13)); //폰트 설정
		add(restoreTextLabel);
		restoreLabel.setSize(50, 50); //각 레이블 사이즈와 위치만 설정
		restoreLabel.setLocation(90,190);
		add(restoreLabel);
	}
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		scorepaperLabel.setSize(scorepaper.getIconWidth(),scorepaper.getIconHeight()); //배경에 양피지 이미지 추가
		scorepaperLabel.setLocation(0,0);
		add(scorepaperLabel);
	}
	public int getScore() { //점수 반환
		return score;
	}
	public void increase() { //점수 증가
		score += 10;
		scoreLabel.setText(Integer.toString(score));
	}
	public void damage(int amount) { //데미지 입을 시 생명 감소
		Heart -= amount;
		if(Heart < 0)
			Heart = 0;
		HeartLabel.setText(Integer.toString(Heart));
	}
	public void restore() { //리스토어 발동 시 생명 회복
		if(Heart < 3) {
			Heart++;
			HeartLabel.setText(Integer.toString(Heart));
		}
	}
	public void init() { //점수와 생명 초기화 후 설정
		score = 0;
		scoreLabel.setText(Integer.toString(score));
		Heart = 3;
		HeartLabel.setText(Integer.toString(Heart));
	}
}
