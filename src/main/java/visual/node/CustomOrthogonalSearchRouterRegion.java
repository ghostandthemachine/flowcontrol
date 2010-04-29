/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package visual.node;

import org.netbeans.api.visual.anchor.Anchor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Kaspar
 */
final class CustomOrthogonalSearchRouterRegion extends Rectangle {

    public static final int MIN_INT_REGION = -20000;
    public static final int MAX_INT_REGION = 20000;

//    private OrthogonalLinkRouterRegion parent;
    private Anchor.Direction direction;
    private boolean horizontal;
    private int depth;

    public CustomOrthogonalSearchRouterRegion (/*OrthogonalLinkRouterRegion parentRegion, */int x, int y, int width, int height, Anchor.Direction direction, int depth) {
        super (x, y, width, height);
//        this.parent = parentRegion;
        this.direction = direction;
        switch (direction) {
            case LEFT:
            case RIGHT:
                horizontal = true;
                break;
            case TOP:
            case BOTTOM:
                horizontal = false;
                break;
            default:
                throw new IllegalArgumentException ();
        }
        this.depth = depth;
    }

    public Anchor.Direction getDirection () {
        return direction;
    }

    public boolean isHorizontal () {
        return horizontal;
    }

    public int getDepth () {
        return depth;
    }

    public void extendToInfinity () {
        switch (direction) {
            case LEFT:
                width = x - MIN_INT_REGION;
                x = MIN_INT_REGION;
                break;
            case RIGHT:
                width = MAX_INT_REGION - x;
                break;
            case TOP:
                height = y - MIN_INT_REGION;
                y = MIN_INT_REGION;
                break;
            case BOTTOM:
                height = MAX_INT_REGION - y;
                break;
            default:
                throw new IllegalArgumentException ();
        }
    }

    public int compareImportant (Rectangle collisionToCheck, Rectangle currentMostImportant) {
        // return: <0 if important, 0 if the same, >0 if not important
        if (! intersectsZero (collisionToCheck))
            return Integer.MAX_VALUE;
        if (currentMostImportant == null)
            return Integer.MIN_VALUE;
        switch (direction) {
            case LEFT:
                return (currentMostImportant.x + currentMostImportant.width) - (collisionToCheck.x + collisionToCheck.width);
            case RIGHT:
                return collisionToCheck.x - currentMostImportant.x;
            case TOP:
                return (currentMostImportant.y + currentMostImportant.height) - (collisionToCheck.y + collisionToCheck.height);
            case BOTTOM:
                return collisionToCheck.y - currentMostImportant.y;
            default:
                throw new IllegalArgumentException ();
        }
    }

    private boolean intersectsZero (Rectangle r) {
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        int tw = this.width + tx;
        int th = this.height + ty;
        int rw = r.width + rx;
        int rh = r.height + ry;
        return rw > tx && rh > ty && tw > rx && th > ry;
    }

    // TODO - optimize
    private void parseIntervalsBy (ArrayList<CustomOrthogonalSearchRouterRegion> intervals, Rectangle collision) {
        int colStart;
        int colEnd;

        if (horizontal) {
            colStart = collision.y;
            colEnd = collision.y + collision.height;
        } else {
            colStart = collision.x;
            colEnd = collision.x + collision.width;
        }

        for (int i = 0; i < intervals.size ();) {
            CustomOrthogonalSearchRouterRegion region = intervals.get (i);
            int regStart;
            int regEnd;
            if (horizontal) {
                regStart = region.y;
                regEnd = region.y + region.height;
            } else {
                regStart = region.x;
                regEnd = region.x + region.width;
            }

            if (colEnd <= regStart || colStart >= regEnd) {
                // outside
                i ++;
                continue;
            }
            if (colStart <= regStart && colEnd >= regEnd) {
                // fully contained
                intervals.remove (i);
                continue;
            }
            if (colStart > regStart && colEnd < regEnd) {
                // split to two
                CustomOrthogonalSearchRouterRegion clonedRegion = region.cloneExactly ();
                int len1 = colStart - regStart;
                int len2 = regEnd - colEnd;
                if (horizontal) {
                    region.height = len1;
                    clonedRegion.y = regEnd - len2;
                    clonedRegion.height = len2;
                    intervals.add (i + 1, clonedRegion);
                } else {
                    region.width = len1;
                    clonedRegion.x = regEnd - len2;
                    clonedRegion.width = len2;
                    intervals.add (i + 1, clonedRegion);
                }
                return;
            }
            if (colStart <= regStart && colEnd > regStart && colEnd < regEnd) {
                // cut region start
                int difference = colEnd - regStart;
                if (horizontal) {
                    region.y += difference;
                    region.height -= difference;
                } else {
                    region.x += difference;
                    region.width -= difference;
                }
                i ++;
                continue;
            }
            if (colEnd >= regEnd && colStart > regStart && colStart < regEnd) {
                // cut region end
                int difference = regEnd - colStart;
                if (horizontal) {
                    region.height -= difference;
                } else {
                    region.width -= difference;
                }
                i++;
                continue;
            }
            throw new IllegalStateException ();
        }
    }

    public CustomOrthogonalSearchRouterRegion cloneExactly () {
        return new CustomOrthogonalSearchRouterRegion (/*parent, */x, y, width, height, direction, depth);
    }

    public ArrayList<CustomOrthogonalSearchRouterRegion> parseSubRegions (List<Rectangle> collisions) {
        ArrayList<Rectangle> importantCollisions = new ArrayList<Rectangle> ();

        Rectangle importantCollision = null;
        for (Rectangle collision : collisions) {
            int howMuch = compareImportant (collision, importantCollision);
            if (howMuch <= 0) {
                if (howMuch < 0) {
                    importantCollisions.clear ();
                    importantCollision = collision;
                }
                importantCollisions.add (collision);
            }
        }

        ArrayList<CustomOrthogonalSearchRouterRegion> subRegions = new ArrayList<CustomOrthogonalSearchRouterRegion> ();
        if (importantCollisions.size () > 0) {
            cutLengthBy (importantCollision);
            subRegions.add (cloneWithForwardEdge ());
            for (Rectangle collision : importantCollisions)
                parseIntervalsBy (subRegions, collision);
        }
        return subRegions;
    }

    public CustomOrthogonalSearchRouterRegion cloneWithForwardEdge () {
        return cloneWithEdge (/*parent, */direction, depth);
    }

    public CustomOrthogonalSearchRouterRegion cloneWithCounterClockwiseEdge () {
        return cloneWithEdge (/*this, */getCounterClockWiseDirection (direction), depth + 1);
    }

    public CustomOrthogonalSearchRouterRegion cloneWithClockwiseEdge () {
        return cloneWithEdge (/*this, */getClockWiseDirection (direction), depth + 1);
    }

    private CustomOrthogonalSearchRouterRegion cloneWithEdge (/*OrthogonalLinkRouterRegion parent, */Anchor.Direction dir, int depth) {
        switch (dir) {
            case LEFT:
                return new CustomOrthogonalSearchRouterRegion (/*parent, */x, y, 0, height, dir, depth);
            case RIGHT:
                return new CustomOrthogonalSearchRouterRegion (/*parent, */x + width, y, 0, height, dir, depth);
            case TOP:
                return new CustomOrthogonalSearchRouterRegion (/*parent, */x, y, width, 0, dir, depth);
            case BOTTOM:
                return new CustomOrthogonalSearchRouterRegion (/*parent, */x, y + height, width, 0, dir, depth);
            default:
                throw new IllegalArgumentException ();
        }
    }

    private void cutLengthBy (Rectangle collision) {
        switch (direction) {
            case LEFT: {
                int toBeMin = collision.x + collision.width;
                int currently = x;
                if (toBeMin > x + width) {
                    x += width;
                    width = 0;
                } else if (toBeMin > currently) {
                    width = x + width - toBeMin;
                    x = toBeMin;
                }
            }
            break;
            case RIGHT: {
                int toBeMax = collision.x;
                int currently = x + width;
                if (toBeMax < x) {
                    width = 0;
                } else if (toBeMax < currently) {
                    width = toBeMax - x;
                }
            }
            break;
            case TOP: {
                int toBeMin = collision.y + collision.height;
                int currently = y;
                if (toBeMin > y + height) {
                    y += height;
                    height = 0;
                } else if (toBeMin > currently) {
                    height = y + height - toBeMin;
                    y = toBeMin;
                }
            }
            break;
            case BOTTOM: {
                int toBeMax = collision.y;
                int currently = y + height;
                if (toBeMax < y) {
                    height = 0;
                } else if (toBeMax < currently) {
                    height = toBeMax - y;
                }
            }
            break;
            default:
                throw new IllegalArgumentException ();
        }
    }

    public int getLength () {
        return horizontal ? width : height;
    }

    public int getDistance (Point point) {
        return horizontal ? Math.abs (point.y - y) : Math.abs (point.x - x);
    }

    public boolean containsInsideEdges (Point point) {
        return point.x >= x && point.x <= x + width && point.y >= y && point.y <= y + height;
    }

    public String toString () {
        return "pos: " + x + "," + y + " size: " + width + "," + height + " dir: " + direction + " depth: " + depth;
    }

    public static Anchor.Direction getCounterClockWiseDirection (Anchor.Direction direction) {
        switch (direction) {
            case LEFT:
                return Anchor.Direction.BOTTOM;
            case RIGHT:
                return Anchor.Direction.TOP;
            case TOP:
                return Anchor.Direction.LEFT;
            case BOTTOM:
                return Anchor.Direction.RIGHT;
            default:
                throw new IllegalArgumentException ();
        }
    }

    public static Anchor.Direction getClockWiseDirection (Anchor.Direction direction) {
        switch (direction) {
            case LEFT:
                return Anchor.Direction.TOP;
            case RIGHT:
                return Anchor.Direction.BOTTOM;
            case TOP:
                return Anchor.Direction.RIGHT;
            case BOTTOM:
                return Anchor.Direction.LEFT;
            default:
                throw new IllegalArgumentException ();
        }
    }

}
