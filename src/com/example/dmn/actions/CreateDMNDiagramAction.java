package com.example.dmn.actions;

import com.example.dmn.stereotype.DMNProfile;
import com.example.dmn.stereotype.DMNStereotypes;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.openapi.uml.PresentationElementsManager;
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.ui.actions.DefaultDiagramAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.ui.dialogs.SelectElementInfo;
import com.nomagic.magicdraw.ui.dialogs.SelectElementTypes;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlgFactory;
import com.nomagic.magicdraw.ui.dialogs.selection.SelectionMode;
import com.nomagic.magicdraw.uml.ClassTypes;
import com.nomagic.magicdraw.uml.DiagramTypeConstants;
import com.nomagic.magicdraw.uml.symbols.DiagramPresentationElement;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.NamedElement;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Action to create a new DMN diagram.
 * Adds an option to create a DMN diagram in the project.
 */
public class CreateDMNDiagramAction extends DefaultDiagramAction {
    private static final String ACTION_NAME = "Create DMN Diagram";
    private static final String DIAGRAM_NAME = "DMN Diagram";
    
    /**
     * Constructor.
     */
    public CreateDMNDiagramAction() {
        super(ACTION_NAME, ACTION_NAME, null, null);
    }
    
    /**
     * Execute the action.
     * 
     * @param e Action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Project project = Application.getInstance().getProject();
        if (project == null) {
            return;
        }
        
        // Select target package
        Package targetPackage = selectTargetPackage(project);
        if (targetPackage == null) {
            return;
        }
        
        // Get diagram name from user
        String diagramName = JOptionPane.showInputDialog(
                MDDialogParentProvider.getProvider().getParent(),
                "Enter DMN diagram name:",
                DIAGRAM_NAME);
        
        if (diagramName == null || diagramName.trim().isEmpty()) {
            return;
        }
        
        try {
            // Create DMN diagram
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
            
            // Open the diagram
            project.getDiagram().openDiagram(diagram);
            
            // Commit the session
            SessionManager.getInstance().closeSession(project);
            
            // Show success message
            Application.getInstance().getGUILog().showMessage(
                    "DMN diagram '" + diagramName + "' created successfully.");
        } catch (ReadOnlyElementException exception) {
            SessionManager.getInstance().cancelSession(project);
            Application.getInstance().getGUILog().showError(
                    "Failed to create DMN diagram: " + exception.getMessage());
        }
    }
    
    /**
     * Show dialog to select target package.
     * 
     * @param project Current project
     * @return Selected package or null if canceled
     */
    private Package selectTargetPackage(Project project) {
        List<java.lang.Class<?>> types = new ArrayList<>();
        types.add(Package.class);
        
        ElementSelectionDlg dlg = ElementSelectionDlgFactory.create(
                MDDialogParentProvider.getProvider().getParent(),
                "Select Target Package",
                true, project.getPrimaryModel(),
                SelectionMode.SINGLE_MODE, null, types);
        
        dlg.setVisible(true);
        
        if (dlg.isOkClicked() && !dlg.getSelectedElements().isEmpty()) {
            return (Package) dlg.getSelectedElements().get(0);
        }
        
        return null;
    }
    
    /**
     * Update action state based on current project state.
     */
    @Override
    public void updateState() {
        Project project = Application.getInstance().getProject();
        setEnabled(project != null && project.isProjectActivated());
    }
}
