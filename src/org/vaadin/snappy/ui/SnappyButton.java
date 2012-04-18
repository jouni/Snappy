package org.vaadin.snappy.ui;

import java.util.Map;

import org.vaadin.snappy.client.VSnappyButton;
import org.vaadin.snappy.util.ActionList;
import org.vaadin.snappy.util.EventType;
import org.vaadin.snappy.util.IsSnappy;
import org.vaadin.snappy.util.Snappy;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Button;
import com.vaadin.ui.ClientWidget;

/**
 * SnappyButton is identical to the core Vaadin Button, but it provides client
 * side event and action extensions.
 * 
 * @author jouni
 * 
 */
@SuppressWarnings({ "unchecked" })
@ClientWidget(VSnappyButton.class)
public class SnappyButton extends Button implements IsSnappy<Button> {

	private static final long serialVersionUID = 5226119843307772289L;

	public SnappyButton() {
		super();
	}

	public SnappyButton(String caption, ClickListener listener) {
		super(caption, listener);
	}

	public SnappyButton(String caption, Object target, String methodName) {
		super(caption, target, methodName);
	}

	public SnappyButton(String caption) {
		super(caption);
	}

	private Snappy<Button> clientApi = new Snappy<Button>(this);

	public ActionList<Button> on(EventType<? super Button> event,
			String... params) {
		return clientApi.on(event, params);
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		clientApi.paintContent(target);
		if (getListeners(Button.ClickEvent.class) != null
				&& getListeners(Button.ClickEvent.class).size() <= 0) {
			target.addAttribute("noserver", true);
		}
	}

	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		clientApi.changeVariables(source, variables);
		super.changeVariables(source, variables);
	}
}
