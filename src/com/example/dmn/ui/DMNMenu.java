package com.example.dmn.ui;

import com.example.dmn.io.DMNImportExportStub;
import com.example.dmn.util.GitHubPushHelper;

import com.nomagic.magicdraw.actions.ActionsCategories;
import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.ui.browser.Tree;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.ui.dialogs.SelectElementInfo;
import com.nomagic.magicdraw.ui.dialogs.SelectElementTypes;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlg;
import com.nomagic.magicdraw.ui.dialogs.selection.ElementSelectionDlgFactory;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DMN menu implementation for Magic Systems
 * Adds DMN-specific menu items to the Tools menu
 */
public class DMNMenu implements AutoCloseable {
    
    private static final String MENU_NAME = "DMN";
    private static final String ACTION_CATEGORY = "DMN";
    
    private List<MDAction> actions = new ArrayList<>();
    
    /**
     * Initialize the menu
     */
    public void initialize() {
        // Create the menu actions
        createImportAction();
        createExportAction();
        createGitHubAction();
        
        // Register all actions with MagicDraw
        for (MDAction action : actions) {
            ActionsCategories.addAction(action, ACTION_CATEGORY);
        }
        
        Application.getInstance().getGUILog().log("DMN Menu initialized");
    }
    
    /**
     * Create the Import DMN action
     */
    private void createImportAction() {
        MDAction importAction = new MDAction("Import DMN...", MENU_NAME, null, "Import a DMN file") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Import DMN File");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".dmn");
                    }
                    
                    @Override
                    public String getDescription() {
                        return "DMN Files (*.dmn)";
                    }
                });
                
                int result = fileChooser.showOpenDialog(MDDialogParentProvider.getProvider().getDialogParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // In a stub implementation, just demonstrate the import capabilities
                        DMNImportExportStub importExport = new DMNImportExportStub();
                        importExport.importDMNFile(selectedFile);
                        
                        Application.getInstance().getGUILog().log("DMN file imported: " + selectedFile.getName());
                    } catch (Exception ex) {
                        Application.getInstance().getGUILog().log("Error importing DMN file: " + ex.getMessage());
                    }
                }
            }
        };
        
        actions.add(importAction);
    }
    
    /**
     * Create the Export DMN action
     */
    private void createExportAction() {
        MDAction exportAction = new MDAction("Export DMN...", MENU_NAME, null, "Export a DMN file") {
            @Override
            public void actionPerformed(ActionEvent e) {
                // First, let the user select a decision element to export
                SelectElementInfo info = new SelectElementInfo(true, false, null, true);
                info.setTitle("Select Decision to Export");
                ElementSelectionDlg dlg = ElementSelectionDlgFactory.create(info);
                dlg.setVisible(true);
                
                if (info.isOkClicked()) {
                    Element selectedElement = info.getSelectedElement();
                    
                    if (selectedElement != null) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Export DMN File");
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                return f.isDirectory() || f.getName().toLowerCase().endsWith(".dmn");
                            }
                            
                            @Override
                            public String getDescription() {
                                return "DMN Files (*.dmn)";
                            }
                        });
                        
                        int result = fileChooser.showSaveDialog(MDDialogParentProvider.getProvider().getDialogParent());
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            String fileName = selectedFile.getAbsolutePath();
                            if (!fileName.toLowerCase().endsWith(".dmn")) {
                                fileName += ".dmn";
                                selectedFile = new File(fileName);
                            }
                            
                            try {
                                // In a stub implementation, just demonstrate the export capabilities
                                DMNImportExportStub.demonstrateImportExport();
                                
                                Application.getInstance().getGUILog().log("DMN exported to: " + selectedFile.getName());
                            } catch (Exception ex) {
                                Application.getInstance().getGUILog().log("Error exporting DMN: " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        };
        
        actions.add(exportAction);
    }
    
    /**
     * Create the GitHub integration action
     */
    private void createGitHubAction() {
        MDAction githubAction = new MDAction("Push to GitHub...", MENU_NAME, null, "Push DMN plugin to GitHub") {
            @Override
            public void actionPerformed(ActionEvent e) {
                GitHubPushHelper.demonstrateGitHubPush();
                Application.getInstance().getGUILog().log("GitHub Push demonstration complete");
            }
        };
        
        actions.add(githubAction);
    }
    
    /**
     * Close and clean up resources
     */
    @Override
    public void close() throws Exception {
        // Unregister all actions
        for (MDAction action : actions) {
            ActionsCategories.removeAction(action);
        }
        actions.clear();
    }
}