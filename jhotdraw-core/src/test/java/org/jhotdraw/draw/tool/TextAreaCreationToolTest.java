package org.jhotdraw.draw.tool;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.text.FloatingTextArea;
import org.junit.Before;
import org.junit.Test;

import javax.swing.undo.UndoableEdit;
import java.awt.geom.Rectangle2D;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TextAreaCreationToolTest {
    private TextHolderFigure typingTarget;
    private FloatingTextArea textArea;
    private DrawingEditor drawingEditor;
    private TextAreaCreationTool textAreaCreationTool;
    private TextUndoableEdit textUndoableEdit;
    private Drawing drawing;
    private DrawingView drawingView;
    private TextAreaFieldBounds textAreaFieldBounds;

    @Before
    public void setUp() {
        typingTarget = mock(TextHolderFigure.class);
        textArea = mock(FloatingTextArea.class);
        drawingEditor = mock(DrawingEditor.class);
        drawing = mock(Drawing.class);
        drawingView = mock(DrawingView.class);
        textUndoableEdit = mock(TextUndoableEdit.class);
        textAreaFieldBounds = mock(TextAreaFieldBounds.class);

        textAreaCreationTool = new TextAreaCreationTool(typingTarget);

        UndoableEdit undoableEdit = mock(UndoableEdit.class);

        when(textUndoableEdit.makeUndoableEdit(any(), anyString(), anyString())).thenReturn(undoableEdit);
        when(typingTarget.getText()).thenReturn("original text");
        when(textArea.getText()).thenReturn("changed text");
        when(drawingEditor.getActiveView()).thenReturn(drawingView);
        when(drawingView.getDrawing()).thenReturn((drawing));
        when(drawingView.getComponent()).thenReturn(new javax.swing.JPanel());
        when(textAreaFieldBounds.getRectangleFieldBounds(typingTarget)).thenReturn(new Rectangle2D.Double());
        doNothing().when(textArea).endOverlay();
    }

    @Test
    public void testEndEdit() throws IllegalAccessException {
        textUndoableEdit = new TextUndoableEdit();

        FieldUtils.writeField(textAreaCreationTool, "editor", drawingEditor, true);
        FieldUtils.writeField(textAreaCreationTool, "textArea", textArea, true);
        FieldUtils.writeField(textAreaCreationTool, "textUndoableEdit", textUndoableEdit, true);
        FieldUtils.writeField(textAreaCreationTool, "typingTarget", typingTarget, true);

        textAreaCreationTool.endEdit();

        Object valueOfField = FieldUtils.readField(textAreaCreationTool, "typingTarget", true);

        assertNull(valueOfField);
        verify(typingTarget).setText("changed text");
        verify(drawing).fireUndoableEditHappened(any());
        verify(textArea).endOverlay();
    }

    @Test
    public void testBeginEdit() throws IllegalAccessException {
        textUndoableEdit = new TextUndoableEdit();

        FieldUtils.writeField(textAreaCreationTool, "editor", drawingEditor, true);
        FieldUtils.writeField(textAreaCreationTool, "textArea", textArea, true);
        FieldUtils.writeField(textAreaCreationTool, "textUndoableEdit", textUndoableEdit, true);
        FieldUtils.writeField(textAreaCreationTool, "textAreaFieldBounds", textAreaFieldBounds, true);
        FieldUtils.writeField(textAreaCreationTool, "typingTarget", null, true);

        textAreaCreationTool.beginEdit(typingTarget);

        Object valueOfField = FieldUtils.readField(textAreaCreationTool, "typingTarget", true);

        assertEquals(typingTarget, valueOfField);
        verify(textArea).createOverlay(drawingView, typingTarget);
        verify(textArea).setBounds(any(), eq("original text"));
        verify(textArea).requestFocus();
    }
}
