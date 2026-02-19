package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextArea;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.swing.undo.UndoableEdit;
import java.awt.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TextAreaEditingToolTest {
    private TextHolderFigure typingTarget;
    private FloatingTextArea textArea;
    private DrawingEditor drawingEditor;
    private TextAreaEditingTool textAreaEditingTool;
    private TextUndoableEdit textUndoableEdit;
    private AbstractTool abstractTool;
    private Drawing drawing;
    private DrawingView drawingView;

    @Before
    public void setUp() {
        typingTarget = mock(TextHolderFigure.class);
        textArea = mock(FloatingTextArea.class);
        drawingEditor = mock(DrawingEditor.class);
        drawing = mock(Drawing.class);
        drawingView = mock(DrawingView.class);
        textUndoableEdit = mock(TextUndoableEdit.class);
        abstractTool = mock(AbstractTool.class);
        textAreaEditingTool = new TextAreaEditingTool(typingTarget);

        UndoableEdit undoableEdit = mock(UndoableEdit.class);

        when(textUndoableEdit.makeUndoableEdit(any(), anyString(), anyString())).thenReturn(undoableEdit);
        when(typingTarget.getText()).thenReturn("original text");
        when(textArea.getText()).thenReturn("changed text");
        when(drawingEditor.getActiveView()).thenReturn(drawingView);
        when(drawingView.getDrawing()).thenReturn((drawing));
        when(drawingView.getComponent()).thenReturn(new javax.swing.JPanel());
        doNothing().when(textArea).endOverlay();
    }

    @Test
    public void testEndEdit() throws IllegalAccessException {
        textUndoableEdit = new TextUndoableEdit();
        FieldUtils.writeField(textAreaEditingTool, "editor", drawingEditor, true);
        FieldUtils.writeField(textAreaEditingTool, "textArea", textArea, true);
        FieldUtils.writeField(textAreaEditingTool, "textUndoableEdit", textUndoableEdit, true);
        textAreaEditingTool.endEdit();
        Object valueOfField = FieldUtils.readField(textAreaEditingTool, "typingTarget", true);
        assertNull(valueOfField);
        verify(typingTarget).setText("changed text");
        verify(drawing).fireUndoableEditHappened(any());
        verify(textArea).endOverlay();
    }

    @Test
    public void testBeginEdit() throws IllegalAccessException {
        textUndoableEdit = new TextUndoableEdit();
        FieldUtils.writeField(textAreaEditingTool, "editor", drawingEditor, true);
        FieldUtils.writeField(textAreaEditingTool, "textArea", textArea, true);
        FieldUtils.writeField(textAreaEditingTool, "textUndoableEdit", textUndoableEdit, true);
        textAreaEditingTool.beginEdit(typingTarget);
        Object valueOfField
    }


}
