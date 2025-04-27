package com.example.dmn.util;

import com.example.dmn.decisiontable.DMNDecisionTable;
import com.example.dmn.model.*;
import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.PresentationElementsManager;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.properties.PropertyManager;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.magicdraw.uml.symbols.shapes.ShapeElement;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility methods for working with DMN models.
 * Provides helper methods for creating, finding, and manipulating DMN elements.
 */
public class DMNModelUtils {
    
    /**
     * Create a new DMN diagram in the given package.
     * 
     * @param project Current project
     * @param targetPackage Package to contain the diagram
     * @param diagramName Name for the new diagram
     * @return The created diagram presentation element, or null if creation failed
     */
    public static DiagramPresentationElement createDMNDiagram(Project project, Package targetPackage, String diagramName) {
        if (project == null || targetPackage == null || diagramName == null || diagramName.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Diagram");
            
            // Create diagram owner class
            Class diagramOwner = project.getElementsFactory().createClassInstance();
            diagramOwner.setName(diagramName);
            diagramOwner.setOwner(targetPackage);
            
            // Apply DMN_Diagram stereotype
            DMNProfile dmnProfile = new DMNProfile();
            dmnProfile.init();
            dmnProfile.applyStereotype(diagramOwner, DMNStereotypes.DMN_DIAGRAM);
            
            // Create the diagram
            DiagramPresentationElement diagram = project.getDiagram().createDiagram(
                    DiagramTypeConstants.UML_CLASS_DIAGRAM, diagramOwner, null);
            diagram.setName(diagramName);
            
            SessionManager.getInstance().closeSession(project);
            
            return diagram;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN diagram: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create a DMN decision element in the given diagram.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param name Decision name
     * @param x X coordinate in the diagram
     * @param y Y coordinate in the diagram
     * @param width Width of the shape
     * @param height Height of the shape
     * @return Created DMNDecision or null if creation failed
     */
    public static DMNDecision createDecision(Project project, DiagramPresentationElement diagram, 
                                            String name, int x, int y, int width, int height) {
        if (project == null || diagram == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Decision");
            
            // Create decision
            DMNDecision decision = DMNDecision.create(project, name, diagram.getElement().getOwner());
            
            if (decision != null) {
                // Add to diagram
                PresentationElement shape = PresentationElementsManager.getInstance()
                        .createShapeElement(decision.getElement(), diagram);
                
                // Set position and size
                if (shape instanceof ShapeElement) {
                    ShapeElement shapeElement = (ShapeElement) shape;
                    Rectangle bounds = new Rectangle(x, y, width, height);
                    shapeElement.setBounds(bounds);
                    
                    // Set style
                    PropertyManager propertyManager = new PropertyManager();
                    propertyManager.setShapeFillColor(shapeElement, Color.decode(DMNStereotypes.DECISION_FILL_COLOR));
                    propertyManager.setLineWidth(shapeElement, 1.0f);
                    propertyManager.setRoundedRectangleRadius(shapeElement, 10, 10);
                }
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return decision;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Decision: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create a DMN input data element in the given diagram.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param name Input data name
     * @param x X coordinate in the diagram
     * @param y Y coordinate in the diagram
     * @param width Width of the shape
     * @param height Height of the shape
     * @return Created DMNInputData or null if creation failed
     */
    public static DMNInputData createInputData(Project project, DiagramPresentationElement diagram, 
                                              String name, int x, int y, int width, int height) {
        if (project == null || diagram == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Input Data");
            
            // Create input data
            DMNInputData inputData = DMNInputData.create(project, name, diagram.getElement().getOwner());
            
            if (inputData != null) {
                // Add to diagram
                PresentationElement shape = PresentationElementsManager.getInstance()
                        .createShapeElement(inputData.getElement(), diagram);
                
                // Set position and size
                if (shape instanceof ShapeElement) {
                    ShapeElement shapeElement = (ShapeElement) shape;
                    Rectangle bounds = new Rectangle(x, y, width, height);
                    shapeElement.setBounds(bounds);
                    
                    // Set style
                    PropertyManager propertyManager = new PropertyManager();
                    propertyManager.setShapeFillColor(shapeElement, Color.decode(DMNStereotypes.INPUT_DATA_FILL_COLOR));
                    propertyManager.setLineWidth(shapeElement, 1.0f);
                }
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return inputData;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Input Data: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create a DMN business knowledge model element in the given diagram.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param name BKM name
     * @param x X coordinate in the diagram
     * @param y Y coordinate in the diagram
     * @param width Width of the shape
     * @param height Height of the shape
     * @return Created DMNBusinessKnowledgeModel or null if creation failed
     */
    public static DMNBusinessKnowledgeModel createBusinessKnowledgeModel(
            Project project, DiagramPresentationElement diagram, 
            String name, int x, int y, int width, int height) {
        if (project == null || diagram == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Business Knowledge Model");
            
            // Create BKM
            DMNBusinessKnowledgeModel bkm = DMNBusinessKnowledgeModel.create(
                    project, name, diagram.getElement().getOwner());
            
            if (bkm != null) {
                // Add to diagram
                PresentationElement shape = PresentationElementsManager.getInstance()
                        .createShapeElement(bkm.getElement(), diagram);
                
                // Set position and size
                if (shape instanceof ShapeElement) {
                    ShapeElement shapeElement = (ShapeElement) shape;
                    Rectangle bounds = new Rectangle(x, y, width, height);
                    shapeElement.setBounds(bounds);
                    
                    // Set style
                    PropertyManager propertyManager = new PropertyManager();
                    propertyManager.setShapeFillColor(shapeElement, Color.decode(DMNStereotypes.BKM_FILL_COLOR));
                    propertyManager.setLineWidth(shapeElement, 1.0f);
                }
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return bkm;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Business Knowledge Model: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create a DMN knowledge source element in the given diagram.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param name Knowledge source name
     * @param x X coordinate in the diagram
     * @param y Y coordinate in the diagram
     * @param width Width of the shape
     * @param height Height of the shape
     * @return Created DMNKnowledgeSource or null if creation failed
     */
    public static DMNKnowledgeSource createKnowledgeSource(
            Project project, DiagramPresentationElement diagram, 
            String name, int x, int y, int width, int height) {
        if (project == null || diagram == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Knowledge Source");
            
            // Create knowledge source
            DMNKnowledgeSource knowledgeSource = DMNKnowledgeSource.create(
                    project, name, diagram.getElement().getOwner());
            
            if (knowledgeSource != null) {
                // Add to diagram
                PresentationElement shape = PresentationElementsManager.getInstance()
                        .createShapeElement(knowledgeSource.getElement(), diagram);
                
                // Set position and size
                if (shape instanceof ShapeElement) {
                    ShapeElement shapeElement = (ShapeElement) shape;
                    Rectangle bounds = new Rectangle(x, y, width, height);
                    shapeElement.setBounds(bounds);
                    
                    // Set style
                    PropertyManager propertyManager = new PropertyManager();
                    propertyManager.setShapeFillColor(shapeElement, 
                            Color.decode(DMNStereotypes.KNOWLEDGE_SOURCE_FILL_COLOR));
                    propertyManager.setLineWidth(shapeElement, 1.0f);
                }
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return knowledgeSource;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Knowledge Source: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create a DMN decision service element in the given diagram.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param name Decision service name
     * @param x X coordinate in the diagram
     * @param y Y coordinate in the diagram
     * @param width Width of the shape
     * @param height Height of the shape
     * @return Created DMNDecisionService or null if creation failed
     */
    public static DMNDecisionService createDecisionService(
            Project project, DiagramPresentationElement diagram, 
            String name, int x, int y, int width, int height) {
        if (project == null || diagram == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Decision Service");
            
            // Create decision service
            DMNDecisionService decisionService = DMNDecisionService.create(
                    project, name, diagram.getElement().getOwner());
            
            if (decisionService != null) {
                // Add to diagram
                PresentationElement shape = PresentationElementsManager.getInstance()
                        .createShapeElement(decisionService.getElement(), diagram);
                
                // Set position and size
                if (shape instanceof ShapeElement) {
                    ShapeElement shapeElement = (ShapeElement) shape;
                    Rectangle bounds = new Rectangle(x, y, width, height);
                    shapeElement.setBounds(bounds);
                    
                    // Set style
                    PropertyManager propertyManager = new PropertyManager();
                    propertyManager.setShapeFillColor(shapeElement, 
                            Color.decode(DMNStereotypes.DECISION_SERVICE_FILL_COLOR));
                    propertyManager.setLineWidth(shapeElement, 2.0f);
                    propertyManager.setRoundedRectangleRadius(shapeElement, 10, 10);
                }
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return decisionService;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Decision Service: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Create an information requirement between two elements.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param source Source element (Decision or InputData)
     * @param target Target element (Decision)
     * @return Created DMNRequirement or null if creation failed
     */
    public static DMNRequirement createInformationRequirement(
            Project project, DiagramPresentationElement diagram, 
            Element source, Element target) {
        return DMNRequirement.createInformationRequirement(project, source, target, diagram);
    }
    
    /**
     * Create a knowledge requirement between two elements.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param source Source element (BusinessKnowledgeModel)
     * @param target Target element (Decision or BusinessKnowledgeModel)
     * @return Created DMNRequirement or null if creation failed
     */
    public static DMNRequirement createKnowledgeRequirement(
            Project project, DiagramPresentationElement diagram, 
            Element source, Element target) {
        return DMNRequirement.createKnowledgeRequirement(project, source, target, diagram);
    }
    
    /**
     * Create an authority requirement between two elements.
     * 
     * @param project Current project
     * @param diagram Target diagram
     * @param source Source element (KnowledgeSource)
     * @param target Target element (Decision, BusinessKnowledgeModel, or KnowledgeSource)
     * @return Created DMNRequirement or null if creation failed
     */
    public static DMNRequirement createAuthorityRequirement(
            Project project, DiagramPresentationElement diagram, 
            Element source, Element target) {
        return DMNRequirement.createAuthorityRequirement(project, source, target, diagram);
    }
    
    /**
     * Create a decision table for a decision element.
     * 
     * @param project Current project
     * @param decision Decision element
     * @param name Name of the decision table
     * @return Created DMNDecisionTable or null if creation failed
     */
    public static DMNDecisionTable createDecisionTable(Project project, Element decision, String name) {
        if (project == null || decision == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        return DMNDecisionTable.create(project, name, decision);
    }
    
    /**
     * Get a decision table associated with an element.
     * 
     * @param element Element to check
     * @return Decision table or null if not found
     */
    public static DMNDecisionTable getDecisionTableForElement(Element element) {
        if (element == null) {
            return null;
        }
        
        // Check if the element itself is a decision table
        if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION_TABLE)) {
            return new DMNDecisionTable(element);
        }
        
        // If the element is a decision, check its owned elements
        if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION)) {
            for (Element owned : element.getOwnedElement()) {
                if (StereotypesHelper.hasStereotype(owned, DMNStereotypes.DECISION_TABLE)) {
                    return new DMNDecisionTable(owned);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Create a FEEL expression element.
     * 
     * @param project Current project
     * @param owner Owner element
     * @param name Expression name
     * @param expression Expression text
     * @return Created element or null if creation failed
     */
    public static Element createFEELExpression(
            Project project, Element owner, String name, String expression) {
        if (project == null || owner == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create FEEL Expression");
            
            // Create class to represent expression
            Class expressionClass = project.getElementsFactory().createClassInstance();
            expressionClass.setName(name);
            expressionClass.setOwner(owner);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(expressionClass, DMNStereotypes.FEEL_EXPRESSION);
            
            // Set expression text
            if (expression != null && !expression.trim().isEmpty()) {
                profile.setTaggedValue(expressionClass, DMNStereotypes.FEEL_EXPRESSION, "expression", expression);
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return expressionClass;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create FEEL Expression: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Find all DMN diagrams in the project.
     * 
     * @param project Current project
     * @return List of DMN diagrams
     */
    public static List<DiagramPresentationElement> findDMNDiagrams(Project project) {
        List<DiagramPresentationElement> result = new ArrayList<>();
        
        if (project == null) {
            return result;
        }
        
        // Get all diagrams in the project
        Collection<DiagramPresentationElement> allDiagrams = project.getDiagram().getDiagramPresentationElements();
        
        for (DiagramPresentationElement diagram : allDiagrams) {
            Element diagramOwner = diagram.getElement();
            
            if (diagramOwner != null && StereotypesHelper.hasStereotype(diagramOwner, DMNStereotypes.DMN_DIAGRAM)) {
                result.add(diagram);
            }
        }
        
        return result;
    }
    
    /**
     * Find all DMN elements of a specific type in the project.
     * 
     * @param project Current project
     * @param stereotypeName Stereotype name to search for
     * @return List of elements with the specified stereotype
     */
    public static List<Element> findDMNElements(Project project, String stereotypeName) {
        List<Element> result = new ArrayList<>();
        
        if (project == null || stereotypeName == null || stereotypeName.trim().isEmpty()) {
            return result;
        }
        
        // Get the stereotype
        DMNProfile profile = new DMNProfile();
        profile.init();
        Stereotype stereotype = profile.getStereotype(stereotypeName);
        
        if (stereotype == null) {
            return result;
        }
        
        // Find all elements with the stereotype
        Collection<Element> elements = StereotypesHelper.getExtendedElements(stereotype);
        result.addAll(elements);
        
        return result;
    }
    
    /**
     * Check if an element is a DMN diagram.
     * 
     * @param element Element to check
     * @return True if the element is a DMN diagram
     */
    public static boolean isDMNDiagram(Element element) {
        return element != null && StereotypesHelper.hasStereotype(element, DMNStereotypes.DMN_DIAGRAM);
    }
    
    /**
     * Get the DMN stereotype name for an element.
     * 
     * @param element Element to check
     * @return DMN stereotype name or null if not a DMN element
     */
    public static String getDMNStereotypeName(Element element) {
        if (element == null) {
            return null;
        }
        
        if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION)) {
            return DMNStereotypes.DECISION;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.INPUT_DATA)) {
            return DMNStereotypes.INPUT_DATA;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL)) {
            return DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION_SERVICE)) {
            return DMNStereotypes.DECISION_SERVICE;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.KNOWLEDGE_SOURCE)) {
            return DMNStereotypes.KNOWLEDGE_SOURCE;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DECISION_TABLE)) {
            return DMNStereotypes.DECISION_TABLE;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.FEEL_EXPRESSION)) {
            return DMNStereotypes.FEEL_EXPRESSION;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.INFORMATION_REQUIREMENT)) {
            return DMNStereotypes.INFORMATION_REQUIREMENT;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.KNOWLEDGE_REQUIREMENT)) {
            return DMNStereotypes.KNOWLEDGE_REQUIREMENT;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.AUTHORITY_REQUIREMENT)) {
            return DMNStereotypes.AUTHORITY_REQUIREMENT;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.DMN_DIAGRAM)) {
            return DMNStereotypes.DMN_DIAGRAM;
        }
        
        return null;
    }
    
    /**
     * Delete a DMN element and all its dependencies.
     * 
     * @param project Current project
     * @param element Element to delete
     * @return True if deletion was successful
     */
    public static boolean deleteElement(Project project, Element element) {
        if (project == null || element == null) {
            return false;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Delete DMN Element");
            
            // Delete the element
            ModelElementsManager.getInstance().removeElement(element);
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to delete DMN element: " + e.getMessage());
            return false;
        }
    }
}
