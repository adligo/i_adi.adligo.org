package org.adligo.i.adi.client.models;

import org.adligo.i.util.client.CollectionFactory;
import org.adligo.i.util.client.I_Collection;
import org.adligo.i.util.client.I_Iterator;

/**
 * This class needs some major rework and
 * will be done in a few days.
 * 
 * this class splits a name by it's slashes (/)
 * so that synchronization blocks can be obvoided in 
 * for the Cache impl
 * 
 * @author scott
 *
 */
public class ReferenceAddressName {
	private String parentFullPath;
	private String localPath;
	/**
	 * the full path parentFullPath + / + localPath
	 */
	private String fullPath;
	
	public ReferenceAddressName(String p) {
		if (p == null) {
			parentFullPath = "";
			localPath = "";
			return;
		}
		fullPath = p;
		
		char [] chars = p.toCharArray();
		I_Collection collection = CollectionFactory.create();
		//no StringBuilder on JME yet so use the slower one
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (i == 0) {
				//keep first slash
				sb.append(c);
			} else if (c == '/') {
				collection.add(sb.toString());
				sb = new StringBuffer();
				sb.append(c);
			} else {
				sb.append(c);
			}
		}
		collection.add(sb.toString());
		String [] nameParts = new String[collection.size()];
		I_Iterator it = collection.getIterator();
		int counter = 0;
		while (it.hasNext()) {
			String o = (String) it.next();
			nameParts[counter] = o;
			counter++;
		}
		if (nameParts.length == 0) {
			parentFullPath = "";
			localPath = "";
		} else if (nameParts.length == 1) {
			parentFullPath = "";
			localPath = nameParts[0];
		} else {
			StringBuffer sbFin = new StringBuffer();
			for (int i = 0; i < nameParts.length - 1; i++) {
				sbFin.append(nameParts[i]);
			}
			parentFullPath = sbFin.toString();
			localPath = nameParts[nameParts.length -1];
		}
	}
	
	public String getParentFullPath() {
		return parentFullPath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public String getFullPath() {
		return fullPath;
	}

	
	
}
