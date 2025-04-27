package com.example.dmn.ui;

import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.shapes.ShapeHandler;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 * Custom shape handler for DMN elements.
 * Renders DMN-specific shapes according to the DMN specification.
 */
public class DMNShapeHandler implements ShapeHandler {
    private static final int DECISION_ARC_WIDTH = 10;
    private static final int DECISION_ARC_HEIGHT = 10;
    private static final int BKM_SLOPE = 10;
    
    /**
     * Draw the shape for a DMN element.
     * 
     * @param element The element to draw
     * @param graphics2D The graphics context
     * @param rectangle The rectangle defining the shape boundaries
     */
    @Override
    public void drawShape(Element element, Graphics2D graphics2D, Rectangle rectangle) {
        if (element == null) {
            return;
        }
        
        // Enable antialiasing for smoother drawing
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw based on stereotype
        if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION)) {
            drawDecision(graphics2D, rectangle);
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.INPUT_DATA)) {
            drawInputData(graphics2D, rectangle);
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL)) {
            drawBusinessKnowledgeModel(graphics2D, rectangle);
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION_SERVICE)) {
            drawDecisionService(graphics2D, rectangle);
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.KNOWLEDGE_SOURCE)) {
            drawKnowledgeSource(graphics2D, rectangle);
        }
    }
    
    /**
     * Draw a Decision element (rounded rectangle with light blue fill).
     * 
     * @param g Graphics context
     * @param rect Rectangle defining the shape boundaries
     */
    private void drawDecision(Graphics2D g, Rectangle rect) {
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(Color.decode(DMNStereotypes.DECISION_FILL_COLOR));
        
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
                rect.x, rect.y, rect.width, rect.height, 
                DECISION_ARC_WIDTH, DECISION_ARC_HEIGHT);
        
        g.fill(roundedRect);
        g.setColor(Color.BLACK);
        g.draw(roundedRect);
    }
    
    /**
     * Draw an Input Data element (oval with light green fill).
     * 
     * @param g Graphics context
     * @param rect Rectangle defining the shape boundaries
     */
    private void drawInputData(Graphics2D g, Rectangle rect) {
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(Color.decode(DMNStereotypes.INPUT_DATA_FILL_COLOR));
        
        g.fillOval(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.BLACK);
        g.drawOval(rect.x, rect.y, rect.width, rect.height);
    }
    
    /**
     * Draw a Business Knowledge Model element (rectangle with 'clipped' corners and light yellow fill).
     * 
     * @param g Graphics context
     * @param rect Rectangle defining the shape boundaries
     */
    private void drawBusinessKnowledgeModel(Graphics2D g, Rectangle rect) {
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(Color.decode(DMNStereotypes.BKM_FILL_COLOR));
        
        int[] xPoints = {
                rect.x + BKM_SLOPE,
                rect.x + rect.width - BKM_SLOPE,
                rect.x + rect.width,
                rect.x + rect.width,
                rect.x + rect.width - BKM_SLOPE,
                rect.x + BKM_SLOPE,
                rect.x,
                rect.x
        };
        
        int[] yPoints = {
                rect.y,
                rect.y,
                rect.y + BKM_SLOPE,
                rect.y + rect.height - BKM_SLOPE,
                rect.y + rect.height,
                rect.y + rect.height,
                rect.y + rect.height - BKM_SLOPE,
                rect.y + BKM_SLOPE
        };
        
        g.fillPolygon(xPoints, yPoints, 8);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 8);
    }
    
    /**
     * Draw a Decision Service element (rounded rectangle with thick border and light pink fill).
     * 
     * @param g Graphics context
     * @param rect Rectangle defining the shape boundaries
     */
    private void drawDecisionService(Graphics2D g, Rectangle rect) {
        g.setStroke(new BasicStroke(2.0f));
        g.setColor(Color.decode(DMNStereotypes.DECISION_SERVICE_FILL_COLOR));
        
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(
                rect.x, rect.y, rect.width, rect.height, 
                DECISION_ARC_WIDTH, DECISION_ARC_HEIGHT);
        
        g.fill(roundedRect);
        g.setColor(Color.BLACK);
        g.draw(roundedRect);
    }
    
    /**
     * Draw a Knowledge Source element (trapezoid with light gray fill).
     * 
     * @param g Graphics context
     * @param rect Rectangle defining the shape boundaries
     */
    private void drawKnowledgeSource(Graphics2D g, Rectangle rect) {
        g.setStroke(new BasicStroke(1.0f));
        g.setColor(Color.decode(DMNStereotypes.KNOWLEDGE_SOURCE_FILL_COLOR));
        
        int[] xPoints = {
                rect.x + rect.width / 4,
                rect.x + rect.width * 3 / 4,
                rect.x + rect.width,
                rect.x
        };
        
        int[] yPoints = {
                rect.y,
                rect.y,
                rect.y + rect.height,
                rect.y + rect.height
        };
        
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.BLACK);
        g.drawPolygon(xPoints, yPoints, 4);
    }
}
