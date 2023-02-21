package TextEditor;

import TextEditor.actions.DeleteAfterAction;
import TextEditor.actions.DeleteBeforeAction;
import TextEditor.actions.DeleteRangeAction;
import TextEditor.actions.InsertAction;
import TextEditor.edit.EditAction;
import TextEditor.edit.UndoManager;
import TextEditor.location.Location;
import TextEditor.location.LocationRange;
import TextEditor.range.SelectionObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Bukovac
 */
public class TextEditorModel {

    private List<String>            lines;
    private LocationRange           selectionRange     = new LocationRange(new Location(0, 0), new Location(0, 0));
    private Location                cursorLocation;
    private List<CursorObserver>    cursorObservers    = new ArrayList<>();
    private List<TextObserver>      textObservers      = new ArrayList<>();
    private List<SelectionObserver> selectionObservers = new ArrayList<>();
    private UndoManager             undoManager        = UndoManager.getInstance();
    private Location                startLocation      = new Location(0, 0);

    public TextEditorModel(String text) {
        lines = new LinkedList<>();
        String[] array = text.split("\n");
        lines.addAll(Arrays.asList(array));
        this.cursorLocation = new Location(0, 0);
    }

    public void moveCursorRight() {
        Location newPosition = new Location(
            cursorLocation.getRow(), cursorLocation.getColumn() + 1);
        if (checkRightBorder(newPosition)) {    //if at the end of the line
            newPosition.increaseRow();          // jump to new line/row
            if (checkBottomBorder(newPosition)) {
                return;
            }
            newPosition.setColumn(0);
        }
        setCursorLocation(newPosition);
        notifyCursorObservers();
        //updateSelectionRange();
        System.out.println("DESNO: " + newPosition.getRow() + ":" + newPosition.getColumn());
    }

    public void moveCursorLeft() {
        Location newPosition = new Location(
            cursorLocation.getRow(), cursorLocation.getColumn() - 1);
        if (checkLeftBorder(newPosition)) {  // if at the beginning of the line
            newPosition.decreaseRow();      // jump to the previous line/row
            if (checkTopBorder(newPosition)) {
                return;
            }
            newPosition.setColumn(lines.get(newPosition.getRow()).length());   // move at the end of the previous line
        }
        setCursorLocation(newPosition);
        notifyCursorObservers();
        //updateSelectionRange();
        //System.out.println("LIJEVO " + newPosition.getRow() + ":" + newPosition.getColumn());
    }

    public void moveCursorUp() {
        Location newPosition = new Location(
            cursorLocation.getRow() - 1, cursorLocation.getColumn());
        if (checkTopBorder(newPosition)) {
            // if (newPosition.getColumn() == 0) return;
            // newPosition.setColumn(0);
            newPosition.setRow(0);              // set row back to 0, we could also do newPosition.increaseRow()
        }
        if (checkRightBorder(newPosition)) {                // check if previous line was longer
            newPosition.setColumn(                          // if it was, set cursor to the end of new line
                lines.get(newPosition.getRow()).length()
            );
        }
        setCursorLocation(newPosition);
        notifyCursorObservers();
        //updateSelectionRange();
    }

    public void moveCursorDown() {
        Location newPosition = new Location(
            cursorLocation.getRow() + 1, cursorLocation.getColumn());

        if (checkBottomBorder(newPosition)) {
            newPosition.decreaseRow();       // set row back to previous value (at the beginning we increased row by 1)
            //System.out.println("BOTTOM BORDER " + newPosition.getRow() + ":" + newPosition.getColumn());
        }
        if (checkRightBorder(newPosition)) {                // check if previous line was longer
            newPosition.setColumn(                          // if it was, set cursor to the end of new line
                lines.get(newPosition.getRow()).length()
            );
        }
        setCursorLocation(newPosition);
        notifyCursorObservers();
    }

    public void moveCursorUpWithoutSelection() {
        moveCursorUp();
        updateSelectionRange();
    }

    public void moveCursorRightWithoutSelection() {
        moveCursorRight();
        updateSelectionRange();
    }

    public void moveCursorLeftWithoutSelection() {
        moveCursorLeft();
        updateSelectionRange();
    }

    public void moveCursorDownWithoutSelection() {
        moveCursorDown();
        updateSelectionRange();
    }

    public void deleteBefore() {
        EditAction deleteBeforeAction = new DeleteBeforeAction(this);
        deleteBeforeAction.execute_do();
        undoManager.push(deleteBeforeAction);
    }

    public void deleteAfter() {
        EditAction deleteAfter = new DeleteAfterAction(this);
        deleteAfter.execute_do();
        undoManager.push(deleteAfter);
    }

    //TODO: fix
    public void deleteRange(LocationRange range) {
        EditAction deleteAction = new DeleteRangeAction(range, this);
        deleteAction.execute_do();
        undoManager.push(deleteAction);
        updateSelectionRange();
        notifyRangeObservers();
        notifyCursorObservers();
        notifyTextObservers();
    }

    public void insert(char c) {
        EditAction insertAction = new InsertAction(this, String.valueOf(c));
        insertAction.execute_do();
        undoManager.push(insertAction);
    }

    public void insertWithoutPush(char c) {
        EditAction insertAction = new InsertAction(this, String.valueOf(c));
        insertAction.execute_do();
    }

    public void insert(String text) {
        EditAction insertAction = new InsertAction(this, text);
        insertAction.execute_do();
        undoManager.push(insertAction);
    }

    public String getSelectedText(LocationRange range) {
        Location rangeStart = range.getStart();
        Location rangeEnd = range.getEnd();
        StringBuilder selectedBuilder = new StringBuilder();

        //in same row
        if (rangeStart.getRow() == rangeEnd.getRow()) {
            int row = rangeStart.getRow();

            String line = lines.get(row); // line
            selectedBuilder.append(line, rangeStart.getColumn(), rangeEnd.getColumn());     //save string
        }
        // through multiple rows
        else {
            int startRow = rangeStart.getRow();
            int endRow = rangeEnd.getRow();
            int currentRow = startRow;

            String line = lines.get(startRow);
            String substr = line.substring(rangeStart.getColumn());
            selectedBuilder.append(substr).append('\n');
            currentRow++; //move to next row

            // loop over rows/lines
            while (currentRow <= endRow) {
                String currLine = lines.get(currentRow);
                if (currentRow == endRow) {  // this is last line
                    selectedBuilder.append(currLine, 0, rangeEnd.getColumn()); // add deleted text
                    break;
                } else {
                    selectedBuilder.append(currLine).append('\n');
                }
                currentRow++;
            }
            notifyRangeObservers();
        }
        System.out.println(selectedBuilder.toString());
        return selectedBuilder.toString();
    }

    public void clearDocument() {
        List<String> emptyList = new ArrayList<>();
        emptyList.add("");
        this.lines = emptyList;
        setCursorLocation(startLocation);
        updateSelectionRange();
        notifyTextObservers();
        notifyCursorObservers();
    }

    /**
     * Check if new location is bigger than line
     *
     * @param location location to check
     * @return <code>true</code> if new location is bigger than line
     */
    public boolean checkRightBorder(Location location) {
        return location.getColumn() > lines.get(location.getRow()).length();
    }

    /**
     * Check if new location is smaller than beginning of the line
     *
     * @param location location to check
     * @return <code>true</code> if new location is smaller than beginning of the line
     */
    public boolean checkLeftBorder(Location location) {
        return location.getColumn() < 0;
    }

    /**
     * Check if new location is after the last row
     *
     * @param location location to check
     * @return <code>true</code> if new location is after the last row
     */
    public boolean checkBottomBorder(Location location) {
        return location.getRow() >= lines.size();
    }

    /**
     * Check if new location is smaller than the top
     *
     * @param location location to check
     * @return <code>true</code> if new location is smaller than the top
     */
    public boolean checkTopBorder(Location location) {
        return location.getRow() < 0;
    }

    public void attachRangeObserver(SelectionObserver observer) {
        selectionObservers.add(observer);
    }

    public void detachRangeObserver(SelectionObserver observer) {
        selectionObservers.remove(observer);
    }

    public void notifyRangeObservers() {
        selectionObservers.forEach(it -> it.onSelectionChanged(selectionRange));
    }

    public void attachTextObserver(TextObserver textObserver) {
        textObservers.add(textObserver);
    }

    public void detachTextObserver(TextObserver textObserver) {
        textObservers.remove(textObserver);
    }

    public void notifyTextObservers() {
        textObservers.forEach(it -> it.updateText());
    }

    public void attachCursorObserver(CursorObserver cursorObserver) {
        cursorObservers.add(cursorObserver);
    }

    public void detachCursorObserver(CursorObserver cursorObserver) {
        cursorObservers.remove(cursorObserver);
    }

    public void notifyCursorObservers() {
        cursorObservers.forEach(it -> it.updateCursorLocation(cursorLocation));
    }

    public Iterator<String> allLines() {
        return lines.iterator();
    }

    public Iterator<String> linesRange(int index1, int index2) {
        return lines.subList(index1, index2).iterator();
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
        notifyTextObservers();
    }

    public LocationRange getSelectionRange() {
        return selectionRange;
    }

    public void setSelectionRange(LocationRange selectionRange) {
        this.selectionRange = selectionRange;
        notifyRangeObservers();
    }

    public Location getCursorLocation() {
        return cursorLocation;
    }

    public void setCursorLocation(Location cursorLocation) {
        this.cursorLocation = cursorLocation;
        notifyCursorObservers();
    }

    /**
     * Update selection range to current cursor value
     */
    public void updateSelectionRange() {
        selectionRange = new LocationRange(cursorLocation, cursorLocation);
        notifyRangeObservers();
        System.out.println("SELECTION RANGE: " + selectionRange.getStart() + " : " + selectionRange.getEnd());
    }

    public boolean selectionExists() {
        return !selectionRange.getStart().equals(selectionRange.getEnd()); // if equal that means there is no selection
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        int row = lines.size() - 1;
        int column = lines.get(lines.size() - 1).length();

        return new Location(row, column);
    }

}
