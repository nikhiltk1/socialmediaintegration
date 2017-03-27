package nikhil.com.facebooksdk.app;

/**
 * Created by nikhil on 24/9/16.
 */
public class Profile {

    String name;
    String email;
    String fbId;

    public Profile(String name, String email, String fbId) {
        this.name = name;
        this.email = email;
        this.fbId = fbId;
    }

    public String getFbId() {

        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public Profile() {
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
