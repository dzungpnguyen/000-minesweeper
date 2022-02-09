import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {

    PlayPanel playPanel;
    private JLabel status;

    public Main(){
        setTitle("Minesweeper");
        setBounds(500,200,321,321);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        status = new JLabel("Start");
        add(status, BorderLayout.SOUTH);
        playPanel = new PlayPanel(status);
        add(playPanel);
    }

    public static void main(String[] args) {
       
        Main game = new Main();
        game.setVisible(true);

    }
}
