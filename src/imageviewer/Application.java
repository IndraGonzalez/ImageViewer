
package imageviewer;

import imageviewer.control.Command;
import imageviewer.control.NextImageCommand;
import imageviewer.control.PrevImageCommand;
import imageviewer.model.Image;
import imageviewer.view.ImageDisplay;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Application extends JFrame {

    // Esto es igual en todas las interfaces de usuario
    
    private final Map<String,Command> commands = new HashMap<>();
    
    public static void main(String[] args) {
        new Application().setVisible(true);
    }
    
    private ImageDisplay imageDisplay;

    public Application() {
        // Crear la Interfaz de Usuario y los comandos
        this.deployUI();
        this.createCommands();
    }

    private void deployUI() {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(imagePanel());
        this.getContentPane().add(toolbar(),BorderLayout.SOUTH);
    }

    private void createCommands() {
        commands.put("next", new NextImageCommand(imageDisplay));
        commands.put("prev", new PrevImageCommand(imageDisplay));
    }

    private ImagePanel imagePanel() {
        String directory = JOptionPane.showInputDialog(this, "Introduzca un directorio");
        System.out.println(directory);
        ImagePanel imagePanel = new ImagePanel(image(directory));
        imageDisplay = imagePanel;
        return imagePanel;
    }

    private Image image(String directory) {
        // El directorio habría que sacarlo de otro sitio (pasarlo por parámetros,
        // pedírselo al usuario...)
        return new FileImageReader(directory).read();
    }    

    private Component toolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(prevButton());
        panel.add(nextButton());
        return panel;
    }

    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(doCommand("next"));
        return button;
    }

    private JButton prevButton() {
        JButton button = new JButton("<");
        button.addActionListener(doCommand("prev"));
        return button;
    }

    private ActionListener doCommand(final String operation) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(operation).execute();
            }
        };
    }
}
