package TextEditor.actions.menu.file;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitAction extends AbstractAction {

    JFrame jFrame;

    public ExitAction(JFrame jFrame) {
        this.jFrame = jFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        jFrame.dispose();
    }

}
