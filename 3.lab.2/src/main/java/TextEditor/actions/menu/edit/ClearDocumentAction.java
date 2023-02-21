package TextEditor.actions.menu.edit;

import TextEditor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClearDocumentAction extends AbstractAction {

    TextEditorModel textEditorModel;

    public ClearDocumentAction(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEditorModel.clearDocument();
    }

}
