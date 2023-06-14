/**
 * Created by Himel Mazumder on 12/20/2018.
 */
public class Friend {
    private String name;
    private String currentLocation;
    private int birthYear;
    private int age;

    Friend next;
    Friend previous;

    public Friend(String name, String currentLocation, int birthYear, int age) {
        this.name = name;
        this.currentLocation = currentLocation;
        this.birthYear = birthYear;
        this.age = age;

        next = null;
        previous = null;
    }

    public String getName() {
        return name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getAge() {
        return age;
    }
}
