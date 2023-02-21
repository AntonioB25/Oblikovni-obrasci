package TextEditor;

import TextEditor.location.Location;

/**
 * @author Antonio Bukovac
 */
public interface CursorObserver {

    void updateCursorLocation(Location loc);

}
