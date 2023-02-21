package TextEditor.actions;

import TextEditor.TextEditorModel;
import TextEditor.edit.EditAction;
import TextEditor.location.Location;

/**
 * @author Antonio Bukovac
 */
public class DeleteAfterAction implements EditAction {

    private TextEditorModel model;
    private Location        startLocation;
    private PositionAndChar deletedChar;

    public DeleteAfterAction(TextEditorModel model) {
        this.model = model;
        startLocation = model.getCursorLocation();
    }

    @Override
    public void execute_do() {
        int charRow =
            model.getCursorLocation().getRow(); // if cursor.row = 1 -> character to delete is also on position 1
        int charColumn = model.getCursorLocation().getColumn();

        if (model.checkRightBorder(new Location(charRow, charColumn + 1))) { // if true, move next line to current line
            if (model.checkBottomBorder(new Location(charRow + 1, charColumn))) {
                return; //if there is no new line, return
            }
            int currentLineIndex = model.getCursorLocation().getRow();
            int nextLineIndex = model.getCursorLocation().getRow() + 1;
            String joinedLine = model.getLines().get(currentLineIndex) + model.getLines().get(nextLineIndex);
            model.getLines().set(currentLineIndex, joinedLine);
            model.getLines().remove(nextLineIndex);
        } else {
            StringBuilder sb = new StringBuilder(model.getLines().get(charRow));
            sb.deleteCharAt(charColumn);
            model.getLines().set(charRow, sb.toString());
        }
        model.notifyTextObservers();
        model.updateSelectionRange();
    }

    @Override
    public void execute_undo() {
        //update selection range
    }

    private class PositionAndChar {

        Character character;
        Location  cursorLocation;

        public PositionAndChar(Character character, Location cursorLocation) {
            this.character = character;
            this.cursorLocation = cursorLocation;
        }

        @Override
        public String toString() {
            return "PositionAndChar{" +
                "character=" + character +
                ", cursorLocation=" + cursorLocation +
                '}';
        }

    }

}
