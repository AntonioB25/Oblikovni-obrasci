package TextEditor.actions;

import TextEditor.TextEditorModel;
import TextEditor.edit.EditAction;
import TextEditor.location.Location;
import TextEditor.location.LocationRange;

/**
 * @author Antonio Bukovac
 */
public class InsertAction implements EditAction {

    private TextEditorModel model;
    private String          text;
    private Location        startLocation;
    private StringBuilder   insertedStringBuilder;

    public InsertAction(TextEditorModel model, String text) {
        this.model = model;
        this.text = text;
        this.startLocation = model.getCursorLocation();
        this.insertedStringBuilder = new StringBuilder();
    }

    @Override
    public void execute_do() {
        char[] arr = text.toCharArray();
        for (char c : arr) {
            insertedStringBuilder.append(c);
            insert(c);
        }
        model.updateSelectionRange();
    }

    @Override
    public void execute_undo() {
        System.out.println("CALCULATED RANGE: " + calculateRange());
        model.deleteRange(calculateRange());
        model.updateSelectionRange();

        //selection range update
    }

    private void insert(char c) {
        int row = model.getCursorLocation().getRow();
        int column = model.getCursorLocation().getColumn();

        if (c == '\n') {
            StringBuilder sb = new StringBuilder(model.getLines().get(row));
            sb.insert(column, c);
            model.getLines().set(row, sb.toString());
            String currentLine = model.getLines().get(row);
            String[] splitLine = currentLine.split("\n", 2);
            model.getLines().set(row, splitLine[0]);
            model.getLines().add(row + 1, splitLine[1]);
            model.moveCursorRight();
            model.notifyTextObservers();
        } else {
            StringBuilder sb = new StringBuilder(model.getLines().get(row));
            sb.insert(column, c);
            model.getLines().set(row, sb.toString());
            model.moveCursorRight();
            model.notifyTextObservers();
        }
    }

    //    public void insert(String text){
    //        //splitaj tekst po \n - dobijes retke
    //        //prvi redak naljepis na trenutni , a ostale insertas row + i, i E string[] size
    //        String[] newLines = text.split("\n");
    //        int row = cursorLocation.getRow();
    //        int column = cursorLocation.getColumn();
    //
    //        // dohvati trenutnu liniju
    //        String currentLine = lines.get(row);
    //        //splitaj trenutnu liniju na mjestu gdje je kursor i ubaci tekst prve
    //        String first = currentLine.substring(0, column);
    //        String last = currentLine.substring(column);
    //        lines.set(row, first + newLines[0]);
    //        setCursorLocation(new Location(row, lines.get(row).length()));
    //        int i;
    //        for (i = 1; i < newLines.length; i++) {
    //            lines.add(row + i, newLines[i]);
    //        }
    //        int lastRow = row + i - 1;
    //        lines.set(lastRow, lines.get(lastRow) + last);
    //        setCursorLocation(new Location(lastRow, lines.get(lastRow).length() - last.length()));
    //        notifyTextObservers();
    //        notifyCursorObservers();
    //    }

    private LocationRange calculateRange() {
        Location start = startLocation;
        Location end;
        int startRow = startLocation.getRow();
        int endRow;
        int endColumn;
        String insertedText = insertedStringBuilder.toString();
        String[] splittedText = insertedText.split("\n");

        endRow = startRow + splittedText.length - 1;                    // endRow is in front by number of newline chars
        endColumn = startLocation.getColumn() + splittedText[splittedText.length
            - 1].length();   // endColumn is last char of last inserted line
        end = new Location(endRow, endColumn);

        return new LocationRange(start, end);
    }

}
