package se.jiv.webshop.model;

public class ActorModel
{
	public static final int DEFAULT_ID = -1;

	int id;
	String firstname;
	String lastname;
	String dob;

	public ActorModel(int id, String firstname, String lastname)
	{
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public ActorModel(int id, String firstname, String lastname, String dob)
	{
		this(id, firstname, lastname);
		this.dob = dob;
	}

	public ActorModel(String firstname, String lastname)
	{
		this(DEFAULT_ID, firstname, lastname);
	}

	public ActorModel(String firstname, String lastname, String dob)
	{
		this(DEFAULT_ID, firstname, lastname);
		this.dob = dob;
	}

	public ActorModel(int id, ActorModel other)
	{
		this(id, other.firstname, other.lastname, other.dob);
	}

	public int getId()
	{
		return id;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public String getDob()
	{
		return dob;
	}

	@Override
	public int hashCode()
	{
		return 43 * id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof ActorModel)
		{
			ActorModel currentActor = (ActorModel) obj;
			if (currentActor.id == this.id)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return String.format("Id: " + id + "%nFirstname: " + firstname + "%nLastname: " + lastname + "%nDate of birth: " + dob);
	}

}
