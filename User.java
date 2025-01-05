/** Represents a user in a social network. A user is characterized by a name,
 *  a list of user names that s/he follows, and the list's size. */
public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; // fixed-size array for storing followees
        fCount = 0;                      // initial number of followees
    }

    /** Creates a user with some followees. The only purpose of this constructor is 
     *  to allow testing the toString and follows methods, before implementing other methods. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /** Returns the name of this user. */
    public String getName() {
        return name;
    }

    /** Returns the follows array. */
    public String[] getfFollows() {
        return follows;
    }

    /** Returns the number of users that this user follows. */
    public int getfCount() {
        return fCount;
    }

    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        for (int i = 0; i < this.fCount; i ++) {
            if (this.follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }
    /** Makes this user follow the given name. If successful, returns true.
     *  If this user already follows the given name, or if the follows list is full, does nothing and returns false; */
    public boolean addFollowee(String name) {

        if (this.fCount >= maxfCount) {return false;}

        boolean nameExists = false;
        for (int i = 0; i < this.fCount; i++) {
            if (this.follows[i].equals(name)) {
                nameExists = true;
                break;
            }
        }

        if (this.fCount < 10 && !nameExists) {
            follows[this.fCount] = name;
            System.out.println("\n...Adding " + name + " to the follows list of " + this.name + "...");
            this.fCount++;
            return true;
        }
        System.out.println("could not add " + name);
        return false;
    }

    /** Removes the given name from the follows list of this user. If successful, returns true.
     *  If the name is not in the list, does nothing and returns false. */
    public boolean removeFollowee(String name) {

        boolean nameExists = false;
        int possition = 0;
        for (int i = 0; i < this.fCount; i++) {
            if (this.follows[i].equals(name)) {
                nameExists = true;
                possition = i;
                fCount--;
                break;
            }
        }

        if (!nameExists) {return false;}

        this.follows[possition] = null;
        for (int i = 0; i < maxfCount - possition; i ++) {
            this.follows[possition] = this.follows[possition + 1];
            possition++;
        }

        for (int i = 0; i < maxfCount - this.fCount; i ++) {
            this.follows[maxfCount - i - 1] = null;
        }
        return true;
    }

    /** Counts the number of users that both this user and the other user follow.
     /*  Notice: This is the size of the intersection of the two follows lists. */
    public int countMutual(User other) {
        if (this.fCount == 0 || other.fCount == 0) {
            return 0;
        }
        int countMutual = 0;

        for (int i = 0; i < this.fCount; i++) {
            for (int j = 0; j < other.fCount; j++) {
                if (this.follows[i].equals(other.follows[j])) {
                    countMutual++;
                    break;
                }
            }
        }
        return countMutual;
    }

    /** Checks is this user is a friend of the other user.
     *  (if two users follow each other, they are said to be "friends.") */
    public boolean isFriendOf(User other) {

        boolean followA = false;
        boolean followB = false;

        for (int i = 0; i < this.fCount; i++) {
            if (this.follows[i] == other.name) {
                followA = true;
                break;
            }
        }
        for (int i = 0; i < other.fCount; i++) {
            if (other.follows[i] == this.name) {
                followB = true;
                break;
            }
        }
        if (followA && followB) {return  true;}
        else {return false;}
    }
    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}
