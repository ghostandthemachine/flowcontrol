/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import dataScene.DataScene;
import java.util.ArrayList;
import atom.Atom;
import atom.AtomFloat;
import atom.AtomInt;
import atom.AtomString;
import data.DataNode;
import data.objects.Add;
import data.objects.DataFloat;
import data.objects.DeadDataNode;
import data.objects.IUGenDataObject;
import data.objects.Metro;
import data.objects.Print;
import data.objects.Toggle;
import overtoneinterface.IUGen;
import visual.scene.VisualScene;

/**
 *
 * @author Jon Rose
 */
public class NodeCreator {

    DataScene dataScene;
    VisualNode node;
    VisualScene visualScene;

    public NodeCreator(DataScene dScene, VisualNode node, VisualScene scene) {
        this.node = node;
        dataScene = dScene;
        visualScene = scene;
    }

    public DataNode createDataNode(IUGen ugen, VisualNode visNode) {
        if (node.getConnections().length > 0) {
          //  visualScene.removeConnections(node);
        }
        DataNode dNode = new IUGenDataObject(ugen.getName(), visNode, dataScene, ugen.numInputs(), ugen.numOutputs(), ugen.getRate());
        return dNode;
    }

    public DataNode createDataNode(String s, VisualNode node) {

        if (node.getConnections().length > 0) {
            visualScene.removeConnections(node);
        }

        ArrayList<Atom> arguments = this.stringToArguments(s);
        String title = this.sliceTitle(s);
        String fullTitle = s;
        DataNode returnNode = new DataNode("     ", node, dataScene, 1, 1);
        int argumentCount = arguments.size();
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
            returnNode = new DataFloat(this.node, dataScene);
            dataScene.addNode(returnNode);
            return returnNode;

        } else if (title.equals("t") || title.equals("toggle")) {
            try {
                returnNode = new Toggle(this.node, dataScene);
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
            returnNode = new DeadDataNode(s, node, dataScene, 0,0);
            dataScene.addNode(returnNode);
            System.err.println("'" + s + "'  is not a valid node in the library");
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
