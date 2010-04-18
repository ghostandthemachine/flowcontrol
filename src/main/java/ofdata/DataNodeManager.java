/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofdata;

import ofDataScene.DataScene;
import java.util.ArrayList;
import ofAtom.Atom;
import ofAtom.AtomFloat;
import ofAtom.AtomInt;
import ofAtom.AtomString;
import ofdata.objects.Add;
import ofdata.objects.DataFloat;
import ofdata.objects.Metro;
import ofdata.objects.Print;
import ofdata.objects.Toggle;
import ofvisual.UINodes.NodeMenu;
import ofvisual.node.ModelNode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon
 */
public class DataNodeManager {

    DataScene dataScene;
    ModelNode parent;
    ModelScene modelScene;

    public DataNodeManager(DataScene dScene, ModelNode node, ModelScene scene) {
        parent = node;
        dataScene = dScene;
        modelScene = scene;
    }

    public DataNode createObject(String s, ModelNode node) {
        ArrayList<Atom> arguments = this.stringToArguments(s);
        String title = this.sliceTitle(s);
        String fullTitle = s;
        DataNode returnNode = new DataNode("     ", node, dataScene, 1, 1);
        int argumentCount = arguments.size();
        System.out.println(s);
        if (title.equals("print")) {
            if (argumentCount > 0) {
                returnNode = new Print(s, node, dataScene);
                dataScene.addNode(returnNode);
                return returnNode;
            } else {
                returnNode = new Print(node, dataScene);
                dataScene.addNode(returnNode);
                return returnNode;
            }

        } else if (title.equals("f") || title.equals("float")) {
            returnNode = new DataFloat(parent, dataScene);
            dataScene.addNode(returnNode);
            return returnNode;

        } else if (title.equals("t") || title.equals("toggle")) {
            try {
                returnNode = new Toggle(parent, dataScene);
                dataScene.addNode(returnNode);
                return returnNode;
            } catch (java.lang.RuntimeException e) {
            }
        } else if (title.equals("metro")) {
            if (argumentCount > 0) {
                returnNode = new Metro(s, node, dataScene, arguments.get(0).getInt());
                dataScene.addNode(returnNode);
                return returnNode;
            } else {
                returnNode = new Metro(s, node, dataScene, 200);
                dataScene.addNode(returnNode);
                return returnNode;
            }
        } else if (title.equals("add")) {
            if (argumentCount > 0) {
                returnNode = new Add(s, node, dataScene, arguments.get(0).getInt());
                dataScene.addNode(returnNode);
                return returnNode;
            } else {
                returnNode = new Add(s, node, dataScene);
                dataScene.addNode(returnNode);
                return returnNode;
            }
        } else {
            returnNode = new DataNode(parent, dataScene);
            dataScene.addNode(returnNode);
            return returnNode;
        }
        return returnNode;
    }

    public void remove(DataNode node) {
        dataScene.removeNode(node);


    }

    public ArrayList stringToArguments(String fullTitle) {
        ArrayList<Atom> args = new ArrayList();
        String[] stringPattern = fullTitle.split(" ");
        ArrayList<String> atomType = new ArrayList<String>();


        for (int i = 0; i
                < stringPattern.length - 1; i++) {
            String arg = stringPattern[i];
            Atom atom = new Atom();


            if (arg.matches("[a-zA-Z][a-zA-Z0-9]*")) {
                atom = new AtomString(arg);
                args.add(0, atom);

            } else if (arg.matches("[0-9]+\\.[0-9]+")) {
                atom = new AtomFloat(Float.parseFloat(arg));
                args.add(0, atom);

            } else if (arg.matches("[0-9]+")) {
                atom = new AtomInt(Integer.parseInt(arg));
                args.add(0, atom);

            }
            atomType.add(0, atom.getAtomType());


        }
        return args;


    }

    public String sliceTitle(String fullTitle) {
        String[] stringPattern = fullTitle.split(" ");
        if (stringPattern.length == 0) {
            return null;
        }
        return stringPattern[0];

    }
}
