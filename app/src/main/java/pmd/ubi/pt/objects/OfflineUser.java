package pmd.ubi.pt.objects;

/**
 * Created by Piotr on 27-Dec-16.
 */

public class OfflineUser
{
    private long userId;
    private String userName;
    private int userStatus;

    public OfflineUser()
    {

    }

    public OfflineUser(long _userId, String _userName, int _userStatus)
    {
        this.userId = _userId;
        this.userName = _userName;
        this.userStatus = _userStatus;
    }

    //Getters

    public long getUserId()
    {
        return this.userId;
    }
    public String getUserName()
    {
        return this.userName;
    }
    public int getUserStatus()
    {
        return this.userStatus;
    }

    //Setters

    public void setUserId(long _id)
    {
        this.userId = _id;
    }
    public void setUserName(String _userName)
    {
        this.userName = _userName;
    }
    public void setUserStatus(int _status)
    {
        this.userStatus = _status;
    }

    public String toString()
    {
        return "Username: " + userName + "\nStatus: " + userStatus;
    }
}
