package TextEditor.range;

import TextEditor.TextEditorModel;
import TextEditor.location.Location;
import TextEditor.location.LocationRange;

/**
 * @author Antonio Bukovac
 */
public class SelectRangeLeft {

    TextEditorModel textEditorModel;
    Location        cursorBefore;
    Location        cursorAfter;

    public SelectRangeLeft(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }

    public void doSelection() {
        cursorBefore = textEditorModel.getCursorLocation();
        textEditorModel.moveCursorLeft();
        cursorAfter = textEditorModel.getCursorLocation();
        LocationRange range = textEditorModel.getSelectionRange();

        if (cursorBefore.equals(range.getStart())) {
            range.setStart(cursorAfter);
            System.out.println("STAVLJAM START");
        } else {
            range.setEnd(cursorAfter);
            System.out.println("STAVLJAM END");
        }
        System.out.println("TENUTNI RANGE: " + range.toString());
        textEditorModel.setSelectionRange(range);
    }

}