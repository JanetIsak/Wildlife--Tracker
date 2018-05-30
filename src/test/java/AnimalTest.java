import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AnimalTest {

  Animal animal1 = new Animal("Owl", "No");
  Animal animal2 = new Animal("Dog", "No");

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_true() {
    Animal testAnimal = animal1;
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getName_personInstantiatesWithName_Owl() {
    Animal testAnimal = animal1;
    assertEquals("Owl", testAnimal.getName());
  }

  @Test
  public void getEndangered_personInstantiatesWithEndangered_No() {
    Animal testAnimal = animal1;
    assertEquals("No", testAnimal.getEndangered());
  }

  @Test
  public void equals_returnsTrueIfNameAreSame_true() {
    Animal testAnimal = animal1;
    Animal anotherAnimal = animal1;
    assertTrue(testAnimal.equals(anotherAnimal));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Animal() {
    Animal testAnimal = animal1;
    testAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(testAnimal));
  }

  @Test
  public void save_assignsIdToAnimal() {
    Animal testAnimal = animal1;
    testAnimal.save();
    Animal savedAnimal = Animal.all().get(0);
    assertEquals(savedAnimal.getId(), testAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_true() {
    Animal firstAnimal = animal1;
    firstAnimal.save();
    Animal secondAnimal = animal2;
    secondAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(firstAnimal));
    assertEquals(true, Animal.all().get(1).equals(secondAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    Animal firstAnimal = animal1;
    firstAnimal.save();
    Animal secondAnimal = animal2;
    secondAnimal.save();
    assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  }

  @Test(expected = IllegalArgumentException.class)
  public void save_throwsExceptionIfNameIsEmpty(){
    Animal myAnimal = new Animal("", "no");
    myAnimal.save();
  }
}
