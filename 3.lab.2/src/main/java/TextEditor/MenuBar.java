package TextEditor;

import TextEditor.clipboard.ClipboardStack;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private TextEditor     textEditor;
    private ClipboardStack clipboard;

    public MenuBar(TextEditor textEditor, ClipboardStack clipboard) {
        this.textEditor = textEditor;
        this.clipboard = clipboard;
    }

}
