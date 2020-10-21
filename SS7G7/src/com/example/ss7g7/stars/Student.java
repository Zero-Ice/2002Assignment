package hihi;


import java.util.Calendar;


public class Student{
	
	private String username;
	private String password;
	private String firstName; 
	private String lastName;
	private String matricNo;
	private String gender;
	private String nationality; 
	private int mobileNo;
	private String email;
	private Calendar accessStart;
	private Calendar accessEnd;
	
	public Student (String username, String password,String firstName, String lastName, String matricNo, String gender,
					String nationality, int mobileNo, String email, Calendar accessStart, Calendar accessEnd) 
	{
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.matricNo	= matricNo;
		this.gender	= gender;
		this.nationality = nationality;
		this.mobileNo = mobileNo;
		this.email = email;
		this.accessStart = accessStart;
		this.accessEnd = accessEnd;
	}

	public String getUserName() {
		return username;
	}

	public void getUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void getPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String matricNo() {
		return matricNo;
	}

	public void matricNo(String matricNo) {
		this.matricNo = matricNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public int getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(int mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getAccessStart() {
		return accessStart;
	}

	public void setAccessStart(Calendar accessStart) {
		this.accessStart = accessStart;
	}

	public Calendar getAccessEnd() {
		return accessEnd;
	}

	public void setAccessEnd(Calendar accessEnd) {
		this.accessEnd = accessEnd;
	}
}
