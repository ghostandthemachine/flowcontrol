/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class PortLine extends Widget {

    private int size = 0;
    private Color portPaint = Color.red;

    public PortLine(Scene scene, int s) {
        super(scene);
        size = s;
    }

    @Override
    protected Rectangle calculateClientArea() {
        return new Rectangle(-size, -size, 2 * size + 1, 2 * size + 1);
    }

    @Override
    protected void paintWidget() {
        Graphics2D g = getGraphics();
        g.setColor(getForeground());
        g.setStroke(new BasicStroke(2f));
        g.drawLine(-size + (size / 3), 0, size - (size / 3), 0);
        g.setStroke(new BasicStroke(1f));
    }

    public Color getPortPaint() {
        return portPaint;
    }

    public void setPortPaint(Color portPaint) {
        this.portPaint = portPaint;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
