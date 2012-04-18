package org.vaadin.snappy.demo;

import org.vaadin.snappy.ui.SnappyButton;
import org.vaadin.snappy.ui.SnappyTextField;
import org.vaadin.snappy.util.Action;
import org.vaadin.snappy.util.Events;
import org.vaadin.snappy.util.Events.Key;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;

public class SearchFieldDemo extends CssLayout {

	public SearchFieldDemo() {
		setMargin(true);
		setWidth("100%");
		addStyleName("search-demo");

		addComponent(new Label("<h2>Instant Field Interaction</h2>", Label.CONTENT_XHTML));
		addComponent(new Label(
				"<p>The value of the textfield triggers the enabled/disabled state of the button. ESC key clears the textfield and ENTER triggers the search &ndash; it clicks the button, disables it and adds a style name to the label next to it (which is the spinner).</p><p>The actual search can be performed on the server without the use of threads (this demo just sleeps for 3 seconds on the server when the button is clicked). The side effect of this is that the UI will be blocked during the search (in the server, not in the client), so a good indication of activity is needed (i.e. the spinner in this example) to keep the user from trying to manipulate the UI during the request.</p>",
				Label.CONTENT_XHTML));

		final SnappyTextField searchField = new SnappyTextField();
		searchField.setInputPrompt("Search for something...");
		searchField.setWidth("15em");

		final Label spinner = new Label();
		spinner.setSizeUndefined();

		final SnappyButton searchButton = new SnappyButton("Search",
				new Button.ClickListener() {
					public void buttonClick(ClickEvent event) {
						// Simulate a slow search operation
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// Show the 'result'
						event.getButton().getWindow()
								.showNotification("Search was finished");

						// Enable the button once the request returns, so that
						// it can be clicked again
						event.getButton().setEnabled(true);
						// Force repaint so that the enabled state is restored
						// (if the real state is enabled already, and disabled
						// only in the client)
						event.getButton().requestRepaint();

						// Remove the spinner
						spinner.removeStyleName("loading");
					}
				});
		searchButton.setEnabled(false);

		/**
		 * Toggle the enabled state of the button depending on the content of
		 * the field. Disable the button only on the client side.
		 */
		searchField.on(Events.TextField.TEXT_IS, "").addAction(searchButton,
				Action.Common.DISABLE, "true");
		searchField.on(Events.TextField.TEXT_IS_NOT, "").addAction(
				searchButton, Action.Common.ENABLE);

		/**
		 * When 'ESC' is pressed inside the field, clear the field
		 */
		searchField.on(Events.TextField.KEYDOWN, Key.ESCAPE.code()).addAction(
				searchField, Action.TextField.SET_TEXT, "");

		/**
		 * When 'ENTER' is pressed inside the field, click the button.
		 */
		searchField.on(Events.TextField.KEYDOWN, Key.ENTER.code()).addAction(
				searchButton, Action.Button.CLICK);

		/**
		 * Disable the button (only in the client side) and add the spinner when
		 * the button is clicked.
		 */
		searchButton.on(Events.Common.CLICK).addAction(searchButton,
				Action.Common.DISABLE, "true");
		searchButton.on(Events.Common.CLICK).addAction(spinner,
				Action.Common.ADD_STYLE, "loading");

		addComponent(searchField);
		addComponent(searchButton);
		addComponent(spinner);
	}

}
