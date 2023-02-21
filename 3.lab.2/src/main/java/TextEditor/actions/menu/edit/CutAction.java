package TextEditor.actions.menu.edit;

import TextEditor.TextEditorModel;
import TextEditor.clipboard.ClipboardStack;
import TextEditor.location.LocationRange;
import TextEditor.range.SelectionObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CutAction extends AbstractAction implements SelectionObserver {

    TextEditorModel textEditorModel;
    ClipboardStack  clipboard;

    public CutAction(TextEditorModel textEditorModel, ClipboardStack clipboard) {
        this.textEditorModel = textEditorModel;
        this.clipboard = clipboard;
        setEnabled(false);
        textEditorModel.attachRangeObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String copiedString = textEditorModel.getSelectedText(textEditorModel.getSelectionRange());
        textEditorModel.deleteRange(textEditorModel.getSelectionRange());
        clipboard.push(copiedString);

    }

    @Override
    public void onSelectionChanged(LocationRange selectionRange) {
        setEnabled(textEditorModel.selectionExists());
    }

}
