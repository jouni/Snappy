package org.vaadin.snappy.util;

import java.io.Serializable;

import com.vaadin.ui.Component;

/**
 * Snappy actions for different component types. Common actions can be applied
 * to all components.
 * 
 * @author jouni
 * 
 * @param <T>
 */
public class Action<T extends Component> implements Serializable {

	private static final long serialVersionUID = 2107459291371668019L;

	public static enum Common implements ActionType<Component> {
		/**
		 * Enable the component. Currently only applies to
		 * {@link com.vaadin.ui.Button} and {@link com.vaadin.ui.TextField}
		 * components.
		 */
		ENABLE,

		/**
		 * Disable the component. Currently only applies to
		 * {@link com.vaadin.ui.Button} and {@link com.vaadin.ui.TextField}
		 * components.
		 */
		DISABLE,

		/**
		 * Add an additional style name to the component. Only adds the style if
		 * it's not already added.
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>style</code></b>: The style name to add</li>
		 * </ol>
		 */
		ADD_STYLE,

		/**
		 * Remove an additional style name from the component.
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>style</code></b>: The style name to remove</li>
		 * </ol>
		 */
		REMOVE_STYLE,

		/**
		 * Add or remove an additional style name to/from the component,
		 * depending whether the component does or doesn't have the style
		 * currently.
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>style</code></b>: The style name to toggle</li>
		 * </ol>
		 */
		TOGGLE_STYLE;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum Label implements ActionType<com.vaadin.ui.Label> {
		/**
		 * <p>
		 * Set a new value for the Label. You can use the action parameters as
		 * replacements in the text, for example:
		 * </p>
		 * 
		 * <code>...addAction(label, Action.Label.SET_TEXT, "A new {1} with {2}", "value", "replacements");</code>
		 * 
		 * <p>
		 * Note that the replacement index starts from 1.
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>value</code></b>: The new value for the label</li>
		 * </ol>
		 */
		SET_TEXT;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum Button implements ActionType<com.vaadin.ui.Button> {
		/**
		 * <p>
		 * Click the button (like the user would've clicked it using the mouse).
		 * </p>
		 * 
		 * <p>
		 * Note that in this case, no event coordinates are available for any
		 * actions registered for the buttons click event.
		 * </p>
		 */
		CLICK,

		/**
		 * Set a new caption for the button.
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>caption</code></b>: The new caption for the button</li>
		 * </ol>
		 */
		SET_TEXT;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum TextField implements ActionType<com.vaadin.ui.TextField> {
		/**
		 * Set a new value for the text field. Note that only the
		 * {@link org.vaadin.snappy.util.Events.TextField#CHANGE} event is
		 * triggered from this action, not KEYDOWN or KEYUP.
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>value</code></b>: The new value for the text field</li>
		 * </ol>
		 */
		SET_TEXT;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum Window implements ActionType<com.vaadin.ui.Window> {
		/**
		 * Set the window position.
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>Y</code></b>: The new X position (pixels) for the
		 * window. You can use +/- to indicate relative values.</li>
		 * <li><b><code>Y</code></b>: The new Y position (pixels) for the
		 * window. You can use +/- to indicate relative values.</li>
		 * </ol>
		 */
		SET_POSITION;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	private Component target;
	private ActionType<T> actionType;
	private String[] params;

	public Action(ActionType<T> action, Component target, String... params) {
		this.actionType = action;
		this.target = target;
		this.params = params;
	}

	public Component getTarget() {
		return target;
	}

	public ActionType<T> getAction() {
		return actionType;
	}

	public String[] getParams() {
		return params;
	}

}
