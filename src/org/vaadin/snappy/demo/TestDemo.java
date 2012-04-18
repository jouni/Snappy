package org.vaadin.snappy.demo;

import org.vaadin.snappy.ui.SnappyButton;
import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Window;

public class TestDemo extends CssLayout {

	Window w;

	public TestDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("test-demo");

		SnappyButton test = new SnappyButton("Test");

		w = new Window();

		test.on(Events.Button.CLICK).addAction(w, Action.Window.SET_POSITION,
				"event.target.absoluteLeft", "event.target.absoluteTop");

		addComponent(test);

	}

	@Override
	public void attach() {
		super.attach();
		getApplication().getMainWindow().addWindow(w);
	}

}
