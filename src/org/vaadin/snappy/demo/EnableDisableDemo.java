package org.vaadin.snappy.demo;

import org.vaadin.codelabel.CodeLabel;
import org.vaadin.snappy.ui.SnappyButton;
import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class EnableDisableDemo extends CssLayout {

	public EnableDisableDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("enable-disable-demo");

		addComponent(new Label(
				"<h2>Enable & Disable Components</h2><p>You can disable and enable Vaadin Button and TextField components using Snappy components. Only those components are supported at this point since they are the only ones which offer a client side API for it.</p><p>Click the 'Enable' and 'Disable' buttons to toggle the state of the TextField.</p>",
				Label.CONTENT_XHTML));

		SnappyButton enable = new SnappyButton("Enable");
		SnappyButton disable = new SnappyButton("Disable");

		TextField text = new TextField();
		text.setValue("Text field value");

		enable.on(Events.Button.CLICK).addAction(text, Action.Common.ENABLE);
		disable.on(Events.Button.CLICK).addAction(text, Action.Common.DISABLE);

		addComponent(enable);
		addComponent(disable);
		addComponent(text);

		addComponent(new Label("<br><h3>Code Example</h3>", Label.CONTENT_XHTML));

		CodeLabel code = new CodeLabel(
				"SnappyButton enable = new SnappyButton(\"Enable\");\n"
						+ "SnappyButton disable = new SnappyButton(\"Disable\");\n\n"

						+ "TextField text = new TextField();\n"
						+ "text.setValue(\"Text field value\");\n\n"

						+ "enable.on(Events.Button.CLICK).addAction(text, Action.Common.ENABLE);\n"
						+ "disable.on(Events.Button.CLICK).addAction(text, Action.Common.DISABLE);");

		addComponent(code);
	}

}
