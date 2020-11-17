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

/** This is the package where Affect modules live */
package org.alchemy.affect;

/** Import the entire Alchemy 'core' */
import com.martiansoftware.jsap.JSAP;
import java.awt.event.KeyEvent;
import org.alchemy.core.*;
import java.awt.*;
//import java.awt.geom.Area;

/** Import some Java objects that are useful for handling mouse events */
import java.awt.event.MouseEvent;
import java.io.File;

// XML Document libraries
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.w3c.dom.svg.*;
import org.w3c.dom.css.CSSStyleDeclaration;

// BATIK (for svg)
import java.awt.Dimension;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.parser.AWTPathProducer;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PathParser;
import java.awt.geom.*;
import java.util.HashSet;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.anim.dom.SVGOMSVGElement;

import com.martiansoftware.jsap.stringparsers.ColorStringParser;

import org.apache.batik.bridge.UserAgent;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.GVTBuilder;

import org.apache.batik.gvt.GraphicsNode;
//import org.w3c.dom.Document; //because of Document class conflict, do not import
//import org.w3c.dom.DOMImplementation;

/**
 *
 * AffectModule.java
 * 'AffectModule' is the name of the module
 * and it must match the filename.
 * 
 * AlcModule is the Alchemy template for a module
 * that does some basic functionality for us
 * such as loading up and telling us when the mouse is moved etc...
 * 
 * We extend AlcModule to add additionaly functionality.
 * 
 */
public class GameDev extends AlcModule {
    
    boolean loadSVGFlag = false;
    boolean eraserEnabled = false;
    AlcShape eraserShape;
    AlcShape draggedShape;

    // Note there is no constructor, use setup() instead

    // Override means to override the default functions given to us by AlcModule
    @Override
    protected void setup() {
        // This function is called when the module is first selected in the menu
        // It will only be called once, so is useful for doing stuff like
        // loading interface elements into the menu bar etc...
    }

    @Override
    protected void cleared() {
        // This function is called when the canvas is cleared
        // You might sometimes need to use it
        // if you are say counting the number of shapes
        // and you want to know when to set it back to zero
    }

    @Override
    protected void reselect() {
        // This function is called when the module is reselected in the menu
        // i.e. the module is turned off then on again
    }

    @Override
    protected void affect() {
        // This function is called only on affect modules
        // It is called everytime the canvas is redrawn
        // The idea is that affect modules can affect a shape
        // that the create module has created


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // This function is called when the mouse/pen moves
        // It is called A LOT
    }

    @Override
    public void mousePressed(MouseEvent e) {
         // This function is called when the mouse/pen is pressed
         // TODO: Set draw mode to over if it is currently set to under (for erasing)
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // This function is called when the mouse/pen is dragged
        if (eraserEnabled){
            draggedShape = canvas.getCurrentCreateShape();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        // This function is called when the mouse/pen is released
        // Remove shapes that intersect with the eraser shape
        if (eraserEnabled){
            canvas.commitShapes();
            
            if (!draggedShape.equals(canvas.getCurrentShape())){
                System.out.printf("ERROR\n");
                eraserShape = draggedShape;
                this.removeEraserShape();
                canvas.redraw(true);
                return;
            }
            else{
                eraserShape = canvas.getCurrentShape();
            }

            
            // If the eraser line is straight (and has no area), remove it and move on
            if (eraserShape == null){
                this.removeEraserShape();
                canvas.redraw(true);
//                System.out.printf("ERASER NULL\n");
                return;
            }
            
            Area eraserArea = new Area(eraserShape.getPath());
            if (eraserArea.isEmpty()){
                this.removeEraserShape();
                canvas.redraw(true);
//                System.out.printf("ERASER AREA NULL\n");
                return;
            }
            
            
            // Remove the shapes under the mouse
            Point p = e.getPoint();
            for (int i = canvas.shapes.size() - 1; i >= 0; i--) {
                AlcShape thisShape = canvas.shapes.get(i);
                GeneralPath currentPath = thisShape.getPath();
                
                // Use areas to determine which shapes to remove
                Area currentArea = new Area(currentPath);
                
                // If the current area is a line (and is therefore empty) test using bounds instead
                if(currentArea.isEmpty() && currentPath.intersects(eraserShape.getPath().getBounds())){
                    canvas.removeShape(i);
                }

                // The area is not a line, so test intersection with eraser shape
                currentArea.intersect(eraserArea);
                if (!currentArea.isEmpty()) {
                    canvas.removeShape(i);
                }
            }
            
            canvas.redraw(true);
        }
        
    }
    
    public void removeEraserShape(){
        for (int i = canvas.shapes.size() - 1; i >= 0; i--) {
            AlcShape thisShape = canvas.shapes.get(i);
            if (thisShape.equals(eraserShape)) {
//                System.out.printf("REMOVE SHAPE\n");
                canvas.removeShape(i);
                return;
            }
        }
    }
    
    public void keyPressed(KeyEvent e){
        if (e.getKeyChar() == 'a'){
            if (loadSVGFlag == false){
                System.out.printf("Loading SVG\n");
                importSVG();
                loadSVGFlag = true;
            }
        }
        
        if (e.getKeyChar() == 'e'){
            if (eraserEnabled == false){
                System.out.printf("Eraser enabled\n");
                eraserEnabled = true;
            }
        }
    }
    
    public void keyReleased(KeyEvent e){
        if (e.getKeyChar() == 'a'){
            if (loadSVGFlag == true){
                loadSVGFlag = false;
            }
            
        }
        
        if (e.getKeyChar() == 'e'){
            if (eraserEnabled == true){
                eraserEnabled = false;
            }
        }
    }
    
    private void importSVG(){
        File importFile = AlcUtil.showFileChooser();
       if(importFile==null){
           System.out.println("No File selected for import");
       }else if(!importFile.canRead()){
//           AlcUtil.showMessageDialog(getS("noReadError"), getS("errorTitle"), JOptionPane.ERROR_MESSAGE);
       }else{
            try{
                System.out.printf("%s\n",importFile.getAbsolutePath());
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                Document doc = builder.parse(importFile);
                
//                String cn="";
//                try {
//                    cn = Class.forName("org.apache.xerces.parsers.SAXParser").getName();
//                } catch (ClassNotFoundException t) {// NOSONAR
//                    try {
//                        cn = Class.forName("com.sun.org.apache.xerces.internal.parsers.SAXParser").getName();
//                    } catch (ClassNotFoundException e) {
//                        System.out.printf("Could not find SAXParser");
//                    }
//                }
                String parser = XMLResourceDescriptor.getXMLParserClassName();
                SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
                SVGDocument doc = f.createSVGDocument(importFile.toURI().toString());
                
                // The following is a bunch of nonsense to get parsing of SVG data working/setup
                UserAgent userAgent;
                DocumentLoader loader;
                BridgeContext ctx;
                GVTBuilder builder;
                GraphicsNode rootGN;

                userAgent = new UserAgentAdapter();
                loader = new DocumentLoader(userAgent);
                ctx = new BridgeContext(userAgent, loader);
                ctx.setDynamicState(BridgeContext.DYNAMIC);
                builder = new GVTBuilder();
                rootGN = builder.build(ctx, doc);
                
                SVGOMSVGElement myRootSVGElement = (SVGOMSVGElement) doc.getDocumentElement();

                // Get all the paths in the file
                NodeList paths = myRootSVGElement.getElementsByTagName("path");

                for (int i = 0; i < paths.getLength(); ++i) {
                    Element cur = (Element) paths.item(i);

                    // Get stroke style
                    String shapeStroke = myRootSVGElement.getComputedStyle(cur, null).getPropertyValue("stroke");
                    // The stroke is defined in the group style
                    if(shapeStroke.equals("")){
                        shapeStroke = myRootSVGElement.getComputedStyle((Element)cur.getParentNode(), null).getPropertyValue("fill");
                    }
                    
                    int style = AlcConstants.STYLE_FILL;
                    if(!shapeStroke.contains("none")){
                        style = AlcConstants.STYLE_STROKE;
                    }
                    
                    // Get the color information
                    // All the places color could be stored for a given shape
                    String finalColor = "";
                    String parentColor = myRootSVGElement.getComputedStyle((Element)cur.getParentNode(), null).getPropertyValue("fill");
                    String strokeColor = myRootSVGElement.getComputedStyle(cur, null).getPropertyValue("stroke");
                    String fillValue = myRootSVGElement.getComputedStyle(cur, null).getPropertyValue("fill");
                    
                    // See if we have a defined fill color
                    if (fillValue.contains("rgb")){
                        fillValue = fillValue.replace("rgb(", "");
                        fillValue = fillValue.replace(")","");
                        fillValue = fillValue.replace(" ","");
                        finalColor = fillValue;
                    }
                    else if (fillValue.contains("none") && (strokeColor.contains("none")||strokeColor.equals(""))){
                        finalColor = parentColor;
                    }
                    // Named color
                    else if(!fillValue.equals("")){
                        finalColor = fillValue;
                    }
                    
                    // Check if we have a defined stroke color
                    if (strokeColor.contains("rgb")){
                        strokeColor = strokeColor.replace("rgb(", "");
                        strokeColor = strokeColor.replace(")","");
                        strokeColor = strokeColor.replace(" ","");
                        finalColor = strokeColor;
                    }
                    else if(strokeColor.equals("none") && finalColor.equals("")){
                        finalColor = parentColor;
                    }
                    // Named color
                    else if(!strokeColor.equals("") && finalColor.equals("")){
                        finalColor = strokeColor;
                    }
                    
                    // If the fill and stroke are undefined, use the parent color
                    if (finalColor.equals(parentColor)){
                        if (finalColor.contains("rgb")) {
                            finalColor = finalColor.replace("rgb(", "");
                            finalColor = finalColor.replace(")", "");
                            finalColor = finalColor.replace(" ", "");
                        }
                        // NOTE: named colors are already handled, no need to do anything here
                    }
                    Color shapeColor = (Color)JSAP.COLOR_PARSER.parse(finalColor);
                    
                    
                    // Get stroke widths
                    String parentStrokeWidth = myRootSVGElement.getComputedStyle((Element)cur.getParentNode(), null).getPropertyValue("stroke-width");
                    String strokeWidth = myRootSVGElement.getComputedStyle(cur, null).getPropertyValue("stroke-width");
                    if (strokeWidth.equals("")){
                        strokeWidth = parentStrokeWidth;
                    }
                    float stroke = Float.parseFloat(strokeWidth);

                    // Parse Paths into shapes
                    String path = cur.getAttribute("d");
                    AWTPathProducer pathProducer = new AWTPathProducer();
                    PathParser pathParser = new PathParser();
                    pathParser.setPathHandler(pathProducer);
                    pathParser.parse(path);

                    PathIterator pi = pathProducer.getShape().getPathIterator(null);
                    GeneralPath generalPath = new GeneralPath();
                    generalPath.append(pi, true);

                    // Create Alchemy shape
                    AlcShape shape = new AlcShape(generalPath);
                    shape.setStyle(style);
                    shape.setColor(shapeColor);
                    shape.setLineWidth(stroke);
                    
                    canvas.createShapes.add(shape);
                    canvas.redraw();

                }
                
//                SVGGraphics2D svgGenerator = new SVGGraphics2D(doc);
//                org.w3c.dom.Element root = svgGenerator.getRoot();
//                Node root = doc.getRootElement();
//                
//                Node test = root.getChildNodes().item(2).getChildNodes().item(0);
//                System.out.printf("%d\n", test.getChildNodes().getLength());
//                int numGroups = root.getChildNodes().item(2).getChildNodes().getLength();
//                System.out.printf("%d\n", root.getChildNodes().item(2).getChildNodes().getLength());
//                for (int j = 0; j < numGroups; j++){
//                    // Determine fill color (if any)
//                    
//                    NodeList paths = root.getChildNodes().item(2).getChildNodes().item(j).getChildNodes();
//                    for (int i = 0; i < paths.getLength(); i++) {
//                        Node cur = paths.item(i);
////                    System.out.printf("%s\n", cur.getNodeName());
//                        if (cur.getNodeName() == "path") {
//                            try {
//                                Node style = cur.getAttributes().item(0);
//                                Node path = cur.getAttributes().item(1);
//                                System.out.printf("%s\n%s\n\n", style.getNodeValue(), path.getNodeValue());
//                                
//                                
//                                AWTPathProducer pathProducer = new AWTPathProducer();
//                                PathParser pathParser = new PathParser();
//                                pathParser.setPathHandler(pathProducer);
//                                pathParser.parse(path.getNodeValue());
//
//                                PathIterator pi = pathProducer.getShape().getPathIterator(null);
//                                GeneralPath generalPath = new GeneralPath();
//                                while (!pi.isDone()) {
//                                    generalPath.append(pi, true);
//
//                                    pi.next();
//                                }
//
//                                // Determine if the shape is filled or a stroke
//                                // Create Alchemy shape
//                                AlcShape shape = new AlcShape(generalPath);
////                            shape.setStyle(AlcConstants.STYLE_FILL);
//                                canvas.createShapes.add(shape);
////                        if (outlineMode && canvas.getStyle() == STYLE_FILL) {
////                            shape.setStyle(STYLE_STROKE);
////                        }
//                                canvas.redraw();
//                            } catch (ParseException ex) {
//                                ex.printStackTrace();
////                        return new Rectangle2D.Float(0, 0, 1, 1);
//                            }
//                        }
//
//                    }
//                }
                
           
            }catch (Exception e) {
                e.printStackTrace();
            }
//           try{
//               // Load SVG stuff
//
//           }catch (IOException ex) {
//                System.err.println(ex);
//           } 
       }
    }
}
