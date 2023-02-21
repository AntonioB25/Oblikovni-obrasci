package TextEditor.actions.menu.move;

import TextEditor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CursorToDocumentEndAction extends AbstractAction {

    TextEditorModel textEditorModel;

    public CursorToDocumentEndAction(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEditorModel.setCursorLocation(textEditorModel.getEndLocation());
    }

}
