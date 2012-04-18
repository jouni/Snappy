package org.vaadin.snappy.client;

import java.util.HashMap;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VButton;
import com.vaadin.terminal.gwt.client.ui.VWindow;

public class VSnappy {

	private static HashMap<String, HandlerRegistration> actionLists = new HashMap<String, HandlerRegistration>();

	public static final String SEPARATOR = "::";

	private VSnappy() {
	}

	public static void handleClientActions(final Widget widget,
			final UIDL uidl, final ApplicationConnection client) {
		final UIDL clientActions = uidl.getChildByTagName("clientActions");
		if (clientActions != null) {
			for (int i = 0; i < clientActions.getChildCount(); i++) {
				final UIDL event = clientActions.getChildUIDL(i);
				addHandler(uidl.getId(), widget, event, client);
			}
		}
	}

	private static void addHandler(final String id, final Widget w,
			final UIDL eventUidl, final ApplicationConnection client) {
		final String eventType = eventUidl.getStringAttribute("type");
		final int eventId = eventUidl.getIntAttribute("id");

		String handlerId = eventType + id + "_" + eventId;
		if ((eventType.equals("keydown") || eventType.equals("mousedown"))
				&& eventUidl.getStringArrayAttribute("p").length > 0) {
			handlerId += "_" + eventUidl.getStringArrayAttribute("p")[0];
		}

		if (actionLists.containsKey(handlerId)) {
			// The same action list would be attached again
			return;
		} else {
			for (String key : actionLists.keySet()) {
				if (key.matches(eventType + id + "_[0-9]+")
						|| (eventUidl.getStringArrayAttribute("p").length > 0 && key
								.matches(eventType
										+ id
										+ "_[0-9]+_"
										+ eventUidl
												.getStringArrayAttribute("p")[0]))) {
					// A new action has been added to the list after the first
					actionLists.get(key).removeHandler();
				}
			}
		}

		HandlerRegistration handlerReg = null;
		if (eventType.equals("click") && w instanceof FocusWidget) {
			handlerReg = Event
					.addNativePreviewHandler(new NativePreviewHandler() {
						public void onPreviewNativeEvent(
								NativePreviewEvent event) {
							if (event.getTypeInt() == Event.ONCLICK
									&& w.getElement().isOrHasChild(
											(Node) event.getNativeEvent()
													.getEventTarget().cast())) {
								if (w instanceof FocusWidget
										&& !((FocusWidget) w).isEnabled()) {
									// Don't click disabled buttons
									return;
								}
								runActions(id, eventUidl, client,
										event.getNativeEvent());
							}
						}
					});
		} else if (eventType.equals("text_is") && w instanceof TextBoxBase) {
			final TextBoxBase textBox = (TextBoxBase) w;
			handlerReg = textBox.addKeyUpHandler(new KeyUpHandler() {
				final String value = eventUidl.getStringArrayAttribute("p")[0];
				String prevValue = textBox.getText();

				public void onKeyUp(KeyUpEvent event) {
					if (textBox.getText().matches(value)
							&& !textBox.getValue().equals(prevValue)) {
						runActions(id, eventUidl, client,
								event.getNativeEvent());
					}
					if (!textBox.getValue().equals(prevValue)) {
						prevValue = textBox.getValue();
					}
				}
			});
		} else if (eventType.equals("text_is_not") && w instanceof TextBoxBase) {
			final TextBoxBase textBox = (TextBoxBase) w;
			handlerReg = textBox.addKeyUpHandler(new KeyUpHandler() {
				final String value = eventUidl.getStringArrayAttribute("p")[0];
				String prevValue = textBox.getText();

				public void onKeyUp(KeyUpEvent event) {
					if (!textBox.getText().matches(value)
							&& !textBox.getValue().equals(prevValue)) {
						runActions(id, eventUidl, client,
								event.getNativeEvent());
					}
					if (!textBox.getValue().equals(prevValue)) {
						prevValue = textBox.getValue();
					}
				}
			});
		} else if (eventType.equals("keydown") && w instanceof FocusWidget) {
			// TODO ESC key is capture in firefox, probably need to use native
			// event preview
			handlerReg = ((FocusWidget) w)
					.addKeyDownHandler(new KeyDownHandler() {
						final int keyCode = eventUidl
								.getStringArrayAttribute("p").length > 0 ? Integer
								.parseInt(eventUidl
										.getStringArrayAttribute("p")[0]) : -1;
						final boolean keepFocus = eventUidl
								.getStringArrayAttribute("p").length > 1 ? !eventUidl
								.getStringArrayAttribute("p")[1]
								.equals("false") : true;

						public void onKeyDown(KeyDownEvent event) {
							if (keyCode == -1
									|| event.getNativeKeyCode() == keyCode) {
								runActions(id, eventUidl, client,
										event.getNativeEvent());
								if (keepFocus)
									((FocusWidget) w).setFocus(true);
								event.preventDefault();
								event.stopPropagation();
							}
						}
					});
		} else if (eventType.equals("mousedown")) {
			final int button = eventUidl.getStringArrayAttribute("p").length > 0 ? Integer
					.parseInt(eventUidl.getStringArrayAttribute("p")[0]) : -1;
			if (button == Event.BUTTON_RIGHT) {
				handlerReg = w.addDomHandler(new ContextMenuHandler() {
					public void onContextMenu(ContextMenuEvent event) {
						event.preventDefault();
						runActions(id, eventUidl, client,
								event.getNativeEvent());
					}
				}, ContextMenuEvent.getType());
			} else {
				handlerReg = w.addDomHandler(new MouseDownHandler() {
					public void onMouseDown(MouseDownEvent event) {
						runActions(id, eventUidl, client,
								event.getNativeEvent());
					}
				}, MouseDownEvent.getType());
			}
		} else if (eventType.equals("mouseup")) {
			handlerReg = w.addDomHandler(new MouseUpHandler() {
				final int button = eventUidl.getStringArrayAttribute("p").length > 0 ? Integer
						.parseInt(eventUidl.getStringArrayAttribute("p")[0])
						: -1;

				public void onMouseUp(MouseUpEvent event) {
					if (button == -1 || event.getNativeButton() == button) {
						runActions(id, eventUidl, client,
								event.getNativeEvent());
					}
				}
			}, MouseUpEvent.getType());
		} else if (eventType.equals("mouseover")) {
			handlerReg = w.addDomHandler(new MouseOverHandler() {
				public void onMouseOver(MouseOverEvent event) {
					runActions(id, eventUidl, client, event.getNativeEvent());
				}
			}, MouseOverEvent.getType());
		} else if (eventType.equals("mouseout")) {
			handlerReg = w.addDomHandler(new MouseOutHandler() {
				public void onMouseOut(MouseOutEvent event) {
					runActions(id, eventUidl, client, event.getNativeEvent());
				}
			}, MouseOutEvent.getType());
		} else if (eventType.equals("mousemove")) {
			handlerReg = w.addDomHandler(new MouseMoveHandler() {
				public void onMouseMove(MouseMoveEvent event) {
					runActions(id, eventUidl, client, event.getNativeEvent());
				}
			}, MouseMoveEvent.getType());
		} else if (eventType.equals("focus") && w instanceof FocusWidget) {
			handlerReg = w.addDomHandler(new FocusHandler() {
				public void onFocus(FocusEvent event) {
					runActions(id, eventUidl, client, event.getNativeEvent());
				}
			}, FocusEvent.getType());
		} else if (eventType.equals("blur") && w instanceof FocusWidget) {
			handlerReg = w.addDomHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					runActions(id, eventUidl, client, event.getNativeEvent());
				}
			}, BlurEvent.getType());
		} else if (eventType.equals("change") && w instanceof TextBoxBase) {
			handlerReg = w.addDomHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					runActions(id, eventUidl, client, event.getNativeEvent());
				}
			}, ChangeEvent.getType());
		}

		// Store the action list that was just added
		if (handlerReg != null)
			actionLists.put(handlerId, handlerReg);

	}

	private static void runActions(final String id, final UIDL eventUidl,
			final ApplicationConnection client, final NativeEvent event) {
		for (int i = 0; i < eventUidl.getChildCount(); i++) {
			final UIDL act = eventUidl.getChildUIDL(i);
			doAction(id, client.getPaintable(act.getStringAttribute("target")),
					act.getStringAttribute("action"), client, event,
					act.getStringArrayAttribute("p"));
		}
	}

	private static void doAction(final String id, final Paintable target,
			final String action, final ApplicationConnection client,
			final NativeEvent event, final String... params) {
		final Widget w = (Widget) target;
		if (action.equals("disable")) {
			if (w instanceof FocusWidget) {
				if (((FocusWidget) w).isEnabled()) {
					w.addStyleName("v-disabled");
					w.addStyleDependentName("disabled");
					if (params.length == 0 || !"true".equals(params[0])) {
						// Not a "client-side-only" disabling, need to
						// communicate it back to server
						client.updateVariable(id, action + SEPARATOR
								+ getPid(w), target, false);
					}
					// Disable components deferred, since it might be a button
					// that is just about to fire a click event, and it won't go
					// through if the button is disabled.
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						public void execute() {
							((FocusWidget) w).setEnabled(false);
						}
					});
				}
			}
		} else if (action.equals("enable")) {
			if (w instanceof FocusWidget) {
				if (!((FocusWidget) w).isEnabled()) {
					((FocusWidget) w).setEnabled(true);
					w.removeStyleName("v-disabled");
					w.removeStyleDependentName("disabled");
					if (params.length == 0 || !"true".equals(params[0])) {
						// Not a "client-side-only" enabling, need to
						// communicate it back to server
						client.updateVariable(id, action + SEPARATOR
								+ getPid(w), target, false);
					}
				}
			}
		} else if (action.equals("set_text")) {
			String text = getParam(params[0], event,
					(Widget) client.getPaintable(id));
			if (params.length > 1) {
				String[] replacements = new String[params.length - 1];
				System.arraycopy(params, 1, replacements, 0, params.length - 1);
				text = replaceTokens(text, replacements, event,
						(Widget) client.getPaintable(id));
			}
			if (w instanceof TextBoxBase
					&& !((TextBoxBase) w).getText().equals(text)) {
				((TextBoxBase) w).setText(text);
				client.updateVariable(getPid(w), "text", text, false);
			} else if (w instanceof HTML) {
				((HTML) w).setText(text);
				client.updateVariable(id, action + SEPARATOR + getPid(w)
						+ SEPARATOR + text, target, false);
			} else if (w instanceof VButton) {
				((VButton) w).setText(text);
				client.updateVariable(id, action + SEPARATOR + getPid(w)
						+ SEPARATOR + text, target, false);
			}
		} else if (action.equals("add_style")) {
			w.addStyleDependentName(params[0]);
			w.addStyleName(params[0]);
			client.updateVariable(id, action + SEPARATOR + getPid(w)
					+ SEPARATOR + params[0], target, false);
		} else if (action.equals("remove_style")) {
			w.removeStyleDependentName(params[0]);
			w.removeStyleName(params[0]);
			client.updateVariable(id, action + SEPARATOR + getPid(w)
					+ SEPARATOR + params[0], target, false);
		} else if (action.equals("toggle_style")) {
			if (w.getStyleName().contains(params[0])) {
				w.removeStyleDependentName(params[0]);
				w.removeStyleName(params[0]);
			} else {
				w.addStyleDependentName(params[0]);
				w.addStyleName(params[0]);
			}
			client.updateVariable(id, action + SEPARATOR + getPid(w)
					+ SEPARATOR + params[0], target, false);
		} else if (action.equals("click")) {
			if (w instanceof FocusWidget && !((FocusWidget) w).isEnabled()) {
				// Don't click disabled buttons
				return;
			}
			NativeEvent evt = Document.get().createClickEvent(1, 0, 0, 0, 0,
					false, false, false, false);
			w.getElement().dispatchEvent(evt);
		} else if (action.equals("set_position") && w instanceof VWindow) {
			String x = getParam(params[0], event,
					(Widget) client.getPaintable(id));
			String y = getParam(params[1], event,
					(Widget) client.getPaintable(id));
			VWindow window = (VWindow) w;
			int curX = w.getAbsoluteLeft();
			int curY = w.getAbsoluteTop();

			if (x.matches("[+-]\\d+")) {
				if (x.startsWith("+")) {
					curX += Integer.parseInt(x.substring(1));
				} else if (x.startsWith("-")) {
					curX -= Integer.parseInt(x.substring(1));
				}
			} else {
				curX = Integer.parseInt(x);
			}

			if (y.matches("[+-]\\d+")) {
				if (y.startsWith("+")) {
					curY += Integer.parseInt(y.substring(1));
				} else if (y.startsWith("-")) {
					curY -= Integer.parseInt(y.substring(1));
				}
			} else {
				curY = Integer.parseInt(y);
			}

			window.setPopupPosition(curX, curY);
		}
	}

	private static String getPid(Widget w) {
		return getPidFromElement(w.getElement());
	}

	private static native String getPidFromElement(Element el)
	/*-{
		return el.tkPid;
	}-*/;

	private static String getParam(final String param, final NativeEvent event,
			final Widget w) {
		if (param.equals("event.clientX")) {
			return "" + event.getClientX();
		} else if (param.equals("event.clientY")) {
			return "" + event.getClientY();
		} else if (param.equals("event.relativeX")) {
			return "" + (event.getClientX() - w.getAbsoluteLeft());
		} else if (param.equals("event.relativeY")) {
			return "" + (event.getClientY() - w.getAbsoluteTop());
		} else if (param.equals("event.target.absoluteLeft")) {
			return "" + w.getAbsoluteLeft();
		} else if (param.equals("event.target.absoluteTop")) {
			return "" + w.getAbsoluteTop();
		} else if (param.equals("event.value")
				|| event.getType().equals("change")) {
			return ""
					+ ((Element) event.getEventTarget().cast()).getInnerText();
		}
		return param;
	}

	private static String replaceTokens(String text,
			final String[] replacements, final NativeEvent event, final Widget w) {
		RegExp regExp = RegExp.compile("\\{([0-9]+)\\}");
		MatchResult matcher = regExp.exec(text);

		while (regExp.test(text)) {
			String replacement = replacements[Integer.parseInt(matcher
					.getGroup(1)) - 1];
			if (replacement != null) {
				text = text.replace("{" + matcher.getGroup(1) + "}",
						getParam(replacement, event, w));
			}
			regExp.setLastIndex(matcher.getIndex());
			matcher = regExp.exec(text);
		}
		return text;
	}
}
