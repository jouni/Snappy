package org.vaadin.snappy.ui;

import java.util.Map;

import org.vaadin.snappy.client.VSnappyTextField;
import org.vaadin.snappy.util.ActionList;
import org.vaadin.snappy.util.EventType;
import org.vaadin.snappy.util.IsSnappy;
import org.vaadin.snappy.util.Snappy;

import com.vaadin.data.Property;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.TextField;

/**
 * SnappyTextField is identical to the core Vaadin TextField, but it provides
 * client side event and action extensions.
 * 
 * @author jouni
 * 
 */
@SuppressWarnings("unchecked")
@ClientWidget(VSnappyTextField.class)
public class SnappyTextField extends TextField implements IsSnappy<TextField> {

	private static final long serialVersionUID = -3055245944522890394L;

	public SnappyTextField() {
		super();
	}

	public SnappyTextField(Property dataSource) {
		super(dataSource);
	}

	public SnappyTextField(String caption, Property dataSource) {
		super(caption, dataSource);
	}

	public SnappyTextField(String caption, String value) {
		super(caption, value);
	}

	public SnappyTextField(String caption) {
		super(caption);
	}

	private Snappy<TextField> clientApi = new Snappy<TextField>(this);

	public ActionList<TextField> on(EventType<? super TextField> event,
			String... params) {
		return clientApi.on(event, params);
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		clientApi.paintContent(target);
	}

	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		clientApi.changeVariables(source, variables);
		super.changeVariables(source, variables);
	}

}
