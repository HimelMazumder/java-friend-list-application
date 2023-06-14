import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Himel Mazumder on 12/19/2018.
 */
class ApplicationClass {
    private Scanner sc = new Scanner(System.in);

    private static User userList = null;
    private static User tail = null;

    private static User userListTemp = null;

    private String userName;
    private String userCurrentLocation;
    private int userBirthYear;
    private int userAge;

    private static int totalUser = 0;

    private User userPrimary;
    private User userSecondary;

    private String userBirthYearString;

    private ArrayList<User> searchResultsArray = new ArrayList<>();
    private ArrayList<Friend> commonFriends = new ArrayList<>();


    private void getUserInfo() {
        System.out.println("User name: ");
        userName = sc.nextLine();

        System.out.println("User current location (City or Town name): ");
        userCurrentLocation = sc.nextLine();

        System.out.println("User birth year: ");
        userBirthYearString = sc.nextLine();
    }

    private boolean validateUserInfo(int type) {
        int i;

        if (type == 1 || type == 2) {
            for (i = 0; i < userName.length(); i++) {
                if (!((userName.charAt(i) >= 97 && userName.charAt(i) <= 122) || (userName.charAt(i) >= 65 && userName.charAt(i) <= 90))) {
                    System.out.println("Your name doesn't seem to be a valid one!");
                    return false;
                }
            }
        }

        if (type == 1 || type == 3) {
            if (userBirthYearString.length() == 4) {
                for (i = 0; i < 4; i++) {
                    if (!(userBirthYearString.charAt(i) >= 48 && userBirthYearString.charAt(i) <= 57)) {
                        System.out.println("Your birth year doesn't seem to be a valid one!");
                        return false;
                    }
                }

                userBirthYear = Integer.parseInt(userBirthYearString);

                final int currentYear = 2018;
                userAge = currentYear - userBirthYear;

                if (userAge < 13) {
                    System.out.println("You must be at least 13 years old!");
                    return false;
                }

            } else {
                System.out.println("Your birth year doesn't seem to be a valid one!");
                return false;
            }
        }

        return true;
    }

    private User searchUser(int type) {
        if (type == 1) {
            while (userListTemp != null) {
                if (userName.equals(userListTemp.getName())) {
                    if (userCurrentLocation.equals(userListTemp.getCurrentLocation())) {
                        if (userBirthYear == userListTemp.getBirthYear()) {
                            return userListTemp;
                        }
                    }
                }
                userListTemp = userListTemp.next;
            }
        } else if (type == 2) {
            while (userListTemp != null) {
                if (userName.equals(userListTemp.getName())) {
                    return userListTemp;
                }
                userListTemp = userListTemp.next;
            }
        } else if (type == 3) {
            while (userListTemp != null) {
                if (userCurrentLocation.equals(userListTemp.getCurrentLocation())) {
                    return userListTemp;
                }
                userListTemp = userListTemp.next;
            }
        } else {
            while (userListTemp != null) {
                if (userBirthYear == userListTemp.getBirthYear()) {
                    return userListTemp;
                }
                userListTemp = userListTemp.next;
            }

        }

        return null;
    }

    void addUser() {
        getUserInfo();
        if (validateUserInfo(1)) {
            userListTemp = userList;
            if (searchUser(1) == null) {
                User user = new User(userName, userCurrentLocation, userBirthYear, userAge);
                if (userList == null) {
                    userList = user;
                    tail = user;

                    userList.next = null;
                    userList.previous = null;
                } else {
                    tail.next = user;
                    user.next = null;
                    user.previous = tail;
                    tail = user;
                }
                totalUser++;
            } else {
                System.out.println("User already exists!");
            }
        }

    }

    void showCredentials() {
        if (userPrimary != null) {
            System.out.println("Your account credentials: ");
            System.out.println("Name : " + userPrimary.getName() + " -> Location : " + userPrimary.getCurrentLocation() + " -> Birth year : " + userPrimary.getBirthYear());
        } else {
            System.out.println("You are not logged in");
        }
    }

    private boolean logIn() {
        System.out.println("Log In: ");
        getUserInfo();

        if (validateUserInfo(1)) {
            userListTemp = userList;
            userPrimary = searchUser(1);

            if (userPrimary != null) {
                System.out.println("You are logged in");
                return true;
            } else {
                System.out.println("No data found1");
            }
        }

        return false;
    }

    private boolean checkLogIn() {
        String choice_cli;

        if (userPrimary != null) {
            System.out.println("You are already logged in ");
            showCredentials();
            System.out.println("Would you like to log into another account (y/n) ");

            choice_cli = sc.nextLine();
            choice_cli = choice_cli.toLowerCase();

            //System.out.println("The choice string in checkLogIn: " + choice_cli + " Length: " + choice_cli.length());

            if (choice_cli.charAt(0) == 'y') {
                if (logOut()) {
                    System.out.println("You are successfully logged out");
                }
                if (logIn()) {
                    return true;
                }
            } else if (choice_cli.charAt(0) == 'n') {
                return true;
            }
        } else {
            if (logIn()) {
                return true;
            }
        }

        return false;
    }

    boolean logOut() {
        if (userPrimary != null) {
            userPrimary = null;
            return true;
        } else {
            System.out.println("You are not logged in!");
        }

        return false;
    }

    private boolean isUserPrimary(User user) {
        if (userPrimary.getName().equals(user.getName())) {
            if (userPrimary.getCurrentLocation().equals(user.getCurrentLocation())) {
                if (userPrimary.getBirthYear() == user.getBirthYear()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void initSearchResultsArray() {
        System.out.println("Search people: ");
        System.out.println("Search by 1-> Name || 2-> City/Town || 3 -> Birth year || 4-> all");

        searchResultsArray.clear();
        userListTemp = userList;

        String choice_isra = sc.nextLine();
        switch (choice_isra.charAt(0)) {
            case '1':
                System.out.println("Enter Name: ");
                userName = sc.nextLine();

                if (validateUserInfo(2)) {
                    while (userListTemp != null) {
                        User match = searchUser(2);
                        if (match != null) {
                            if (!isUserPrimary(match)) {
                                searchResultsArray.add(match);
                            }
                        }

                        if (userListTemp != null) {
                            userListTemp = userListTemp.next;
                        }
                    }
                }
                break;
            case '2':
                System.out.println("Enter City/Town name: ");
                userCurrentLocation = sc.nextLine();

                while (userListTemp != null) {
                    User match = searchUser(3);
                    if (match != null) {
                        if (!isUserPrimary(match)) {
                            searchResultsArray.add(match);
                        }
                    }

                    if (userListTemp != null) {
                        userListTemp = userListTemp.next;
                    }
                }

                break;
            case '3':
                System.out.println("Enter Birth year: ");
                userBirthYearString = sc.nextLine();

                System.out.println("User birth year: " + userBirthYear);

                if (validateUserInfo(3)) {
                    while (userListTemp != null) {
                        User match = searchUser(4);
                        if (match != null) {
                            if (!isUserPrimary(match)) {
                                searchResultsArray.add(match);
                            }
                        }

                        if (userListTemp != null) {
                            userListTemp = userListTemp.next;
                        }
                    }
                }
                break;
            case '4':
                System.out.println("Enter Name: ");
                userName = sc.nextLine();

                System.out.println("Enter City/Town name: ");
                userCurrentLocation = sc.nextLine();

                System.out.println("Enter Birth year: ");
                userBirthYearString = sc.nextLine();

                if (validateUserInfo(1)) {

                    User match = searchUser(1);
                    if (match != null) {
                        if (!isUserPrimary(match)) {
                            searchResultsArray.add(match);
                        }
                    }
                }
                break;
            default:
                System.out.println("Wrong choice!");
        }
    }

    private void showSearchResultsArray() {

        int i = 1;
        for (User user : searchResultsArray) {
            System.out.println("User " + i + " -> Name : " + user.getName() + " -> Location : " + user.getCurrentLocation() + " -> Birth year : " +
                    "" + user.getBirthYear() + " -> Age : " + user.getAge());
            i++;
        }

        System.out.println("");
    }

    private int getFriendSerial() {
        int serial = sc.nextInt();
        sc.nextLine();
        return serial - 1;
    }

    private void setUserSecondary(User user) {
        userSecondary = user;
    }

    private void addFriendBothSide() {

        if (userPrimary.addFriend(userSecondary.getName(), userSecondary.getCurrentLocation(), userSecondary.getBirthYear(), userSecondary.getAge())) {
            userSecondary.addFriend(userPrimary.getName(), userPrimary.getCurrentLocation(), userPrimary.getBirthYear(), userPrimary.getAge());
        } else {
            System.out.println("You both are already friends!");
        }

    }


    void initAddFriend() {

        if (checkLogIn()) {
            initSearchResultsArray();

            if (!searchResultsArray.isEmpty()) {
                showSearchResultsArray();

                System.out.println("Enter the serial number of the user you want to add to your friend list ");
                setUserSecondary(searchResultsArray.get(getFriendSerial()));
                addFriendBothSide();
                System.out.println("Friend successfully added!");

                System.out.println("Your friend list: ");
                userPrimary.showFriends();

            } else {
                System.out.println("No data found!");
            }


        }

    }

    private Friend searchFriendList(String name, String cLocation, int bYear) {
        Friend t = userSecondary.getFriends();

        while (t != null) {
            if (t.getBirthYear() == bYear) {
                if (t.getCurrentLocation().equals(cLocation)) {
                    if (t.getName().equals(name)) {
                        return t;
                    }
                }
            } else {
                //nothing happens
            }
            t = t.next;
        }


        return null;
    }

    private void getCommonFriends() {
        if (checkLogIn()) {
            System.out.println("Enter the other user information: ");
            getUserInfo();

            if (validateUserInfo(1)) {
                userListTemp = userList;
                User su = searchUser(1);

                if (su != null) {
                    commonFriends.clear();
                    setUserSecondary(su);

                    Friend temp_00 = userPrimary.getFriends();
                    Friend temp_01;

                    while (temp_00 != null) {
                        temp_01 = searchFriendList(temp_00.getName(), temp_00.getCurrentLocation(), temp_00.getBirthYear());

                        if (temp_01 != null) {
                            commonFriends.add(temp_01);
                        }

                        temp_00 = temp_00.next;
                    }
                } else {
                    System.out.println("User not found!");
                }
            }
        }
    }

    void showCommonFriends() {
        getCommonFriends();
        if (!commonFriends.isEmpty()) {
            System.out.println("Common friend between you and " + userSecondary.getName() + " are: ");
            int i = 1;
            for (Friend f : commonFriends) {

                System.out.println(i + " -> Name : " + f.getName() + " -> Location : " + f.getCurrentLocation() + " -> Birth year" +
                        " : " + f.getBirthYear() + " -> Age : " + f.getAge());
                i++;
            }
        } else {
            System.out.println("No common friends found!");
        }
    }

    void showUserFriendList() {
        if (checkLogIn()) {
            System.out.println("List of your friends: ");
            userPrimary.showFriends();
        }
    }

    private void removeUser(User user) {
        if (totalUser == 1) {
            userList = null;
            tail = null;
        } else if (totalUser > 1 && user == userList) {
            user.next.previous = user.previous;
            userList = userList.next;
        } else if (user == tail) {
            user.previous.next = user.next;
            tail = tail.previous;
        } else {
            user.next.previous = user.previous;
            user.previous.next = user.next;
        }

        totalUser--;
    }

    void initRemoveUser() {
        System.out.println("Enter details of user you'd like to remove ");
        getUserInfo();

        User userTobeDeleted;

        if (validateUserInfo(1)) {
            userListTemp = userList;
            userTobeDeleted = searchUser(1);

            if (userTobeDeleted != null) {

                userListTemp = userList;
                while (userListTemp != null) {
                    userListTemp.removeFriend(userTobeDeleted.getName(), userTobeDeleted.getCurrentLocation(), userTobeDeleted.getBirthYear());
                    userListTemp = userListTemp.next;
                }

                removeUser(userTobeDeleted);
                System.out.println("User has been successfully removed");
            } else {
                System.out.println("User not found!");
            }
        }
    }

    void getUserWithOldest_MaximumFriend() {
        if (userList != null) {
            int oldestFriendAge = 0;
            User userWithOldestFriend = null;
            Friend oldestFriend;
            Friend oldestFriendOfOldestUser = null;

            int maximumFriend = 0;
            User userWithMaximumFriend = null;


            userListTemp = userList;
            while (userListTemp != null) {
                oldestFriend = userListTemp.getOldestFriend();

                if (oldestFriend != null) {
                    //System.out.println("Oldest friend of " + userListTemp.getName() + " : " + oldestFriend.getName() + " age " + oldestFriend.getAge());
                    if (oldestFriend.getAge() > oldestFriendAge) {
                        userWithOldestFriend = userListTemp;
                        oldestFriendOfOldestUser = oldestFriend;
                        oldestFriendAge = oldestFriend.getAge();

                        //System.out.println("User with the oldest friend : " + userWithOldestFriend.getName() + " " + userWithOldestFriend.getCurrentLocation() + " " + userWithOldestFriend.getAge());
                    }
                }

                if (userListTemp.getTotalFriends() > maximumFriend) {
                    userWithMaximumFriend = userListTemp;
                    maximumFriend = userListTemp.getTotalFriends();
                }

                userListTemp = userListTemp.next;
            }

            if (userWithOldestFriend != null) {
                System.out.println("The user with oldest friend: ");
                System.out.println("User -> Name : " + userWithOldestFriend.getName() + " -> Location : " + userWithOldestFriend.getCurrentLocation() + " -> Birth year " +
                        ": " + userWithOldestFriend.getBirthYear() + " -> Age : " + userWithOldestFriend.getAge());

                System.out.println("Friend -> Name : " + oldestFriendOfOldestUser.getName() + " -> Location : " + oldestFriendOfOldestUser.getCurrentLocation() + " " +
                        "-> Birth year : " + oldestFriendOfOldestUser.getBirthYear() + " -> Age : " + oldestFriendOfOldestUser.getAge());
            } else {
                System.out.println("No friendship is constructed yet!");
            }

            if (maximumFriend != 0) {
                System.out.println("The user with Maximum friend: ");
                System.out.println("User -> Name : " + userWithMaximumFriend.getName() + " -> Location : " + userWithMaximumFriend.getCurrentLocation() + " -> " +
                        "Birth year : " + userWithMaximumFriend.getBirthYear() + " -> Age : " + userWithMaximumFriend.getAge());

                System.out.println("Number total friends: " + maximumFriend);
            } else {
                System.out.println("No friendship is constructed yet!");
            }
        } else {
            System.out.println("User list is empty!");
        }
    }

    void showUserList(int type) {
        User user = userList;
        int i = 1;
        if (user == null) {
            System.out.println("No data found!");
        }
        while (user != null) {
            System.out.println("User " + i + " -> Name : " + user.getName() + " -> Location : " + user.getCurrentLocation() + " -> Birth year" +
                    " : " + user.getBirthYear() + " -> Age : " + user.getAge());

            if (type == 1) {
                System.out.println("Friend List:--------------------------------------------------------------------------------------- ");
                user.showFriends();
                System.out.println("");
            }

            user = user.next;
            i++;
        }

        System.out.println("");
    }

    int getTotalUser() {
        return totalUser;
    }

}
