package server.request;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import static server.connection.Server.keyType;
import client.connection.MouseInfo;
import java.awt.*;
import java.awt.event.InputEvent;
public class GetInput {
    private Robot robot;

    public GetInput() throws Exception {
        robot = new Robot();
    }

    public void onReceive(Object obj) {
        Object[] objs = (Object[]) obj;
        String cls = (String) objs[1];
        Object req = objs[0];

        if (cls.equals(keyType)) {
            hitKey((KeyEvent) req);
        } else {
            hitMouse((MouseInfo) req, (double) objs[2], (double) objs[3]);
        }
    }

    private void hitMouse(MouseInfo mouseEvent, double sceneMaxX, double sceneMaxY) {
        double sceneX = mouseEvent.getSceneX();
        double sceneY = mouseEvent.getSceneY();
        double screenMaxX = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double screenMaxY = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        double screenX = (screenMaxX * sceneX) / sceneMaxX;
        double screenY = (screenMaxY * sceneY) / sceneMaxY;
        robot.mouseMove((int) screenX, (int) screenY);
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED.getName())) {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                robot.mousePress(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON1));
            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                robot.mousePress(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON3));
            }
        } else if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED.getName())) {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                robot.mouseRelease(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON1));
            } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                robot.mouseRelease(InputEvent.getMaskForButton(java.awt.event.MouseEvent.BUTTON3));
            }
        }
    }

    private void hitKey(KeyEvent keyEvent) {
        if (keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)) {

            System.out.println();
            robot.keyPress((int)keyEvent.getCode().getChar().charAt(0));
        } else if (keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            robot.keyRelease((int)keyEvent.getCode().getChar().charAt(0));
        }
    }
}
