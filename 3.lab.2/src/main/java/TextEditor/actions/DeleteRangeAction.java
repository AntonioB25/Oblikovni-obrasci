package TextEditor.actions;

import TextEditor.TextEditorModel;
import TextEditor.edit.EditAction;
import TextEditor.location.Location;
import TextEditor.location.LocationRange;

/**
 * @author Antonio Bukovac
 */
public class DeleteRangeAction implements EditAction {

    LocationRange   range;
    TextEditorModel model;
    StringBuilder   deletedTextBuilder;
    Location        oldLocation;

    public DeleteRangeAction(LocationRange range, TextEditorModel model) {
        this.range = range;
        this.model = model;
        this.deletedTextBuilder = new StringBuilder();
    }

    @Override
    public void execute_do() {
        Location rangeStart = range.getStart();
        Location rangeEnd = range.getEnd();
        oldLocation = rangeStart;                // save start location
        String firstTextPart = "";
        String lastTextPart = "";

        //in same row
        if (rangeStart.getRow() == rangeEnd.getRow()) {
            int row = rangeStart.getRow();
            //obriši od start columna do end columna
            String line = model.getLines().get(row); // linija
            String firstHalf = line.substring(0, rangeStart.getColumn());
            deletedTextBuilder.append(line, rangeStart.getColumn(), rangeEnd.getColumn());     //save deleted string
            String secondHalf = line.substring(rangeEnd.getColumn());
            String newLine = firstHalf + secondHalf;
            model.getLines().set(row, newLine);
            //kursor ide na mjesto start-a
            model.setCursorLocation(new Location(row, rangeStart.getColumn()));
        }
        // through multiple rows
        else {
            int startRow = rangeStart.getRow();
            int endRow = rangeEnd.getRow();
            int currentRow = startRow;

            String line = model.getLines().get(startRow);
            firstTextPart = line.substring(0, rangeStart.getColumn()); // save text before selected
            String substr = line.substring(rangeStart.getColumn());
            deletedTextBuilder.append(substr).append('\n');
            currentRow++; //move to next row

            // loop over rows/lines
            while (currentRow <= endRow) {
                String currLine = model.getLines().get(currentRow);
                if (currentRow == endRow) {  // this is last line
                    deletedTextBuilder.append(currLine, 0, rangeEnd.getColumn()); // add deleted text
                    lastTextPart = currLine.substring(rangeEnd.getColumn()); // save text after selected
                    model.getLines().remove(currentRow);
                    break;
                } else {
                    deletedTextBuilder.append(currLine).append('\n');
                    model.getLines().remove(currentRow);
                }
                endRow--;
            }

            // "1234567\n" length is 8, but last place of cursor is 7
            if (rangeStart.getColumn()
                < model.getLines().get(startRow).length() - 1) {   //connect first, and last text part
                model.getLines().set(startRow, firstTextPart + lastTextPart);
            } else {
                model.getLines().add(currentRow, lastTextPart);
            }

            model.setCursorLocation(new Location(startRow, rangeStart.getColumn()));
            model.updateSelectionRange();
            model.notifyCursorObservers();
            model.notifyTextObservers();
        }
    }

    @Override
    public void execute_undo() {
        //pomakni kursor na staru i ubaci tekst
        model.setCursorLocation(oldLocation);
        model.insert(deletedTextBuilder.toString());
    }

}
