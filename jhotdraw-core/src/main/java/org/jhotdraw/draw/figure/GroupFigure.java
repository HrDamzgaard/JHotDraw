/*
 * @(#)GroupFigure.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.figure;

import java.awt.geom.*;
import org.jhotdraw.geom.Geom;

/**
 * A {@link org.jhotdraw.draw.figure.Figure} which groups a collection of figures.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class GroupFigure extends AbstractCompositeFigure {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance.
     */
    public GroupFigure() {
        setConnectable(false);
    }

    /**
     * Returns the closest chop point by checking each child figure.
     * This replaces the default implementation that only checked the group's bounding box.
     */

    @Override
    public Point2D.Double chop(Point2D.Double from)
    {
        Point2D.Double closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Figure f : children)
        {
            Rectangle2D.Double r = f.getBounds();

            Point2D.Double childChop = Geom.angleToPoint(r, Geom.pointToAngle(r, from));

            if (childChop != null)
            {
                double distance = from.distanceSq(childChop);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = childChop;
                }
            }
        }

        if (closestPoint == null)
        {
            Rectangle2D.Double r = getBounds();
            closestPoint = Geom.angleToPoint(r, Geom.pointToAngle(r, from));
        }

        return closestPoint;
    }

    /**
     * Returns true if all children of the group are transformable.
     */
    @Override
    public boolean isTransformable() {
        for (Figure f : children) {
            if (!f.isTransformable()) {
                return false;
            }
        }
        return true;
    }
}
