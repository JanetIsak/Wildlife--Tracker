import org.sql2o.*;
import java.util.List;
import java.sql.Timestamp;

public class Sighting {
  private String ranger_name;
  private String location;
  private int animal_id;
  private Timestamp timestamp;
  private int id;

  public Sighting (String ranger_name, String location, int animal_id) {
    this.ranger_name = ranger_name;
    this.location = location;
    this.animal_id = animal_id;
  }

  public String getRangerName() {
    return ranger_name;
  }

  public String getLocation() {
    return location;
  }

  public int getAnimalId() {
    return animal_id;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (ranger_name, location, animal_id, timestamp) VALUES (:ranger_name, :location, :animal_id, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("ranger_name", this.ranger_name)
        .addParameter("location", this.location)
        .addParameter("animal_id", this.animal_id)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherSighting) {
    if (!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getRangerName().equals(newSighting.getRangerName()) &&
             this.getLocation().equals(newSighting.getLocation());
    }
  }

  public static List<Sighting> all() {
    String sql = "select * from sightings";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Sighting.class);
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id=:id";
      Sighting blog = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
      return blog;
    }
  }

}
