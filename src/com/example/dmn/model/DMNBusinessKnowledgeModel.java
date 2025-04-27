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
 * Implementation of a DMN Business Knowledge Model element.
 * Represents a business knowledge model in a DMN decision model.
 */
public class DMNBusinessKnowledgeModel {
    private Element element;
    private DMNProfile dmnProfile;
    
    /**
     * Constructor with existing element.
     * 
     * @param element Existing element with Business Knowledge Model stereotype
     */
    public DMNBusinessKnowledgeModel(Element element) {
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
    }
    
    /**
     * Create a new DMN Business Knowledge Model.
     * 
     * @param project Current project
     * @param name BKM name
     * @param owner Owner element
     * @return New DMNBusinessKnowledgeModel or null if creation failed
     */
    public static DMNBusinessKnowledgeModel create(Project project, String name, Element owner) {
        if (project == null || name == null || name.trim().isEmpty() || owner == null) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Business Knowledge Model");
            
            // Create class to represent BKM
            Class bkmClass = project.getElementsFactory().createClassInstance();
            bkmClass.setName(name);
            bkmClass.setOwner(owner);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(bkmClass, DMNStereotypes.BUSINESS_KNOWLEDGE_MODEL);
            
            SessionManager.getInstance().closeSession(project);
            
            return new DMNBusinessKnowledgeModel(bkmClass);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Business Knowledge Model: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the underlying element.
     * 
     * @return Element representing this BKM
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * Get the BKM name.
     * 
     * @return BKM name
     */
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return null;
    }
    
    /**
     * Set the BKM name.
     * 
     * @param name New BKM name
     * @return True if successful, false otherwise
     */
    public boolean setName(String name) {
        if (!(element instanceof NamedElement) || name == null || name.trim().isEmpty()) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Set BKM Name");
            ((NamedElement) element).setName(name);
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            return false;
        }
    }
    
    /**
     * Add a knowledge requirement to another BKM.
     * 
     * @param targetBKM Target BKM element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addKnowledgeRequirement(Element targetBKM, DiagramPresentationElement diagram) {
        if (targetBKM == null) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Knowledge Requirement");
            
            // Create dependency from this BKM to target BKM
            Element dependency = ModelHelper.createDependency(element, targetBKM);
            
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
     * Add an authority requirement from a knowledge source.
     * 
     * @param knowledgeSource Knowledge source element
     * @param diagram Current diagram
     * @return True if requirement added successfully
     */
    public boolean addAuthorityRequirement(Element knowledgeSource, DiagramPresentationElement diagram) {
        if (knowledgeSource == null) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Add Authority Requirement");
            
            // Create dependency from knowledge source to this BKM
            Element dependency = ModelHelper.createDependency(knowledgeSource, element);
            
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
