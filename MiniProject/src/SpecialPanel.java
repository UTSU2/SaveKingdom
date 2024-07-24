import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpecialPanel extends JPanel{
	private ImageIcon tmpexplosion = new ImageIcon("explosion.png"); //익스플로전 마법 이미지
	private Image explosionimg = tmpexplosion.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon explosion = new ImageIcon(explosionimg);
	private JLabel explosionLabel = new JLabel(explosion); //마법 이미지 부착된 레이블
	private ImageIcon tmpslow = new ImageIcon("slow.png"); //슬로우 마법 이미지
	private Image slowimg = tmpslow.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon slow = new ImageIcon(slowimg);
	private JLabel slowLabel = new JLabel(slow); //마법 이미지 부착된 레이블
	private ImageIcon tmprestore = new ImageIcon("restore.png"); //리스토어 마법 이미지
	private Image restoreimg = tmprestore.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);//이미지 크기 수정
	private ImageIcon restore = new ImageIcon(restoreimg);
	private JLabel restoreLabel = new JLabel(restore); //마법 이미지 부착된 레이블
	
	public SpecialPanel() {
		this.setBackground(Color.DARK_GRAY); //배경색 설정
		setLayout(null);
		explosionLabel.setSize(150,150); //각 레이블 사이즈와 위치만 설정
		explosionLabel.setLocation(15,15);
		slowLabel.setSize(150,150);
		slowLabel.setLocation(15,15);
		restoreLabel.setSize(150,150);
		restoreLabel.setLocation(15,15);
	}
	public void magic(int num) { //각 특수 마법 발동 시 패널에 레이블 보이게 함
		if(num == 1) {
			this.removeAll();
			this.add(explosionLabel);
			this.repaint();
		}else if(num == 2) {
			this.removeAll();
			this.add(slowLabel);
			this.repaint();
		}else if(num == 3) {
			this.removeAll();
			this.add(restoreLabel);
			this.repaint();
		}
	}
}
