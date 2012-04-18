package org.vaadin.snappy;

import org.vaadin.snappy.demo.EnableDisableDemo;
import org.vaadin.snappy.demo.Intro;
import org.vaadin.snappy.demo.LabelValueDemo;
import org.vaadin.snappy.demo.MouseEventDemo;
import org.vaadin.snappy.demo.SearchFieldDemo;
import org.vaadin.snappy.demo.SnappyTabsDemo;
import org.vaadin.snappy.demo.StylesDemo;
import org.vaadin.snappy.ui.SnappyTabs;

import com.vaadin.Application;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class SnappyDemoApp extends Application {

	@Override
	public void init() {
		setTheme("trim");

		final Window mainWindow = new Window("Snappy Vaadin");
		setMainWindow(mainWindow);

		SnappyTabs tabs = new SnappyTabs(new String[] { "Intro",
				"Enable & Disable", "Style Names", "Setting Text",
				"Mouse Events", "Field Interactions", "Client-only Tabs" },
				new Component[] { new Intro(), new EnableDisableDemo(),
						new StylesDemo(), new LabelValueDemo(),
						new MouseEventDemo(), new SearchFieldDemo(),
						new SnappyTabsDemo() });
		tabs.setSizeFull();
		tabs.addStyleName(SnappyTabs.Style.vertical.name());

		tabs.addSection("<h2>Snappy</h2>", 0);
		tabs.addSection("<h4>Features</h4>", 2);
		tabs.addSection("<h4>Examples</h4>", 7);

		tabs.addSection(
				"<em class=\"badge\"><strong>No Server</strong>No action* in this app requires a server request!<br><br><span>* Except one, which is the fake 'search' in the 'Field Interactions' demo</span></em>",
				1);

		mainWindow.setContent(tabs);
	}
}
