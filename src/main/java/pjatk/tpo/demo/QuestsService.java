package pjatk.tpo.demo;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestsService {

    public final QuestsRepository questsRepository;

    public QuestsService(QuestsRepository questsRepository) {
        this.questsRepository = questsRepository;
    }

    public Quests addQuest(Quests quests) {
        return this.questsRepository.save(quests);
    }

    public List<Quests> getQuests(String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        return questsRepository.findAll(sort);
    }

    public void deleteQuestById(Long id) {
        questsRepository.deleteById(id);


    }

    public Quests updateQuest(Long id, Quests updatedQuest) {
        return questsRepository.findById(id).map(quest -> {
            quest.setName(updatedQuest.getName());
            quest.setDescription(updatedQuest.getDescription());
            quest.setStartDate(updatedQuest.getStartDate());
            quest.setEndDate(updatedQuest.getEndDate());
            quest.setCompleted(updatedQuest.isCompleted());
            return questsRepository.save(quest);
        }).orElseThrow(() -> new RuntimeException("Nie znaleziono zadania o ID: " + id));
    }
}

