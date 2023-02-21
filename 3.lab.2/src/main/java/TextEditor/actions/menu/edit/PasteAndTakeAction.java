package TextEditor.actions.menu.edit;

import TextEditor.TextEditorModel;
import TextEditor.clipboard.ClipboardObserver;
import TextEditor.clipboard.ClipboardStack;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PasteAndTakeAction extends AbstractAction implements ClipboardObserver {

    TextEditorModel textEditorModel;
    ClipboardStack  clipboard;

    public PasteAndTakeAction(TextEditorModel textEditorModel, ClipboardStack clipboard) {
        this.textEditorModel = textEditorModel;
        this.clipboard = clipboard;
        setEnabled(false);
        clipboard.attachClipboardObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String topText = clipboard.pop();
        textEditorModel.insert(topText);
    }

    @Override
    public void updateClipboard(ClipboardStack clipboard) {
        setEnabled(!clipboard.empty());
    }

}
