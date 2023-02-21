package TextEditor.edit;

/**
 * @author Antonio Bukovac
 */
public interface RedoStackObserver {

    void updateRedoStackState(Boolean isEmpty);

}
