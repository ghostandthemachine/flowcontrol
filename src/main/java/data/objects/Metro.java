package data.objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import atom.AtomInt;
import dataScene.DataScene;
import data.DataNode;
import visual.node.VisualNode;

public class Metro extends DataNode {
    float delayTime = 100;
    int trigger = 0;
    TimerListener listener = new TimerListener(this);
    Timer timer = new Timer((int) delayTime, listener);
    int counter = 0;

    public Metro(String title, VisualNode parent, DataScene ds, float delay) {
        super(title, parent, ds, 2, 1);
        this.setValue(new AtomInt(0));
        delayTime = delay;
        timer.addActionListener(new TimerListener(this));
        timer.start();
    }

    public Metro(String title, VisualNode parent, DataScene ds,  int delay) {
        super(title, parent, ds, 2, 1);
        this.setValue(new AtomInt(0));
        delayTime = delay;
        timer.addActionListener(new TimerListener(this));
        timer.start();
    }

    @Override
    public void update() {
        this.setOutput(0, this.getValue());
        updateConnections();
        this.getParent().repaint();
    }
}

class TimerListener implements ActionListener {

    Metro node;

    TimerListener(Metro n) {
        node = n;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        node.counter++;
        node.setValue(new AtomInt(node.counter));
        node.update();
    }
}
