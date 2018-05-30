import org.sql2o.*;
import java.util.List;

public class Animal {
  public String name;
  public String endangered;
  public int id;

  public Animal(String name, String endangered) {
    this.name = name;
    this.endangered = endangered;
  }

  public String getName() {
    return name;
  }

  public String getEndangered() {
    return endangered;
  }

  public int getId() {
    return id;
  }

  public void save() {
    if (name.equals("")) {
      throw new IllegalArgumentException("Please enter a name.");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (name, endangered) VALUES (:name, :endangered)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("endangered", this.endangered)
        .throwOnMappingFailure(false)
        .executeUpdate()
        .getKey();
    }
  }

  public void setEndangered(String endangered, String health, String age) {
    if ((!health.equals(EndangeredAnimal.HEALTH_ILL)
      && !health.equals(EndangeredAnimal.HEALTH_OKAY)
      && !health.equals(EndangeredAnimal.HEALTH_HEALTHY))
      || (!age.equals(EndangeredAnimal.AGE_NEWBORN)
      && !age.equals(EndangeredAnimal.AGE_YOUNG)
      && !age.equals(EndangeredAnimal.AGE_ADULT))) {
      throw new IllegalArgumentException("Please choose from ill, okay, healthy or newborn, young, adult");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET endangered = :endangered, health = :health, age = :age WHERE id = :id";
      con.createQuery(sql)
        .addParameter("endangered", endangered)
        .addParameter("health", health)
        .addParameter("age", age)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if (!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getName().equals(newAnimal.getName());
    }
  }

  public static List<Animal> all() {
    String sql = "select * from animals";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .throwOnMappingFailure(false)
      .executeAndFetch(Animal.class);
    }
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id=:id";
      Animal blog = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Animal.class);
      return blog;
    }
  }

  public static String getAnimalName(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT name FROM animals WHERE id = :id;";
      String name = con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(String.class);
      return name;
    }
  }

  public static String getAnimalEndangered(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT endangered FROM animals WHERE id = :id;";
      String name = con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(String.class);
      return name;
    }
  }

  public static String getAnimalHealth(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT health FROM animals WHERE id = :id;";
      String name = con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(String.class);
      return name;
    }
  }

  public static String getAnimalAge(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT age FROM animals WHERE id = :id;";
      String name = con.createQuery(sql)
        .addParameter("id", id)
        .executeScalar(String.class);
      return name;
    }
  }

  public boolean completeSave() {
    try {
      this.save();
      return true;
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }

  public boolean completeEndangered(String endangered, String health, String age) {
    try {
      this.setEndangered(endangered, health, age);
      return true;
    } catch (IllegalArgumentException exception) {
      return false;
    }
  }
}
