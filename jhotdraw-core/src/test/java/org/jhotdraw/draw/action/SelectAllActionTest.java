package org.jhotdraw.draw.action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class SelectAllActionTest {


    private DrawingView view;

    private Figure figure1;
    private Figure figure2;

    private SelectAllAction action;

    @Before
    public void setUp() {
        Drawing drawing;
        DrawingEditor editor;
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);
        drawing = mock(Drawing.class);

        figure1 = mock(Figure.class);
        figure2 = mock(Figure.class);

        List<Figure> figures = Arrays.asList(figure1, figure2);

        when(editor.getActiveView()).thenReturn(view);
        when(view.getDrawing()).thenReturn(drawing);
        when(drawing.getChildren()).thenReturn(figures);

        when(figure1.isSelectable()).thenReturn(true);
        when(figure2.isSelectable()).thenReturn(false);

        action = new SelectAllAction(editor);
    }

    @Test
    public void testSelectAll() {
        action.actionPerformed(new ActionEvent(this, 0, null));

        // figure1 is selectable, so addToSelection should be called
        verify(view).addToSelection(figure1);

        // figure2 is not selectable, so addToSelection should NOT be called
        verify(view, never()).addToSelection(figure2);

        // clearSelection should always be called
        verify(view).clearSelection();
    }
}
