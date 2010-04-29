package visual.node;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;

public class CustomConnectionWidget extends ConnectionWidget {
    private Point lastPoint;

    public CustomConnectionWidget(Scene scene) {
        super(scene);
    }

    @Override
    protected void paintWidget() {
        Graphics2D gr = getGraphics();
        gr.setColor(getForeground());
        GeneralPath path = null;

        Point firstControlPoint = getFirstControlPoint();
        Point lastControlPoint = getLastControlPoint();

        //checking to see if we should draw line through the AnchorShape. If the
        //AnchorShape is hollow, the cutdistance will be true.
        boolean isSourceCutDistance = this.getSourceAnchorShape().getCutDistance() != 0.0;
        boolean isTargetCutDistance = this.getTargetAnchorShape().getCutDistance() != 0.0;

        double firstControlPointRotation =
                firstControlPoint != null && (this.getSourceAnchorShape().isLineOriented()
                || isSourceCutDistance)
                ? this.getSourceAnchorShapeRotation() : 0.0;
        double lastControlPointRotation =
                lastControlPoint != null
                && (this.getTargetAnchorShape().isLineOriented() || isTargetCutDistance)
                ? getTargetAnchorShapeRotation() : 0.0;

        List<Point> points;
        if ((isSourceCutDistance || isTargetCutDistance) && this.getControlPoints().size() >= 2) {
            points = new ArrayList<Point>(this.getControlPoints());
            points.set(0, new Point(
                    firstControlPoint.x + (int) (this.getSourceAnchorShape().getCutDistance() * Math.cos(firstControlPointRotation)),
                    firstControlPoint.y + (int) (this.getSourceAnchorShape().getCutDistance() * Math.sin(firstControlPointRotation))));
            points.set(this.getControlPoints().size() - 1, new Point(
                    lastControlPoint.x + (int) (this.getTargetAnchorShape().getCutDistance() * Math.cos(lastControlPointRotation)),
                    lastControlPoint.y + (int) (this.getTargetAnchorShape().getCutDistance() * Math.sin(lastControlPointRotation))));
        } else {
            points = this.getControlPoints();
        }

        if (this.getControlPointCutDistance() > 0) {
            for (int a = 0; a < points.size() - 1; a++) {
                Point p1 = points.get(a);
                Point p2 = points.get(a + 1);
                double len = p1.distance(p2);

                if (a > 0) {
                    Point p0 = points.get(a - 1);
                    double ll = p0.distance(p1);
                    if (len < ll) {
                        ll = len;
                    }
                    ll /= 2;
                    double cll = this.getControlPointCutDistance();
                    if (cll > ll) {
                        cll = ll;
                    }
                    double direction = Math.atan2(p2.y - p1.y, p2.x - p1.x);
                    if (!Double.isNaN(direction)) {
                        path = customAddToPath(path,
                                p1.x + (int) (cll * Math.cos(direction)),
                                p1.y + (int) (cll * Math.sin(direction)));
                    }
                } else {
                    path = customAddToPath(path, p1.x, p1.y);
                }

                if (a < points.size() - 2) {
                    Point p3 = points.get(a + 2);
                    double ll = p2.distance(p3);
                    if (len < ll) {
                        ll = len;
                    }
                    ll /= 2;
                    double cll = this.getControlPointCutDistance();
                    if (cll > ll) {
                        cll = ll;
                    }
                    double direction = Math.atan2(p2.y - p1.y, p2.x - p1.x);
                    if (!Double.isNaN(direction)) {
                        path = customAddToPath(path,
                                p2.x - (int) (cll * Math.cos(direction)),
                                p2.y - (int) (cll * Math.sin(direction)));
                    }
                } else {
                    path = customAddToPath(path, p2.x, p2.y);
                }
            }
        } else {
            for (Point point : points) {
                path = customAddToPath(path, point.x, point.y);
            }
        }
        if (path != null) {
            Stroke previousStroke = gr.getStroke();
            gr.setPaint(getForeground());
            gr.setStroke(getStroke());
            gr.draw(path);
            gr.setStroke(previousStroke);
        }


        AffineTransform previousTransform;

        if (firstControlPoint != null) {
            previousTransform = gr.getTransform();
            gr.translate(firstControlPoint.x, firstControlPoint.y);
            if (this.getSourceAnchorShape().isLineOriented()) {
                gr.rotate(firstControlPointRotation);
            }
            this.getSourceAnchorShape().paint(gr, true);
            gr.setTransform(previousTransform);
        }

        if (lastControlPoint != null) {
            previousTransform = gr.getTransform();
            gr.translate(lastControlPoint.x, lastControlPoint.y);
            if (this.getTargetAnchorShape().isLineOriented()) {
                gr.rotate(lastControlPointRotation);
            }
            this.getTargetAnchorShape().paint(gr, false);
            gr.setTransform(previousTransform);
        }

        if (this.isPaintControlPoints()) {
            int last = this.getControlPoints().size() - 1;
            for (int index = 0; index <= last; index++) {
                Point point = this.getControlPoints().get(index);
                previousTransform = gr.getTransform();
                gr.translate(point.x, point.y);
                if (index == 0 || index == last) {
                    this.getEndPointShape().paint(gr);
                } else {
                    this.getControlPointShape().paint(gr);
                }
                gr.setTransform(previousTransform);
            }
        }
    }

    private GeneralPath customAddToPath(GeneralPath path, int x, int y) {
        if (path == null) {
            path = new GeneralPath();
            path.moveTo(x, y);
        } else {
            int x1 = (int) ((x - lastPoint.getX()));
            int y1 = (int) ((y - lastPoint.getY()));
            int x2 = x;
            int y2 = y;
            path.quadTo(x1, y1, x2, y2);
        }
        lastPoint = new Point(x,y);

        return path;
    }

    /**
     * Returns the rotation of the source anchor shape.
     * @return the source anchor shape rotation
     */
    private double getSourceAnchorShapeRotation() {
        if (this.getControlPoints().size() <= 1) {
            return 0.0;
        }
        Point point1 = this.getControlPoints().get(0);
        Point point2 = this.getControlPoints().get(1);
        return Math.atan2(point2.y - point1.y, point2.x - point1.x);
    }
}
