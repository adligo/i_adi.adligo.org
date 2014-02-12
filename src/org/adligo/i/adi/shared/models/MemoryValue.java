package org.adligo.i.adi.shared.models;

public class MemoryValue {
	private Object value;
	private Object owner;
	
	public MemoryValue(Object pValue, Object pOwner) {
		value = pValue;
		owner = pOwner;
	}
	
	public Object getValue() {
		return value;
	}
	public Object getOwner() {
		return owner;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("MemoryValue [value=");
		sb.append(value);
		sb.append(",owner=");
		sb.append(owner);
		sb.append("]");
		
		return sb.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemoryValue other = (MemoryValue) obj;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
