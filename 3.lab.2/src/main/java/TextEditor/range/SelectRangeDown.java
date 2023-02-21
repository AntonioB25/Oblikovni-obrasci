package TextEditor.range;

import TextEditor.TextEditorModel;
import TextEditor.location.Location;
import TextEditor.location.LocationRange;

/**
 * @author Antonio Bukovac
 */
public class SelectRangeDown {

    TextEditorModel textEditorModel;
    Location        cursorBefore;
    Location        cursorAfter;

    public SelectRangeDown(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
    }

    public void doSelection() {
        cursorBefore = textEditorModel.getCursorLocation();
        textEditorModel.moveCursorDown();
        cursorAfter = textEditorModel.getCursorLocation();
        LocationRange range = textEditorModel.getSelectionRange();

        if (cursorBefore.equals(range.getStart())) {
            range.setStart(cursorAfter);
            //System.out.println("STAVLJAM START");
        } else {
            range.setEnd(cursorAfter);
            //System.out.println("STAVLJAM END");
        }
        //System.out.println("TENUTNI RANGE: " + range.toString());
        textEditorModel.setSelectionRange(range);
    }

}
