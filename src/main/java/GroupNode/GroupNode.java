/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GroupNode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import visual.node.NodeBorder;
import visual.node.VisualNode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class GroupNode extends VisualNode {

    private int radius;
    NodeBorder border = new NodeBorder(visualScene, borderRadius, 5, 3, new Color(255, 255, 255), new Color(250, 250, 250), 3);

    public GroupNode(VisualScene scene, int radius) {
        super(scene);
        this.radius = radius;

        labelWidget.setPreferredLocation(new Point(radius / 2, radius));


    }

    @Override
    protected Rectangle calculateClientArea() {
        return new Rectangle(0, 0, radius + 2, radius + 2);
    }

    @Override
    protected void paintWidget() {
        Graphics2D g = visualScene.getGraphics();

        Point2D center = new Point2D.Float(this.radius / 2, this.radius / 2);
        float[] locations = {0.6f, 0.9f};
        Color c1 = new Color(173, 255, 47, 125);
        Color c2 = new Color(34, 139, 34, 125);

        Color[] colors = {c1, c2};


        RadialGradientPaint gradient = new RadialGradientPaint(center, radius, locations, colors);

        g.setPaint(gradient);
        g.fillOval(0, 0, radius, radius);

        g.setPaint(Color.black);
        g.drawOval(0, 0, radius, radius);
    }
}
