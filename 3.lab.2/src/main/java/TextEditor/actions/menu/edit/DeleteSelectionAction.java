package TextEditor.actions.menu.edit;

import TextEditor.TextEditorModel;
import TextEditor.location.LocationRange;
import TextEditor.range.SelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteSelectionAction extends AbstractAction implements SelectionObserver {

    TextEditorModel textEditorModel;

    public DeleteSelectionAction(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
        textEditorModel.attachRangeObserver(this);
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEditorModel.deleteRange(textEditorModel.getSelectionRange());
    }

    @Override
    public void onSelectionChanged(LocationRange selectionRange) {
        setEnabled(textEditorModel.selectionExists());
    }

}
