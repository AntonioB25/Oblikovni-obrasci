package TextEditor.actions.menu.edit;

import TextEditor.edit.RedoStackObserver;
import TextEditor.edit.UndoManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction implements RedoStackObserver {

    UndoManager undoManager;

    public RedoAction() {
        this.undoManager = UndoManager.getInstance();
        undoManager.attachRedoStackObserver(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        undoManager.redo();
    }

    @Override
    public void updateRedoStackState(Boolean isEmpty) {
        setEnabled(!isEmpty);
    }

}
