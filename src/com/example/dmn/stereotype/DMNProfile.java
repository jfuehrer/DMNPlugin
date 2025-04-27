package com.example.dmn.stereotype;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.uml.Stereotype;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;

/**
 * Provides functionality for the DMN profile which includes all stereotypes
 * needed for DMN 1.6 modeling elements.
 */
public class DMNProfile {
    
    private Project project;
    private Profile dmnProfile;
    
    /**
     * Default constructor
     */
    public DMNProfile() {
        this.project = Application.getInstance().getProject();
    }
    
    /**
     * Initializes the DMN profile
     */
    public void initialize() {
        if (project == null) {
            Application.getInstance().getGUILog().log("Warning: Cannot initialize DMN profile - no active project");
            return;
        }
        
        // Check if profile already exists
        dmnProfile = findDMNProfile();
        
        if (dmnProfile == null) {
            Application.getInstance().getGUILog().log("Creating DMN profile...");
            createDMNProfile();
        } else {
            Application.getInstance().getGUILog().log("DMN profile already exists");
        }
    }
    
    /**
     * Disposes resources used by the profile
     */
    public void dispose() {
        // Clean up resources
    }
    
    /**
     * Searches for existing DMN profile
     * 
     * @return the profile if found, null otherwise
     */
    private Profile findDMNProfile() {
        for (Profile profile : StereotypesHelper.getProfiles(project)) {
            if (profile.getName().equals("DMN Profile")) {
                return profile;
            }
        }
        return null;
    }
    
    /**
     * Creates the DMN profile with all stereotypes
     */
    private void createDMNProfile() {
        // This method would create the actual profile in a real implementation
        // For now, we'll just simulate the profile creation
        
        Application.getInstance().getGUILog().log("Creating DMN Profile with stereotypes");
        // In a real implementation, create:
        // - Decision stereotype
        // - InputData stereotype 
        // - BusinessKnowledgeModel stereotype
        // - DecisionService stereotype
        // - KnowledgeSource stereotype
        // - InformationRequirement stereotype
        // - KnowledgeRequirement stereotype
        // - AuthorityRequirement stereotype
    }
    
    /**
     * Applies a stereotype to an element
     * 
     * @param element the element to apply the stereotype to
     * @param stereotypeName the name of the stereotype
     * @return true if successful
     */
    public boolean applyStereotype(Element element, String stereotypeName) {
        if (dmnProfile == null) {
            return false;
        }
        
        Stereotype stereotype = StereotypesHelper.getStereotype(project, stereotypeName, dmnProfile);
        if (stereotype != null) {
            StereotypesHelper.addStereotype(element, stereotype);
            return true;
        }
        return false;
    }
}