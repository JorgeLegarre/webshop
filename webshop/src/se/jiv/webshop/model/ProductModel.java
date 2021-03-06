package se.jiv.webshop.model;

import java.util.ArrayList;
import java.util.List;

public final class ProductModel {
	public static final int DEFAULT_PRODUCT_ID = -1;

	private final int id;
	private final String name;
	private final String description;
	private final double cost;
	private final double rrp;
	private final List<Integer> categories;

	public static class Builder {
		// Required parameters
		private final String name;


		// optional parameters
		private int id;
		private String description;
		private double cost;
		private double rrp;
		private final List<Integer> categories;

		public Builder(String name) {
			this.name = name;
			
			id = DEFAULT_PRODUCT_ID;
			description = "";
			cost = 0;
			rrp = 0;
			categories = new ArrayList<Integer>();
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder cost(double cost) {
			this.cost = cost;
			return this;
		}

		public Builder rrp(double rrp) {
			this.rrp = rrp;
			return this;
		}

		public Builder categories(List<Integer> categories) {
			if (categories != null) {
				this.categories.addAll(categories);
			}
			return this;
		}

		public ProductModel build() {
			return new ProductModel(this);
		}

	}

	private ProductModel(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.cost = builder.cost;
		this.rrp = builder.rrp;
		this.categories = builder.categories;
	}

	public ProductModel(int id, ProductModel other) {
		this.id = id;
		this.name = other.name;
		this.description = other.description;
		this.cost = other.cost;
		this.rrp = other.rrp;
		this.categories = other.categories;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public double getCost() {
		return this.cost;
	}

	public double getRrp() {
		return this.rrp;
	}

	public List<Integer> getCategories() {
		return categories;
	}

		@Override
	public int hashCode() {
		if (id != DEFAULT_PRODUCT_ID) {
			return 37 * id;
		}

		int hash = 37;
		hash *= name.hashCode();
		hash *= description.hashCode();
		hash += cost;
		hash += rrp;
		hash *= categories.hashCode();

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj instanceof ProductModel) {
			ProductModel other = (ProductModel) obj;

			if ((other.id != DEFAULT_PRODUCT_ID)
					|| (this.id != DEFAULT_PRODUCT_ID)) {
				return (other.id == this.id);
			}

			boolean isEqual = true;
			isEqual = isEqual && other.name.equals(this.name);
			isEqual = isEqual && other.description.equals(this.description);
			isEqual = isEqual && other.cost == this.cost;
			isEqual = isEqual && other.rrp == this.rrp;
			isEqual = isEqual && other.categories.equals(this.categories);

			return isEqual;

		}
		return false;
	}

	@Override
	public String toString() {
		String categoriesTxt = "[";
		boolean first = true;
		for (int cat : this.categories) {
			if (!first) {
				categoriesTxt += ", ";
			} else {
				first = false;
			}
			categoriesTxt += cat;
		}
		categoriesTxt += "]";

		return String
				.format("Id: %s, Name: %s, Description: %s, Cost: %s, RRP: %s, Categories: %s",
						id, name, description, cost, rrp, categoriesTxt);
	}

}
