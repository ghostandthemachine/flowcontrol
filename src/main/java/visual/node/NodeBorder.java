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

import org.netbeans.api.visual.border.Border;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.api.visual.widget.ResourceTable;
import org.netbeans.api.visual.widget.Widget;
import visual.scene.VisualScene;

/**
 * @author David Kaspar
 */
public class NodeBorder extends Widget implements Border {

    private int arcWidth;
    private int arcHeight;
    private int insetWidth;
    private int insetHeight;
    private Color fillColor;
    private Color drawColor;
    private DrawResourceTableListener drawListener = null;
    private FillResourceTableListener fillListener = null;
    private float thickness;

    public NodeBorder(VisualScene scene, int arc, int insetWidth, int insetHeight, Color fillColor, Color drawColor, int thickness) {
        super(scene);
        this.arcWidth = arc;
        this.arcHeight = arc;
        this.insetWidth = insetWidth;
        this.insetHeight = insetHeight;
        this.fillColor = fillColor;
        this.drawColor = drawColor;
        this.thickness = thickness;
    }

    public Insets getInsets() {
        return new Insets(insetHeight, insetWidth, insetHeight, insetWidth);
    }

    public void paint(Graphics2D gr, Rectangle bounds) {
        if (fillColor != null) {
            gr.setColor(fillColor);
            gr.fill(new RoundRectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height, arcWidth, arcHeight));
        }
        if (drawColor != null) {
            gr.setColor(drawColor);
            gr.setStroke(new BasicStroke(thickness));
            gr.draw(new RoundRectangle2D.Float(bounds.x + thickness / 2, bounds.y + thickness / 2, bounds.width - thickness, bounds.height - thickness, arcWidth, arcHeight));
        }
    }

    public class DrawResourceTableListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent event) {
            drawColor = (Color) event.getNewValue();
        }
    }

    public class FillResourceTableListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent event) {
            fillColor = (Color) event.getNewValue();
        }
    }

    public void setFill(Color c) {
        fillColor = c;
    }

    public void setDraw(Color c) {
        drawColor = c;
    }


    
}
