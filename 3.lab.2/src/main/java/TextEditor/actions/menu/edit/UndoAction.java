package TextEditor.actions.menu.edit;

import TextEditor.edit.UndoManager;
import TextEditor.edit.UndoStackObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction implements UndoStackObserver {

    UndoManager undoManager;

    public UndoAction() {
        this.undoManager = UndoManager.getInstance();
        undoManager.attachUndoStackObserver(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        undoManager.undo();
    }

    @Override
    public void updateUndoStackState(Boolean isEmpty) {
        setEnabled(!isEmpty);
    }

}

