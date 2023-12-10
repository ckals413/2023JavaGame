import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GamePanel extends JPanel {
	private ScorePanel scorePanel;
	private GameGround gameGround;
	private String selectedOption; 
	private SoundEffects soundEffects;
	private GameFrame gameFrame;

	public GamePanel(String selectedOption, SoundEffects soundEffects, GameFrame gameFrame) {
		this.selectedOption = selectedOption; // selectedOption 초기화
		this.soundEffects = soundEffects;  //soundEffects 초기화 
		this.gameFrame = gameFrame; // gameFrame 초기화
		scorePanel = new ScorePanel();
		setBackground(Color.yellow);
		setLayout(new BorderLayout());
		splitPanel(); //패널 나누기
	}

	public void setPlayerId(String id) {
		scorePanel.setPlayerId(id); // id 설정
	}

	private void splitPanel() {
		JSplitPane hPane = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(800);
		hPane.setEnabled(false); // 못움직이게 하는 설정
		add(hPane);

		JSplitPane vPane = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setEnabled(false); // 못움직이게 하는 설정
		vPane.setDividerLocation(300);
		vPane.setTopComponent(scorePanel);
		vPane.setBottomComponent(new EditPanel());

		hPane.setRightComponent(vPane);
		gameGround = new GameGround(scorePanel, selectedOption, soundEffects, gameFrame);
		hPane.setLeftComponent(gameGround);
	}
}
