package org.jhotdraw.draw.action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Action to select all selectable figures in the drawing.
 */
public class SelectAllAction extends AbstractAction {

    private final DrawingEditor editor;

    public SelectAllAction(DrawingEditor editor) {
        super("Select All");
        this.editor = editor;

        // ✅ Bind Ctrl+A as the accelerator
        putValue(ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_A,
                        InputEvent.CTRL_MASK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DrawingView view = editor.getActiveView();
        if (view == null) {
            return;
        }

        view.clearSelection();

        Drawing drawing = view.getDrawing();

        for (Figure f : drawing.getChildren()) {
            if (f.isSelectable()) {
                view.addToSelection(f);
            }
        }
    }
}
