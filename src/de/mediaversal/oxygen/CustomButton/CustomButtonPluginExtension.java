package de.mediaversal.oxygen.CustomButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ro.sync.exml.plugin.workspace.WorkspaceAccessPluginExtension;
import ro.sync.exml.workspace.api.standalone.StandalonePluginWorkspace;
import ro.sync.exml.workspace.api.standalone.ToolbarComponentsCustomizer;
import ro.sync.exml.workspace.api.standalone.ToolbarInfo;

public class CustomButtonPluginExtension implements WorkspaceAccessPluginExtension {

	@Override
	public void applicationStarted(final StandalonePluginWorkspace pluginWorkspaceAccess) {

		// add new toolbar
		pluginWorkspaceAccess.addToolbarComponentsCustomizer(new ToolbarComponentsCustomizer() {
			@Override
			public void customizeToolbar(final ToolbarInfo toolbarInfo) {

				// if toolbar is our custom toolbar (referenced by ID in plugin.xml)
				if (toolbarInfo.getToolbarID().equals("CustomToolbar")) {

					JButton demoButton = new JButton("Demo Button");
					demoButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(
									new JFrame(),
									"Toolbar ID: " + toolbarInfo.getToolbarID(),
									"Window Title",
									JOptionPane.INFORMATION_MESSAGE
									);
						}
					});

					// Add demoButton to toolbar
					List<JComponent> comps = new ArrayList<JComponent>();
					comps.add(demoButton);
					toolbarInfo.setComponents(comps.toArray(new JComponent[0]));

					// Set toolbar title
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

}