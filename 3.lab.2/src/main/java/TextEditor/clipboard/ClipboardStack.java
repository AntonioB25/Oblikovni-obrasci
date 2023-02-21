package TextEditor.clipboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Antonio Bukovac
 */
public class ClipboardStack {

    private Stack<String>           texts;
    private List<ClipboardObserver> clipboardObservers;

    public ClipboardStack() {
        this.texts = new Stack<>();
        this.clipboardObservers = new ArrayList<>();
    }

    public void push(String text) {
        texts.push(text);
        notifyClipboardObservers();
    }

    public String peek() {
        return texts.peek();
    }

    public String pop() {
        notifyClipboardObservers();
        return texts.pop();
    }

    public boolean empty() {
        return texts.empty();
    }

    public void attachClipboardObserver(ClipboardObserver observer) {
        clipboardObservers.add(observer);
    }

    public void detachClipboardObserver(ClipboardObserver observer) {
        clipboardObservers.remove(observer);
    }

    public void notifyClipboardObservers() {
        clipboardObservers.forEach(it -> it.updateClipboard(this));
    }

}
