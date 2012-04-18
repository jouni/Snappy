package org.vaadin.snappy.demo;

import org.vaadin.codelabel.CodeLabel;
import org.vaadin.snappy.ui.SnappyButton;
import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class MouseEventDemo extends CssLayout {

	public MouseEventDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("mouse-event-demo");

		addComponent(new Label(
				"<h2>Full Mouse Interactivity in the Browser</h2>",
				Label.CONTENT_XHTML));
		addComponent(new Label(
				"<p>The Vaadin core doesn't provide any other mouse events than the basic click event. Mousemove, mouseover, mouseout, mousedown and mouseup are all missing. Snappy fixes that.</p><p>You get all of the events, and they work instantly in the browser, so the user interaction is much more responsive than with a server round trip.</p><p>These events are supported by all Snappy components. You can try out the different events with the test component below &ndash; the events are triggered on the Button, but the actions are performed on the Label next to it.</p>",
				Label.CONTENT_XHTML));

		SnappyButton eventArea = new SnappyButton("Event area");
		Label actionTarget = new Label("Action target");
		actionTarget.setSizeUndefined();
		actionTarget.addStyleName("target");

		eventArea.on(Events.Common.MOUSEOVER).addAction(actionTarget,
				Action.Common.ADD_STYLE, "loading");
		eventArea.on(Events.Common.MOUSEOUT).addAction(actionTarget,
				Action.Common.REMOVE_STYLE, "loading");
		eventArea.on(Events.Common.MOUSEMOVE).addAction(actionTarget,
				Action.Label.SET_TEXT, "Mouse position: {1},{2}",
				"event.relativeX", "event.relativeY");
		eventArea.on(Events.Common.MOUSEDOWN, Events.Mouse.PRIMARY.button())
				.addAction(actionTarget, Action.Label.SET_TEXT, "Mouse down");

		addComponent(eventArea);
		addComponent(actionTarget);

		addComponent(new Label("<br><h3>Code Example</h3>", Label.CONTENT_XHTML));

		CodeLabel code = new CodeLabel(
				"SnappyButton eventArea = new SnappyButton(\"Event area\");\n"
						+ "Label actionTarget = new Label(\"Action target\");\n"
						+ "actionTarget.setSizeUndefined();\n"
						+ "actionTarget.addStyleName(\"target\");\n\n"

						+ "eventArea.on(Events.Common.MOUSEOVER).addAction(actionTarget,\n"
						+ "		Action.Common.ADD_STYLE, \"loading\");\n"
						+ "eventArea.on(Events.Common.MOUSEOUT).addAction(actionTarget,\n"
						+ "		Action.Common.REMOVE_STYLE, \"loading\");\n"
						+ "eventArea.on(Events.Common.MOUSEMOVE).addAction(actionTarget,\n"
						+ "		Action.Label.SET_TEXT, \"Mouse position: {1},{2}\",\n"
						+ "		\"event.relativeX\", \"event.relativeY\");\n"
						+ "eventArea.on(Events.Common.MOUSEDOWN, Events.Mouse.PRIMARY.button())\n"
						+ "		.addAction(actionTarget, Action.Label.SET_TEXT, \"Mouse down\");");

		addComponent(code);
	}

}
