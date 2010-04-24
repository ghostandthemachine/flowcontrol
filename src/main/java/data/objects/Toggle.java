/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.objects;

import dataScene.DataScene;
import data.DataNode;
import visual.UINodes.VisualToggle;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class Toggle extends DataNode{
    private boolean toggleState = false;
    public Toggle (VisualNode p, DataScene ds){
        super(p, ds);
        
    }
    
    public void hit(){
        toggleState = toggleState ? true : false;
        System.out.println(toggleState);
    }
    
    public void setParent() {
        VisualToggle toggle = (VisualToggle) this.getParent();
        toggle.setToggle(toggleState);
    }

}
