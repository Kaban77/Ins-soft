package ru.demidov.insSoft.insurant;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.demidov.insSoft.documents.Document;
import ru.demidov.insSoft.documents.DocumentRepository;

@Component
public class InsurantManager {

	private final DocumentRepository documentRepository;
	private final InsurantRepository insurantRepository;

	public InsurantManager(DocumentRepository documentRepository, InsurantRepository insurantRepository) {
		this.documentRepository = documentRepository;
		this.insurantRepository = insurantRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createDocument(Document document, int insurantId) {
		documentRepository.update(insurantId, document.getDocType());
		document.setId(documentRepository.getDocumentId());
		documentRepository.insert(document, insurantId);

		return document.getId();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer createInsurant(Insurant insurant) {
		insurant.setId(insurantRepository.getId());
		insurantRepository.insert(insurant);
		createDocument(insurant.getDocument(), insurant.getId());

		return insurant.getId();
	}

	@Transactional(readOnly = true)
	public List<Insurant> findInsurants(Insurant insurant) {
		return insurantRepository.findInsurants(insurant);
	}
}
