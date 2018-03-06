package ru.demidov.insSoft.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.demidov.insSoft.interfaces.InsertDao;
import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Document;
import ru.demidov.insSoft.objects.Insurant;
import ru.demidov.insSoft.objects.PolicyToDB;

public class InsertDaoImpl implements InsertDao {

	private JdbcTemplate jdbctemplate;

	private static final Logger logger = LoggerFactory.getLogger(InsertDaoImpl.class);

	public InsertDaoImpl() {
	}

	public InsertDaoImpl(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}

	@Override
	public Integer createInsurant(Insurant insurant) {

		String selectId = "select policies.pkg_utilities.get_id('t_insurants') from dual";
		String insertInsurant = "insert into policies.t_insurants (insurant_id, name, patronymic, surname, birth_date, sex) values (:insurantId, :name, :patronymic, :surname, to_date(:birthDate, 'DD.MM.YYYY'), :sex)";
		int insurantId;

		try {
			insurantId = jdbctemplate.queryForObject(selectId, Integer.class);
			jdbctemplate.update(insertInsurant, new Object[] { insurantId, insurant.getName(), insurant.getPatronymic(), insurant.getSurname(), insurant.getBirthDate(), insurant.getSex() });

		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}

		if (createDocument(insurant.getDocument(), insurantId) != null)
			return insurantId;
		else
			return null;
	}

	@Override
	public Integer createDocument(Document document, int insurantId) {

		String selectDocId = "select policies.pkg_utilities.get_id('t_insurant_docs') from dual";
		String updateOldDoc = "update policies.t_insurant_docs set is_actual = 'N' where insurant_id = :insurantId and doc_type = :docType";
		String insertNewDoc = "insert into policies.t_insurant_docs (doc_id, insurant_id, doc_type, doc_serial, doc_number, date_of_issue, is_actual) values (:docId, :insurantId, :docType, :serial, :numb, to_date(:dateOfIssue, 'DD.MM.YYYY'), 'Y')";

		int documentId;

		jdbctemplate.update(updateOldDoc, new Object[] { insurantId, document.getDocType() });

		try {
			documentId = jdbctemplate.queryForObject(selectDocId, Integer.class);
			jdbctemplate.update(insertNewDoc, new Object[] { documentId, insurantId, document.getDocType(), document.getSerial(), document.getNumber(), document.getDateOfIssue() });

			return documentId;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Coefficients insertPolicy(PolicyToDB policy) {

		String selectPolicyId = "select policies.pkg_utilities.get_id('t_policy') from dual";
		String insertPolicy = "insert into policies.t_policy (policy_id, policy_number, begin_date, end_date, insurant_id, state, premium) values (:policyId, :policyNo, sysdate, trunc(sysdate) + 364, :insurantId, 0, :premium)";
		String insertCar = "insert into policies.t_policy_car (policy_id, model_id, year_of_issue, vin, register_sign, engine_power) values (:policyId, (select cd.model_id from policies.t_car_directory cd where cd.brand_id = :brandId and cd.model_name = :modelName), :year, :vin, :sign, :power)";
		String insertCoeff = "insert into policies.t_policy_coefficients (policy_id, tariff, bonus, power, season, age_and_experience, period, driver_limit, territory) values(:policyId, :tariff, :bonus, :power, :season,:ageExperience, :period,:driver_limit, :territory)";

		int policyId;
		Coefficients coeff = new CalcCoefficients(jdbctemplate).calcPremium(policy);

		try {
			policyId = jdbctemplate.queryForObject(selectPolicyId, Integer.class);
			coeff.setPolicyId(policyId);
			jdbctemplate.update(insertPolicy, new Object[] { policyId, "EEE" + policyId, policy.getInsurantId(), coeff.getPremium() });
			jdbctemplate.update(insertCar,
					new Object[] { policyId, policy.getBrandId(), policy.getModelName(), policy.getYearOfIssue(), policy.getVin(), policy.getRegisterSign(), policy.getEnginePower() });

			jdbctemplate.update(insertCoeff, new Object[] { policyId, coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(), coeff.getAgeAndExperience(), coeff.getPeriod(),
					coeff.getDriverLimit(), coeff.getTerritory() });

			return coeff;

		} catch (Exception e) {
			logger.info(e.getMessage());
			return new Coefficients();
		}

	}

	@Override
	public Coefficients updatePolicy(PolicyToDB policy) {

		try {
			String updateCar = "update policies.t_policy_car pc set model_id = (select cd.model_id from policies.t_car_directory cd where cd.brand_id = :brandId and cd.model_name = :modelName), pc.year_of_issue = :year, pc.vin = :vin, pc.register_sign = :sign, pc.engine_power = :power where pc.policy_id = :policyId";
			String updateCoeff = "update policies.t_policy_coefficients set tariff = :tariff, bonus = :bonus, power = :power, season = :season, age_and_experience = :ex, period = :period, driver_limit = :limit, territory = :territory where policy_id = :policyId";

			if (policy.getInsurantId() != null) {
				String updateInsurant = "update policies.t_policy set insurant_id = :insurantId where policy_id = :policyId";
				jdbctemplate.update(updateInsurant, new Object[] { policy.getInsurantId(), policy.getPolicyId() });
			} else {
				String selectInsurantId = "select insurant_id from t_policy where insurant_id = :insurantId";
				int insurantId = jdbctemplate.queryForObject(selectInsurantId, Integer.class);

				policy.setInsurantId(insurantId);
			}

			if (policy.getBrandId() == null) {
				String selectBrandId = "select cd.brand_id from policies.t_car_directory cd join policies.t_policy_car pc on cd.model_id = pc.model_id where pc.policy_id = :policyId";
				int brandId = jdbctemplate.queryForObject(selectBrandId, Integer.class);
				policy.setBrandId(brandId);
			}

			jdbctemplate.update(updateCar,
					new Object[] { policy.getBrandId(), policy.getModelName(), policy.getYearOfIssue(), policy.getVin(), policy.getRegisterSign(), policy.getEnginePower(), policy.getPolicyId() });

			Coefficients coeff = new CalcCoefficients(jdbctemplate).calcPremium(policy);
			coeff.setPolicyId(policy.getPolicyId());

			jdbctemplate.update(updateCoeff, new Object[] { coeff.getTariff(), coeff.getBonus(), coeff.getPower(), coeff.getSeason(), coeff.getAgeAndExperience(), coeff.getPeriod(),
					coeff.getDriverLimit(), coeff.getTerritory(), coeff.getPolicyId() });

			return coeff;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new Coefficients();
		}
	}
}
