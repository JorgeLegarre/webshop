package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ActorModel;
import se.jiv.webshop.repository.ActorRepository;

public class ActorDAO extends GeneralDAO implements ActorRepository
{
	private static final Logger LOGGER = Logger.getLogger(ActorDAO.class.getName());

	@Override
	public ActorModel addActor(ActorModel actor) throws WebshopAppException
	{
		if (actor != null)
		{
			int generatedId;

			try (Connection conn = getConnection())
			{

				String sql = "INSERT INTO actors (firstname, lastname, dob)"
						+ "VALUES (?, ?, ?)";

				try (PreparedStatement pstmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS))
				{

					pstmt.setString(1, actor.getFirstname());
					pstmt.setString(2, actor.getLastname());
					pstmt.setString(3, actor.getDob());

					pstmt.executeUpdate();

					generatedId = ActorModel.DEFAULT_ID;
					try (ResultSet rs = pstmt.getGeneratedKeys())
					{
						if (rs.next())
						{
							generatedId = rs.getInt(1);
						}
					}

					ActorModel newModel = new ActorModel(generatedId, actor);

					LOGGER.trace("Added new actor to database: " + newModel);

					return newModel;

				}
			}
			catch (SQLException e)
			{
				WebshopAppException excep = new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "ADD_ACTOR");
				LOGGER.error(excep);
				throw excep;
			}

		}
		return null;

	}

	@Override
	public boolean updateActor(ActorModel actor) throws WebshopAppException
	{
		try (Connection conn = getConnection())
		{

			String sql = "UPDATE actors SET firstname = ?, lastname = ?, dob = ? WHERE id = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setString(1, actor.getFirstname());
				pstmt.setString(2, actor.getLastname());
				pstmt.setString(3, actor.getDob());
				pstmt.setInt(4, actor.getId());

				pstmt.executeUpdate();

				LOGGER.trace("Updated actor: " + actor);

				return true;
			}

		}
		catch (SQLException e)
		{
			WebshopAppException excep = new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "UPDATE_ACTOR");
			LOGGER.error(excep);
			throw excep;
		}
	}

	@Override
	public ActorModel getActor(int id) throws WebshopAppException
	{
		try (Connection conn = getConnection())
		{
			String sql = "SELECT * FROM actors WHERE id = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setInt(1, id);

				try (ResultSet rs = pstmt.executeQuery())
				{

					int returnedId = 0;
					String firstname = "";
					String lastname = "";
					String dob = "";

					if (rs.next())
					{
						returnedId = rs.getInt("id");
						firstname = rs.getString("firstname");
						lastname = rs.getString("lastname");
						dob = rs.getString("dob");

						ActorModel foundActor = new ActorModel(returnedId, firstname, lastname, dob);

						LOGGER.trace("Found actor: " + foundActor);

						return foundActor;

					}
				}
			}
		}
		catch (SQLException e)
		{
			WebshopAppException excep = new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "GET_ACTOR");
			LOGGER.error(excep);
			throw excep;
		}
		return null;
	}

	@Override
	public List<ActorModel> search(String name)
	{
		List<ActorModel> foundActors = new ArrayList<>();

		try (Connection conn = getConnection())
		{
			String sql = "SELECT * FROM actors WHERE firstname LIKE ? OR firstname LIKE ? OR firstname LIKE ?"
					+ " OR lastname LIKE ? OR lastname LIKE ? OR lastname LIKE ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setString(1, name + "%");
				pstmt.setString(2, "%" + name);
				pstmt.setString(3, "%" + name + "%");
				pstmt.setString(4, name + "%");
				pstmt.setString(5, "%" + name);
				pstmt.setString(6, "%" + name + "%");

				System.out.println(pstmt);

				try (ResultSet rs = pstmt.executeQuery())
				{
					while (rs.next())
					{
						int id = rs.getInt("id");
						String firstname = rs.getString("firstname");
						String lastname = rs.getString("lastname");
						String dob = rs.getString("dob");

						foundActors.add(new ActorModel(id, firstname, lastname, dob));

					}
				}
			}

			LOGGER.trace("Found actors: " + foundActors);

			return foundActors;
		}
		catch (SQLException e)
		{
			WebshopAppException excep = new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "SEARCH_ACTOR");
			LOGGER.error(excep);
		}
		return null;
	}

	@Override
	public List<ActorModel> getAllActors() throws WebshopAppException
	{
		List<ActorModel> foundActors = new ArrayList<>();

		try (Connection conn = getConnection())
		{
			String sql = "SELECT * FROM actors";

			try (Statement stmt = conn.createStatement())
			{
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					while (rs.next())
					{
						int id = rs.getInt("id");
						String firstname = rs.getString("firstname");
						String lastname = rs.getString("lastname");
						String dob = rs.getString("dob");

						foundActors.add(new ActorModel(id, firstname, lastname, dob));

					}
				}
			}

			LOGGER.trace("Found actors: " + foundActors);

			return foundActors;

		}
		catch (SQLException e)
		{
			WebshopAppException excep = new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "GET_ALL_ACTOR");
			LOGGER.error(excep);
		}
		return null;
	}

	@Override
	public boolean deleteActor(int id) throws WebshopAppException
	{
		try (Connection conn = getConnection())
		{
			String sql = "DELETE FROM actors WHERE id = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setInt(1, id);
				pstmt.executeUpdate();

				LOGGER.trace("Deleted user with id: " + id);

				return true;
			}
		}
		catch (SQLException e)
		{
			WebshopAppException excep = new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "DELETE_ACTOR");
			LOGGER.error(excep);
		}
		return false;
	}

}
