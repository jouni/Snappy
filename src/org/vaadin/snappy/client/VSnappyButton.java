package org.vaadin.snappy.client;

import com.google.gwt.dom.client.Node;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VButton;

public class VSnappyButton extends VButton {

	private HandlerRegistration preventClick;

	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		super.updateFromUIDL(uidl, client);

		// Prevent server request if no server side click listeners are present
		if (uidl.hasAttribute("noserver") && preventClick == null) {
			preventClick = Event
					.addNativePreviewHandler(new NativePreviewHandler() {
						public void onPreviewNativeEvent(
								NativePreviewEvent event) {
							if (event.getTypeInt() == Event.ONCLICK
									&& VSnappyButton.this.getElement()
											.isOrHasChild(
													(Node) event
															.getNativeEvent()
															.getEventTarget()
															.cast())) {
								event.cancel();
							}
						}
					});
		} else if (preventClick != null) {
			preventClick.removeHandler();
		}

		VSnappy.handleClientActions(this, uidl, client);
	}

}
