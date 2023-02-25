package ru.demidov.insSoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.demidov.insSoft.objects.Document;
import ru.demidov.insSoft.objects.Insurant;

@Component
public class InsurantDaoImpl {

	@Autowired
	private JdbcTemplate jdbctemplate;

	private static final String getInsurantId = "select policies.pkg_utilities.get_id('t_insurants') from dual";
	private static final String insertInsurant = "insert into policies.t_insurants (insurant_id, name, patronymic, surname, birth_date, sex) values (:insurantId, :name, :patronymic, :surname, to_date(:birthDate, 'DD.MM.YYYY'), :sex)";
	private static final String getDocId = "select policies.pkg_utilities.get_id('t_insurant_docs') from dual";
	private static final String updateOldDoc = "update policies.t_insurant_docs set is_actual = 'N' where insurant_id = :insurantId and doc_type = :docType";
	private static final String insertNewDoc = "insert into policies.t_insurant_docs (doc_id, insurant_id, doc_type, doc_serial, doc_number, date_of_issue, is_actual) values (:docId, :insurantId, :docType, :serial, :numb, to_date(:dateOfIssue, 'DD.MM.YYYY'), 'Y')";

	private static final Logger logger = LoggerFactory.getLogger(InsurantDaoImpl.class);

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createDocument(Document document, int insurantId) {
		try {
			jdbctemplate.update(updateOldDoc, new Object[] { insurantId, document.getDocType() });
			int documentId = jdbctemplate.queryForObject(getDocId, Integer.class);
			jdbctemplate.update(insertNewDoc, new Object[] { documentId, insurantId, document.getDocType(), document.getSerial(), document.getNumber(), document.getDateOfIssue() });

			return documentId;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createInsurant(Insurant insurant) {
		try {
			int insurantId = jdbctemplate.queryForObject(getInsurantId, Integer.class);
			jdbctemplate.update(insertInsurant, new Object[] { insurantId, insurant.getName(), insurant.getPatronymic(), insurant.getSurname(), insurant.getBirthDate(), insurant.getSex() });
			if (createDocument(insurant.getDocument(), insurantId) == null)
				throw new Exception("Invalid docs");

			return insurantId;
		} catch (Exception e) {
			logger.info(" createInsurant " + e.getMessage());
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<Insurant> findInsurants(Insurant insurant) {
		String sql = "select * from policies.v_insurant_info where upper(name) like :name and upper(patronymic) like :patronymic and upper(surname) like :surname and doc_type = 3";
		ArrayList<String> args = new ArrayList<String>();

		args.add("%" + insurant.getName().toUpperCase().trim() + "%");
		args.add("%" + insurant.getPatronymic().toUpperCase().trim() + "%");
		args.add("%" + insurant.getSurname().toUpperCase().trim() + "%");

		if (tryParseDate(insurant.getBirthDate()) != false) {
			sql += " and birth_date = to_date(:birthDate, 'DD.MM.YYYY')";
			args.add(insurant.getBirthDate());
		}

		try {
			return jdbctemplate.query(sql, args.toArray(), new InsurantRowMapper());

		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ArrayList<Insurant>();
		}
	}

	private boolean tryParseDate(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			format.parse(date);
			return true;
		} catch (ParseException pe) {
			return false;
		}
	}

	private static final class InsurantRowMapper implements RowMapper<Insurant> {

		@Override
		public Insurant mapRow(ResultSet rs, int rowNum) throws SQLException {
			Document document = new Document();
			document.setSerial(rs.getString("doc_serial"));
			document.setNumber(rs.getString("doc_number"));

			Insurant insurant = new Insurant();

			insurant.setDocument(document);
			insurant.setId(rs.getInt("insurant_id"));
			insurant.setName(rs.getString("name"));
			insurant.setPatronymic(rs.getString("patronymic"));
			insurant.setSurname(rs.getString("surname"));
			insurant.setBirthDate(rs.getDate("birth_date").toString());
			insurant.setSex(rs.getString("sex"));
			return insurant;
		}

	}
}
