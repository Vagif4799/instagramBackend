package instagram.dao;

import instagram.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(
                new Person(id, person.getName(), person.getSurname(), person.getUsername(),
                person.getMail(), person.getPassword(), person.getGender(),
                person.getBirthdate(), person.getPhone_number(), person.getDescription(),
                person.getProfile_photo(), person.getNumber_followers(), person.getNumber_follow())
        );
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletepersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if(!personMaybe.isPresent()) {
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(
                        person ->{
                            int indexOfPersonToUpdate = DB.indexOf(person);
                            if (indexOfPersonToUpdate >= 0) {
                                DB.set(indexOfPersonToUpdate, new Person(id, update.getName(),
                                        update.getSurname(), update.getUsername(), update.getMail(),
                                        update.getPassword(), update.getGender(), update.getBirthdate(),
                                        update.getPhone_number(), update.getDescription(), update.getProfile_photo(),
                                        update.getNumber_followers(), update.getNumber_follow()));
                                return 1;
                            }
                            return 0;
                        }
                )
                .orElse(0);
    }
}
