package org.vaadin.snappy.client;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VTextField;

public class VSnappyTextField extends VTextField {

	@Override
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		super.updateFromUIDL(uidl, client);
		VSnappy.handleClientActions(this, uidl, client);
	}

}
