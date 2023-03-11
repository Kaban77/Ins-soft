package ru.demidov.insSoft.documents;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DocumentRepository {

	private final JdbcTemplate jdbctemplate;

	private static final String SQL_UPDATE = "update policies.t_insurant_docs set is_actual = 'N' where insurant_id = :insurantId and doc_type = :docType";
	private static final String SQL_GET_DOC_ID = "select policies.pkg_utilities.get_id('t_insurant_docs') from dual";
	private static final String SQL_INSERT = """
			insert into policies.t_insurant_docs (doc_id, insurant_id, doc_type, doc_serial, doc_number, date_of_issue, is_actual)
			values (:docId, :insurantId, :docType, :serial, :numb, to_date(:dateOfIssue, 'DD.MM.YYYY'), 'Y')
			""";

	public DocumentRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public Integer getDocumentId() {
		return jdbctemplate.queryForObject(SQL_GET_DOC_ID, Integer.class);
	}

	public void insert(Document document, int insurantId) {
		jdbctemplate.update(SQL_INSERT, document.getId(), insurantId, document.getDocType(), document.getSerial(), document.getNumber(),
				document.getDateOfIssue());
	}

	public void update(int insurantId, int docType) {
		jdbctemplate.update(SQL_UPDATE, insurantId, docType);
	}
}
