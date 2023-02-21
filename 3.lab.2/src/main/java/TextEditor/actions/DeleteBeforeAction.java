package TextEditor.actions;

import TextEditor.TextEditorModel;
import TextEditor.edit.EditAction;
import TextEditor.location.Location;

/**
 * @author Antonio Bukovac
 */
public class DeleteBeforeAction implements EditAction {

    private TextEditorModel model;
    private Location        startLocation;
    private PositionAndChar deletedChar;

    public DeleteBeforeAction(TextEditorModel model) {
        this.model = model;
        this.startLocation = model.getCursorLocation();
    }

    @Override
    public void execute_do() {
        if (model.getCursorLocation().getRow() == 0 && model.getCursorLocation().getColumn() == 0) {
            return; // if at start, return
        }
        int charRow = model.getCursorLocation().getRow();
        int charColumn = model.getCursorLocation().getColumn() - 1; // position of character to delete

        if (model
            .checkLeftBorder(new Location(charRow, charColumn))) {    // if true, move current line to previous line
            int previousLineIndex = model.getCursorLocation().getRow() - 1;
            int currentLineIndex = model.getCursorLocation().getRow();
            String joinedLine = model.getLines().get(previousLineIndex) + model.getLines().get(currentLineIndex);
            this.deletedChar = new PositionAndChar('\n', model.getCursorLocation());
            model.moveCursorLeft(); // move cursor
            model.getLines().set(previousLineIndex, joinedLine);
            model.getLines().remove(currentLineIndex);
        } else {
            StringBuilder sb = new StringBuilder(model.getLines().get(charRow));
            Character character = sb.charAt(charColumn);                // save deleted char
            System.out.println("DELETED CHAR: " + "#" + character + "#");
            sb.deleteCharAt(charColumn);
            model.moveCursorLeft(); // move cursor
            this.deletedChar = new PositionAndChar(character, model.getCursorLocation());
            model.getLines().set(charRow, sb.toString());
        }
        model.notifyTextObservers();
        model.notifyCursorObservers();
        model.updateSelectionRange();
    }

    @Override
    public void execute_undo() {
        //get posAndChar from stack
        //set cursor, insert character

        model.setCursorLocation(deletedChar.cursorLocation);
        System.out.println("COLUMN IN UNDO: " + model.getCursorLocation().getColumn());
        model.insertWithoutPush(deletedChar.character);
        // UndoManager.getInstance().push();
        model.updateSelectionRange();
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
