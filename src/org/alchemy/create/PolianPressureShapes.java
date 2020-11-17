/*
 * This file is part of the Alchemy project - http://al.chemy.org
 * 
 * Copyright (c) 2007-2010 Karl D.D. Willis
 * 
 * Alchemy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Alchemy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Alchemy.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.alchemy.create;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.alchemy.core.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.Point;


/**
 *
 * PolianPressureShapes.java
 */
public class PolianPressureShapes extends AlcModule {

    private AlcToolBarSubSection subToolBarSection;
    private int pressureAmount;
    private int startPressure = 25;
    
    // Key press flags
    private boolean hideFGFlag = false;
    private boolean hideBGFlag = false;
    private boolean hideBGToggle = false;
    private boolean setBGFlag = false;
    private boolean colorSelectFlag = false;
    private boolean singleColorFlag = false;
    
    private boolean singleColorModeActive = false;
    private boolean singleColorSelectActive = false;
 
    
    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    @Override
    public void setup() {
        pressureAmount = startPressure;
        createSubToolBarSection();
        toolBar.addSubToolBarSection(subToolBarSection);
    }

    @Override
    public void reselect() {
        toolBar.addSubToolBarSection(subToolBarSection);
    }

    private void createSubToolBarSection() {
        subToolBarSection = new AlcToolBarSubSection(this);


        final int pressureMin = 1;
        final int pressureMax = 200;

        final AlcSubSpinner pressureSpinner = new AlcSubSpinner(
                "PolianPressure",
                pressureMin,
                pressureMax,
                startPressure,
                1);

        pressureSpinner.setToolTip("Change the amount of pressure");
        pressureSpinner.addChangeListener(
                new ChangeListener() {

                    public void stateChanged(ChangeEvent e) {
                        pressureAmount = pressureSpinner.getValue();
                    }
                });

        subToolBarSection.add(pressureSpinner);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AlcShape shape = new AlcShape();
        Point2D.Float p = canvas.getPenLocation();
        shape.spineTo(p, getPressure());
        canvas.createShapes.add(shape);
        
        // Set the color based on the loaded image
        BufferedImage bgImage = canvas.getBufferedImage();
        
        if (bgImage != null){
            if(singleColorModeActive){
                shape.setColor(canvas.getColor());
            }
            else{
                // Determine where the click translates to on the image
                int pX = (int) p.x - canvas.getImageLocation().x;
                int pY = (int) p.y - canvas.getImageLocation().y;

                // If we are outside the bounds of the image, use a border color
                if (p.x <= canvas.getImageLocation().x) {
                    pX = 5;
                }

                if (p.x >= bgImage.getWidth() + canvas.getImageLocation().x) {
                    pX = (bgImage.getWidth()) - 5;
                }

                if (p.y <= canvas.getImageLocation().y) {
                    pY = 5;
                }

                if (p.y >= bgImage.getHeight() + canvas.getImageLocation().y) {
                    pY = (bgImage.getHeight()) - 5;
                }

                Color traceColor = new Color(bgImage.getRGB(pX, pY));

                shape.setColor(traceColor);
            }
            
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        AlcShape currentShape = canvas.getCurrentCreateShape();
        if (currentShape != null) {
            Point2D.Float p = canvas.getPenLocation();
            currentShape.spineTo(p, getPressure());
            canvas.redraw();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.commitShapes();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        
        // When the color select key is pressed (and we're in single-color mode), pull color from the background image
        if (singleColorModeActive && singleColorSelectActive){
            // Set the color based on the loaded image
            Point2D.Float p = canvas.getPenLocation();
            BufferedImage bgImage = canvas.getBufferedImage();

            if (bgImage != null){
                // Determine where the click translates to on the image
                int pX = (int) p.x - canvas.getImageLocation().x;
                int pY = (int) p.y - canvas.getImageLocation().y;

                // If we are outside the bounds of the image, use a border color
                if (p.x <= canvas.getImageLocation().x) {
                    pX = 5;
                }

                if (p.x >= bgImage.getWidth() + canvas.getImageLocation().x) {
                    pX = (bgImage.getWidth()) - 5;
                }

                if (p.y <= canvas.getImageLocation().y) {
                    pY = 5;
                }

                if (p.y >= bgImage.getHeight() + canvas.getImageLocation().y) {
                    pY = (bgImage.getHeight()) - 5;
                }

                Color traceColor = new Color(bgImage.getRGB(pX, pY));
                canvas.setColor(traceColor);
            }
        }
    }

    private float getPressure() {
        float pressure = 1;
        if (canvas.getPenType() != PEN_CURSOR) {
            pressure = canvas.getPenPressure() * (float) pressureAmount;
        }
        return pressure;
    }
    
    public void keyPressed(KeyEvent e) {
        // Toggle single color mode
        if (e.getKeyChar() == 'n' && singleColorFlag == false){
            if (singleColorModeActive == true){
                singleColorModeActive = false;
                System.out.printf("Single Color Mode: Deactivated\n");
            }
            else{
                singleColorModeActive = true;
                System.out.printf("Single Color Mode: Active\n");
                
            }
            
            singleColorFlag = true;
        }
        
        // Enable color selection (it will only do anything while single color mode is active)
        if (e.getKeyChar() == 'm' && colorSelectFlag == false){
            singleColorSelectActive = true;
            
            colorSelectFlag = true;
        }
        
        // Set blank canvas image
        // NOTE: the blank canvas image is set when the image is first loaded
        if (e.getKeyChar() == 'o' && setBGFlag == false){
            canvas.setBlankCanvasImage();
            setBGFlag = true;
        }
        
        // Hide everything but background
        if (e.getKeyChar() == 'a'){
            if (hideFGFlag == false){
                canvas.hideShapes();
                hideFGFlag = true;
            }
        }
        
        // Hide background image
        if (e.getKeyChar() == 'p'){
            if (hideBGFlag == false){
                hideBGToggle = !hideBGToggle;
                
                hideBGFlag = true;
                
                if (hideBGToggle){
                    canvas.setImageDisplay(false);
                    canvas.redraw();
                }
                else{
                    canvas.setImageDisplay(true);
                    canvas.redraw();
                }
            }
        }
    }
    
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'n' && singleColorFlag){
            singleColorFlag = false;
        }
        
        if (e.getKeyChar() == 'm' && colorSelectFlag){
            singleColorSelectActive = false;
            
            colorSelectFlag = false;
        }
        
        if (e.getKeyChar() == 'o' && setBGFlag){
            setBGFlag = false;
        }
        
        if (e.getKeyChar() == 'a'){
            if (hideFGFlag == true){
                canvas.showShapes();
                hideFGFlag = false;
            }
            
        }
        
        // show background image
        if (e.getKeyChar() == 'p'){
            if (hideBGFlag == true){
                hideBGFlag = false;
            }
        }
    }
}
