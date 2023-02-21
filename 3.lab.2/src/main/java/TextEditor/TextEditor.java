package TextEditor;

import TextEditor.actions.menu.edit.CopyAction;
import TextEditor.actions.menu.edit.CutAction;
import TextEditor.actions.menu.edit.PasteAction;
import TextEditor.actions.menu.edit.PasteAndTakeAction;
import TextEditor.actions.menu.edit.RedoAction;
import TextEditor.actions.menu.edit.UndoAction;
import TextEditor.clipboard.ClipboardObserver;
import TextEditor.clipboard.ClipboardStack;
import TextEditor.edit.RedoStackObserver;
import TextEditor.edit.UndoManager;
import TextEditor.edit.UndoStackObserver;
import TextEditor.location.Location;
import TextEditor.location.LocationRange;
import TextEditor.range.SelectRangeDown;
import TextEditor.range.SelectRangeLeft;
import TextEditor.range.SelectRangeRight;
import TextEditor.range.SelectRangeUp;
import TextEditor.range.SelectionObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;

/**
 * @author Antonio Bukovac
 */

public class TextEditor extends JComponent implements CursorObserver, TextObserver, ClipboardObserver,
    UndoStackObserver, RedoStackObserver, SelectionObserver {

    private static final String CTRL_C       = "control_c";
    private static final String CTRL_V       = "control_v";
    private static final String CTRL_X       = "control_x";
    private static final String CTRL_SHIFT_V = "control_shift_v";
    private static final String CTRL_Z       = "control_z";
    private static final String CTRL_Y       = "control_y";
    private static final String SHIFT_RIGHT  = "shift_right";
    private static final String SHIFT_LEFT   = "shift_left";
    private static final String SHIFT_UP     = "shift_up";
    private static final String SHIFT_DOWN   = "shift_down";
    private static final String LEFT         = "left";
    private static final String RIGHT        = "right";
    private static final String DOWN         = "down";
    private static final String UP           = "up";
    private static final String DEL_BEFORE   = "del_before";
    private static final String DEL_AFTER    = "del_after";

    private TextEditorModel textEditorModel;
    private Location        cursorLocation;
    private ClipboardStack  clipboard;
    private Boolean         undoStackIsEmpty = true; // at the beginning it is empty
    private Boolean         redoStackIsEmpty = true; // at the beginning it is empty
    private LocationRange   selectionRange;

    public TextEditor(TextEditorModel textEditorModel, JFrame jFrame, ClipboardStack clipboard) {
        this.textEditorModel = textEditorModel;
        this.cursorLocation = textEditorModel.getCursorLocation();
        this.clipboard = clipboard;
        this.selectionRange = textEditorModel.getSelectionRange();
        textEditorModel.attachCursorObserver(this);
        textEditorModel.attachTextObserver(this);
        textEditorModel.attachRangeObserver(this);
        UndoManager.getInstance().attachRedoStackObserver(this);
        UndoManager.getInstance().attachUndoStackObserver(this);
        setCursorMovements(jFrame);
    }

    private void setCursorMovements(JFrame jFrame) {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char c = e.getKeyChar();
                if (isPrintableChar(c)) {
                    if (selectionExists()) {
                        textEditorModel.deleteRange(selectionRange);
                    }
                    textEditorModel.insert(c);

                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textEditorModel.insert('\n');
                }
            }
        });

        SelectRangeLeft rangeLeft = new SelectRangeLeft(textEditorModel);
        SelectRangeRight rangeRight = new SelectRangeRight(textEditorModel);
        SelectRangeUp rangeUp = new SelectRangeUp(textEditorModel);
        SelectRangeDown rangeDown = new SelectRangeDown(textEditorModel);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), RIGHT);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), LEFT);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), DOWN);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), UP);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK), SHIFT_LEFT);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_DOWN_MASK), SHIFT_RIGHT);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK), SHIFT_UP);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK), SHIFT_DOWN);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK), CTRL_C);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK), CTRL_V);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK), CTRL_X);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK),
            CTRL_SHIFT_V);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), CTRL_Z);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), CTRL_Y);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), DEL_AFTER);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), DEL_BEFORE);

        Action moveRight = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorRightWithoutSelection();
            }
        };

        Action moveLeft = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorLeftWithoutSelection();
            }
        };

        Action moveUp = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorUpWithoutSelection();
            }
        };

        Action moveDown = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textEditorModel.moveCursorDownWithoutSelection();
            }
        };

        Action selectLeft = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rangeLeft.doSelection();
            }
        };

        Action selectRight = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rangeRight.doSelection();
            }
        };

        Action selectUp = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rangeUp.doSelection();
            }
        };

        Action selectDown = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rangeDown.doSelection();
            }
        };

        Action deleteAfter = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectionExists()) {
                    textEditorModel.deleteRange(selectionRange);
                } else {
                    //test
                    //LocationRange test = new LocationRange(new Location(1, 7), new Location(4, 0));
                    //textEditorModel.deleteRange(test);
                    textEditorModel.deleteAfter();
                }
            }
        };

        Action deleteBefore = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectionExists()) {
                    textEditorModel.deleteRange(selectionRange);
                } else {
                    textEditorModel.deleteBefore();
                }
            }
        };

        getActionMap().put(RIGHT, moveRight);
        getActionMap().put(LEFT, moveLeft);
        getActionMap().put(UP, moveUp);
        getActionMap().put(DOWN, moveDown);

        getActionMap().put(SHIFT_LEFT, selectLeft);
        getActionMap().put(SHIFT_RIGHT, selectRight);
        getActionMap().put(SHIFT_UP, selectUp);
        getActionMap().put(SHIFT_DOWN, selectDown);

        getActionMap().put(DEL_BEFORE, deleteBefore);
        getActionMap().put(DEL_AFTER, deleteAfter);

        getActionMap().put(CTRL_C, new CopyAction(textEditorModel, clipboard));
        getActionMap().put(CTRL_V, new PasteAction(textEditorModel, clipboard));
        getActionMap().put(CTRL_X, new CutAction(textEditorModel, clipboard));
        getActionMap().put(CTRL_SHIFT_V, new PasteAndTakeAction(textEditorModel, clipboard));

        getActionMap().put(CTRL_Z, new UndoAction());
        getActionMap().put(CTRL_Y, new RedoAction());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setFont(new Font("Courier New", Font.PLAIN, 16));
        int width = getBounds().width;
        int height = getBounds().height;
        drawText(graphics2D, width, height);
        drawCursor(graphics2D);
        drawSelectionBackground(graphics2D);

    }

    private void drawText(Graphics2D graphics2D, int width, int height) {
        int fontHeight = graphics2D.getFontMetrics().getHeight();
        int x = 0;
        int y = fontHeight;

        for (Iterator<String> it = textEditorModel.allLines(); it.hasNext(); ) {
            String line = it.next();
            graphics2D.drawString(line, x, y);
            y += fontHeight;
        }
    }

    private void drawSelectionBackground(Graphics2D graphics2d) {

        if (!selectionExists()) {
            return;
        }
        Location selectionRangeStart = selectionRange.getStart();
        Location selectionRangeEnd = selectionRange.getEnd();

        int startX = selectionRangeStart.getColumn();
        int endX = selectionRangeEnd.getColumn();
        int startY = selectionRangeStart.getRow();
        int endY = selectionRangeEnd.getRow();

        int height = graphics2d.getFontMetrics().getHeight();
        graphics2d.setColor(new Color(0, 94, 255, 100));

        if (startY == endY) { // if in same row
            int width = (endX - startX) * getCharWidth(graphics2d);
            graphics2d.fillRect(startX * getCharWidth(graphics2d), (startY) * height, width, height);
        } else {
            // mogu prvi odma ovdje
            int rowLength = textEditorModel.getLines().get(selectionRangeStart.getRow()).length();
            int width = rowLength * getCharWidth(graphics2d) - startX * getCharWidth(
                graphics2d);     // remove offset from width
            graphics2d.fillRect(startX * getCharWidth(graphics2d), startY * height, width, height);

            //idi po redovima i crtaj za svaki
            for (int i = startY + 1; i <= endY; i++) {
                if (i == endY) {                                                      // in last row
                    width = endX * getCharWidth(graphics2d);
                    graphics2d.fillRect(0, (i) * height, width, height);
                } else {                                                             // other rows in their entirety
                    //dohvati row i izbroji slova tj length
                    rowLength = textEditorModel.getLines().get(i).length();

                    width = rowLength * getCharWidth(graphics2d);
                    graphics2d.fillRect(0, (i) * height, width, height);
                }
            }
        }

        //System.out.println("ENDX: " + endX + " ; " + "startX " + startX );
        // System.out.println("width: " +width);
        // System.out.println("CRTAM SELECTION BACKGROUND: " + "Startx: " + startX + " Y: " + (startY) * height + " width " + width + "height: " + height);
    }

    private void drawCursor(Graphics2D graphics2D) {
        graphics2D.setPaint(Color.BLACK);

        int row = cursorLocation.getRow();
        int column = cursorLocation.getColumn();
        int lineHeight = graphics2D.getFontMetrics().getHeight();
        int charWidth = getCharWidth(graphics2D);
        graphics2D.drawLine(column * charWidth, row * lineHeight, column * charWidth, (row + 1) * lineHeight);

        //        String line = textEditorModel.linesRange(row, row+1).next().substring(0, col);
        //        int x = STRING_WIDTH.apply(graphics2D, line);
        //        int offset = graphics2D.getFontMetrics().getHeight() + LINE_SPACING;
        //        int yStart = cursor.getRow()*offset;
        //        int yEnd = yStart + offset;
        //        graphics2D.drawLine(x, yStart, x, yEnd);
    }

    @Override
    public void updateCursorLocation(Location loc) {
        cursorLocation = loc;
        repaint();
    }

    @Override
    public void updateText() {
        repaint();
    }

    @Override
    public void updateClipboard(ClipboardStack clipboard) {
        this.clipboard = clipboard;
    }

    @Override
    public void updateRedoStackState(Boolean isEmpty) {
        this.redoStackIsEmpty = isEmpty;
    }

    @Override
    public void updateUndoStackState(Boolean isEmpty) {
        this.undoStackIsEmpty = isEmpty;
    }

    @Override
    public void onSelectionChanged(LocationRange selectionRange) {
        this.selectionRange = selectionRange;
        repaint();
    }

    public boolean isPrintableChar(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
            c != KeyEvent.CHAR_UNDEFINED &&
            block != null &&
            block != Character.UnicodeBlock.SPECIALS;
    }

    private int getCharWidth(Graphics2D graphics2D) {
        return graphics2D.getFontMetrics().charWidth('a'); // all characters are same width
    }

    private boolean selectionExists() {
        return !selectionRange.getStart().equals(selectionRange.getEnd()); // if equal that means there is no selection
    }

}

//***********************************************************************************************************************************//

//        Action deleteSelection = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (selectionExists()) {
//                    textEditorModel.deleteRange(selectionRange);
//                }
//            }
//        };

//        Action paste = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!clipboard.empty()) {
//                    String topText = clipboard.peek();
//                    textEditorModel.insert(topText);
//                }
//            }
//        };

//        Action cut = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (selectionExists()) {
//                    String copiedString = textEditorModel.getSelectedText(selectionRange);
//                    textEditorModel.deleteRange(selectionRange);
//                    clipboard.push(copiedString);
//                }
//            }
//        };

//        Action pasteAndDelete = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!clipboard.empty()) {
//                    String topText = clipboard.pop();
//                    textEditorModel.insert(topText);
//                }
//            }
//        };

//        Action undo = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!undoStackIsEmpty) {
//                    UndoManager.getInstance().undo();
//                } else {
//                }
//            }
//        };

//        Action redo = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (!redoStackIsEmpty) {
//                    UndoManager.getInstance().redo();
//                } else {
//                }
//            }
//        };
