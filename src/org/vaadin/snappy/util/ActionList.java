package org.vaadin.snappy.util;

import java.io.Serializable;
import java.util.LinkedList;

import com.vaadin.ui.Component;

public class ActionList<T extends Component> extends LinkedList<Action<?>>
		implements Serializable {

	private static final long serialVersionUID = 7922046135770931759L;

	private T owner;
	private EventType<?> eventType;
	private String[] params;
	private int id = -1;

	public ActionList(T owner, EventType<?> type, String... params) {
		this.owner = owner;
		eventType = type;
		if (eventType == Events.Common.MOUSEDOWN && params.length == 0) {
			params = new String[] { "1" };
		}
		this.params = params;
	}

	public EventType<?> getEventType() {
		return eventType;
	}

	public String[] getParameters() {
		return params;
	}

	public int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	/**
	 * Add a new client side action to this action list. All of the actions in
	 * the list will be executed at the same time in the order they are added.
	 * 
	 * @param target
	 *            The component to which the action is applied to
	 * @param action
	 *            The action which to perform on the target
	 * @param params
	 *            Additional parameters for the action. See the JavaDoc
	 *            documentation for each {@link Action}.
	 * @return The new action that is added to the action list.
	 */
	@SuppressWarnings("hiding")
	public <T extends Component> Action<T> addAction(T target,
			ActionType<T> action, String... params) {
		if (action == Action.Window.SET_POSITION && params.length < 2) {
			throw new IllegalArgumentException(
					"The action requires two parameters, X and Y positions.");
		}
		final Action<T> newAction = new Action<T>(action, target, params);
		add(newAction);
		owner.requestRepaint();
		return newAction;
	}

}
