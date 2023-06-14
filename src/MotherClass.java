import java.util.Scanner;

/**
 * Created by Himel Mazumder on 12/19/2018.
 */
public class MotherClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ApplicationClass ac = new ApplicationClass();

        String choice_m;

        do {
            System.out.println("1 -> Add User || 2 -> Display User List || 3 -> Add Friend || 4 -> Remove User || 5 -> Statistics || 6 -> Show Account Credentials || 7 -> Show User Friend List");
            System.out.println("8 -> Common friends || 9 -> Log out || 0 -> Quit");
            choice_m = sc.nextLine();
            //System.out.println("The choice string in checkLogIn: " + choice_m + " Length: " + choice_m.length());

            switch (choice_m.charAt(0)) {
                case '1':
                    ac.addUser();
                    break;
                case '2':
                    System.out.println("1-> Without friend list || 2-> With friend list");
                    int showFL = sc.nextInt();
                    sc.nextLine();

                    if(showFL == 1) {
                        ac.showUserList(0);
                    } else {
                        ac.showUserList(1);
                    }
                    break;
                case '3':
                    ac.initAddFriend();
                    break;
                case '4':
                    ac.initRemoveUser();
                    break;
                case '5':
                    System.out.println("Total user in the friend list is " + ac.getTotalUser());
                    ac.getUserWithOldest_MaximumFriend();
                    break;
                case '6':
                    ac.showCredentials();
                    break;
                case '7':
                    ac.showUserFriendList();
                    break;
                case '8':
                    ac.showCommonFriends();
                    break;
                case '9':
                    if(ac.logOut()) {
                        System.out.println("You are successfully logged out ");
                    }
                    break;
                case '0':
                    choice_m = "Q";
                    System.out.println("programme terminated");
                    break;
                default:
                    System.out.println("Wrong choice!");


            }
        } while (choice_m.charAt(0) != 'Q');
    }
}
