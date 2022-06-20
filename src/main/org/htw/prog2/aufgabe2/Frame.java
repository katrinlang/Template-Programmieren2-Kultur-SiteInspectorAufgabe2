package org.htw.prog2.aufgabe2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame {

    private BufferedImage image;
    private BufferedImage tracing;
    private BufferedImage grayscaleImage;
    private BufferedImage overlay;
    private BufferedImage combinedImage;

    boolean showTracing= false;

    public Frame(BufferedImage image) {
        this.image = image;
    }

    public Frame(BufferedImage image, BufferedImage grayscaleImage, BufferedImage tracing, BufferedImage overlay) {

        this.image = image;
        this.tracing = tracing;
        if(grayscaleImage==null) computeGrayscaleImage();
        else this.grayscaleImage= grayscaleImage;
        if(overlay==null) computeOverlay();
        else this.overlay= overlay;
        computeCombinedImage();
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getGrayscaleImage() {
        return grayscaleImage;
    }

    public BufferedImage getTracing() {
        return tracing;
    }

    public BufferedImage getOverlay() {
        return overlay;
    }

    public BufferedImage getCombinedImage(boolean showTracing) {
        if(showTracing!=this.showTracing){
            this.showTracing= showTracing;
            computeCombinedImage();
        }
        return combinedImage;
    }

    private int brightness(BufferedImage image, int x, int y) {
        Color c = new Color(image.getRGB(x, y));
        return Math.max(c.getRed(), Math.max(c.getGreen(), c.getBlue()));
    }

    private void computeGrayscaleImage() {

        int width = image.getWidth();
        int height = image.getHeight();

        grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int x= 0; x<width; x++) {
            for (int y = 0; y < height; y++) {
                int brightness= brightness(image, x, y);
                Color c= new Color(brightness, brightness, brightness);
                grayscaleImage.setRGB(x, y, c.getRGB());
            }
        }
    }

    private void computeOverlay() {

        int width = tracing.getWidth();
        int height = tracing.getHeight();

        overlay = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int brightness = 255 - new Color(tracing.getRGB(x, y)).getRed();
                Color c = new Color(brightness, brightness, brightness, brightness);
                overlay.setRGB(x, y, c.getRGB());
            }
        }
    }

    public void computeCombinedImage(){

        int width = image.getWidth();
        int height = image.getHeight();

        float aspect= (float)width/(float)height;
        if(aspect>1) height*=2;
        else width*=2;

        combinedImage= new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = combinedImage.getGraphics();
        graphics.drawImage(grayscaleImage, 0, 0, null);
        if(aspect>1) {
            graphics.drawImage(image, 0, height/2, null);
            if(showTracing)
                graphics.drawImage(overlay, 0, height/2, null);
        }
        else{
            graphics.drawImage(image, width/2, 0, null);
            if(showTracing)
                graphics.drawImage(overlay, width/2, 0, null);

        }
        graphics.dispose();
    }
}
