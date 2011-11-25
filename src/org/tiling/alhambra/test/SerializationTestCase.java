package org.tiling.alhambra.test;

import java.io.*;




import junit.framework.*;








public class SerializationTestCase extends TestCase {
	public SerializationTestCase(String name) {
		super(name);
	}







 
 












	public Object testPersist(Object obj) {
		try {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ObjectOutputStream objects = new ObjectOutputStream(bytes);
			objects.writeObject(obj);
			objects.close();
			ByteArrayInputStream returnBytes = new ByteArrayInputStream(bytes.toByteArray());
			ObjectInputStream returnObjects = new ObjectInputStream(returnBytes);
			Object returnObj = returnObjects.readObject();
			returnObjects.close();
			assertNotNull(returnObj);
			return returnObj;
		} catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}}