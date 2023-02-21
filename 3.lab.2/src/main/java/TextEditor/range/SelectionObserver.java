package TextEditor.range;

import TextEditor.location.LocationRange;

/**
 * @author Antonio Bukovac
 */
public interface SelectionObserver {

    void onSelectionChanged(LocationRange selectionRange);

}
