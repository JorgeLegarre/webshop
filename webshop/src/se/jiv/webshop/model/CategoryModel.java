package se.jiv.webshop.model;

public final class CategoryModel {
	public static final int DEFAULT_ID = -1;

	private int id;
	private String name;
	private int staff_responsible;

	public CategoryModel(int id, String name, int staff_responsible) {
		this.id = id;
		this.name = name;
		this.staff_responsible = staff_responsible;
	}

	public CategoryModel(String name, int staff_responsible) {
		this(DEFAULT_ID, name, staff_responsible);
	}

	public CategoryModel(int id, CategoryModel other) {
		this(id, other.name, other.staff_responsible);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getStaff_responsible() {
		return staff_responsible;
	}

	@Override
	public int hashCode() {
		return 31 * id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof CategoryModel) {
			CategoryModel other = (CategoryModel) obj;
			return id == other.id;
		}
		return false;
	}

	public String toString() {
		return String.format("Id: %s, Name: %s, Straff_responsible: %s", id,
				name, staff_responsible);
	}

}
