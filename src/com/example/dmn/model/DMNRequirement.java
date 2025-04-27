package com.example.dmn.model;

import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/**
 * Implementation of DMN requirements (Information, Knowledge, Authority).
 * Handles creation and manipulation of requirement relationships.
 */
public class DMNRequirement {
    private Element element;
    private DMNProfile dmnProfile;
    private String requirementType;
    
    /**
     * Constructor with existing element.
     * 
     * @param element Existing element with a requirement stereotype
     */
    public DMNRequirement(Element element) {
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
        
        // Determine requirement type
        if (StereotypesHelper.hasStereotype(element, DMNStereotypes.INFORMATION_REQUIREMENT)) {
            requirementType = DMNStereotypes.INFORMATION_REQUIREMENT;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.KNOWLEDGE_REQUIREMENT)) {
            requirementType = DMNStereotypes.KNOWLEDGE_REQUIREMENT;
        } else if (StereotypesHelper.hasStereotype(element, DMNStereotypes.AUTHORITY_REQUIREMENT)) {
            requirementType = DMNStereotypes.AUTHORITY_REQUIREMENT;
        }
    }
    
    /**
     * Create a new information requirement.
     * 
     * @param project Current project
     * @param source Source element (InputData or Decision)
     * @param target Target element (Decision)
     * @param diagram Diagram for visualization
     * @return New DMNRequirement or null if creation failed
     */
    public static DMNRequirement createInformationRequirement(Project project, Element source, Element target, 
                                                             DiagramPresentationElement diagram) {
        return createRequirement(project, source, target, DMNStereotypes.INFORMATION_REQUIREMENT, diagram);
    }
    
    /**
     * Create a new knowledge requirement.
     * 
     * @param project Current project
     * @param source Source element (BusinessKnowledgeModel)
     * @param target Target element (Decision or BusinessKnowledgeModel)
     * @param diagram Diagram for visualization
     * @return New DMNRequirement or null if creation failed
     */
    public static DMNRequirement createKnowledgeRequirement(Project project, Element source, Element target, 
                                                           DiagramPresentationElement diagram) {
        return createRequirement(project, source, target, DMNStereotypes.KNOWLEDGE_REQUIREMENT, diagram);
    }
    
    /**
     * Create a new authority requirement.
     * 
     * @param project Current project
     * @param source Source element (KnowledgeSource)
     * @param target Target element (Decision, BusinessKnowledgeModel, or KnowledgeSource)
     * @param diagram Diagram for visualization
     * @return New DMNRequirement or null if creation failed
     */
    public static DMNRequirement createAuthorityRequirement(Project project, Element source, Element target, 
                                                           DiagramPresentationElement diagram) {
        return createRequirement(project, source, target, DMNStereotypes.AUTHORITY_REQUIREMENT, diagram);
    }
    
    /**
     * Create a requirement relationship.
     * 
     * @param project Current project
     * @param source Source element
     * @param target Target element
     * @param requirementType Type of requirement
     * @param diagram Diagram for visualization
     * @return New DMNRequirement or null if creation failed
     */
    private static DMNRequirement createRequirement(Project project, Element source, Element target, 
                                                  String requirementType, DiagramPresentationElement diagram) {
        if (project == null || source == null || target == null) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Requirement");
            
            // Create dependency (for requirements)
            Element dependency = ModelHelper.createDependency(source, target);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(dependency, requirementType);
            
            // Add to diagram if provided
            if (diagram != null) {
                project.getDiagram().createPathElement(dependency, diagram);
            }
            
            SessionManager.getInstance().closeSession(project);
            
            return new DMNRequirement(dependency);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Requirement: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the underlying element.
     * 
     * @return Element representing this requirement
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * Get the source element of the requirement.
     * 
     * @return Source element
     */
    public Element getSource() {
        return ModelHelper.getClientElement(element);
    }
    
    /**
     * Get the target element of the requirement.
     * 
     * @return Target element
     */
    public Element getTarget() {
        return ModelHelper.getSupplierElement(element);
    }
    
    /**
     * Get the requirement type.
     * 
     * @return Requirement type (Information, Knowledge, or Authority)
     */
    public String getRequirementType() {
        return requirementType;
    }
}
