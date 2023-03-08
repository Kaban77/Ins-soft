package ru.demidov.insSoft.insurant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class InsurantRepository {

	private final JdbcTemplate jdbctemplate;

	private static final String SQL_SELECT = """
			select * from policies.v_insurant_info
			where upper(name) like '%' || :name || '%'
			and upper(patronymic) like '%' ||  :patronymic || '%'
			and upper(surname) like '%' ||  :surname || '%'
			and doc_type = 3
			""";
	private static final String SQL_GET_ID = "select policies.pkg_utilities.get_id('t_insurants') from dual";
	private static final String SQL_INSERT = """
			insert into policies.t_insurants (insurant_id, name, patronymic, surname, birth_date, sex)
			values (:insurantId, :name, :patronymic, :surname, to_date(:birthDate, 'DD.MM.YYYY'), :sex)
			""";

	public InsurantRepository(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	public Integer getId() {
		return jdbctemplate.queryForObject(SQL_GET_ID, Integer.class);
	}

	public List<Insurant> findInsurants(Insurant insurant) {
		var sql = new StringBuilder(SQL_SELECT);
		var args = new ArrayList<String>();

		args.add(insurant.getName().toUpperCase().trim());
		args.add(insurant.getPatronymic().toUpperCase().trim());
		args.add(insurant.getSurname().toUpperCase().trim());

		if (tryParseDate(insurant.getBirthDate()) != false) {
			sql.append(" and birth_date = to_date(:birthDate, 'DD.MM.YYYY')");
			args.add(insurant.getBirthDate());
		}
		
		return jdbctemplate.query(SQL_SELECT, Insurant::new, args.toArray());
	}

	public void insert(Insurant insurant) {
		jdbctemplate.update(SQL_INSERT, new Object[] { insurant.getId(), insurant.getName(), insurant.getPatronymic(),
				insurant.getSurname(), insurant.getBirthDate(), insurant.getSex() });
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
}
