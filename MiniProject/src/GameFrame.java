import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame{
	private JMenuItem startItem = new JMenuItem("열기"); //메뉴에 게임 시작, 정지, 타이틀 이동 기능의 아이템 추가
	private JMenuItem stopItem = new JMenuItem("닫기");
	private JMenuItem titleItem = new JMenuItem("타이틀로");
	private JButton startBtn = new JButton("열기"); //툴바에 게임 시작, 정지, 타이틀 이동 기능의 버튼 추가
	private JButton stopBtn = new JButton("닫기");
	private JButton titleBtn = new JButton("타이틀");
	
	private ScorePanel scorePanel = new ScorePanel(); //점수 패널을 프레임에 추가하기 위해 선언
	private SpecialPanel specialPanel = new SpecialPanel(); //삭제 예정
	public GamePanel gamePanel = new GamePanel(scorePanel,specialPanel); //게임 패널을 가져와 프레임에 추가하기 위해 선언
	
	public GameFrame(int x, int y) {
		setTitle("왕국을 지켜라");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		this.setLocation(x,y); //전 프레임이 위치했던 좌표 불러와서 그 위치에 생성

		splitPane(); //화면을 나누어 패널을 추가하는 메소드
		makeMenu(); // 메뉴바 생성 메소드
		makeToolBar(); //툴바 생성 메소드
		
		this.setResizable(false);
		setVisible(true);
	}
	
	private void splitPane() {
		JSplitPane hPane = new JSplitPane(); //스플릿 팬 추가
		getContentPane().add(hPane, BorderLayout.CENTER); 
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(600); //좌표 600 기준으로 양옆으로 분리
		hPane.setLeftComponent(gamePanel); //왼쪽에는 게임 패널 부착
		hPane.setEnabled(false);
		
		JSplitPane pPane = new JSplitPane();
		pPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane.setDividerLocation(300); //좌표 300 기준으로 위아래로 분리
		pPane.setTopComponent(scorePanel);  //오른쪽에서 위쪽에는 스코어 패널 부착
		pPane.setBottomComponent(specialPanel); //오른쪽에서 아래쪽에는 스페셜 패널 부착
		pPane.setEnabled(false);
		hPane.setRightComponent(pPane); //스플릿팬의 오른쪽에 위에서 새로 선언한 스플릿팬 부착
	}
	
	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		this.setJMenuBar(mBar);
		JMenu fileMenu = new JMenu("File"); //메뉴 추가
		fileMenu.add(startItem); //시작 종료 타이틀 기능 추가
		fileMenu.add(stopItem);
		fileMenu.add(titleItem);
		/*
		 * fileMenu.addSeparator(); fileMenu.add(new JMenuItem("종료"));
		 */
		mBar.add(fileMenu); //메뉴바에 만들어진 메뉴 객체 추가
		
		startItem.addActionListener(new StartAction()); //게임 시작
		stopItem.addActionListener(new StopAction()); //게임 종료 후 기록
		titleItem.addActionListener(new titleAction()); //게임 종료 후 기록하지 않고 타이틀로 이동
	}
	private void makeToolBar() {
		JToolBar tBar = new JToolBar(); //툴바 생성
		tBar.add(startBtn); //시작 종료 타이틀 기능 추가
		tBar.add(stopBtn);
		tBar.add(titleBtn);
		getContentPane().add(tBar, BorderLayout.NORTH); //툴바 콘텐트팬에 추가
		
		startBtn.addActionListener(new StartAction()); //게임 시작
		stopBtn.addActionListener(new StopAction()); //게임 종료 후 기록
		titleBtn.addActionListener(new titleAction()); //게임 종료 후 기록하지 않고 타이틀로 이동
	}
	private class StartAction implements ActionListener { //게임 시작 리스너
		public void actionPerformed(ActionEvent e) {
			scorePanel.init(); //스코어 패널 초기화
			gamePanel.startGame(); //게임 시작
		}
	}
	private class StopAction implements ActionListener { //게임 종료 리스너
		public void actionPerformed(ActionEvent e) {
			gamePanel.stopGame(); //게임 종료
		}
	}
	public class titleAction implements ActionListener{ //게임이 시작되어 있으면 종료 후 타이틀로, 아니면 그냥 타이틀로 이동
		@Override
		public void actionPerformed(ActionEvent e) { //x, y좌표 알아내서 그 위치에 불러내기
			if(gamePanel.getifgamestart()) //게임이 시작되어 있으면 종료 후 이동
				gamePanel.stopGame(); //게임 종료
			int x = GameFrame.this.getX(); //좌표 가져오기
			int y = GameFrame.this.getY();
			new MainFrame(x,y); //메인 화면 프레임 부르기
			setVisible(false); //전에 있던 프레임 보이지 않게 하기
		}
	}
	
}
