package ru.demidov.insSoft.interfaces;

import ru.demidov.insSoft.objects.Coefficients;
import ru.demidov.insSoft.objects.Document;
import ru.demidov.insSoft.objects.Insurant;
import ru.demidov.insSoft.objects.PolicyToDB;

public interface InsertDao {

	Integer createInsurant(Insurant insurant);

	Integer createDocument(Document document, int insurantId);

	Coefficients insertPolicy(PolicyToDB policy);

	Coefficients updatePolicy(PolicyToDB policy);

}
