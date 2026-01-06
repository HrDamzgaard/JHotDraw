package org.jhotdraw.draw.action;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class GroupActionTest
{
    private DrawingEditor editor;
    private DrawingView view;
    private GroupAction groupAction;

    @Before
    public void setUp()
    {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);

        when(editor.getActiveView()).thenReturn(view);

        groupAction = new GroupAction(editor);
    }

    @Test
    public void testCanGroupWithMultipleFigures()
    {
        when(view.getSelectionCount()).thenReturn(3);

        assertTrue("3 figures should be groupable when picked", groupAction.canGroup());
    }

    @Test
    public void testCannotGroupWithSingleFigure()
    {
        when(view.getSelectionCount()).thenReturn(1);

        assertFalse("it shouldn't be posible to group 1 figure", groupAction.canGroup());
    }
}
