package anyeight.model;

/**
 * Created by 啊 on 2017/5/25.
 */
public class UserInfo {
    private String user_id;            //用户ID
    private String userName;           //用户名字
    private String telephone;           //用户电话
    private String email;               //用户邮箱
    private String graph;              //用户头像(相对地址
    public UserInfo(){

    }
    public UserInfo(String user_id,String userName,String telephone,String email,String graph){
        this.user_id=user_id;
        this.userName=userName;
        this.telephone=telephone;
        this.email=email;
        this.graph=graph;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }





    public String getUser_idId() {
        return user_id;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getGraph() {
        return graph;
    }

    public String getUserName() {
        return userName;
    }
}
