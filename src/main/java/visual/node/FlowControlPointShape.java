package visual.node;

import org.netbeans.modules.visual.anchor.SquarePointShape;

import java.awt.*;

/**
 * @author Jon Rose
 */
public interface FlowControlPointShape {

    /**
     * Returns a radius of the shape.
     * @return the radius
     */
    public int getRadius();

    /**
     * Renders a shape into the graphics instance
     * @param graphics
     */
    public void paint(Graphics2D graphics);
    /**
     * The empty point shape.
     */
    public static final FlowControlPointShape NONE = new FlowControlPointShape() {

        public int getRadius() {
            return 0;
        }

        public void paint(Graphics2D graphics) {
        }
    };
    /**
     * The 8px big filled-square shape.
     */
    public static final SquarePointShape SQUARE_FILLED_BIG = new SquarePointShape(4, true);
    /**
     * The 6px big filled-square shape.
     */
    public static final SquarePointShape SQUARE_FILLED_SMALL = new SquarePointShape(3, true);

        /**
     * The 8px big filled-square shape.
     */
    public static final CirclePointShape CIRCLE_FILLED_BIG = new CirclePointShape(4, true);
    /**
     * The 6px big filled-square shape.
     */
    public static final CirclePointShape CIRCLE_FILLED_SMALL = new CirclePointShape(3, true);

}
