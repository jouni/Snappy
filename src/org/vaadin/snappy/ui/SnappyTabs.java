package org.vaadin.snappy.ui;

import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class SnappyTabs extends CssLayout {

	private static final long serialVersionUID = -7525792128014561913L;
	
	final SnappyButton[] tabButtons;
	final Component[] tabContents;

	final CssLayout menu;

	public static enum Style {
		vertical;
	}

	public SnappyTabs(final String[] captions, final Component[] contents) {
		addStyleName("snappy-tabs");

		tabButtons = new SnappyButton[captions.length];
		for (int i = 0; i < captions.length; i++) {
			tabButtons[i] = new SnappyButton(captions[i]);
		}
		tabContents = contents;

		menu = new CssLayout();
		menu.addStyleName("snappy-tabs-buttons");
		addComponent(menu);

		for (int i = 0; i < captions.length; i++) {
			SnappyButton tab = tabButtons[i];

			// On every click, first deselect all
			for (int j = 0; j < captions.length; j++) {
				SnappyButton other = tabButtons[j];
				if (other != tab) {
					tab.on(Events.Button.CLICK).addAction(other,
							Action.Common.REMOVE_STYLE, "selected");
					tab.on(Events.Button.CLICK).addAction(contents[j],
							Action.Common.ADD_STYLE, "hide");
				}
			}

			// Then select the actual selection
			tab.on(Events.Button.CLICK).addAction(tab, Action.Common.ADD_STYLE,
					"selected");
			tab.on(Events.Button.CLICK).addAction(contents[i],
					Action.Common.REMOVE_STYLE, "hide");

			if (i == 0) {
				tab.addStyleName("selected");
			} else {
				contents[i].addStyleName("hide");
			}

			menu.addComponent(tab);
			addComponent(contents[i]);
			contents[i].addStyleName("snappy-tabs-content");
		}

	}

	public void addSection(final String captionHtml, int index) {
		Label label = new Label(captionHtml, Label.CONTENT_XHTML);
		label.setSizeUndefined();
		menu.addComponent(label, index);
	}
}
