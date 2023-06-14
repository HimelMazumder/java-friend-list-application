/**
 * Created by Himel Mazumder on 12/19/2018.
 */
class User {
    private String name;
    private String currentLocation;
    private int birthYear;
    private int age;

    private int totalFriends;

    private Friend friends;
    private Friend tailFriends;

    User next;
    User previous;

    User(String name, String currentLocation, int birthYear, int age) {
        this.name = name;
        this.currentLocation = currentLocation;
        this.birthYear = birthYear;
        this.age = age;

        friends = null;
        tailFriends = null;

        next = null;
        previous = null;

        totalFriends = 0;
    }

    String getName() {
        return name;
    }

    String getCurrentLocation() {
        return currentLocation;
    }

    int getBirthYear() {
        return birthYear;
    }

    int getAge() {
        return age;
    }

    public Friend getFriends() {
        return friends;
    }

    private Friend searchFriend(String name, String currentLocation, int birthYear) {
        Friend friendsTemp = friends;
        while (friendsTemp != null) {
            if (friendsTemp.getName().equals(name)) {
                if (friendsTemp.getCurrentLocation().equals(currentLocation)) {
                    if (friendsTemp.getBirthYear() == birthYear) {
                        return friendsTemp;
                    }
                }
            }
            friendsTemp = friendsTemp.next;
        }

        return null;
    }

    boolean addFriend(String name, String currentLocation, int birthYear, int age) {
        Friend friendTemp = searchFriend(name, currentLocation, birthYear);
        if(friendTemp == null) {
            Friend friend = new Friend(name, currentLocation, birthYear, age);

            if (friends == null) {
                friends = friend;
                friends.next = null;
                friends.previous = null;

                tailFriends = friends;
            } else {
                tailFriends.next = friend;
                friend.next = null;
                friend.previous = tailFriends;
                tailFriends = friend;
            }

            totalFriends++;
            return true;

        } else {
            return false;
        }
    }

    void removeFriend(String name, String currentLocation, int birthYear) {
        Friend friendTemp = searchFriend(name, currentLocation, birthYear);

        if (friendTemp != null) {
            if (totalFriends == 1 && friendTemp == friends) {
                friends = null;
                tailFriends = null;
            } else if (totalFriends > 1 && friendTemp == friends) {
                friendTemp.next.previous = friendTemp.previous;
                friends = friends.next;
            } else if (friendTemp == tailFriends) {
                friendTemp.previous.next = friendTemp.next;
                tailFriends = tailFriends.previous;
            } else {
                friendTemp.next.previous = friendTemp.previous;
                friendTemp.previous.next = friendTemp.next;
            }

            totalFriends--;
        }
    }

    Friend getOldestFriend() {
        Friend oldestFriend = null;
        int oldestFriendAge = 0;

        Friend friendTemp = friends;
        while (friendTemp != null) {
            if(friendTemp.getAge() > oldestFriendAge) {
                oldestFriend = friendTemp;
                oldestFriendAge = friendTemp.getAge();
            }

            friendTemp = friendTemp.next;
        }

        return oldestFriend;
    }

    void showFriends() {
        Friend friend = friends;
        int i = 1;

        if (friend == null) {
            System.out.println("No friends found!");
        }

        while (friend != null) {
            System.out.println("friend " + i + " -> Name : " + friend.getName() + " -> Location : " + friend.getCurrentLocation() + " -> Birth year : " + friend.getBirthYear() + " -> Age : " + friend.getAge());
            i++;
            friend = friend.next;
        }
    }

    int getTotalFriends() {
        return totalFriends;
    }


}
