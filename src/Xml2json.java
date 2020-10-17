import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

import static org.json.XML.toJSONObject;

public class Xml2json{
    public static void main(String[] args) throws JSONException, IOException {
        String line;
        StringBuilder str = new StringBuilder();
        FileWriter file = null;
        String filePath;

        JFrame frame = new JFrame("XML to JSON");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLayout(new FlowLayout());
        frame.setResizable(false);

        JLabel label = new JLabel("");
        label.setVerticalTextPosition(SwingConstants.CENTER);
        label.setSize(new Dimension(400,50));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        JButton button = new JButton("Dosya Seç");
        button.setSize(new Dimension(100,50));

        frame.getContentPane().add(button, BorderLayout.CENTER); // Adds Button to content pane of frame
        frame.setVisible(true);

        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML file", "xml"));
            fileChooser.setAcceptAllFileFilterUsed(false);

            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                label.setText(selectedFile.getAbsolutePath());
            }
        });

        while(label.getText().equals(""))
            System.out.println("boş");

        filePath = label.getText();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        while ((line = br.readLine()) != null) {
            str.append(line);
        }
        JSONObject jsonObject = toJSONObject(str.toString(), false);

        try {
            filePath = filePath.replace(".xml",".json");
            file = new FileWriter(filePath);
            System.out.println(filePath);
            file.write(jsonObject.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
