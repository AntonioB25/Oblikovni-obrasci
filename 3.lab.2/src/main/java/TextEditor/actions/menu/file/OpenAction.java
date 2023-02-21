package TextEditor.actions.menu.file;

import TextEditor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class OpenAction extends AbstractAction {

    TextEditorModel textEditorModel;
    JFrame          jFrame;

    public OpenAction(JFrame jFrame, TextEditorModel textEditorModel) {
        this.textEditorModel = textEditorModel;
        this.jFrame = jFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Specify a file to read");

        int userSelection = fileChooser.showSaveDialog(jFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            readFromFile(fileToSave);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
    }

    private void readFromFile(File fileToSave) {
        textEditorModel.clearDocument();                    // clear document
        List<String> lines = textEditorModel.getLines();
        lines.remove(0);                              // delete first line that was set by clear document
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(fileToSave));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
            textEditorModel.notifyTextObservers();
            textEditorModel.notifyCursorObservers();
            textEditorModel.notifyRangeObservers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
