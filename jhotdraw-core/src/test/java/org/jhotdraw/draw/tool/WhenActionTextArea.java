package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.text.FloatingTextArea;
import org.mockito.Mockito;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

public class WhenActionTextArea extends Stage<WhenActionTextArea> {

    @ScenarioState
    Drawing drawing;
    @ScenarioState
    DrawingEditor drawingEditor;
    @ScenarioState
    DrawingView drawingView;
    @ScenarioState
    TextAreaCreationTool textAreaCreationTool;

    public WhenActionTextArea user_creates_text_area() {
        Component viewComponent = drawingView.getComponent();
        MouseEvent pressed = new MouseEvent(viewComponent, MouseEvent.MOUSE_PRESSED,
                System.currentTimeMillis(), 0, 0, 0, 1, false, MouseEvent.BUTTON1);
        textAreaCreationTool.mousePressed(pressed);
        return self();
    }

    public WhenActionTextArea user_writes(String text) throws NoSuchFieldException, IllegalAccessException {
        FloatingTextArea mockTextArea = mock(FloatingTextArea.class);

        Mockito.when(mockTextArea.getText()).thenReturn(text);

        Field field = textAreaCreationTool.getClass().getDeclaredField("textArea");
        field.setAccessible(true);
        field.set(textAreaCreationTool, mockTextArea);

        textAreaCreationTool.actionPerformed(null);
        return this;
    }

}
