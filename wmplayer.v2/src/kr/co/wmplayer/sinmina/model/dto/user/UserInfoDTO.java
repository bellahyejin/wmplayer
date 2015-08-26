package kr.co.wmplayer.sinmina.model.dto.user;

import kr.co.wmplayer.sinmina.interfaces.WMPlayerDTO;

public class UserInfoDTO implements WMPlayerDTO {
	private String userID;
	private String passwd;
	private String name;
	private String birth;
	private String gender;
	private String email;
	private String status;
	
	public UserInfoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UserInfoDTO(String userID, String passwd) {
		this.userID = userID;
		this.passwd = passwd;
	}

	public UserInfoDTO(String userID, String name, String gender)
	{
		this.userID = userID;
		this.name = name;
		this.gender = gender;

	}

	public UserInfoDTO(String userID, String passwd, String name, String birth,
			String gender, String email, String status) {
		super();
		this.userID = userID;
		this.passwd = passwd;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.email = email;
		this.status = status;
	}
	
	@Override
	public String toString()
	{
		return "UserInfoDTO [userID=" + userID + ", passwd=" + passwd + ", name=" + name + ", birth=" + birth + ", gender=" + gender + ", email=" + email + ", status=" + status + "]";
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
