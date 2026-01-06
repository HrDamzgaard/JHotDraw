package org.jhotdraw.draw.action;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.GroupFigure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class UngroupActionTest
{
    private DrawingEditor editor;
    private DrawingView view;
    private UngroupAction ungroupAction;

    @Before
    public void setUp()
    {
        editor = mock(DrawingEditor.class);
        view = mock(DrawingView.class);

        when(editor.getActiveView()).thenReturn(view);
        ungroupAction = new UngroupAction(editor);
    }

    @Test
    public void testCanUngroupReturnsTrueWhenGroupIsSelected()
    {
        when(view.getSelectionCount()).thenReturn(1);

        GroupFigure realGroup = new GroupFigure();

        java.util.Set<Figure> selection = new java.util.HashSet<>();
        selection.add(realGroup);
        when(view.getSelectedFigures()).thenReturn(selection);

        assertTrue("the ungroup button should be aktiv", ungroupAction.canUngroup());
    }

    @Test
    public void testCanUngroupReturnsFalseWhenRegularFigureSelected()
    {
        when(view.getSelectionCount()).thenReturn(1);
        Figure regularFigure = mock(RectangleFigure.class);

        java.util.Set<Figure> selection = new java.util.HashSet<>();
        selection.add(regularFigure);
        when(view.getSelectedFigures()).thenReturn(selection);

        assertFalse("it sholdn't be posibale to ungroup a normal figure", ungroupAction.canUngroup());
    }
}
