/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < this.users.length; i ++) {
            if (this.users[i] != null && this.users[i].getName().equals(User.formatName(name))) {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
     *  If ths network is full, does nothing and returns false;
     *  If the given name is already a user in this network, does nothing and returns false;
     *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {

        if (this.userCount >= users.length) {return false;}

        for (int i = 0; i < this.userCount; i++) {
            if (this.users[i].getName().equals(name)) {
                return false;
            }
        }
        users[this.userCount] = new User(name);
        userCount ++;
        return true;
    }


    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {

        User user1= getUser(name1);
        User user2= getUser(name2);

        if (user1 == null || user2 == null || user1.equals(user2)) {
            return false;
        }
        return (user1.addFollowee(name2)); // both executing the add and returns a boolean that tells if secceeded to add.
    }

    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User mostRecommendedUserToFollow = null;
        int maxMutual = 0;
        User user1 = getUser(name);

        if (user1 == null) {return null;}

        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getName().equals(name)) continue;

            if (user1 != null && users[i] != null) {
                int mutual = (user1.countMutual(users[i]));
                if (mutual > maxMutual) {
                    maxMutual = mutual;
                    mostRecommendedUserToFollow = users[i];
                }
            }
        }
        if (mostRecommendedUserToFollow != null) {
            return mostRecommendedUserToFollow.getName();
        }
        else {return null;}
    }

    /** Computes and returns the name of the most popular user in this network:
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        int maxFollowers = 0;
        User mostPopular = null;

        for (int i = 0; i < userCount; i++) {
            int currentFollowers = followeeCount(users[i].getName());
            if (currentFollowers > maxFollowers) {
                maxFollowers = currentFollowers;
                mostPopular = users[i];
            }
        }
        return mostPopular!= null ? mostPopular.getName() : null;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int counter = 0;

        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].follows(name)) {
                counter++;
            }
        }
        return counter;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String networktoString = "Network:";
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null) {
                networktoString += "\n";
                networktoString += users[i].toString();
            }
        }
        return networktoString;
    }
}
