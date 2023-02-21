package TextEditor;

import TextEditor.actions.menu.edit.ClearDocumentAction;
import TextEditor.actions.menu.edit.CopyAction;
import TextEditor.actions.menu.edit.CutAction;
import TextEditor.actions.menu.edit.DeleteSelectionAction;
import TextEditor.actions.menu.edit.PasteAction;
import TextEditor.actions.menu.edit.PasteAndTakeAction;
import TextEditor.actions.menu.edit.RedoAction;
import TextEditor.actions.menu.edit.UndoAction;
import TextEditor.actions.menu.file.ExitAction;
import TextEditor.actions.menu.file.OpenAction;
import TextEditor.actions.menu.file.SaveAction;
import TextEditor.actions.menu.move.CursorToDocumentEndAction;
import TextEditor.actions.menu.move.CursorToDocumentStartAction;
import TextEditor.clipboard.ClipboardStack;

import javax.swing.*;
import java.awt.*;

/**
 * @author Antonio Bukovac
 */
public class TextEditorFrame extends JFrame {

    public TextEditorFrame() {
        setTitle("Text editor");
        setSize(1000, 800);
        setLocation(250, 250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TextEditorFrame().setVisible(true));
    }

    private void initGUI() {
        Container cp = getContentPane();

        String str =
            "Oblikovati grafičku komponentu TextEditor koja će korisnicima omogućiti prikazivanje i jednostavno uređivanje teksta.\n"
                + "1234567\n"
                + "Vaš zadatak je pratiti položaj kursora, pritiske na tipke, iscrtavati liniju koja predstavlja kursor te iscrtati tekst na površini komponente.\n"
                + "Vaša komponenta treba se temeljiti na primitivnim prozorima (npr. JFrame pod Swingom odnosno Tk() pod tkinterom) te ne\n"
                + "smije koristiti komponente visoke razine (npr. Text pod tkinterom odnosno JTextArea pod Swingom).\n";

        TextEditorModel textEditorModel = new TextEditorModel(str);
        ClipboardStack clipboard = new ClipboardStack();

        cp.setLayout(new BorderLayout());
        cp.add(new TextEditor(textEditorModel, this, clipboard), BorderLayout.CENTER);
        cp.add(new StatusLabel(textEditorModel), BorderLayout.SOUTH);

        cp.add(fetchMenuBar(textEditorModel, clipboard), BorderLayout.NORTH);
    }

    //getMenuBar method is already in use
    private JMenuBar fetchMenuBar(TextEditorModel textEditorModel, ClipboardStack clipboard) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(100, 25));

        JMenu editMenu = new JMenu("Edit");

        JMenuItem copyItem = new JMenuItem(new CopyAction(textEditorModel, clipboard));
        copyItem.setText("Copy");
        JMenuItem undoItem = new JMenuItem(new UndoAction());
        undoItem.setText("Undo");
        JMenuItem redoItem = new JMenuItem(new RedoAction());
        redoItem.setText("Redo");
        JMenuItem cutItem = new JMenuItem(new CutAction(textEditorModel, clipboard));
        cutItem.setText("Cut");
        JMenuItem pasteItem = new JMenuItem(new PasteAction(textEditorModel, clipboard));
        pasteItem.setText("Paste");
        JMenuItem pasteAndTakeItem = new JMenuItem(new PasteAndTakeAction(textEditorModel, clipboard));
        pasteAndTakeItem.setText("Paste and take");
        JMenuItem deleteSelectionItem = new JMenuItem(new DeleteSelectionAction(textEditorModel));
        deleteSelectionItem.setText("Delete selection");
        JMenuItem clearDocItem = new JMenuItem(new ClearDocumentAction(textEditorModel));
        clearDocItem.setText("Clear document");
        editMenu.add(deleteSelectionItem);
        editMenu.add(copyItem);
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(cutItem);
        editMenu.add(pasteItem);
        editMenu.add(pasteAndTakeItem);
        editMenu.add(clearDocItem);
        menuBar.add(editMenu);

        JMenu moveMenu = new JMenu("Move");

        JMenuItem cursorToStartItem = new JMenuItem(new CursorToDocumentStartAction(textEditorModel));
        cursorToStartItem.setText("Cursor to document start");
        JMenuItem cursorToEndItem = new JMenuItem(new CursorToDocumentEndAction(textEditorModel));
        cursorToEndItem.setText("Cursor to document end");
        moveMenu.add(cursorToStartItem);
        moveMenu.add(cursorToEndItem);
        menuBar.add(moveMenu);

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem(new ExitAction(this));
        exitItem.setText("Exit");
        JMenuItem saveItem = new JMenuItem(new SaveAction(this, textEditorModel));
        saveItem.setText("Save");
        JMenuItem openItem = new JMenuItem(new OpenAction(this, textEditorModel));
        openItem.setText("Open");
        fileMenu.add(exitItem);
        fileMenu.add(saveItem);
        fileMenu.add(openItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

}
