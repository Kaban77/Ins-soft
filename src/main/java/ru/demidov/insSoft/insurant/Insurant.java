package ru.demidov.insSoft.insurant;

import java.sql.ResultSet;
import java.sql.SQLException;

import ru.demidov.insSoft.documents.Document;

public class Insurant {
	private Integer id;
	private String surname;
	private String name;
	private String patronymic;
	private String birthDate;
	private String sex;
	private Document document;

	public Insurant() {
	}

	public Insurant(String surname, String name, String patronymic, String birthDate) {
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
		this.birthDate = birthDate;
	}

	public Insurant(ResultSet rs, int rowNum) throws SQLException {
		var document = new Document();
		document.setSerial(rs.getString("doc_serial"));
		document.setNumber(rs.getString("doc_number"));

		setDocument(document);
		setId(rs.getInt("insurant_id"));
		setName(rs.getString("name"));
		setPatronymic(rs.getString("patronymic"));
		setSurname(rs.getString("surname"));
		setBirthDate(rs.getDate("birth_date").toString());
		setSex(rs.getString("sex"));
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
