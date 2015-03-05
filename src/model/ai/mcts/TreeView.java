package model.ai.mcts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Simon M. Lucas
 * sml@essex.ac.uk
 * Date: 29-Nov-2010
 * Time: 22:23:45
 * <p/>
 * Simple TreeView for a MCTS Tree
 */
public class TreeView extends JComponent {

    Node root;
    int nw = 30;
    int nh = 20;
    int inset = 20;
    int minWidth = 300;
    int heightPerLevel = 40;
    Color fg = Color.black;
    Color bg = Color.cyan;
    Color nodeBg = Color.white;

    public TreeView(Node root) {
        this.root = root;
    }

    public void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = inset;
        int x = getWidth() / 2;
        g.setColor(bg);
        g.fillRect(0, 0, getWidth(), getHeight());
        draw(g, root, x, y, (int) (1.1 * getWidth()));
    }

    private void draw(Graphics2D g, Node cur, int x, int y, int wFac) {
        // draw this one, then it's children
        int arity = cur.getChildren().size();
        for (int i = 0; i < arity; i++) {
            if (cur.getChildren().get(i).getVisits() > 0) {
                int xx = (int) ((i + 1.0) * wFac / (arity + 1) + (x - wFac / 2));
                int yy = y + heightPerLevel;
                g.setColor(fg);
                g.drawLine(x, y, xx, yy);
                draw(g, cur.getChildren().get(i), xx, yy, wFac / arity);
            }
        }
        drawNode(g, cur, x, y);
    }

    private void drawNode(Graphics2D g, Node node, int x, int y) {
        String s = String.format("%s %d", node.getMove(), (int) (node.getWinProbability() * 100));
        g.setColor(nodeBg);
        g.fillOval(x - nw / 2, y - nh / 2, nw, nh);
        g.setColor(fg);
        g.drawOval(x - nw / 2, y - nh / 2, nw, nh);
        g.setColor(fg);
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(s, g);
        g.drawString(s, x - (int) (rect.getWidth() / 2), (int) (y + rect.getHeight() / 2));
    }

    public Dimension getPreferredSize() {
        // should make this depend on the tree ...
        return new Dimension(minWidth, heightPerLevel * (10 - 1) + inset * 2);
    }

    public TreeView showTree(String title) {
        new JEasyFrame(this, title);
        return this;
    }
}