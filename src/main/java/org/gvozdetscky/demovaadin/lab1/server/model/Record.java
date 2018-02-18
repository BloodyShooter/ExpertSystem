package org.gvozdetscky.demovaadin.lab1.server.model;

public class Record {

	private double param1;

	private double param2;

	private double param3;

	public double getParam1() {
		return param1;
	}

	public void setParam1(double param1) {
		this.param1 = param1;
	}

	public double getParam2() {
		return param2;
	}

	public void setParam2(double param2) {
		this.param2 = param2;
	}

	public double getParam3() {
		return param3;
	}

	public void setParam3(double param3) {
		this.param3 = param3;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Record record = (Record) o;

		return !(record.getParam1() != param1) &&
				!(record.getParam2() != param2) &&
				!(record.getParam3() != param3);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(param1);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(param2);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(param3);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Record{" +
				"param1=" + param1 +
				", param2=" + param2 +
				", param3=" + param3 +
				'}';
	}
}
