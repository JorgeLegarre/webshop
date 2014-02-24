package se.jiv.webshop.model;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {
	public static final int DEFAULT_PRODUCT_ID = -1;

	private final int id;
	private final String name;
	private final String description;
	private final double cost;
	private final double rrp;
	private final List<Integer> categories;
	private final int productType;

	public static abstract class Builder<T extends Builder<T>> {
		// Required parameters
		private final String name;
		private final int productType;

		protected abstract T self();

		// optional parameters
		private int id;
		private String description;
		private double cost;
		private double rrp;
		private final List<Integer> categories;

		public Builder(String name, int productType) {
			this.name = name;
			this.productType = productType;

			id = DEFAULT_PRODUCT_ID;
			description = "";
			cost = 0;
			rrp = 0;
			categories = new ArrayList<Integer>();
		}

		public T id(int id) {
			this.id = id;
			return self();
		}

		public T description(String description) {
			this.description = description;
			return self();
		}

		public T cost(double cost) {
			this.cost = cost;
			return self();
		}

		public T rrp(double rrp) {
			this.rrp = rrp;
			return self();
		}

		public T categories(List<Integer> categories) {
			if (categories != null) {
				this.categories.addAll(categories);
			}
			return self();
		}

		public ProductModel build() {
			return new ProductModel(this);
		}

	}

	private static class Builder2 extends Builder<Builder2> {
		public Builder2(String name, int productType) {
			super(name, productType);
		}

		@Override
		protected Builder2 self() {
			return this;
		}
	}

	protected ProductModel(Builder<?> builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.cost = builder.cost;
		this.rrp = builder.rrp;
		this.categories = builder.categories;
		this.productType = builder.productType;
	}

	public ProductModel(int id, ProductModel other) {
		this.id = id;
		this.name = other.name;
		this.description = other.description;
		this.cost = other.cost;
		this.rrp = other.rrp;
		this.categories = other.categories;
		this.productType = other.productType;
	}

	public static Builder<?> builder(String name, int productType) {
		return new Builder2(name, productType);
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

	public int getProductType() {
		return productType;
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
		hash *= productType;

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
			isEqual = isEqual && other.productType == this.productType;

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
				.format("Id: %s, Name: %s, Description: %s, Cost: %s, RRP: %s, ProductType: %s, Categories: %s",
						id, name, description, cost, rrp, productType,
						categoriesTxt);
	}

}
