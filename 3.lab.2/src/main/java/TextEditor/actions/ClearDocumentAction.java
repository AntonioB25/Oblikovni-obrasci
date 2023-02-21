package TextEditor.actions;

import TextEditor.TextEditorModel;
import TextEditor.edit.EditAction;

import java.util.List;

public class ClearDocumentAction implements EditAction {

    TextEditorModel textEditorModel;
    List<String>    deletedDocument;

    public ClearDocumentAction(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void execute_do() {
        deletedDocument = textEditorModel.getLines();
        textEditorModel.clearDocument();
    }

    @Override
    public void execute_undo() {
        textEditorModel.setLines(deletedDocument);
    }

}
