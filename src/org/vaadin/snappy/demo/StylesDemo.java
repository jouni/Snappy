package org.vaadin.snappy.demo;

import org.vaadin.codelabel.CodeLabel;
import org.vaadin.snappy.ui.SnappyButton;
import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public class StylesDemo extends CssLayout {

	public StylesDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("styles-demo");

		addComponent(new Label(
				"<h2>Add, Remove & Toggle Style Names</h2><p>Additional style names can be modified directly in the client. The target can be any Vaadin component.</p>",
				Label.CONTENT_XHTML));

		SnappyButton addStyle = new SnappyButton("Add 'selected' style");
		SnappyButton removeStyle = new SnappyButton("Remove 'selected' style");
		SnappyButton toggleStyle = new SnappyButton("Toggle 'selected' style");

		Panel panel = new Panel("Panel Caption");
		panel.addComponent(new Label("Some content for the panel"));

		addStyle.on(Events.Button.CLICK).addAction(panel,
				Action.Common.ADD_STYLE, "selected");
		removeStyle.on(Events.Button.CLICK).addAction(panel,
				Action.Common.REMOVE_STYLE, "selected");
		toggleStyle.on(Events.Button.CLICK).addAction(panel,
				Action.Common.TOGGLE_STYLE, "selected");

		addComponent(addStyle);
		addComponent(removeStyle);
		addComponent(toggleStyle);
		addComponent(panel);

		addComponent(new Label("<br><h3>Code Example</h3>", Label.CONTENT_XHTML));

		CodeLabel code = new CodeLabel(
				"SnappyButton addStyle = new SnappyButton(\"Add 'selected' style\");\n"
						+ "SnappyButton removeStyle = new SnappyButton(\"Remove 'selected' style\");\n"
						+ "SnappyButton toggleStyle = new SnappyButton(\"Toggle 'selected' style\");\n\n"

						+ "Panel panel = new Panel(\"Panel Caption\");\n"
						+ "panel.addComponent(new Label(\"Some content for the panel\"));\n\n"

						+ "addStyle.on(Events.Button.CLICK).addAction(panel, \n\t\t\tAction.Common.ADD_STYLE, \"selected\");\n"
						+ "removeStyle.on(Events.Button.CLICK).addAction(panel, \n\t\t\tAction.Common.REMOVE_STYLE, \"selected\");\n"
						+ "toggleStyle.on(Events.Button.CLICK).addAction(panel, \n\t\t\tAction.Common.TOGGLE_STYLE, \"selected\");");

		addComponent(code);

	}

}
