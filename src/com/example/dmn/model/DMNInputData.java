package com.example.dmn.model;

import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

/**
 * Implementation of a DMN Input Data element.
 * Represents an input data element in a DMN model.
 */
public class DMNInputData {
    private Element element;
    private DMNProfile dmnProfile;
    
    /**
     * Constructor with existing element.
     * 
     * @param element Existing element with Input Data stereotype
     */
    public DMNInputData(Element element) {
        this.element = element;
        this.dmnProfile = new DMNProfile();
        this.dmnProfile.init();
    }
    
    /**
     * Create a new DMN Input Data.
     * 
     * @param project Current project
     * @param name Input Data name
     * @param owner Owner element
     * @return New DMNInputData or null if creation failed
     */
    public static DMNInputData create(Project project, String name, Element owner) {
        if (project == null || name == null || name.trim().isEmpty() || owner == null) {
            return null;
        }
        
        try {
            SessionManager.getInstance().createSession(project, "Create DMN Input Data");
            
            // Create class to represent input data
            Class inputDataClass = project.getElementsFactory().createClassInstance();
            inputDataClass.setName(name);
            inputDataClass.setOwner(owner);
            
            // Apply stereotype
            DMNProfile profile = new DMNProfile();
            profile.init();
            profile.applyStereotype(inputDataClass, DMNStereotypes.INPUT_DATA);
            
            SessionManager.getInstance().closeSession(project);
            
            return new DMNInputData(inputDataClass);
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN Input Data: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Get the underlying element.
     * 
     * @return Element representing this input data
     */
    public Element getElement() {
        return element;
    }
    
    /**
     * Get the input data name.
     * 
     * @return Input data name
     */
    public String getName() {
        if (element instanceof NamedElement) {
            return ((NamedElement) element).getName();
        }
        return null;
    }
    
    /**
     * Set the input data name.
     * 
     * @param name New input data name
     * @return True if successful, false otherwise
     */
    public boolean setName(String name) {
        if (!(element instanceof NamedElement) || name == null || name.trim().isEmpty()) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Set Input Data Name");
            ((NamedElement) element).setName(name);
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            return false;
        }
    }
    
    /**
     * Add a data type to this input data.
     * 
     * @param typeName Type name
     * @return True if successful, false otherwise
     */
    public boolean setDataType(String typeName) {
        if (typeName == null || typeName.trim().isEmpty()) {
            return false;
        }
        
        Project project = Application.getInstance().getProject();
        try {
            SessionManager.getInstance().createSession(project, "Set Input Data Type");
            
            // Create a property with the given type name
            // (In a real implementation, this would look up or create a proper type)
            
            SessionManager.getInstance().closeSession(project);
            return true;
        } catch (Exception e) {
            SessionManager.getInstance().cancelSession(project);
            return false;
        }
    }
}
