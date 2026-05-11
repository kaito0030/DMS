package dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private String userName;
    private String realName;
    private String password;
    private boolean isAdmin;
    
    public UserDTO(String userName, String realName, String password,boolean isAdmin) {
        this.userName = userName;
        this.realName = realName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public String getRealName() {
        return realName;
    }

    public String getPassword() {
        return password;
    }
    public boolean getAdmin() {
    		return isAdmin;
    }
}