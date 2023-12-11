import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class EditPanel extends JPanel {
    private JTextField wordInput;
    private JLabel saveImageLabel;
    private JButton saveButton;

    public EditPanel() {
        setBackground(Color.cyan);
        saveImageLabel = new JLabel(new ImageIcon(getClass().getResource("/labelSave.png")));
        add(saveImageLabel);
        wordInput = new JTextField(10);
        add(wordInput);

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTextToFile();
            }
        });
        add(saveButton);
    }

    private void saveTextToFile() {
        String text = wordInput.getText().trim().replace(" ", ""); // 공백 제거
        try {
            File file = new File("words.txt");
            FileWriter fout = new FileWriter(file, true);
            fout.write(text + "\r\n");
            fout.close();
        } catch (IOException e) {
            System.out.println("단어 저장 중 오류 발생!");
            e.printStackTrace();
        }
        wordInput.setText("");
    }
}
