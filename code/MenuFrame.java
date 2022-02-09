import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public abstract class MenuFrame extends JFrame implements ActionListener{

    JButton start;
    JPanel panelRoot;

    public MenuFrame() {

        setTitle("Minesweeper");
        setBounds(500,200,480,480);
        setBackground(new Color(204,204,204));
        setResizable(true);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String text = "Start";
        start = new JButton (text);
        start.addActionListener(this);
        
        panelRoot = new JPanel();
        panelRoot.setBounds(0, 0, this.getWidth(), this.getHeight());;
        panelRoot.add(start);
        panelRoot.setVisible(true);

        this.add(panelRoot);
    }
}