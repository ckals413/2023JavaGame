import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	//private ImageIcon mainIcon = new ImageIcon("background.png");
	private ImageIcon mainIcon = new ImageIcon(getClass().getResource("background.png"));
	private Image mainImg = mainIcon.getImage();
	private ImageIcon bubble1 = new ImageIcon(getClass().getResource("bubblebobble1.png"));
	private ImageIcon bubble2 = new ImageIcon(getClass().getResource("bubblebobble2.png"));
	private ImageIcon startButton1 = new ImageIcon(getClass().getResource("start1.png"));
	private ImageIcon startButton2 = new ImageIcon(getClass().getResource("start2.png"));
	private ImageIcon scoreButton1 = new ImageIcon(getClass().getResource("scoreButton1.png"));
	private ImageIcon scoreButton2 = new ImageIcon(getClass().getResource("scoreButton2.png"));

	private ImageIcon mainPlayer1_1 = new ImageIcon(getClass().getResource("mainPlayer1_1.png"));
	private ImageIcon mainPlayer1_2 = new ImageIcon(getClass().getResource("mainPlayer1_2.png"));
	private ImageIcon mainPlayer2_1 = new ImageIcon(getClass().getResource("mainPlayer2_1.png"));
	private ImageIcon mainPlayer2_2 = new ImageIcon(getClass().getResource("mainPlayer2_2.png"));

	private JLabel gameStartLabel = new JLabel(startButton1);
	private JLabel scoreLabel = new JLabel(scoreButton1);
	private JLabel bubbleLabel = new JLabel(bubble1);
	private JLabel mainPlayer1Label = new JLabel(mainPlayer1_1);
	private JLabel mainPlayer2Label = new JLabel(mainPlayer2_1);
	private MainFrame mainFrame;

	public void paintComponent(Graphics g) {
		g.drawImage(mainImg, 0, 0, getWidth(), getHeight(), this);
		setOpaque(false);
		super.paintComponent(g);
	}

	public MainPanel(MainFrame mainFrame) {
		setLayout(null);
		this.mainFrame = mainFrame;

		gameStartLabel.setSize(startButton1.getIconWidth(), startButton1.getIconHeight());
		gameStartLabel.setLocation(280, 300);
		add(gameStartLabel); //

		scoreLabel.setSize(scoreButton2.getIconWidth(), scoreButton2.getIconHeight());
		scoreLabel.setLocation(270, 400);
		add(scoreLabel);

		// 처음 화면, 상단 중앙 bubble 이미지 설정
		bubbleLabel.setSize(bubble1.getIconWidth(), bubble1.getIconHeight());
		bubbleLabel.setLocation(250, 30);
		add(bubbleLabel);

		// mainPlayer1Label 및 mainPlayer2Label 설정
		mainPlayer1Label.setSize(mainPlayer1_2.getIconWidth(), mainPlayer1_2.getIconHeight());
		mainPlayer1Label.setLocation(20, 200);
		add(mainPlayer1Label);

		mainPlayer2Label.setSize(mainPlayer2_2.getIconWidth(), mainPlayer2_2.getIconHeight());
		mainPlayer2Label.setLocation(530, 200);
		add(mainPlayer2Label);

		// 새로운 이미지 전환 스레드 시작
		new ImageSwitcher(bubbleLabel, bubble1, bubble2).start();
		new ImageSwitcher(mainPlayer1Label, mainPlayer1_1, mainPlayer1_2).start();
		new ImageSwitcher(mainPlayer2Label, mainPlayer2_1, mainPlayer2_2).start();

		gameStartLabel.addMouseListener((MouseListener) new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				click2Audio();
				new IdFrame(mainFrame);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				gameStartLabel.setIcon(startButton2);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				gameStartLabel.setIcon(startButton1);

			}
		});

		scoreLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("Click scoreButton");
				click2Audio();
				ScoreBoard scoreboard = new ScoreBoard();
				// 점수판 창 열기
				try {
					new ScoreBoardFrame(scoreboard).setVisible(true);
				} catch (IOException e1) {
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				scoreLabel.setIcon(scoreButton2);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				scoreLabel.setIcon(scoreButton1);

			}

		});
	}

	// 클릭 효과음 재생
	public void click2Audio() {
		playSound("bubblePop.wav");
	}

	// 오디오 파일을 로드하고 재생하는 메소드
	private void playSound(String soundFileName) {
	    try {
	        Clip clip = AudioSystem.getClip();
	        InputStream audioSrc = getClass().getResourceAsStream("/" + soundFileName);
	        if (audioSrc == null) {
	            throw new IOException("Audio file not found: " + soundFileName);
	        }
	        BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
	        AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
	        clip.open(audioStream);
	        clip.start();
	    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
	        System.out.println("오디오 로드 오류: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


	// ImageSwitcher 스레드 클래스
	class ImageSwitcher extends Thread {
		private JLabel label;
		private ImageIcon image1;
		private ImageIcon image2;
		private boolean isImage1 = true;

		public ImageSwitcher(JLabel label, ImageIcon image1, ImageIcon image2) {
			this.label = label;
			this.image1 = image1;
			this.image2 = image2;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);
					if (isImage1) {
						label.setIcon(image2);
					} else {
						label.setIcon(image1);
					}
					isImage1 = !isImage1;
				} catch (InterruptedException e) {

				}
			}
		}
	}

}
