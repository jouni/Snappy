package org.vaadin.snappy.demo;

import org.vaadin.snappy.ui.SnappyTabs;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class SnappyTabsDemo extends CssLayout {

	public SnappyTabsDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("tabs-demo");

		addComponent(new Label(
				"<h2>Client-only Tabs</h2><p>You can use Snappy for building a tab sheet component that works directly in the client. The visible tab is changed in the client, so it's a great solution for mobile use for instance, where responsiveness and low bandwidth use are key.</p>",
				Label.CONTENT_XHTML));

		SnappyTabs tabs = new SnappyTabs(new String[] { "First", "Second",
				"Third" }, new Component[] { new Label("First content"),
				new Label("Second content"), new Label("Third content") });

		addComponent(tabs);
	}

}
