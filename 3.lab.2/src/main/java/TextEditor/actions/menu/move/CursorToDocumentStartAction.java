package TextEditor.actions.menu.move;

import TextEditor.TextEditorModel;
import TextEditor.location.Location;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CursorToDocumentStartAction extends AbstractAction {

    TextEditorModel textEditorModel;

    public CursorToDocumentStartAction(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textEditorModel.setCursorLocation(Location.getStartLocation());
    }

}
