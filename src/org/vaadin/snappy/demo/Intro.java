package org.vaadin.snappy.demo;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class Intro extends CssLayout {

	public Intro() {
		setMargin(true);
		setWidth("100%");
		addStyleName("intro");

		addComponent(new Label("<h1>Responsive User Interface</h1>",
				Label.CONTENT_XHTML));

		addComponent(new Label(
				"<p>As you probably know, almost every action in a Vaadin application requires a server request. Most of the time this is not a problem, but sometimes you probably wish you could optimize some small operations to be handled mainly on the client side.</p>",
				Label.CONTENT_XHTML));

		addComponent(new Label("<h2>Motivation</h2>", Label.CONTENT_XHTML));

		addComponent(new Label(
				"<p>There are mainly two reasons you would want to move some operations to the client/browser:</p>",
				Label.CONTENT_XHTML));

		addComponent(new Label(
				"<ol><li>Make the interface respond faster to user input, so that it feels more responsive and 'snappy'</li><li>Reduce HTTP traffic to the server, most notably in mobile devices, as latency and bandwidth could slow down the application</li></ol>",
				Label.CONTENT_XHTML));

		addComponent(new Label(
				"<p>Both of these reasons come from <em>the need to make the application feel fast to the end user</em>.</p>",
				Label.CONTENT_XHTML));

		addComponent(new Label("<h2>Solution</h2>", Label.CONTENT_XHTML));

		addComponent(new Label(
				"<p>With the Snappy add-on, you are allowed to define some actions to execute primarily on the client/browser, and let the new state be synchronized to the server lazily. No server request is made instantly when the actions are performed. The state is synchronized only when some other server request is triggered, such as button click which requires server side processing.</p>",
				Label.CONTENT_XHTML));

	}

}
