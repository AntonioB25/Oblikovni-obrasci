package TextEditor.actions.menu.file;

import TextEditor.TextEditorModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

public class SaveAction extends AbstractAction {

    JFrame          jFrame;
    TextEditorModel textEditorModel;

    public SaveAction(JFrame jFrame, TextEditorModel textEditorModel) {
        this.jFrame = jFrame;
        this.textEditorModel = textEditorModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(jFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            writeToFile(fileToSave);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
    }

    private void writeToFile(File fileToSave) {

        try (FileOutputStream fos = new FileOutputStream(fileToSave);
             OutputStreamWriter outStream = new OutputStreamWriter(fos);) {

            Iterator<String> textIterator = textEditorModel.allLines();

            while (textIterator.hasNext()) {
                String line = textIterator.next();
                outStream.write(line + '\n');
                System.out.println("LINE: " + line);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}
