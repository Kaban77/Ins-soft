package ru.demidov.insSoft.interfaces;

import java.util.List;

import ru.demidov.insSoft.objects.Document;
import ru.demidov.insSoft.objects.Insurant;

public interface InsurantDao {

	Integer createInsurant(Insurant insurant);

	Integer createDocument(Document document, int insurantId);

	List<Insurant> findInsurants(Insurant insurant);

}
