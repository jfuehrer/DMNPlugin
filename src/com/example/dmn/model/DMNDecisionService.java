package com.example.dmn.model;

import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a DMN Decision Service element.
 * Represents a decision service in a DMN model.
 */
public class DMNDecisionService {
    private Element element;
    private DMNProfile dmnProfile;
    private List<Element> outputDecisions = new ArrayList<>();
    private List<Element> encapsulatedDecisions = new ArrayList<>();
    private List<Element> inputDecisions = new ArrayList<>();
    private List<Element> inputData = new ArrayList<>();
    
    /**
     * Constructor with existing element.
     * 
     * @param element Existing element with Decision Service stereotype
     */
    public DMNDecisionService(Element element) {
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
    }
    
    /**
     * Create a new DMN Decision Service.
     * 
     * @param project Current project
     * @param name Decision Service name
     * @param owner Owner element
     * @return New DMNDecisionService or null if creation failed
     */
    public static DMNDecisionService create(Project project, String name, Element owner) {
        if (project == null || name == null || name.trim().isEmpty() || owner == null) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Decision Service");
            
            // Create class to represent decision service
            Class decisionServiceClass = project.getElementsFactory().createClassInstance();
            decisionServiceClass.setName(name);
            decisionServiceClass.setOwner(owner);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(decisionServiceClass, DMNStereotypes.DECISION_SERVICE);
            
            SessionManager.getInstance().closeSession(project);
            
            return new DMNDecisionService(decisionServiceClass);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Decision Service: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the underlying element.
     * 
     * @return Element representing this decision service
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * Get the decision service name.
     * 
     * @return Decision service name
     */
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return null;
    }
    
    /**
     * Set the decision service name.
     * 
     * @param name New decision service name
     * @return True if successful, false otherwise
     */
    public boolean setName(String name) {
        if (!(element instanceof NamedElement) || name == null || name.trim().isEmpty()) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Set Decision Service Name");
            ((NamedElement) element).setName(name);
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            return false;
        }
    }
    
    /**
     * Add an output decision to this decision service.
     * 
     * @param decision Decision element to add as output
     * @param diagram Current diagram for visualization
     * @return True if successful, false otherwise
     */
    public boolean addOutputDecision(Element decision, DiagramPresentationElement diagram) {
        if (decision == null || !StereotypesHelper.hasStereotype(decision, DMNStereotypes.DECISION)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Output Decision");
            
            // Add decision as part of decision service (containment)
            // In a real implementation, this would create appropriate relationships
            // to represent the decision service output
            outputDecisions.add(decision);
            
            // Create dependency to visualize the relationship
            Element dependency = ModelHelper.createDependency(element, decision);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add output decision: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add an encapsulated decision to this decision service.
     * 
     * @param decision Decision element to encapsulate
     * @param diagram Current diagram for visualization
     * @return True if successful, false otherwise
     */
    public boolean addEncapsulatedDecision(Element decision, DiagramPresentationElement diagram) {
        if (decision == null || !StereotypesHelper.hasStereotype(decision, DMNStereotypes.DECISION)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Encapsulated Decision");
            
            // Add decision as encapsulated in decision service
            // In a real implementation, this would create appropriate relationships
            encapsulatedDecisions.add(decision);
            
            // Create dependency to visualize the relationship
            Element dependency = ModelHelper.createDependency(element, decision);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add encapsulated decision: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add an input decision to this decision service.
     * 
     * @param decision Decision element to add as input
     * @param diagram Current diagram for visualization
     * @return True if successful, false otherwise
     */
    public boolean addInputDecision(Element decision, DiagramPresentationElement diagram) {
        if (decision == null || !StereotypesHelper.hasStereotype(decision, DMNStereotypes.DECISION)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Input Decision");
            
            // Add decision as input to decision service
            // In a real implementation, this would create appropriate relationships
            inputDecisions.add(decision);
            
            // Create dependency to visualize the relationship
            Element dependency = ModelHelper.createDependency(decision, element);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add input decision: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add input data to this decision service.
     * 
     * @param data Input data element
     * @param diagram Current diagram for visualization
     * @return True if successful, false otherwise
     */
    public boolean addInputData(Element data, DiagramPresentationElement diagram) {
        if (data == null || !StereotypesHelper.hasStereotype(data, DMNStereotypes.INPUT_DATA)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Input Data");
            
            // Add input data to decision service
            // In a real implementation, this would create appropriate relationships
            inputData.add(data);
            
            // Create dependency to visualize the relationship
            Element dependency = ModelHelper.createDependency(data, element);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add input data: " + e.getMessage());
            return false;
        }
    }
}
