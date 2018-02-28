package ru.demidov.insSoft.objects;

public class Insurant {
	private int id;
	private String surname;
	private String name;
	private String patronymic;
	private String birthDate;
	private String sex;

	public Insurant() {
	}

	public Insurant(String surname, String name, String patronymic, String birthDate) {
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.birthDate = birthDate;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
