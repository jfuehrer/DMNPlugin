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

/**
 * Implementation of a DMN Decision element.
 * Represents a decision in a DMN model, with methods to create, configure, and manipulate it.
 */
public class DMNDecision {
    private Element element;
    private DMNProfile dmnProfile;
    
    /**
     * Constructor with existing element.
     * 
     * @param element Existing element with Decision stereotype
     */
    public DMNDecision(Element element) {
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
    }
    
    /**
     * Create a new DMN Decision.
     * 
     * @param project Current project
     * @param name Decision name
     * @param owner Owner element
     * @return New DMNDecision or null if creation failed
     */
    public static DMNDecision create(Project project, String name, Element owner) {
        if (project == null || name == null || name.trim().isEmpty() || owner == null) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Decision");
            
            // Create class to represent decision
            Class decisionClass = project.getElementsFactory().createClassInstance();
            decisionClass.setName(name);
            decisionClass.setOwner(owner);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(decisionClass, DMNStereotypes.DECISION);
            
            SessionManager.getInstance().closeSession(project);
            
            return new DMNDecision(decisionClass);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Decision: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the underlying element.
     * 
     * @return Element representing this decision
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * Get the decision name.
     * 
     * @return Decision name
     */
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return null;
    }
    
    /**
     * Set the decision name.
     * 
     * @param name New decision name
     * @return True if successful, false otherwise
     */
    public boolean setName(String name) {
        if (!(element instanceof NamedElement) || name == null || name.trim().isEmpty()) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Set Decision Name");
            ((NamedElement) element).setName(name);
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            return false;
        }
    }
    
    /**
     * Get the decision question.
     * 
     * @return Decision question or null if not set
     */
    public String getQuestion() {
        return (String) dmnProfile.getTaggedValue(element, DMNStereotypes.DECISION, DMNStereotypes.TAG_QUESTION);
    }
    
    /**
     * Set the decision question.
     * 
     * @param question Question text
     * @return True if successful, false otherwise
     */
    public boolean setQuestion(String question) {
        return dmnProfile.setTaggedValue(element, DMNStereotypes.DECISION, DMNStereotypes.TAG_QUESTION, question);
    }
    
    /**
     * Get the allowed answers.
     * 
     * @return Allowed answers string or null if not set
     */
    public String getAllowedAnswers() {
        return (String) dmnProfile.getTaggedValue(
                element, DMNStereotypes.DECISION, DMNStereotypes.TAG_ALLOWED_ANSWERS);
    }
    
    /**
     * Set the allowed answers.
     * 
     * @param allowedAnswers Allowed answers text
     * @return True if successful, false otherwise
     */
    public boolean setAllowedAnswers(String allowedAnswers) {
        return dmnProfile.setTaggedValue(
                element, DMNStereotypes.DECISION, DMNStereotypes.TAG_ALLOWED_ANSWERS, allowedAnswers);
    }
    
    /**
     * Add an information requirement from an input element to this decision.
     * 
     * @param inputElement Source element of the information requirement
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addInformationRequirement(Element inputElement, DiagramPresentationElement diagram) {
        if (inputElement == null || diagram == null) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Information Requirement");
            
            // Create dependency from input to decision
            Element dependency = ModelHelper.createDependency(inputElement, element);
            
            // Apply stereotype
            dmnProfile.applyStereotype(dependency, DMNStereotypes.INFORMATION_REQUIREMENT);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add information requirement: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add a knowledge requirement from a BKM to this decision.
     * 
     * @param bkmElement Source business knowledge model element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addKnowledgeRequirement(Element bkmElement, DiagramPresentationElement diagram) {
        if (bkmElement == null) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Knowledge Requirement");
            
            // Create dependency from BKM to decision
            Element dependency = ModelHelper.createDependency(bkmElement, element);
            
            // Apply stereotype
            dmnProfile.applyStereotype(dependency, DMNStereotypes.KNOWLEDGE_REQUIREMENT);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add knowledge requirement: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add an authority requirement from a knowledge source to this decision.
     * 
     * @param sourceElement Source knowledge source element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addAuthorityRequirement(Element sourceElement, DiagramPresentationElement diagram) {
        if (sourceElement == null) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Authority Requirement");
            
            // Create dependency from knowledge source to decision
            Element dependency = ModelHelper.createDependency(sourceElement, element);
            
            // Apply stereotype
            dmnProfile.applyStereotype(dependency, DMNStereotypes.AUTHORITY_REQUIREMENT);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to add authority requirement: " + e.getMessage());
            return false;
        }
    }
}
