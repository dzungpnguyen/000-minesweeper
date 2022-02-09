import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.util.Random;
import java.util.ArrayList;

public class PlayPanel extends JPanel {

    private final int nCols = 16;
    private final int nRows = 16;
    private final int nBombs = 40;
    private final int nCells = nCols * nRows;

    Cell cell = new Cell();
    ArrayList<Cell> myCells = new ArrayList<Cell>(nCells);

    private final int boardWidth = nCols * cell.getCSize() + 1;
    private final int boardHeight = nRows * cell.getCSize() + 1;

    private boolean isFinished = false;
    private int countFlag = nBombs;
    private int realFlag = 0;
    private int countRevealed = 0;

    private final JLabel status;


    public PlayPanel(JLabel status) {
        this.status = status;
        setVisible(true);
        initButtons();
    }

    private void initButtons() {

        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setLayout(new GridLayout(nRows,nCols)); // arrange all cells by rows/columns

        int count = 0; // count number of bombs
        for (int i = 0; i < nCells; i++){
            Cell c = new Cell();
            c.addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        c.setRevealed(true);
                        if (isFinished == false) {
                            if (c.getBomb() == true) {
                                c.setIcon(new ImageIcon("src/bomb.png"));
                                status.setText("Bye loser ^^");
                                isFinished = true; // lose
                                for (int i = 0; i < nCells; i++) {
                                    if (myCells.get(i).getBomb() == true) {
                                        myCells.get(i).setIcon(new ImageIcon("src/bomb.png"));
                                    }
                                }
                            } else {
                                int value = countAdjacent(c);
                                c.setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                                countRevealed ++;
                                if (value == 0) {
                                    revealAdjacent(c);
                                }
                            }
                            checkFinish();
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (isFinished == false) {
                            if (c.getRevealed() == false) {
                                if (c.getFlag() == false) {
                                    c.setFlag(true);
                                    c.setIcon(new ImageIcon("src/flag.png"));
                                    countFlag --;
                                    if (c.getBomb() == true) {
                                        realFlag ++;
                                    }
                                    status.setText(Integer.toString(countFlag) + " bombs left");
                                } else {
                                    c.setFlag(false);
                                    c.setIcon(null);
                                    countFlag ++;
                                    if (c.getBomb() == true) {
                                        realFlag --;
                                    }
                                    status.setText(Integer.toString(countFlag) + " bombs left");
                                }
                            }
                            checkFinish();
                        }
                    }
                }
                                
                public void mouseExited(MouseEvent e) {}
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
            });

            Random rand = new Random();
            int value = rand.nextInt(6); // random 0 or 5
            
            if (count != nBombs && value == 0) {
                c.setBomb(true);
                //c.setIcon(new ImageIcon("src/bomb.png"));
                count = count + 1;
            } else {
                c.setBomb(false);
            }
            myCells.add(c);
            add(c);
        }
    }

    private int countAdjacent(Cell c) { //condition: c.getBomb == false

        int res = 0;

        int x = c.getLocation().x;
        int y = c.getLocation().y;
        int index = 0;
        
        for (int i = 0; i < myCells.size(); i++) {
            if (x == myCells.get(i).getLocation().x && y == myCells.get(i).getLocation().y) {
                index = i;
                break;
            }
        }

        if (index == 0) {
            int[] index_neighbors = {index + 1, index + nCols, index + nCols + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        } else if (index == nCols - 1) {
            int[] index_neighbors = {index - 1, index + nCols, index + nCols - 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        } else if (index == nCols * (nCols - 1)) {
            int[] index_neighbors = {index - nCols, index - nCols + 1, index + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        } else if (index == (nCols - 1) * (nCols + 1)) {
            int[] index_neighbors = {index - nCols, index - nCols - 1, index - 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        } else if (index % nCols == 0) {
            int[] index_neighbors = {index - nCols, index - nCols + 1, index + 1, index + nCols, index + nCols + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        } else if (index % nCols == nCols - 1) {
            int[] index_neighbors = {index - nCols, index - nCols - 1, index - 1, index + nCols, index + nCols - 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        } else {
            int[] index_neighbors = {index - nCols - 1, index - nCols, index - nCols + 1, index - 1, index + 1, index + nCols - 1, index + nCols, index + nCols + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    boolean b = myCells.get(index_neighbors[i]).getBomb();
                    if (b == true) {
                        res = res + 1;
                    }
                }
            }
        }

        return res;
    }

    public void revealAdjacent(Cell c) {
        int x = c.getLocation().x;
        int y = c.getLocation().y;
        int index = 0;
        
        for (int i = 0; i < myCells.size(); i++) {
            if (x == myCells.get(i).getLocation().x && y == myCells.get(i).getLocation().y) {
                index = i;
                break;
            }
        }

        if (index == 0) {
            int[] index_neighbors = {index + 1, index + nCols, index + nCols + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        } else if (index == nCols - 1) {
            int[] index_neighbors = {index - 1, index + nCols, index + nCols - 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        } else if (index == nCols * (nCols - 1)) {
            int[] index_neighbors = {index - nCols, index - nCols + 1, index + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        } else if (index == (nCols - 1) * (nCols + 1)) {
            int[] index_neighbors = {index - nCols, index - nCols - 1, index - 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        } else if (index % nCols == 0) {
            int[] index_neighbors = {index - nCols, index - nCols + 1, index + 1, index + nCols, index + nCols + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        } else if (index % nCols == nCols - 1) {
            int[] index_neighbors = {index - nCols, index - nCols - 1, index - 1, index + nCols, index + nCols - 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        } else {
            int[] index_neighbors = {index - nCols - 1, index - nCols, index - nCols + 1, index - 1, index + 1, index + nCols - 1, index + nCols, index + nCols + 1};
            for (int i = 0; i < index_neighbors.length; i++) {
                if (index_neighbors[i] >= 0 && index_neighbors[i] < myCells.size()) {
                    if (myCells.get(index_neighbors[i]).getBomb() == false && myCells.get(index_neighbors[i]).getRevealed() == false) {
                        myCells.get(index_neighbors[i]).setRevealed(true);
                        int value = countAdjacent(myCells.get(index_neighbors[i]));
                        myCells.get(index_neighbors[i]).setIcon(new ImageIcon("src/" + Integer.toString(value) + ".png"));
                        countRevealed ++;
                        if (value == 0) {
                            revealAdjacent(myCells.get(index_neighbors[i]));
                        }
                    }
                }
            }
        }
    }

    public void checkFinish() {
        if (realFlag == nBombs || countRevealed == nCells - nBombs){
            if (isFinished == false) {
                isFinished = true; //win
                this.status.setText("Good job -_-");
                for (int i = 0; i < nCells; i++) {
                    if (myCells.get(i).getBomb() == true) {
                        myCells.get(i).setIcon(new ImageIcon("src/bomb.png"));
                    }
                }
            }
        }
    }
}