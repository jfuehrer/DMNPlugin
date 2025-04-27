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
 * Implementation of a DMN Knowledge Source element.
 * Represents a source of knowledge in a DMN model.
 */
public class DMNKnowledgeSource {
    private Element element;
    private DMNProfile dmnProfile;
    
    /**
     * Constructor with existing element.
     * 
     * @param element Existing element with Knowledge Source stereotype
     */
    public DMNKnowledgeSource(Element element) {
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
    }
    
    /**
     * Create a new DMN Knowledge Source.
     * 
     * @param project Current project
     * @param name Knowledge Source name
     * @param owner Owner element
     * @return New DMNKnowledgeSource or null if creation failed
     */
    public static DMNKnowledgeSource create(Project project, String name, Element owner) {
        if (project == null || name == null || name.trim().isEmpty() || owner == null) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Knowledge Source");
            
            // Create class to represent knowledge source
            Class knowledgeSourceClass = project.getElementsFactory().createClassInstance();
            knowledgeSourceClass.setName(name);
            knowledgeSourceClass.setOwner(owner);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(knowledgeSourceClass, DMNStereotypes.KNOWLEDGE_SOURCE);
            
            SessionManager.getInstance().closeSession(project);
            
            return new DMNKnowledgeSource(knowledgeSourceClass);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Knowledge Source: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the underlying element.
     * 
     * @return Element representing this knowledge source
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * Get the knowledge source name.
     * 
     * @return Knowledge source name
     */
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return null;
    }
    
    /**
     * Set the knowledge source name.
     * 
     * @param name New knowledge source name
     * @return True if successful, false otherwise
     */
    public boolean setName(String name) {
        if (!(element instanceof NamedElement) || name == null || name.trim().isEmpty()) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Set Knowledge Source Name");
            ((NamedElement) element).setName(name);
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            return false;
        }
    }
    
    /**
     * Add an authority requirement to a decision.
     * 
     * @param decision Target decision element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addAuthorityRequirementToDecision(Element decision, DiagramPresentationElement diagram) {
        if (decision == null || !StereotypesHelper.hasStereotype(decision, DMNStereotypes.DECISION)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Authority Requirement");
            
            // Create dependency from this knowledge source to decision
            Element dependency = ModelHelper.createDependency(element, decision);
            
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
    
    /**
     * Add an authority requirement to a business knowledge model.
     * 
     * @param bkm Target business knowledge model element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addAuthorityRequirementToBKM(Element bkm, DiagramPresentationElement diagram) {
        if (bkm == null || !StereotypesHelper.hasStereotype(bkm, DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Authority Requirement");
            
            // Create dependency from this knowledge source to BKM
            Element dependency = ModelHelper.createDependency(element, bkm);
            
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
    
    /**
     * Add an authority requirement to another knowledge source.
     * 
     * @param targetKnowledgeSource Target knowledge source element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addAuthorityRequirementToKnowledgeSource(Element targetKnowledgeSource, 
                                                           DiagramPresentationElement diagram) {
        if (targetKnowledgeSource == null || 
            !StereotypesHelper.hasStereotype(targetKnowledgeSource, DMNStereotypes.KNOWLEDGE_SOURCE)) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Authority Requirement");
            
            // Create dependency from this knowledge source to target knowledge source
            Element dependency = ModelHelper.createDependency(element, targetKnowledgeSource);
            
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
