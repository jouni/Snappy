package org.vaadin.snappy.demo;

import org.vaadin.codelabel.CodeLabel;
import org.vaadin.snappy.ui.SnappyButton;
import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class LabelValueDemo extends CssLayout {

	public LabelValueDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("label-value-demo");

		addComponent(new Label("<h2>Change a Text in the Browser</h2>",
				Label.CONTENT_XHTML));
		addComponent(new Label(
				"<p>The value/caption of a Label, Button and TextField can be altered on the client side. It is also reflected on the server. Notice how the button doesn't trigger a server request, it only happens in the browser.</p>",
				Label.CONTENT_XHTML));

		Label changeMe = new Label("Original label text");
		SnappyButton changeIt = new SnappyButton("Set Text");
		changeIt.on(Events.Common.CLICK).addAction(changeMe,
				Action.Label.SET_TEXT, "New label text");

		addComponent(changeMe);
		addComponent(changeIt);

		addComponent(new Label("<br><h3>Code Example</h3>", Label.CONTENT_XHTML));

		CodeLabel code = new CodeLabel(
				"Label changeMe = new Label(\"Original label text\");\n"
						+ "SnappyButton changeIt = new SnappyButton(\"Set Text\");\n"
						+ "changeIt.on(Events.Common.CLICK).addAction(changeMe,\n\t\tAction.Label.SET_TEXT, \"New label text\");");

		addComponent(code);
	}

}
