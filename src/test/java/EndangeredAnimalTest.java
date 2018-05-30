import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class EndangeredAnimalTest {

  EndangeredAnimal endangered1 = new EndangeredAnimal("Cheetah", "yes", "ill", "young");
  EndangeredAnimal endangered2 = new EndangeredAnimal("chimpanzee", "yes", "okay", "newborn");

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void endangeredAnimal_instantiatesCorrectly_true() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    assertEquals(true, testEndangeredAnimal instanceof EndangeredAnimal);
  }

  @Test
  public void getName_personInstantiatesWithName_Cheetah() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    assertEquals("Cheetah", testEndangeredAnimal.getName());
  }

  @Test
  public void getEndangered_personInstantiatesWithEndangered_yes() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    assertEquals("yes", testEndangeredAnimal.getEndangered());
  }

  @Test
  public void getEndangered_personInstantiatesWithAge_young() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    assertEquals("young", testEndangeredAnimal.getAge());
  }

  @Test
  public void getEndangered_personInstantiatesWithHealth_ill() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    assertEquals("ill", testEndangeredAnimal.getHealth());
  }

  @Test
  public void equals_returnsTrueIfNameAreSame_true() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    EndangeredAnimal anotherEndangeredAnimal = endangered1;
    assertTrue(testEndangeredAnimal.equals(anotherEndangeredAnimal));
  }

  @Test
  public void save_insertsObjectIntoDatabase_EndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    testEndangeredAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(testEndangeredAnimal));
  }

  @Test
  public void save_assignsIdToEndangeredAnimal() {
    EndangeredAnimal testEndangeredAnimal = endangered1;
    testEndangeredAnimal.save();
    Animal savedEndangeredAnimal = Animal.all().get(0);
    assertEquals(savedEndangeredAnimal.getId(), testEndangeredAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfEndangeredAnimal_true() {
    EndangeredAnimal firstEndangeredAnimal = endangered1;
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = endangered2;
    secondEndangeredAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(firstEndangeredAnimal));
    assertEquals(true, Animal.all().get(1).equals(secondEndangeredAnimal));
  }

  @Test
  public void find_returnsEndangeredAnimalWithSameId_secondEndangeredAnimal() {
    EndangeredAnimal firstEndangeredAnimal = endangered1;
    firstEndangeredAnimal.save();
    EndangeredAnimal secondEndangeredAnimal = endangered2;
    secondEndangeredAnimal.save();
    assertEquals(Animal.find(secondEndangeredAnimal.getId()), secondEndangeredAnimal);
  }

  @Test
  public void setEndangered_Animal_true() {
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Cheetah", "no", "healthy", "young");
    myEndangeredAnimal.save();
    myEndangeredAnimal.setEndangered("yes", "ill", "newborn");
    assertEquals("yes", EndangeredAnimal.find(myEndangeredAnimal.getId()).getEndangered());
    assertEquals("ill", EndangeredAnimal.find(myEndangeredAnimal.getId()).getHealth());
    assertEquals("newborn", EndangeredAnimal.find(myEndangeredAnimal.getId()).getAge());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setEndangered_throwsExceptionIfHealthIsNotAChoice(){
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Cheetah", "no", "healthy", "young");
    myEndangeredAnimal.save();
    myEndangeredAnimal.setEndangered("yes", "blech", "newborn");
  }

  @Test(expected = IllegalArgumentException.class)
  public void setEndangered_throwsExceptionIfAgeIsNotAChoice(){
    EndangeredAnimal myEndangeredAnimal = new EndangeredAnimal("Cheetah", "no", "healthy", "young");
    myEndangeredAnimal.save();
    myEndangeredAnimal.setEndangered("yes", "ill", "blech");
  }
}
