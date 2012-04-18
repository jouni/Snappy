package org.vaadin.snappy.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.snappy.client.VSnappy;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class Snappy<T extends Component> implements Serializable {

	private static final long serialVersionUID = -4909260375240503971L;

	// ID to keep track of which actions are already active in the client (avoid
	// running the same actions multiple times)
	private static int actionId = 0;

	private HashMap<String, ActionList<T>> eventToActions = new HashMap<String, ActionList<T>>();

	private T owner;

	public Snappy(T owner) {
		this.owner = owner;
	}

	public ActionList<T> on(EventType<? super T> event, String... params) {
		if (eventToActions.containsKey(event.toString())
				|| (event == Events.TextField.KEYDOWN && params.length > 0 && eventToActions
						.containsKey(event.toString() + ":" + params[0]))) {
			ActionList<T> actionList;
			if ((event == Events.TextField.KEYDOWN || event == Events.Common.MOUSEDOWN)
					&& params.length > 0) {
				actionList = eventToActions.get(event.toString() + ":"
						+ params[0]);
			} else {
				actionList = eventToActions.get(event.toString());
			}
			if (actionList.getId() > -1) {
				// This actionlist has been painted at least once, and we need
				// to reset it on the client
				actionList.setId(actionId++);
			}
			return actionList;
		} else {
			ActionList<T> list = new ActionList<T>(owner, event, params);
			if ((event == Events.TextField.KEYDOWN || event == Events.Common.MOUSEDOWN)
					&& params.length > 0) {
				eventToActions.put(event.toString() + ":" + params[0], list);
			} else {
				eventToActions.put(event.toString(), list);
			}
			return list;
		}
	}

	public void paintContent(PaintTarget target) throws PaintException {
		target.startTag("clientActions");
		for (String eventType : eventToActions.keySet()) {
			target.startTag("event");
			ActionList<T> actionList = eventToActions.get(eventType);
			target.addAttribute("type", eventType.split(":")[0]);
			target.addAttribute("p", actionList.getParameters());

			// This action list is painted for a second time, let client side
			// know that it should disregard the previous list (the id is used
			// for that)
			if (actionList.getId() == -1) {
				actionList.setId(actionId++);
			}
			
			target.addAttribute("id", actionList.getId());
			for (Action<?> act : actionList) {
				target.startTag("a");
				target.addAttribute("target", act.getTarget());
				target.addAttribute("action", act.getAction().toString());
				target.addAttribute("p", act.getParams());
				target.endTag("a");
			}
			target.endTag("event");
		}
		target.endTag("clientActions");
	}

	public void changeVariables(Object source, Map<String, Object> variables) {
		for (String key : variables.keySet()) {
			if (key.startsWith((Action.Common.ENABLE.toString()))) {
				Component target = (Component) variables.get(key);
				target.setEnabled(true);
			} else if (key.startsWith((Action.Common.DISABLE.toString()))) {
				Component target = (Component) variables.get(key);
				target.setEnabled(false);
			} else if (key.startsWith((Action.Common.ADD_STYLE.toString()))) {
				Component target = (Component) variables.get(key);
				target.addStyleName(getParamValue(key));
			} else if (key.startsWith((Action.Common.REMOVE_STYLE.toString()))) {
				Component target = (Component) variables.get(key);
				target.removeStyleName(getParamValue(key));
			} else if (key.startsWith((Action.Common.TOGGLE_STYLE.toString()))) {
				Component target = (Component) variables.get(key);
				String style = getParamValue(key);
				if (target.getStyleName().contains(style)) {
					target.removeStyleName(style);
				} else {
					target.addStyleName(style);
				}
			} else if (key.startsWith((Action.Label.SET_TEXT.toString()))) {
				Component target = (Component) variables.get(key);
				if (target instanceof Label) {
					((Label) target).setValue(getParamValue(key));
				} else if (target instanceof Button) {
					((Button) target).setCaption(getParamValue(key));
				}
			}
		}
	}

	private String getParamValue(String variableKey) {
		String[] temp = variableKey.split(VSnappy.SEPARATOR);
		return temp[temp.length - 1];
	}

}
