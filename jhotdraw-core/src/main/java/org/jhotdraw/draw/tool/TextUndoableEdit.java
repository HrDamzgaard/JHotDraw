package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.util.ResourceBundleUtil;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;

public class TextUndoableEdit {

    public UndoableEdit makeUndoableEdit(TextHolderFigure editedFigure, String oldText, String newText){
        return new AbstractUndoableEdit() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getPresentationName() {
                ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
                return labels.getString("attribute.text.text");
            }

            @Override
            public void undo() {
                super.undo();
                editedFigure.willChange();
                editedFigure.setText(oldText);
                editedFigure.changed();
            }

            @Override
            public void redo() {
                super.redo();
                editedFigure.willChange();
                editedFigure.setText(newText);
                editedFigure.changed();
            }
        };
    }

}
