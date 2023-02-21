package TextEditor.edit;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Antonio Bukovac
 */
public class UndoManager {

    private static UndoManager             undoManager;
    private        Stack<EditAction>       undoStack          = new Stack<>();
    private        Stack<EditAction>       redoStack          = new Stack<>();
    private        List<UndoStackObserver> undoStackObservers = new ArrayList<>();
    private        List<RedoStackObserver> redoStackObservers = new ArrayList<>();

    private UndoManager() {
    }

    public static UndoManager getInstance() {
        if (undoManager == null) {
            undoManager = new UndoManager();
        }
        return undoManager;
    }

    public void undo() {

        if (!undoStack.empty()) {
            EditAction action = undoStack.pop();
            System.out.println("POPPED: " + action);
            redoStack.push(action);
            System.out.println("REDOSTACK: " + redoStack.size());
            action.execute_undo();
        }
        notifyRedoStackObservers(false);    //notify that redo stack is not empty
        if (undoStack.empty()) {
            notifyUndoStackObservers(true); //notify that undo stack is empty
        }
    }

    public void push(EditAction c) {
        System.out.println("PUSHED: " + c.toString());
        redoStack.clear();
        undoStack.push(c);
        notifyRedoStackObservers(true);     //notify that redo is empty
        notifyUndoStackObservers(false);    // notify that undo is not empty
    }

    public void redo() {
        EditAction action = redoStack.pop();
        action.execute_do();
        undoStack.push(action);
        notifyUndoStackObservers(false);    // notify that undo is not empty
        if (redoStack.empty()) {
            notifyRedoStackObservers(true); //notify that redo is empty
        }
    }

    public void attachUndoStackObserver(UndoStackObserver observer) {
        undoStackObservers.add(observer);
    }

    public void detachUndoStackObserver(UndoStackObserver observer) {
        undoStackObservers.remove(observer);
    }

    public void attachRedoStackObserver(RedoStackObserver observer) {
        redoStackObservers.add(observer);
    }

    public void detachRedoStackObserver(RedoStackObserver observer) {
        redoStackObservers.remove(observer);
    }

    public void notifyUndoStackObservers(Boolean isEmpty) {
        undoStackObservers.forEach(it -> it.updateUndoStackState(isEmpty));
    }

    public void notifyRedoStackObservers(Boolean isEmpty) {
        redoStackObservers.forEach(it -> it.updateRedoStackState(isEmpty));
    }

}
