package de.mediaversal.oxygen.CustomButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;
import ro.sync.exml.workspace.api.PluginWorkspace;
import ro.sync.exml.workspace.api.editor.WSEditor;
import ro.sync.exml.workspace.api.listeners.WSEditorChangeListener;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ToolbarComponentsCustomizer;
import ro.sync.exml.workspace.api.standalone.ToolbarInfo;

public class CustomButtonPluginExtension implements WorkspaceAccessPluginExtension {

	// step 2: creating a bunch of private variables for this class to temporarily store some information
	private StandalonePluginWorkspace pluginWorkspaceAccess;
	private WSEditor currentEditor;
	private String currentEditorLocation;
	// step 2: make sure you create the JButton at class-loading time to avoid exceptions
	private JButton demoButton = new JButton("Demo Button");


	@Override
	public void applicationStarted(final StandalonePluginWorkspace pluginWorkspaceAccess) {

		// step 2: store current pluginWorkspaceAccess globally
		this.pluginWorkspaceAccess = pluginWorkspaceAccess;

		/* step 2: refresh current editor variables at startup
		 * 
		 * this enables the button if you open up oxygen with an XML file
		 * or disables the button if no documents are open at startup time
		 */
		checkCurrentEditorAndChangeButtonStatus();

		// step 2: add a change listener for editor panels and check the currently selected editor at every change
		pluginWorkspaceAccess.addEditorChangeListener(new WSEditorChangeListener() {
			public void editorOpened(URL editorLocation) {
				checkCurrentEditorAndChangeButtonStatus();
			}
			public void editorPageChanged(URL editorLocation) {
				checkCurrentEditorAndChangeButtonStatus();
			}
			public void editorSelected(URL editorLocation) {
				checkCurrentEditorAndChangeButtonStatus();
			}
			public void editorActivated(URL editorLocation) {
				checkCurrentEditorAndChangeButtonStatus();
			}
			public void editorClosed(URL editorLocation) {
				checkCurrentEditorAndChangeButtonStatus();
			}
		}, StandalonePluginWorkspace.MAIN_EDITING_AREA);


		// step 1: add new toolbar
		pluginWorkspaceAccess.addToolbarComponentsCustomizer(new ToolbarComponentsCustomizer() {
			@Override
			public void customizeToolbar(final ToolbarInfo toolbarInfo) {

				// step 1: if toolbar is our custom toolbar (referenced by ID in plugin.xml)
				if (toolbarInfo.getToolbarID().equals("CustomToolbar")) {

					// step 2: work with globally declared button now
					demoButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(
									new JFrame(),
									"Current file url:\n" + currentEditorLocation,	// step 2: display currently selected file
									"Window Title",
									JOptionPane.INFORMATION_MESSAGE
									);
						}
					});

					// step 1: Add demoButton to toolbar
					List<JComponent> comps = new ArrayList<JComponent>();
					comps.add(demoButton);
					toolbarInfo.setComponents(comps.toArray(new JComponent[0]));

					// step 1: Set toolbar title
					toolbarInfo.setTitle("Custom Demo Toolbar");
				}
			}
		});
	}

	@Override
	public boolean applicationClosing() {
		// 'true' if plugin allows application close
		return true;
	}




	/**
	 * step 2: checkCurrentEditorAndChangeButton
	 * 
	 * checks whether there's an editor open at the moment
	 * and depending on that, enables or disables the plugin button
	 * 
	 */
	private void checkCurrentEditorAndChangeButtonStatus() {

		// step2: get and save current editor
		this.currentEditor = pluginWorkspaceAccess.getCurrentEditorAccess(PluginWorkspace.MAIN_EDITING_AREA);

		// step2: if there's no editor open at the moment
		if(currentEditor == null) {

			// set 'currentEditorLocation' to 'null'
			this.currentEditorLocation = null;
			
			// disable demoButton
			demoButton.setEnabled(false);

		} else {
			
			// step 2: get and save the file location of the currently selected editor as a string
			this.currentEditorLocation = currentEditor.getEditorLocation().toString();
			
			// step 2: check file extension for XML
			if(currentEditorLocation.endsWith(".xml")) {
				
				// enable demoButton if it's an XML file in the current editor
				demoButton.setEnabled(true);

			} else {
				demoButton.setEnabled(false);
			}
		}
	}
}