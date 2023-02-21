package TextEditor;

import TextEditor.location.Location;

import javax.swing.*;

public class StatusLabel extends JLabel implements CursorObserver, TextObserver {

    private TextEditorModel textEditorModel;
    private Location        cursor;

    public StatusLabel(TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
        this.cursor = textEditorModel.getCursorLocation();
        textEditorModel.attachCursorObserver(this);
        setVerticalAlignment(BOTTOM);
        setHorizontalAlignment(RIGHT);
        setText(getLabelText());
    }

    @Override
    public void updateCursorLocation(Location loc) {
        cursor = loc;
        setText(getLabelText());
    }

    private String getLabelText() {
        return "total lines: " + textEditorModel.getLines().size() + "  " + cursor.getRow() + ":" + cursor.getColumn()
            + " ";
    }

    @Override
    public void updateText() {
        setText(getLabelText());
    }

}
