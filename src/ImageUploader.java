import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageUploader extends JFrame {

    private JTextField filePathField;
    private JLabel imageLabel;

    public ImageUploader() {
        setTitle("Image Uploader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout());

        filePathField = new JTextField(20);
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                    displayImage(selectedFile.getAbsolutePath());
                }
            }
        });

        filePanel.add(filePathField);
        filePanel.add(browseButton);

        mainPanel.add(filePanel, BorderLayout.NORTH);

        imageLabel = new JLabel();
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void displayImage(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageUploader().setVisible(true);
            }
        });
    }
}