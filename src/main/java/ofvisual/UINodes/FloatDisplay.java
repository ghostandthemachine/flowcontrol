/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.UINodes;

import ofDataScene.DataScene;
import ofdata.DataNode;
import ofdata.objects.DataFloat;
import ofvisual.node.UINode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon Rose
 */
public class FloatDisplay extends UINode {

    public FloatDisplay(ModelScene s, DataScene ds) {
        super(s, ds);
        this.setDataNode(new DataFloat(this, ds));
        this.removeLabelEditor();
    }

    public FloatDisplay(ModelScene modelScene, DataScene ds, DataNode dataNode) {
        super(modelScene, ds);
        this.setDataNode(dataNode);
        this.removeLabelEditor();
    }

    public void setValue(int i) {
        this.setValue(i);
    }

    @Override
    public void updateModel() {
        this.getDataNode().update();
    }

    @Override
    public void updateUI() {
        System.out.println(this.getValue());
        if (this.getMouseDragged()) {
            this.setValue((float) (this.getValue() + (this.getxVelocity() + this.getyVelocity())));
        }
        this.setLabel(Float.toString(this.getValue()));
    }
}
