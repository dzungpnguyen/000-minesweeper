import javax.swing.JButton;

public class Cell extends JButton {

    private final int cSize = 20;

    private boolean isRevealed;
    private boolean isBomb;
    private boolean isFlagged;

    public Cell() {
        setSize(cSize, cSize);
    }

    public void setFlag(boolean b) {
        this.isFlagged = b;
    }

    public boolean getFlag() {
        return this.isFlagged;
    }

    public void setBomb(boolean b) {
        this.isBomb = b;
    }
    
    public boolean getBomb() {
        return this.isBomb;
    }

    public void setRevealed(boolean b) {
        this.isRevealed = b;
    }

    public boolean getRevealed() {
        return this.isRevealed;
    }

    public int getCSize() {
        return this.cSize;
    }
}