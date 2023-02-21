package TextEditor.edit;

/**
 * @author Antonio Bukovac
 */
public interface UndoStackObserver {

    void updateUndoStackState(Boolean isEmpty);

}
