import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	private GamePanel gamePanel;
	private GameGround gameGround;
	private String selectedOption;
	private SoundEffects soundEffects = new SoundEffects();

	public GameFrame(String id, String selectedOption) {
		this.selectedOption = selectedOption;
		setTitle("추억의 버블 보블"); //제목 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //종료 시 창 닫음 
		setSize(1050, 600);
		makeToolbar(); // 툴바 생성
		gamePanel = new GamePanel(selectedOption, soundEffects, this);
		gamePanel.setPlayerId(id); //플레이어 ID 설정
		getContentPane().add(gamePanel, BorderLayout.CENTER);
		setVisible(true);
		soundEffects.loadAudio(); // 오디오 클립 로드

	}
	
	//툴바 생성 메소드
	public void makeToolbar() {
		JToolBar bar = new JToolBar();
		getContentPane().add(bar, BorderLayout.NORTH);

		ImageIcon exitIcon = new ImageIcon("toolExit.png");
		JButton exitBtn = new JButton(exitIcon);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//클릭 시 확인 다이얼로그 
				int result = JOptionPane.showConfirmDialog(GameFrame.this, "정말 종료하시겠습니까?", "종료 확인",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		bar.add(exitBtn);

		ImageIcon helpIcon = new ImageIcon("toolHelp.png");
		JButton helpBtn = new JButton(helpIcon);
		helpBtn.addActionListener(new ActionListener() {
			@Override
			// 클릭 시 메세지 다이얼로그  
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(GameFrame.this,
						"추억의 버블보블입니다.\n 각각의 몬스터 마다 능력이 다 다릅니다.\n" + " 텍스트를 맞춰 점수를 높게 얻으세요!", "게임 정보",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		bar.add(helpBtn);
		bar.addSeparator();
		bar.addSeparator();
		bar.addSeparator(); //툴팁 간격 저절

		ImageIcon soundOffIcon = new ImageIcon("toolSoundOff.png");
		JButton soundOffBtn = new JButton(soundOffIcon);
		bar.add(soundOffBtn);

		ImageIcon soundOnIcon = new ImageIcon("toolSoundOn.png");
		JButton soundOnBtn = new JButton(soundOnIcon);
		bar.add(soundOnBtn);

		soundOnBtn.setEnabled(false);

		soundOffBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//클릭 시 사운드 정지
				System.out.println("soundOffBtn  clicked");
				soundOffBtn.setEnabled(false);
				soundOnBtn.setEnabled(true);
				soundEffects.stopAudio();
			}
		});

		soundOnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//클릭 시 사운드 재개 
				System.out.println("soundOnBtn  clicked");
				soundOffBtn.setEnabled(true);
				soundOnBtn.setEnabled(false);
				soundEffects.startAudio();
			}
		});

		bar.setFloatable(false);// 툴바 고정 (디폴트 = 움직임)

	}

	public void invisbleGameFrame() {
		this.setVisible(false); //숨김 처리
	}

}