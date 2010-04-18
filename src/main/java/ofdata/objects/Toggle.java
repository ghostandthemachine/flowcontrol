/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ofdata.objects;

import ofDataScene.DataScene;
import ofdata.DataNode;
import ofvisual.UINodes.ModelToggle;
import ofvisual.node.ModelNode;

/**
 *
 * @author Jon
 */
public class Toggle extends DataNode{
    private boolean toggleState = false;
    public Toggle (ModelNode p, DataScene ds){
        super(p, ds);
        
    }
    
    public void hit(){
        toggleState = toggleState ? true : false;
        System.out.println(toggleState);
    }
    
    public void setParent() {
        ModelToggle toggle = (ModelToggle) this.getParent();
        toggle.setToggle(toggleState);
    }

}
