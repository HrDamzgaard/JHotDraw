/*
 * @(#)SelectionTool.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 */
package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.Figure;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.event.ToolAdapter;
import org.jhotdraw.draw.event.ToolEvent;
import org.jhotdraw.draw.handle.Handle;

/**
 * Tool to select and manipulate figures.
 * <p>
 * States: area selection, figure dragging, handle manipulation.
 * Implemented using Tracker objects (Strategy pattern).
 */
public class SelectionTool extends AbstractTool {

    private static final long serialVersionUID = 1L;

    private Tool tracker;
    private HandleTracker handleTracker;
    private SelectAreaTracker selectAreaTracker;
    private DragTracker dragTracker;

    private TrackerHandler trackerHandler;

    public static final String SELECT_BEHIND_ENABLED_PROPERTY = "selectBehindEnabled";
    private boolean isSelectBehindEnabled = true;

    /**
     * Creates a new instance.
     */
    public SelectionTool() {
        tracker = getSelectAreaTracker();
        trackerHandler = new TrackerHandler();
        tracker.addToolListener(trackerHandler);
    }

    public void setSelectBehindEnabled(boolean newValue) {
        boolean oldValue = isSelectBehindEnabled;
        isSelectBehindEnabled = newValue;
        firePropertyChange(SELECT_BEHIND_ENABLED_PROPERTY, oldValue, newValue);
    }

    public boolean isSelectBehindEnabled() {
        return isSelectBehindEnabled;
    }

    @Override
    public void activate(DrawingEditor editor) {
        super.activate(editor);
        tracker.activate(editor);
    }

    @Override
    public void deactivate(DrawingEditor editor) {
        super.deactivate(editor);
        tracker.deactivate(editor);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (getView() != null && getView().isEnabled()) {
            tracker.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (getView() != null && getView().isEnabled()) {
            tracker.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (getView() != null && getView().isEnabled()) {
            tracker.keyTyped(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (getView() != null && getView().isEnabled()) {
            tracker.mouseClicked(evt);
        }
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        if (getView() != null && getView().isEnabled()) {
            tracker.mouseDragged(evt);
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        super.mouseEntered(evt);
        tracker.mouseEntered(evt);
    }

    @Override
    public void mouseExited(MouseEvent evt) {
        super.mouseExited(evt);
        tracker.mouseExited(evt);
    }

    @Override
    public void mouseMoved(MouseEvent evt) {
        tracker.mouseMoved(evt);
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        if (getView() != null && getView().isEnabled()) {
            tracker.mouseReleased(evt);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        tracker.draw(g);
    }

    /**
     * Refactored mousePressed() – small, readable, no duplicated logic.
     */
    @Override
    public void mousePressed(MouseEvent evt) {
        if (getView() == null || !getView().isEnabled()) return;

        super.mousePressed(evt); // sets anchor Point

        DrawingView view = getView();

        // 1. Check for handle first
        Handle handle = view.findHandle(new Point2D.Double(anchor.x, anchor.y));
        if (handle != null) {
            setTracker(getHandleTracker(handle));
            tracker.mousePressed(evt);
            return;
        }

        // 2. Convert anchor to drawing coordinates
        Point2D.Double drawingPoint = view.viewToDrawing(new Point2D.Double(anchor.x, anchor.y));

        // 3. Select figure using SelectionHelper
        Figure figure = SelectionHelper.selectFigure(view, drawingPoint, isSelectBehindEnabled(), evt.getModifiersEx());

        // 4. Decide tracker based on selection
        Tool newTracker;
        if (figure != null && figure.isSelectable()) {
            newTracker = getDragTracker(figure);
        } else {
            if (!evt.isShiftDown()) {
                view.clearSelection();
                view.setHandleDetailLevel(0);
            }
            newTracker = getSelectAreaTracker();
        }

        setTracker(newTracker);
        tracker.mousePressed(evt);
    }

    protected void setTracker(Tool newTracker) {
        if (tracker != null) {
            tracker.deactivate(getEditor());
            tracker.removeToolListener(trackerHandler);
        }
        tracker = newTracker;
        if (tracker != null) {
            tracker.activate(getEditor());
            tracker.addToolListener(trackerHandler);
        }
    }

    protected HandleTracker getHandleTracker(Handle handle) {
        if (handleTracker == null) handleTracker = new DefaultHandleTracker();
        handleTracker.setHandles(handle, getView().getCompatibleHandles(handle));
        return handleTracker;
    }

    protected DragTracker getDragTracker(Figure f) {
        if (dragTracker == null) dragTracker = new DefaultDragTracker();
        dragTracker.setDraggedFigure(f);
        return dragTracker;
    }

    protected SelectAreaTracker getSelectAreaTracker() {
        if (selectAreaTracker == null) selectAreaTracker = new DefaultSelectAreaTracker();
        return selectAreaTracker;
    }

    public void setHandleTracker(HandleTracker newValue) {
        handleTracker = newValue;
    }

    public void setSelectAreaTracker(SelectAreaTracker newValue) {
        selectAreaTracker = newValue;
    }

    public void setDragTracker(DragTracker newValue) {
        dragTracker = newValue;
    }

    @Override
    public boolean supportsHandleInteraction() {
        return true;
    }

    /**
     * Handles callbacks from trackers.
     */
    private class TrackerHandler extends ToolAdapter {
        @Override
        public void toolDone(ToolEvent event) {
            Tool newTracker = getSelectAreaTracker();
            if (newTracker != null) {
                if (tracker != null) {
                    tracker.deactivate(getEditor());
                    tracker.removeToolListener(this);
                }
                tracker = newTracker;
                tracker.activate(getEditor());
                tracker.addToolListener(this);
            }
            fireToolDone();
        }

        @Override
        public void areaInvalidated(ToolEvent e) {
            fireAreaInvalidated(e.getInvalidatedArea());
        }

        @Override
        public void boundsInvalidated(ToolEvent e) {
            fireBoundsInvalidated(e.getInvalidatedArea());
        }
    }
}
