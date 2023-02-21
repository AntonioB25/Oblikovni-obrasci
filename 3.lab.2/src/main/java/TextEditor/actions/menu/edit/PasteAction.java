package TextEditor.actions.menu.edit;

import TextEditor.TextEditorModel;
import TextEditor.clipboard.ClipboardObserver;
import TextEditor.clipboard.ClipboardStack;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Antonio Bukovac
 */
public class PasteAction extends AbstractAction implements ClipboardObserver {

    TextEditorModel textEditorModel;
    ClipboardStack  clipboard;

    public PasteAction(TextEditorModel textEditorModel, ClipboardStack clipboard) {
        this.textEditorModel = textEditorModel;
        this.clipboard = clipboard;
        setEnabled(false);
        clipboard.attachClipboardObserver(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String topText = clipboard.peek();
        textEditorModel.insert(topText);
    }

    @Override
    public void updateClipboard(ClipboardStack clipboard) {
        System.out.println("CLIPBOARD U PASTE: " + clipboard.empty());
        setEnabled(!clipboard.empty());
    }

}
