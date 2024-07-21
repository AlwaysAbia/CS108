import java.awt.*;
import javax.swing.*;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.awt.Toolkit;


public class JBrainTetris extends JTetris {
    protected int rCount;
    private Brain brian;
    private Brain.Move move;
    private Brain.Move testM;

    private JCheckBox brainMode;
    private JCheckBox animateFallingMode;
    private JSlider adversary;
    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
        brian = new DefaultBrain();
    }

    @Override
    public JComponent createControlPanel(){
        //Everything in the superclass method
        JComponent panel = super.createControlPanel();

        panel.add(new JLabel("Brain:"));
        brainMode = new JCheckBox("Brain active");
        animateFallingMode = new JCheckBox("Animate falling mode");
        panel.add(brainMode);
        panel.add(animateFallingMode);
        animateFallingMode.setSelected(true); //By Default animates falling

        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary);
        panel.add(adversary);

        return panel;
    }

    @Override
    public Piece pickNextPiece(){ //For Adversary Mode
        if(super.random.nextInt(99) < adversary.getValue()){//For Example, if slider is 100, this will always happen
            Piece nextPiece = super.pickNextPiece();
            double largestScore = 0;
            for(Piece piece: pieces){
                testM = brian.bestMove(board,piece,board.getHeight()-TOP_SPACE, testM);
                if(testM != null && testM.score > largestScore){
                    largestScore = testM.score;
                    nextPiece = piece;
                }
            }
            return nextPiece;
        }
        else{
            return super.pickNextPiece();
        }
    }

    @Override
    public void tick(int verb){
        if(brainMode.isSelected() && verb == DOWN){
            if(rCount != super.count){
                rCount = super.count;
                board.undo();
                move = brian.bestMove(board,super.currentPiece,board.getHeight()-TOP_SPACE,move);
            }
            if(move != null){
                if(!move.piece.equals(currentPiece)) super.tick(ROTATE);
                if(move.x < currentX) super.tick(LEFT);
                else if(move.x > currentX) super.tick(RIGHT);
                else if(!animateFallingMode.isSelected() && move.x == currentX && currentY > move.y) super.tick(DROP);
            }
        }
        super.tick(verb);
    }


    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}

