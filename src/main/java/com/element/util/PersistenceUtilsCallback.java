package com.element.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This interface is used by several PersistenceUtils classes inside JIDE to register a callback when writing or reading
 * the xml element.
 */
public interface PersistenceUtilsCallback {
	interface Save {
		/**
		 * This method is called when writing the object to the element.
		 *
		 * @param document the XML document
		 * @param element  the element representing the object
		 * @param object   the object to be saved.
		 */
		void save(Document document, Element element, Object object);
	}

	interface Load {
		/**
		 * This method is called when reading the object from the element.
		 *
		 * @param document the XML document
		 * @param element  the element representing the object
		 * @param object   the object to be written.
		 */
		void load(Document document, Element element, Object object);
	}
}
