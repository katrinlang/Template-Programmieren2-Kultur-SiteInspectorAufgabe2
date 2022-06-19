package org.htw.prog2.aufgabe2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageSeries {
    private ArrayList<Frame> frames = new ArrayList<>();
    private String paths[];

    public ImageSeries(File[] files) {
        this.paths= new String[files.length];

        BufferedImage image= null, tracing= null, grayscaleImage= null, overlay= null;
        int i = 0;
        try {
            for (;i < files.length; i++) {
                try {
                    paths[i] = files[i].getAbsolutePath().replace(".png", "");
                    image = ImageIO.read(files[i]);
                } catch (IOException e) {
                    System.out.println("Error reading image file " + files[i].getAbsolutePath() + ": " + e.getMessage());
                }
                try {
                    tracing = ImageIO.read(new File(files[i].getAbsolutePath().replace(".png", "_tracing.png")));
                } catch (IOException e) {
                    System.out.println("Error reading tracing for image file " + files[i].getAbsolutePath() + ": " + e.getMessage());
                }
                try {
                    grayscaleImage = ImageIO.read(new File(files[i].getAbsolutePath().replace(".png", "_grayscale.png")));
                } catch (IOException e) {
                }
                try {
                    overlay = ImageIO.read(new File(files[i].getAbsolutePath().replace(".png", "_overlay.png")));
                } catch (IOException e) {
                }
                frames.add(new Frame(image, grayscaleImage, tracing, overlay));
            }
        }catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void writeFrames(int from, int to, boolean grayscale, boolean overlay, boolean combined) {
        for(int i=from; i<frames.size() && i<to; i++) {
            if(grayscale) {
                String filename = paths[i] + "_grayscale.png";
                writeImage(frames.get(i).getGrayscaleImage(), filename);
            }
            if(overlay) {
                String filename = paths[i] + "_overlay.png";
                writeImage(frames.get(i).getOverlay(), filename);
            }
            if(combined) {
                String filename = paths[i] + "_combined.png";
                writeImage(frames.get(i).getCombinedImage(true), filename);
            }
        }
    }

    private void writeImage(BufferedImage image, String filename) {
        File outfile = new File(filename);
        try {
            ImageIO.write(image, "png", outfile);
        } catch (IOException e) {
            System.out.println("Could not write file " + filename + ": " + e.getMessage());
        }
    }

    public int getNumFrames() {return 0;}

    public Frame getFrame(int num) {return null;}
}
