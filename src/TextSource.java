import java.io.*;
import java.util.*;

public class TextSource {
    private Vector<String> wordVector = new Vector<String>(); // 문자를 저장

    public TextSource() {
        File file = new File("words.txt"); // 현재 디렉토리의 words.txt 파일
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String word;
            while ((word = reader.readLine()) != null) {
                wordVector.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("words.txt 파일을 찾을 수 없습니다: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String next() {
        if (wordVector.isEmpty()) {
            return null; // 단어가 없으면 null 반환
        }
        int n = wordVector.size();
        int index = (int) (Math.random() * n);
        return wordVector.get(index);
    }
}
