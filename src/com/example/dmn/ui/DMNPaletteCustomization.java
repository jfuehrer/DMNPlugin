package com.example.dmn.ui;

import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.DiagramToolbar;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.magicdraw.uml.symbols.PresentationElement;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Customizes the palette for DMN diagrams.
 * Adds DMN-specific tools to the diagram toolbar.
 */
public class DMNPaletteCustomization {
    private List<DefaultDiagramAction> dmnActions = new ArrayList<>();
    private Map<String, Icon> dmnIcons = new HashMap<>();
    
    /**
     * Initialize palette customization.
     */
    public void init() {
        loadIcons();
        createPaletteActions();
        registerPaletteActions();
    }
    
    /**
     * Dispose resources.
     */
    public void dispose() {
        // Unregister palette actions if needed
        for (DefaultDiagramAction action : dmnActions) {
            DiagramToolbar.getInstance().unregisterAction(action);
        }
        dmnActions.clear();
        dmnIcons.clear();
    }
    
    /**
     * Load DMN element icons.
     */
    private void loadIcons() {
        // Load icons from resources
        dmnIcons.put(DMNStereotypes.DECISION, 
                new ImageIcon(getClass().getResource("/com/example/dmn/resources/icons/decision.svg")));
        dmnIcons.put(DMNStereotypes.INPUT_DATA, 
                new ImageIcon(getClass().getResource("/com/example/dmn/resources/icons/inputdata.svg")));
        dmnIcons.put(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL, 
                new ImageIcon(getClass().getResource("/com/example/dmn/resources/icons/bkm.svg")));
        dmnIcons.put(DMNStereotypes.DECISION_SERVICE, 
                new ImageIcon(getClass().getResource("/com/example/dmn/resources/icons/decisionservice.svg")));
        dmnIcons.put(DMNStereotypes.KNOWLEDGE_SOURCE, 
                new ImageIcon(getClass().getResource("/com/example/dmn/resources/icons/knowledgesource.svg")));
    }
    
    /**
     * Create palette actions for DMN elements.
     */
    private void createPaletteActions() {
        // Decision action
        DefaultDiagramAction decisionAction = new DefaultDiagramAction(
                "DMN_CreateDecision",
                "Decision",
                "Create a DMN Decision element",
                dmnIcons.get(DMNStereotypes.DECISION)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNElement(DMNStereotypes.DECISION);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(decisionAction);
        
        // Input Data action
        DefaultDiagramAction inputDataAction = new DefaultDiagramAction(
                "DMN_CreateInputData",
                "Input Data",
                "Create a DMN Input Data element",
                dmnIcons.get(DMNStereotypes.INPUT_DATA)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNElement(DMNStereotypes.INPUT_DATA);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(inputDataAction);
        
        // Business Knowledge Model action
        DefaultDiagramAction bkmAction = new DefaultDiagramAction(
                "DMN_CreateBusinessKnowledgeModel",
                "Business Knowledge Model",
                "Create a DMN Business Knowledge Model element",
                dmnIcons.get(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNElement(DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(bkmAction);
        
        // Decision Service action
        DefaultDiagramAction decisionServiceAction = new DefaultDiagramAction(
                "DMN_CreateDecisionService",
                "Decision Service",
                "Create a DMN Decision Service element",
                dmnIcons.get(DMNStereotypes.DECISION_SERVICE)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNElement(DMNStereotypes.DECISION_SERVICE);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(decisionServiceAction);
        
        // Knowledge Source action
        DefaultDiagramAction knowledgeSourceAction = new DefaultDiagramAction(
                "DMN_CreateKnowledgeSource",
                "Knowledge Source",
                "Create a DMN Knowledge Source element",
                dmnIcons.get(DMNStereotypes.KNOWLEDGE_SOURCE)) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNElement(DMNStereotypes.KNOWLEDGE_SOURCE);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(knowledgeSourceAction);
        
        // Information Requirement action
        DefaultDiagramAction infoReqAction = new DefaultDiagramAction(
                "DMN_CreateInformationRequirement",
                "Information Requirement",
                "Create a DMN Information Requirement relationship",
                null) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNRelationship(DMNStereotypes.INFORMATION_REQUIREMENT);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(infoReqAction);
        
        // Knowledge Requirement action
        DefaultDiagramAction knowledgeReqAction = new DefaultDiagramAction(
                "DMN_CreateKnowledgeRequirement",
                "Knowledge Requirement",
                "Create a DMN Knowledge Requirement relationship",
                null) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNRelationship(DMNStereotypes.KNOWLEDGE_REQUIREMENT);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(knowledgeReqAction);
        
        // Authority Requirement action
        DefaultDiagramAction authorityReqAction = new DefaultDiagramAction(
                "DMN_CreateAuthorityRequirement",
                "Authority Requirement",
                "Create a DMN Authority Requirement relationship",
                null) {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDMNRelationship(DMNStereotypes.AUTHORITY_REQUIREMENT);
            }
            
            @Override
            public boolean isApplicable(DiagramPresentationElement diagram) {
                return isDMNDiagram(diagram);
            }
        };
        dmnActions.add(authorityReqAction);
    }
    
    /**
     * Register palette actions with the diagram toolbar.
     */
    private void registerPaletteActions() {
        // Register all actions with the diagram toolbar
        for (DefaultDiagramAction action : dmnActions) {
            DiagramToolbar.getInstance().registerAction(action);
        }
    }
    
    /**
     * Create a DMN element in the active diagram.
     * 
     * @param stereotypeName Name of the DMN stereotype to apply
     */
    private void createDMNElement(String stereotypeName) {
        // Implementation will use MagicDraw API to create a new element with the given stereotype
        // and add it to the active diagram at the cursor position
        Application.getInstance().getGUILog().showMessage("Creating DMN element: " + stereotypeName);
        
        // Actual implementation would use:
        // 1. Get active diagram
        // 2. Create new UML Class
        // 3. Apply DMN stereotype
        // 4. Add to diagram
        // 5. Apply styling
    }
    
    /**
     * Create a DMN relationship in the active diagram.
     * 
     * @param stereotypeName Name of the DMN relationship stereotype to apply
     */
    private void createDMNRelationship(String stereotypeName) {
        // Implementation will use MagicDraw API to create a new relationship with the given stereotype
        // between two selected elements in the diagram
        Application.getInstance().getGUILog().showMessage("Creating DMN relationship: " + stereotypeName);
        
        // Actual implementation would use:
        // 1. Get active diagram
        // 2. Get selected elements (source and target)
        // 3. Create appropriate UML relationship
        // 4. Apply DMN stereotype
        // 5. Add to diagram
        // 6. Apply styling
    }
    
    /**
     * Check if the diagram is a DMN diagram.
     * 
     * @param diagram Diagram to check
     * @return True if it's a DMN diagram
     */
    private boolean isDMNDiagram(DiagramPresentationElement diagram) {
        if (diagram == null) {
            return false;
        }
        
        Element diagramOwner = diagram.getElement();
        return StereotypesHelper.hasStereotype(diagramOwner, DMNStereotypes.DMN_DIAGRAM);
    }
}
