package de.mediaversal.oxygen.CustomButton;

import ro.sync.exml.plugin.Plugin;
import ro.sync.exml.plugin.PluginDescriptor;

/**
 * CustomButton demo
 *
 * @author tofi86
 * @created 2014-01-24
 */
public class CustomButtonPlugin extends Plugin {
	/**
	 * The static plugin instance.
	 */
	private static CustomButtonPlugin instance = null;
	/**
	 * Constructs the plugin.
	 *
	 * @param descriptor The plugin descriptor
	 */
	public CustomButtonPlugin(PluginDescriptor descriptor) {
		super(descriptor);
		if (instance != null) {
			throw new IllegalStateException("Already instantiated!");
		}
		instance = this;
	}
	/**
	 * Get the plugin instance.
	 *
	 * @return the shared plugin instance.
	 */
	public static CustomButtonPlugin getInstance() {
		return instance;
	}

}