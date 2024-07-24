import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel {
	private int Heart = 3; // 성벽 내구도
	private ImageIcon tmpfireball = new ImageIcon("fireball.png"); //파이어볼 마법 이미지
	private Image fireballimg = tmpfireball.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon fireball = new ImageIcon(fireballimg);
	private ImageIcon tmplightening = new ImageIcon("lightening.png"); //라이트닝 마법 이미지
	private Image lighteningimg = tmplightening.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon lightening = new ImageIcon(lighteningimg);
	private ImageIcon tmpmeteo = new ImageIcon("meteo.png"); //메테오 마법 이미지
	private Image meteoimg = tmpmeteo.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon meteo = new ImageIcon(meteoimg);
	private ImageIcon icon = new ImageIcon("castlewall.jpg"); //게임 진행 패널 배경 추가
	private Image img = icon.getImage();
	private ImageIcon textfieldicon = new ImageIcon("castletitle.png"); //inputpanel 배경 이미지
	private Image textfieldimg = textfieldicon.getImage();
	private Vector<ImageLabel> attackVector = new Vector<ImageLabel>(); // 단어 임시 저장
	private Vector<AttackThread> attackThread = new Vector<AttackThread>(); // 스레드 벡터 선언
	private JTextField input = new JTextField(40); // 입력 받을 텍스트 필드
	private ScorePanel scorePanel = null; // 점수 패널
	private SpecialPanel specialPanel = null;
	private TextSource textSource = new TextSource(); // 텍스트 파일에서 단어장 불러오기
	private GradeSource gradeSource = new GradeSource(); //이름과 점수 텍스트파일에 저장할 용도
	private GameGroundPanel gamegroundPanel = new GameGroundPanel(); // 게임그라운드 패널에 레이블 추가 용도
	private int indexNum = 0;
	private double MaxX = 0; // 성벽과 가장 가까운 레이블의 x좌표
	private int closeJLabel = -1; // 성벽과 가장 가까운 레이블의 인덱스
	private int MagicCount = 0; // 10의 배수가 될 때마다 마법 사용
	private int addDelay = 3000; // 공격 마법 생성 딜레이
	private final int fireBasicDelay = 750; //level 1 기준 속도
	private final int lightBasicDelay = 375;
	private final int meteoBasicDelay = 1000;
	private int fireDelay = 750; // 파이어볼 속도
	private int lightDelay = 375; // 라이트닝 속도
	private int meteoDelay = 1000; // 메테오 속도
	private int fireDelayCount = 0; //슬로우 마법 발동시 각 마법들의 딜레이 카운트 증가, 카운트가 끝나면 속도 정상화
	private int lightDelayCount = 0;
	private int meteoDelayCount = 0;
	private int levelcount = 0; //카운트 증가 시 레벨 증가
	private int level = 1; //레벨 설정 초기설정은 1
	private boolean ifgamestart = false;

	public GamePanel(ScorePanel scorePanel, SpecialPanel specialPanel) {
		this.scorePanel = scorePanel; //스코어패널 사용하기 위해 선언
		this.specialPanel = specialPanel;
		
		setLayout(new BorderLayout());
		add(gamegroundPanel, BorderLayout.CENTER); //게임 그라운드 패널 추가
		add(new InputPanel(), BorderLayout.SOUTH); //단어 입력용 패널 추가

		input.addActionListener(new ActionListener() { //단어 입력 후 엔터키 입력 시 발생하는 메소드
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) (e.getSource());
				String inWord = t.getText(); //텍스트 필드에서 값 가져오기
				for (int i = 0; i < attackThread.size(); i++) { // 입력된 단어의 레이블 중 성벽과 가장 가까운 레이블 찾기
					if (attackThread.get(i).attackText.getText().equals(inWord)) {
						if (MaxX < (attackThread.get(i).attackText.getX())) {
							MaxX = (double) (attackThread.get(i).attackText.getX());
							closeJLabel = i; //더 가까운 레이블 인덱스로 변경
						}
					}
				}
				if (closeJLabel != -1) { // 성벽과 가장 가까운 레이블 지우고 점수 추가
					attackThread.get(closeJLabel).count--; //메테오 마법의 경우 2번입력해야 깨질 수 있도록 카운트 값을 줄이는 방식
					if (attackThread.get(closeJLabel).count == 0) { //카운트가 0면 마법 삭제
						//Container c = gamegroundPanel; //게임 그라운드 패널의 콘테이너 
						// 점수 올리기
						scorePanel.increase();
						gamegroundPanel.remove(attackThread.get(closeJLabel).attackText); //게임 그라운드 패널에서 레이블 삭제
						attackThread.get(closeJLabel).interrupt(); //선택된 레이블 중지
						attackThread.remove(closeJLabel); //레이블 스레드 벡터에서 삭제						
						gamegroundPanel.repaint(); //다시 패널 그려서 레이블 없어지게 하기
						MagicCount++; //카운트가 5가 되면 특수 마법 발동
						if (MagicCount % 5 == 0) { // 10번 입력 성공했을 때 특수 마법 발동
							int randomMagic = (int) (Math.random() * 10); //확률에 따라 랜덤 마법 발동
							if(randomMagic>6) { //익스플로전 마법 발동
								Explosion();
								specialPanel.magic(1);
								gamegroundPanel.repaint();
							}
							else if(randomMagic>3) { //슬로우 마법 발동
								Slow();
								specialPanel.magic(2);
							}
							else { //리스토어 마법 발동
								Restore();
								specialPanel.magic(3);
							}	
						}
					}
				}
				closeJLabel = -1; //가까운 레이블 인덱스 초기화
				MaxX = 0; //가까운 레이블의 x좌표 값 초기화
				t.setText(""); // input 창 지우기
			}
		});
	}

	class GameGroundPanel extends JPanel {
		private JLabel levelLabel = null;
		public GameGroundPanel() {
			setLayout(null);
			levelLabel = new JLabel("LEVEL 1"); //레벨 레이블 설정
			levelLabel.setSize(100,30); //사이즈와 위치, 폰트와 글자 색상 설정 후 추가
			levelLabel.setLocation(10,0);
			levelLabel.setFont(new Font("Serif",Font.BOLD,20));
			levelLabel.setForeground(Color.WHITE);
			add(levelLabel);
		}

		public void GameOver() { //게임 오버 메소드
			ifgamestart = false;
			while (attackThread.size() != 0) { //스레드 벡터에 있는 모든 스레드 중단 후 삭제
				for (int i = 0; i < attackThread.size(); i++) {
					remove(attackThread.get(i).attackText);
					attackThread.get(i).interrupt();
					attackThread.remove(i);
				}
			}
			int score = scorePanel.getScore(); //스코어 패널에서 점수 가져오기
			String name = JOptionPane.showInputDialog("이름을 입력하세요");
			gradeSource.InputGrade(name, score); //이름과 점수 텍스트 파일에 기록
			repaint(); //패널 다시 그려서 패널 초기화
		}
		@Override
		public void paintComponent(Graphics g) {  //배경 그리기
			  super.paintComponent(g);
			  g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),null);
		}
	}

	class InputPanel extends JPanel {
		public InputPanel() {
			setLayout(new FlowLayout());
			this.setBackground(Color.darkGray);
			add(input); //입력 받을 수 있는 텍스트 필드 추가
		}
		@Override
		public void paintComponent(Graphics g) { //배경 그리기
			super.paintComponent(g);
			g.drawImage(textfieldimg, 0,0,this.getWidth(),this.getHeight(),null);
		}
	}
	
	public class ImageLabel extends JLabel { //이미지 위에 텍스트가 있는 레이블
		private String text;
		
		public ImageLabel(String text, ImageIcon magicIcon) {
			super(magicIcon);
			this.text = text;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(text != null) {
				FontMetrics fontMetrics = g.getFontMetrics(); //폰트의 정보를 가지고 옴
				int textWidth = fontMetrics.stringWidth(text); //문자열의 너비 반환
	            int x = getWidth() - textWidth - 20; //x,y좌표값 설정
	            int y = getHeight() - fontMetrics.getHeight()/2;
	            g.setFont(new Font("Serif",Font.BOLD,15));
	            g.drawString(text, x, y); //레이블에 좌표에 맞게 그리기
	            g.dispose(); //Graphics 개체 이제 사용하지 않기 위해 자원 해제
			}
		}
		public String getText() { //레이블의 텍스트 반환
			return text;
		}
	}

	synchronized public void progressGame() { // 단어 스레드 새로 추가
		textSource = new TextSource(); //텍스트 파일에서 불러올 수 있도록 객체 선언
		String newWord = null;
		newWord = textSource.get(); //랜덤으로 단어 읽어와서 저장

		int random = (int) (Math.random() * 10); //랜덤하게 공격 마법 선정해서 스레드 추가
		if (random > 5) { 
			ImageLabel Word = new ImageLabel(newWord,fireball); //읽어온 단어 입력해서 새로운 레이블 추가
			attackVector.add(Word); //벡터에 단어 레이블 추가
			attackThread.add(new AttackThread(1, indexNum)); //파이어볼 마법
		} else if (random > 2) {
			ImageLabel Word = new ImageLabel(newWord,lightening); //읽어온 단어 입력해서 새로운 레이블 추가
			attackVector.add(Word); //벡터에 단어 레이블 추가
			attackThread.add(new AttackThread(2, indexNum)); // 라이트닝 마법
		} else {
			ImageLabel Word = new ImageLabel(newWord,meteo); //읽어온 단어 입력해서 새로운 레이블 추가
			attackVector.add(Word); //벡터에 단어 레이블 추가
			attackThread.add(new AttackThread(3, indexNum)); //메테오 마법
		}
		indexNum++; //스레드에 번호 추가해서 각 스레드에 번호만으로 접근 가능할 수 있게 계속 번호 추가
		attackThread.get(attackThread.size() - 1).start(); //생성한 스레드 시작
	}

	public void startGame() { // 그 전 게임 정보 초기화하면서 게임 시작
		while (attackThread.size() != 0) { // 벡터에 들어있던 내용들 초기화 및 패널에서 지우기
			for (int i = 0; i < attackThread.size(); i++) {
				gamegroundPanel.remove(attackThread.get(i).attackText); // 패널에서 레이블 삭제
				attackThread.get(i).interrupt(); // 스레드 중단
				attackThread.remove(i); // 벡터에서 스레드 삭제
				gamegroundPanel.repaint(); // 화면 다시 그려서 깨끗하게 하기
			}
		}
		attackThread.removeAllElements(); // 혹시 남아있을 벡터 내용 삭제(주기적 생성으로 생길 수 있음)
		attackVector.removeAllElements(); // 혹시 남아있을 벡터 내용 삭제
		MagicCount = 0; // 값들 초기화
		addDelay = 2000;
		fireDelay = 750;
		lightDelay = 375;
		meteoDelay = 1000;
		levelcount = 0;
		level = 1;
		ifgamestart = true;
		gamegroundPanel.levelLabel.setText("LEVEL 1");
		progressGame(); // 게임 진행
	}

	public void stopGame() {
		ifgamestart = false;
		gamegroundPanel.GameOver(); //게임 오버 메소드 실행
	}
	
	public boolean getifgamestart() {
		return ifgamestart;
	}

	public void Explosion() { // 폭발 마법 발동 메소드
		int amount = Math.min(attackThread.size(), 5); //최대 5의 숫자가 입력되어 최대 5개의 공격 마법을 지울 수 있게 함
		for (int i = amount - 1; i >= 0; i--) { //설정된 숫자만큼의 스레드 제거
			Container c = gamegroundPanel;
			gamegroundPanel.remove(attackThread.get(i).attackText);
			attackThread.get(i).interrupt();
			attackThread.remove(i);
			gamegroundPanel.repaint();
		}
		progressGame(); //모든 스레드가 지워져도 계속 진행될 수 있도록 게임 진행 메소드 실행
	}

	public void Slow() { // 슬로우 마법 발동 메소드
		fireDelay = 1500; //속도 딜레이 증가
		lightDelay = 750;
		meteoDelay = 2000;
		fireDelayCount = 4; //카운트 끝나면 정상 속도 회복
		lightDelayCount = 8;
		meteoDelayCount = 3;
	}

	public void Restore() { //성벽 내구도 1 회복
		scorePanel.restore();
	}

	class AttackThread extends Thread { // 공격 레이블이 들어있는 스레드로 각각 움직이게 함
		private ImageLabel attackText = null;
		private int count = 0;
		private int attackType = 0;
		private int index = -1;

		public AttackThread(int type, int index) {
			attackType = type; //숫자에 따라 공격 타입 지정
			this.index = index; //각 스레드에 고유한 번호 지정
			attackText = attackVector.get(0); //벡터에 입력된 가장 첫 번째 레이블 가져오기
			attackText.setSize(100, 30); //크기 설정
			if (attackType == 1) { //파이어볼 마법
				count = 1;
			} else if (attackType == 2) { //라이트닝 마법
				count = 1;
			} else if (attackType == 3) { //메테오 마법
				count = 2; //메테오는 두 번 입력해야 소멸시킬 수 있음
			}
			attackText.setVisible(false);
			gamegroundPanel.add(attackText); //게임 진행 패널에 레이블 추가
			attackVector.remove(0); //스레드에 추가한 레이블 벡터에서 삭제
			attackText.setLocation(0, (int) (Math.random() * 300));// 랜덤한 위치에서 생성
		}
		
		@Override
		public void run() {
			if (attackThread.size() < 30) { //스레드 개수가 30개보다 적으면 주기적으로 레이블 스레드 생성되게 함
				try {
					sleep(addDelay); //생성 딜레이 설정
				} catch (InterruptedException e) {
					return;
				}
				levelcount++;
				if(levelcount > 20) { //레벨 카운트가 증가함에 따라 난이도 상승 - 딜레이 감소
					gamegroundPanel.levelLabel.setText("LEVEL 3");
					level = 3;
					addDelay = 1000;
					fireDelay = fireBasicDelay/3;
					lightDelay = lightBasicDelay/3;
					meteoDelay = meteoBasicDelay/3;
				} else if(levelcount > 10) {
					gamegroundPanel.levelLabel.setText("LEVEL 2");
					level = 2;
					addDelay = 2000;
					fireDelay = fireBasicDelay/2;
					lightDelay = lightBasicDelay/2;
					meteoDelay = meteoBasicDelay/2;
				}
				progressGame(); //스레드 생성
			}
			attackText.setVisible(true);
			attackText.setOpaque(false);
			while (true) {
				if (attackType == 1) { //타입에 따라 각 레이블의 속도 설정
					if(fireDelayCount == 0) { //딜레이 카운트 없을 시 속도 정상화
						if(level == 1) {
							fireDelay = fireBasicDelay;
						}
						else if(level == 2) {
							fireDelay = fireBasicDelay/2;
						}
						else if(level == 3) {
							fireDelay = fireBasicDelay/3;
						}
					}else {
						fireDelayCount--;
					}
					if (attackText.getX() < GamePanel.this.getWidth() - 100)
						attackText.setLocation(attackText.getX() + 10, attackText.getY());
					else {
						Container c = gamegroundPanel; //벽에 닿을 시 레이블 삭제 후 데미지
						c.remove(attackText);
						c.repaint();
						Heart--;
						scorePanel.damage(1);
						break;
					}
					try {
						sleep(fireDelay);
					} catch (InterruptedException e) {
						return;
					}
				} else if (attackType == 2) { //타입에 따라 각 레이블의 속도 설정
					if(lightDelayCount == 0) { //딜레이 카운트 없을 시 속도 정상화
						if(level == 1) {
							lightDelay = lightBasicDelay;
						}
						else if(level == 2) {
							lightDelay = lightBasicDelay/2;
						}
						else if(level == 3) {
							lightDelay = lightBasicDelay/3;
						}
					}else {
						lightDelayCount--;
					}
					if (attackText.getX() < GamePanel.this.getWidth() - 100)
						attackText.setLocation(attackText.getX() + 10, attackText.getY());
					else {
						Container c = gamegroundPanel; //벽에 닿을 시 레이블 삭제 후 데미지
						c.remove(attackText);
						c.repaint();
						Heart--;
						scorePanel.damage(1);
						break;
					}
					try {
						sleep(lightDelay);
					} catch (InterruptedException e) {
						return;
					}
				} else if (attackType == 3) { //타입에 따라 각 레이블의 속도 설정
					if(meteoDelayCount == 0) { //딜레이 카운트 없을 시 속도 정상화
						if(level == 1) {
							meteoDelay = meteoBasicDelay;
						}
						else if(level == 2) {
							meteoDelay = meteoBasicDelay/2;
						}
						else if(level == 3) {
							meteoDelay = meteoBasicDelay/3;
						}
					}else {
						meteoDelayCount--;
					}
					if (attackText.getX() < GamePanel.this.getWidth() - 100)
						attackText.setLocation(attackText.getX() + 10, attackText.getY());
					else {
						Container c = gamegroundPanel; //벽에 닿을 시 레이블 삭제 후 데미지
						c.remove(attackText);
						c.repaint();
						Heart--;
						scorePanel.damage(2);
						break;
					}
					try {
						sleep(meteoDelay);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
			for (int i = 0; i < attackThread.size(); i++) {//스레드가 벽에 닿았을 경우 고유 인덱스 번호를 통해 스레드 삭제
				if (attackThread.get(i).index == index) {
					attackThread.remove(i);
				}
			}
			if (Heart <= 0) { //목숨 수치를 0으로 만든 스레드가 있을 경우 게임 종료 메소드 실행
				gamegroundPanel.GameOver();
			}
		}
	}
}
