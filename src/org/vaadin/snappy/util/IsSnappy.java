package org.vaadin.snappy.util;

public interface IsSnappy<T> {

	public ActionList<?> on(EventType<? super T> event, String... params);

}
