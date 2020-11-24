package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Drawer {
    private final Canvas downLayer;
    private final Canvas upperLayer;
    private final Canvas preview;
    private final GraphicsContext gcDown;
    private final GraphicsContext gcUp;
    private final GraphicsContext gcPreview;
    private Color color;
    private double size;
    private double old_x, old_y;
    private enum Brush {
        BRUSH, ERASER
    }


    Brush brush;

    public Drawer(Canvas downLayer, Canvas upperLayer, Canvas preview) {
        this(downLayer, upperLayer, preview, Color.BLACK, 5);
    }

    public Drawer(Canvas downCanvas, Canvas upperCanvas, Canvas preview, Color color, double size) {
        this.downLayer = downCanvas;
        gcDown = this.downLayer.getGraphicsContext2D();
        gcDown.setLineCap(StrokeLineCap.ROUND);

        this.upperLayer = upperCanvas;
        gcUp = this.upperLayer.getGraphicsContext2D();

        this.preview = preview;
        gcPreview = this.preview.getGraphicsContext2D();
        gcPreview.setStroke(Color.BLACK);
        gcPreview.setLineWidth(2);

        this.color = color;

        setSize(size);

        brush = Brush.BRUSH;

        updatePreview();
    }

    public void drawMouse(double x, double y) {
        clearUpperLayer();
        if (brush == Brush.BRUSH)
            gcUp.fillOval(x - size / 2, y - size / 2, size, size);
        else
            gcUp.strokeOval(x - size / 2, y - size / 2, size, size);
    }

    public void startLine(double x, double y) {
        old_x = x;
        old_y = y;
        drawMouse(x, y);
    }

    public void continueLine(double x, double y) {
        gcDown.strokeLine(old_x, old_y, x, y);
        old_x = x;
        old_y = y;
        drawMouse(x, y);
//        sendPic();
    }

    public void endLine(double x, double y) {
        gcDown.strokeLine(old_x, old_y, x, y);
        drawMouse(x, y);
//        sendPic();
    }

    private void updatePreview() {
        clearCanvas(preview);
        if (brush == Brush.BRUSH)
            gcPreview.fillOval(preview.getWidth() / 2 - size / 2, preview.getHeight() / 2 - size / 2, size, size);
        else
            gcPreview.strokeOval(preview.getWidth() / 2 - size / 2, preview.getHeight() / 2 - size / 2, size, size);
    }

    public void clearUpperLayer() {
        clearCanvas(upperLayer);
    }

    public void clearDownLayer() {
        clearCanvas(downLayer);
    }

    public void setToEraser() {
        brush = Brush.ERASER;
        gcDown.setFill(Color.WHITE);
        gcDown.setStroke(Color.WHITE);
        gcUp.setFill(Color.WHITE);
        gcUp.setStroke(Color.BLACK);
        gcUp.setLineWidth(2);
        updatePreview();
    }

    public void setToBrush() {
        brush = Brush.BRUSH;
        gcDown.setLineWidth(size);
        setColor(color);
        updatePreview();
    }

    private void clearCanvas(Canvas canvas) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void setColor(Color color) {
        this.color = color;
        if(brush == Brush.BRUSH) {
            gcDown.setFill(color);
            gcUp.setFill(color);
            gcDown.setStroke(color);
            gcPreview.setFill(color);
            updatePreview();
        }
    }

    public void setSize(double size) {
        this.size = size;
        gcDown.setLineWidth(size);
        updatePreview();
    }

    public String snapshotAndEncodeImage(){
        WritableImage image = new WritableImage((int)downLayer.getWidth(),(int)downLayer.getHeight());
        downLayer.snapshot(null,image);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image,null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(renderedImage,"png",baos);
            byte[] byteImage = baos.toByteArray();
            baos.close();
            return Base64.getEncoder().encodeToString(byteImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void decodeAndLoadPic(String encodedImage){
        byte[] byteImage = Base64.getDecoder().decode(encodedImage);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteImage);
        try {
            BufferedImage bufferedImage = ImageIO.read(bais);
            WritableImage finalImage = new WritableImage((int)downLayer.getWidth(),(int)downLayer.getHeight());
            finalImage = SwingFXUtils.toFXImage(bufferedImage,finalImage);
            gcDown.drawImage(finalImage,0,0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
