package visual.node;

import org.netbeans.api.visual.anchor.PointShape;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * @author Jon Rose
 */
public final class CirclePointShape implements PointShape {

    private int size;
    private boolean filled;

    /**
     * Creates a circle shape.
     * @param size   the size
     * @param filled if true, then the shape is filled
     */
    public CirclePointShape(int size, boolean filled) {
        this.size = size;
        this.filled = filled;
    }

    @Override
    public int getRadius() {
        return (int) Math.ceil(1.5f * size);
    }

    @Override
    public void paint(Graphics2D graphics) {
        int size2 = size + size;
        Ellipse2D innerCircle = new Ellipse2D.Float(-size, -size, size2, size2);
        if (filled) {
            graphics.fill(innerCircle);
        } else {
            graphics.draw(innerCircle);
        }
        int size3 = size2 + size2;
        Ellipse2D outerCircle = new Ellipse2D.Float(-size2, -size2, size3, size3);
        graphics.setStroke(new BasicStroke(2.0f));
        graphics.draw(outerCircle);
    }
}
