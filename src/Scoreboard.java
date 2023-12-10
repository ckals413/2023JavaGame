import java.io.*;
import java.util.*;

//점수 판 메소드 
public class ScoreBoard {
    private ArrayList<PlayerScore> scores = new ArrayList<>();

    public static class PlayerScore {
        public String playerId;
        public int score;

        public PlayerScore(String playerId, int score) {
            this.playerId = playerId;
            this.score = score;
        }
    }

    public void loadScores() throws IOException {
    	scores.clear();
        File file = new File("topRank.txt"); // topRank.txt 에서  읽어온온다.
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(","); //저장된 값을 ,로 나눈다.
                    if (parts.length == 2) {
                    	//,로 나뉜값을 배열에 넣는다. 
                        scores.add(new PlayerScore(parts[0], Integer.parseInt(parts[1])));
                    }
                }
            }
        }
    }

    public void updateScore(String playerId, int score) throws IOException {
        scores.add(new PlayerScore(playerId, score));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("topRank.txt", true))) {
            writer.write(playerId + "," + score);
            writer.newLine();
        }
    }

    public ArrayList<PlayerScore> getTopScores(int topN) throws IOException {
        loadScores();
        simpleSortScores(); // 점수를 정렬하는 메소드 호출
        
        ArrayList<PlayerScore> topScores = new ArrayList<>();
        for (int i = 0; i < Math.min(scores.size(), topN); i++) {
            topScores.add(scores.get(i));
        }
        return topScores;
    }

    // 점수를 정렬
    private void simpleSortScores() {
        for (int i = 0; i < scores.size(); i++) {
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(i).score < scores.get(j).score) {
                    PlayerScore temp = scores.get(i);
                    scores.set(i, scores.get(j));
                    scores.set(j, temp);
                }
            }
        }
    }
}
