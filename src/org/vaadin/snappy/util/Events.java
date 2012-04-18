package org.vaadin.snappy.util;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.ui.Component;

public class Events {

	/**
	 * Common events for all Snappy components. All events provide the absolute
	 * position of the event target as parameters for the actions as
	 * "event.target.absoluteLeft/Top".
	 * 
	 * @author jouni
	 * 
	 */
	public static enum Common implements EventType<Component> {

		/**
		 * <p>
		 * Triggered when the component is clicked with the mouse.
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>button</code></b>: (Optional) The mouse button used to
		 * click the element. If not specified, all click events will trigger
		 * the actions. See {@link Mouse} for values.</li>
		 * </ol>
		 */
		CLICK,

		/**
		 * <p>
		 * Triggered when a mouse button is pressed down over the component. The
		 * event coordinates are available for the actions as "event.clientX/Y"
		 * and "relative.X/Y".
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>button</code></b>: (Optional) The mouse button used to
		 * click the element. If not specified, all click events will trigger
		 * the actions. See {@link Mouse} for values.</li>
		 * </ol>
		 */
		MOUSEDOWN,

		/**
		 * <p>
		 * Triggered when a mouse button is released over the component. The
		 * event coordinates are available for the actions as "event.clientX/Y"
		 * and "relative.X/Y".
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>button</code></b>: (Optional) The mouse button used to
		 * click the element. If not specified, all click events will trigger
		 * the actions. See {@link Mouse} for values.</li>
		 * </ol>
		 */
		MOUSEUP,

		/**
		 * <p>
		 * Triggered when the mouse cursor is moved over the component.
		 * </p>
		 */
		MOUSEOVER,

		/**
		 * <p>
		 * Triggered when the mouse cursor is moved out the component.
		 * </p>
		 */
		MOUSEOUT,

		/**
		 * <p>
		 * Triggered when the mouse cursor is moving over the component. The
		 * event coordinates are available for the actions as "event.clientX/Y"
		 * and "relative.X/Y".
		 * </p>
		 */
		MOUSEMOVE;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum Button implements EventType<com.vaadin.ui.Button> {
		/**
		 * <i>See {@link Common#CLICK}</i>
		 */
		CLICK,

		/**
		 * Triggered when the Button is focused.
		 */
		FOCUS,

		/**
		 * Triggered when focus is moved out of the Button.
		 */
		BLUR;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	public static enum TextField implements EventType<com.vaadin.ui.TextField> {
		/**
		 * Triggered when the Button is focused.
		 */
		FOCUS,

		/**
		 * Triggered when focus is moved out of the Button.
		 */
		BLUR,

		/**
		 * <p>
		 * Triggered when the value of the TextField changes. The event is
		 * triggered when either the field loses focus, or the Enter key is
		 * pressed. If you need to react to the field value as it is typed (i.e.
		 * Vaadin core {@link TextChangeEvent}), use the
		 * {@link org.vaadin.snappy.util.Events.TextField#TEXT_IS} or
		 * {@link org.vaadin.snappy.util.Events.TextField#TEXT_IS_NOT} events.
		 * </p>
		 * 
		 * <p>
		 * The new value is available for the actions as "event.value".
		 * </p>
		 */
		CHANGE,

		/**
		 * <p>
		 * Triggered when the value/text of the field matches the string/regexp
		 * specified as the first parameter.
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>regExp</code></b>: The string/regular expressing to
		 * match against in the client.</li>
		 * </ol>
		 */
		TEXT_IS,

		/**
		 * <p>
		 * Triggered when the value/text of the field doesn't match the
		 * string/regexp specified as the first parameter.
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>regExp</code></b>: The string/regular expressing to
		 * match against in the client.</li>
		 * </ol>
		 */
		TEXT_IS_NOT,

		/**
		 * <p>
		 * Triggered when the field has focus and a key is pressed down. Takes
		 * two optional parameters:
		 * </p>
		 * 
		 * <h3>Parameters</h3>
		 * <ol>
		 * <li><b><code>keyCode</code></b>: The {@link Events.Key} which should
		 * trigger the actions. Use the <code>code()</code> method for the
		 * actual value, e.g. <code>Events.Key.ENTER.code()</code></li>
		 * <li><b><code>keepFocus</code></b>: Should the focus stay in the field
		 * after the actions are completed (e.g. if a button is clicked using
		 * the actions, focus would normally move to that button). Defaults to
		 * <code>"true"</code>, but you can set it to <code>"false"</code>.</li>
		 * </ol>
		 */
		KEYDOWN;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	/**
	 * <p>
	 * Mouse buttons to be used with Snappy mouse events.
	 * </p>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * snappyComponent.on(Events.Common.MOUSEDOWN, Events.Mouse.SECONDARY.button()).addAction(...);
	 * </pre>
	 */
	public static enum Mouse {
		PRIMARY(1), SECONDARY(2);

		private int button;

		Mouse(int button) {
			this.button = button;
		}

		public String button() {
			return this.toString();
		}

		public String toString() {
			return "" + this.button;
		}
	}

	/**
	 * <p>
	 * Key codes to be used with Snappy key events.
	 * </p>
	 * 
	 * Example:
	 * 
	 * <pre>
	 * textfield.on(Events.TextField.KEYDOWN, Key.ESCAPE.code()).addAction(...);
	 * </pre>
	 */
	public static enum Key {
		ENTER(13),

		ESCAPE(27),

		PAGE_UP(33),

		PAGE_DOWN(34),

		TAB(9),

		ARROW_LEFT(37),

		ARROW_UP(38),

		ARROW_RIGHT(39),

		ARROW_DOWN(40),

		BACKSPACE(8),

		DELETE(46),

		INSERT(45),

		END(35),

		HOME(36),

		F1(112),

		F2(113),

		F3(114),

		F4(115),

		F5(116),

		F6(117),

		F7(118),

		F8(119),

		F9(120),

		F10(121),

		F11(122),

		F12(123),

		A(65),

		B(66),

		C(67),

		D(68),

		E(69),

		F(70),

		G(71),

		H(72),

		I(73),

		J(74),

		K(75),

		L(76),

		M(77),

		N(78),

		O(79),

		P(80),

		Q(81),

		R(82),

		S(83),

		T(84),

		U(85),

		V(86),

		W(87),

		X(88),

		Y(89),

		Z(90),

		NUM0(48),

		NUM1(49),

		NUM2(50),

		NUM3(51),

		NUM4(52),

		NUM5(53),

		NUM6(54),

		NUM7(55),

		NUM8(56),

		NUM9(57),

		SPACEBAR(32);

		private int code;

		Key(int keyCode) {
			this.code = keyCode;
		}

		public String code() {
			return this.toString();
		}

		public String toString() {
			return "" + this.code;
		}
	}

}
